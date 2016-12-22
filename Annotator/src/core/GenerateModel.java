package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.swing.JOptionPane;

public class GenerateModel
{


	public void execute(List<String> artist, List<String> opere)
	{
		try
		{
			FileReader fileReader = new FileReader(new File("src/core/questionTemplate.txt"));
			BufferedReader br = new BufferedReader(fileReader);
			PrintWriter pw = new PrintWriter(new File("src/core/model.txt"));
			String corr = br.readLine();
			while (corr != null)
			{
				if (corr.contains("<START:artista>"))
				{
					printOnModel(corr, pw, artist);

				} else if (corr.contains("<START:opera>"))
				{
					printOnModel(corr, pw, artist);
				}
				corr = br.readLine();
			}
			br.close();
			pw.close();
		} catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, e.getStackTrace());
		}
	}
	
	private void printOnModel(String corr, PrintWriter pw, List<String> data)
	{
		for (String s : data)
		{
			String toWrite = corr.replaceAll("OBJECT", s);
			pw.println(toWrite);
		}
	}
}
