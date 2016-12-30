package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class GenerateModel
{

	public void execute(List<String> artist, List<String> opere)
	{
		try
		{
			List<String> toPrint = new ArrayList<String>();
			FileReader fileReader = new FileReader(new File("src/core/questionTemplate.txt"));
			BufferedReader br = new BufferedReader(fileReader);
			PrintWriter pw = new PrintWriter(new File("src/core/model.txt"));
			String corr = br.readLine();
			while (corr != null)
			{
				if (corr.contains("<START:artist>"))
				{
					toPrint.addAll(toAddAtModel(corr,artist, "artist"));

				} if (corr.contains("<START:opera>"))
				{
					toPrint.addAll(toAddAtModel(corr,opere, "opera"));
				}
				corr = br.readLine();
			}
			for (String s : toPrint)
			{
				if (s.contains("OBJECT"))
				{
					for (String s2 : artist)
					{
						s = s.replaceAll("<START:artist> OBJECT", "<START:artist> " + s2);
					}
					
					for (String s3 : opere)
					{
						s= s.replaceAll("<START:opera> OBJECT", "<START:opere> " + s3);
						
					}
					
				}
				pw.println(s);
			}
			br.close();
			pw.close();
		} catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, e.getStackTrace());
		}
	}

	private List<String> toAddAtModel(String corr, List<String> data, String type)
	{
		List<String> toReturn = new ArrayList<String>();
		for (String s : data)
		{
			String toWrite = corr.replaceAll("<START:"+ type +"> OBJECT", "<START:"+ type +"> " + s);
			toReturn.add(toWrite);
		}
		return toReturn;
	}
	
	public void addToTemplate(File input, File output)
	{
		try
		{
			PrintWriter pw2 = new PrintWriter(output);
			FileReader fr = new FileReader(input);
			BufferedReader br = new BufferedReader(fr);
			String s2 = br.readLine();
			while (s2 != null)
			{
				if (s2.contains("START"))
				{
					pw2.println(s2);
				}
				s2 = br.readLine();
			}
			br.close();
			pw2.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
