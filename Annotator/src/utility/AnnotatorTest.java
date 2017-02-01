package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import core.AnnotationStrategy;
import core.OpenNlpAnnotator;
import opennlp.ItalianTokenizer;
import opennlp.tools.namefind.TokenNameFinderModel;

public class AnnotatorTest
{

	public static final int OPEN_NLP_ANNOTATOR = 0;
	public static final int PATTERN_RESEARCH_ANNOTATOR = 1;

	private HashMap<String, List<String>> testCase;
	private HashMap<String, String[]> annotationResults;
	private List<String> data;

	private int applicationTipe;

	public AnnotatorTest(int applicationTipe)
	{
		this.data = new ArrayList<String>();
		this.testCase = new HashMap<String, List<String>>();
		this.annotationResults = new HashMap<String, String[]>();
		this.applicationTipe = applicationTipe;
	}

	public String processTestCase(String line)
	{
		String[] tokens = ItalianTokenizer.getInstance().tokenize(line);
		HashMap<Pair<Integer, Integer>, String> elementsTypes = new HashMap<Pair<Integer, Integer>, String>();
		int founds = 0;
		for (int i = 0; i < tokens.length; i++)
		{
			if (tokens[i].equals("opera>") || tokens[i].equals("artist>"))
			{
				int length = 1;
				int start = (i + 1) - (founds * 4) - 3;
				for (int j = i + 2; j < tokens.length; j++)
				{
					if (!tokens[j].equals("<END>"))
						length++;
					else
						break;
				}
				elementsTypes.put(new Pair<Integer, Integer>(start, start + length), tokens[i].replaceAll(">", ""));
				founds += 1;
			}
		}

		String returnValue = line;
		if (line.contains("START:artist"))
		{
			returnValue = returnValue.replaceAll("<START:artist> ", "");
		}
		if (line.contains("START:opera"))
		{
			returnValue = returnValue.replaceAll("<START:opera> ", "");
		}

		returnValue = returnValue.replaceAll("<END> ", "");

		returnValue = returnValue + "::";

		for (Entry<Pair<Integer, Integer>, String> entry : elementsTypes.entrySet())
		{
			returnValue = returnValue + ",[" + entry.getKey().getFirstElement() + ".." + entry.getKey().getSecondElement() + ") " + entry.getValue();
		}

		return returnValue.replaceAll("::,", "::");
	}

	public void generateTestCase(File model)
	{
		try
		{
			int index = 0;
			FileReader fr = new FileReader(model);
			BufferedReader br = new BufferedReader(fr);
			PrintWriter pw = new PrintWriter(new File("src/models/testCase2.txt"));
			String line = br.readLine();
			while (line != null)
			{
				index++;
				System.out.println("Processing line " + index);
				String testCase = processTestCase(line);
				pw.println(testCase);
				line = br.readLine();
			}
			br.close();
			pw.close();
			fr.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public void readTestCase(File template)
	{
		try
		{
			FileReader fr = new FileReader(template);
			BufferedReader br = new BufferedReader(fr);
			String s = br.readLine();
			while (s != null)
			{
				String[] testCase = s.split("::");
				List<String> results = new ArrayList<String>();
				String[] allRes = testCase[1].split(",");
				for (String s2 : allRes)
				{
					results.add(s2);
				}
				this.testCase.put(testCase[0], results);
				this.data.add(testCase[0]);
				s = br.readLine();
			}
			br.close();
			fr.close();
		} catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "Errore nella lettura dei test case");
		}
	}

	public void compute()
	{
		try
		{
			MySQLResourcesAcquisition rs = new MySQLResourcesAcquisition();

			Connection connection = rs.getConnection();

			rs.getData().addAll(rs.getDataFromDb(connection, "artist", "name", ""));
			rs.getData().addAll(rs.getDataFromDb(connection, "opere", "operename", ""));

			File f = new File("src/models/it-ner-art.bin");
			TokenNameFinderModel model = new TokenNameFinderModel(f);
			AnnotationStrategy as = new OpenNlpAnnotator(model);

			for (String s : data)
			{
				String tmp = as.annotatorStrategy("src/models/it-ner-art.bin", s).toString();
				for (String res : testCase.get(s))
				{
					System.err.println("Correct result: " + res);
				}
				String result = tmp.substring(1, tmp.length() - 1);
				if (result.equals(""))
					result = "NTA";
				String[] allRes = result.split(", ");
				this.annotationResults.put(s, allRes);
			}
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "Errore durante le valutazioni");
		}
	}

	public void evalutate(HashMap<String, String[]> annotatorResult)
	{
		double tp = 0;
		double fp = 0;
		double fn = 0;

		switch (applicationTipe)
		{
			case OPEN_NLP_ANNOTATOR:
				
				for (Map.Entry<String, List<String>> entry : this.testCase.entrySet())
				{
					List<String> negative = new ArrayList<String>();
					for (String s : entry.getValue())
					{
						boolean found = false;
						String[] tag = annotatorResult.get(entry.getKey());
						
						for (int i = 0; i < tag.length; i++)
						{
							if (tag[i].contains(s))
							{
								tp++;
								found = true;
							}
							if (!entry.getValue().contains(tag[i]) && !negative.contains(tag[i]))
							{
								negative.add(tag[i]);
								if (!tag[i].equals("NTA"))
								{
									fp++;
									System.out.println("found at " + entry.getKey() + "for " + tag[i]);
								}
							}
						}
						if (!found)
						{
							System.out.println("negative found at " + entry.getKey());
							fn++;
						}
					}

				}
				double prec = FMeasure.calculatePrecision(tp, fp);
				double rec = FMeasure.calculateRecall(tp, fn);
				System.err.println("Dati : tp " + tp + " fp " + fp + " fn " + fn);
				System.out.println("Precision : " + prec);
				System.out.println("Recall : " + rec);
				System.out.println("F1 Score : " + FMeasure.calculateF1Score(prec, rec, 1));
				break;
			case PATTERN_RESEARCH_ANNOTATOR:
				break;
			default:
				break;
		}
	}

	public HashMap<String, List<String>> getTestCase()
	{
		return testCase;
	}

	public HashMap<String, String[]> getAnnotationResults()
	{
		return annotationResults;
	}

}
