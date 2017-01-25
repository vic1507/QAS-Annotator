package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import core.AnnotationStrategy;
import core.OpenNlpAnnotator;
import opennlp.tools.namefind.TokenNameFinderModel;

public class AnnotatorTest
{
	private double precision = 0;
	private double recall = 0;
	private double f1Score = 0;

	public static final int OPEN_NLP_ANNOTATOR = 0;
	public static final int PATTERN_RESEARCH_ANNOTATOR = 1;

	private HashMap<String, String> testCase;
	private HashMap<String, String> annotationResults;
	private List<String> data;

	private int applicationTipe;

	public AnnotatorTest(int applicationTipe)
	{
		this.data = new ArrayList<String>();
		this.testCase = new HashMap<String, String>();
		this.annotationResults = new HashMap<String, String>();
		this.applicationTipe = applicationTipe;
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
				this.testCase.put(testCase[0], testCase[1]);
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
				String result = tmp.substring(1, tmp.length()-1);
				if (result.equals(""))
					result = "NTA";
				this.annotationResults.put(s, result);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void evalutate(HashMap<String, String> annotatorResult)
	{
		double tp = 0;
		double fp = 0;
		double fn = 0;

		switch (applicationTipe)
		{
			case OPEN_NLP_ANNOTATOR:
				for (Map.Entry<String, String> entry : this.testCase.entrySet())
				{
				
					String tag = annotatorResult.get(entry.getKey());
					if (tag.contains(entry.getValue()))
						tp++;
					if (!tag.contains(entry.getValue()))
						fn++;
					if (!entry.getValue().contains(tag) && !tag.equals("NTA"))
						fp++;
				}
				System.err.println("Dati : tp " + tp + " fp " + fp + " fn " + fn );
				System.out.println("Precision : " + calculatePrecision(tp, fp));
				System.out.println("Recall : " + calculateRecall(tp, fn));
				break;
			case PATTERN_RESEARCH_ANNOTATOR:
				break;
			default:
				break;
		}
	}

	private double calculatePrecision(double tp, double fp)
	{
		return tp / (tp + fp);
	}

	private double calculateRecall(double tp, double fn)
	{
		return tp / (tp + fn);
	}

	private double calculateF1Score(double precision, double recall, double metric)
	{
		return 0;
	}

	public double getPrecision()
	{
		return precision;
	}

	public double getRecall()
	{
		return recall;
	}

	public double getF1Score()
	{
		return f1Score;
	}

	public HashMap<String, String> getTestCase()
	{
		return testCase;
	}

	public HashMap<String, String> getAnnotationResults()
	{
		return annotationResults;
	}

}
