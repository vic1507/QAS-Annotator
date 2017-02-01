package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class GenerateModel
{
	public static final String MODEL1 = "src/models/equilibratedTemplates.txt";
	public static final String MODEL2 = "src/models/questionTemplate.txt";
	private String model;
	
	public GenerateModel (String model)
	{
		this.model = model;
	}
	public void executeProva(List<String> artist, List<String> opere)
	{
		try
		{
			FileReader fr = new FileReader(new File(model));
			BufferedReader br = new BufferedReader(fr);
			PrintWriter pw = new PrintWriter(new File("src/models/model2.txt"));
			String corr = br.readLine();
			int index = 0;
			int index2 = 0;
			while (corr != null)
			{
				while (corr.contains("OBJECT"))
				{
					if (index < artist.size() - 1)
					{
						corr = corr.replaceFirst("<START:artist> OBJECT <END>", "<START:artist> " + artist.get(index) + " <END>");
						index++;
					}
					else
						index = 0;
					
					if (index2 < opere.size()-1)
					{
						corr = corr.replaceFirst("<START:opera> OBJECT <END>","<START:opera> "+ opere.get(index2) + " <END>");
						index2++;
					}else
						index2=0;
				}
				
				pw.println(corr);
				corr = br.readLine();
			}
			br.close();
			pw.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void execute(List<String> artist, List<String> opere)
	{
		try
		{
			List<String> toPrint = new ArrayList<String>();
			FileReader fileReader = new FileReader(new File(MODEL1));
			BufferedReader br = new BufferedReader(fileReader);
			PrintWriter pw = new PrintWriter(new File("src/models/model.txt"));
			String corr = br.readLine();
			while (corr != null)
			{
				if (corr.contains("<START:artist>"))
				{
					toPrint.addAll(addToModel(corr, artist, "artist"));
				}
				if (corr.contains("<START:opera>"))
				{
					toPrint.addAll(addToModel(corr, opere, "opera"));
				}
				corr = br.readLine();
			}
			for (int i = 0; i < toPrint.size(); i++)
			{
				if (toPrint.get(i).contains("OBJECT"))
				{
					for (String s2 : artist)
					{
						String replace = toPrint.get(i).replaceAll("<START:artist> OBJECT", "<START:artist> " + s2);
						toPrint.set(i, replace);
					}

					for (String s3 : opere)
					{
						String replace = toPrint.get(i).replaceAll("<START:opera> OBJECT", "<START:opera> " + s3);
						toPrint.set(i, replace);
					}

				}
				pw.println(toPrint.get(i));
			}
			br.close();
			pw.close();
		} catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, e.getStackTrace());
		}
	}

	private List<String> addToModel(String corr, List<String> data, String type)
	{
		List<String> toReturn = new ArrayList<String>();
		for (String s : data)
		{
			String toWrite = corr.replaceAll("<START:" + type + "> OBJECT", "<START:" + type + "> " + s);
			toReturn.add(toWrite);
		}
		return toReturn;
	}

	public void addToTemplate(File input, String output)
	{
		try
		{
			FileWriter fw = new FileWriter(output, true);
			FileReader fr = new FileReader(input);
			BufferedReader br = new BufferedReader(fr);
			String s2 = br.readLine();
			while (s2 != null)
			{
				if (s2.contains("START"))
				{
					String writeS = s2.replaceAll(",", "");
					fw.write("\n" + writeS);
				}
				s2 = br.readLine();
			}
			br.close();
			fw.flush();
			fw.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
