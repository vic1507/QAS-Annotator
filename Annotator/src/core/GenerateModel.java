package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JOptionPane;

public class GenerateModel
{
	public static final String MODEL1 = "src/models/equilibratedTemplates.txt";
	public static final String MODEL2 = "src/models/questionTemplate.txt";
	public static final String MODEL3 = "src/models/artistTemplate.txt";

	private String model;

	public GenerateModel(String model)
	{
		this.model = model;
	}

	public void miniModelExecution(List<String> artist, List<String> opere)
	{
		try
		{
			List<String> opereForModel = generateOpereForModel(opere);
			List<String> artistForModel = generateArtistForModel(artist);
			FileReader fr = new FileReader(new File(model));
			BufferedReader br = new BufferedReader(fr);
			PrintWriter pw = new PrintWriter(new File("src/models/model2.txt"));
			String corr = br.readLine();
			List<String> questions = new ArrayList<String>();
			while (corr != null)
			{
				questions.add(corr);
				corr = br.readLine();
			}
			List<String> def = new ArrayList<String>();
			List<String> ciao = new ArrayList<String>();
			int index3 = 0;
			for (String s : artistForModel)
			{
				boolean used = false;
				if (!s.contains("$"))
				{
					while (!used)
					{
						String m = questions.get(index3);

						if (!m.contains("START:artist"))
							index3++;

						if (index3 > questions.size() - 1)
							index3 = 0;

						if (questions.get(index3).contains("<START:artist> OBJECT <END>"))
						{
							used = true;
							m = questions.get(index3).replaceFirst("<START:artist> OBJECT <END>", "<START:artist> " + s + " <END>");
						}
						index3++;
						if (index3 > questions.size() - 1)
							index3 = 0;

						if (!m.contains("OBJECT"))
							def.add(m);
						else
							ciao.add(m);
					}
				}
			}

			index3 = 0;

			for (String s2 : opereForModel)
			{
				if (!s2.contains("$"))
				{
					if (index3 > ciao.size() - 1)
						index3 = 0;

					String m = ciao.get(index3);

					if (index3 > ciao.size() - 1)
						index3 = 0;

					while (m.contains("<START:opera> OBJECT <END>"))
					{
						m = ciao.get(index3).replaceFirst("<START:opera> OBJECT <END>", "<START:opera> " + s2 + " <END>");
					}

					index3++;
					if (index3 > questions.size() - 1)
						index3 = 0;

					if (!m.contains("OBJECT"))
						def.add(m);

				}
			}

			for (String s222 : def)
			{
				pw.println(s222);
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
			FileReader fileReader = new FileReader(new File(model));
			BufferedReader br = new BufferedReader(fileReader);
			PrintWriter pw = new PrintWriter(new File("src/models/model3.txt"));
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
				System.out.println("metto su modello");
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
			if (!s.contains("$"))
			{
				String toWrite = corr.replaceAll("<START:" + type + "> OBJECT", "<START:" + type + "> " + s);
				toReturn.add(toWrite);
			}
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

	private List<String> generateArtistForModel(List<String> artist)
	{

		List<String> artistForModel = new ArrayList<String>();
		for (int i = 0; i < 30; i++)
		{
			int returnIndex = new Random().nextInt(artist.size() - 1);
			artistForModel.add(artist.get(returnIndex));
		}
		return artistForModel;
	}

	private List<String> generateOpereForModel(List<String> opere)
	{
		List<String> opereForModel = new ArrayList<String>();
		for (int i = 0; i < 100; i++)
		{
			int returnIndex = new Random().nextInt(opere.size() - 1);
			opereForModel.add(opere.get(returnIndex));
		}
		return opereForModel;
	}

	public void createModel(List<String> artist, List<String> opere)
	{
		try
		{
			List<String> toPrint = new ArrayList<String>();
			List<String> opereForModel = generateOpereForModel(opere);
			List<String> artistForModel = generateArtistForModel(artist);
			FileReader fr = new FileReader(new File(model));
			BufferedReader br = new BufferedReader(fr);
			PrintWriter pw = new PrintWriter(new File("src/models/model3.txt"));
			String corr = br.readLine();
			while (corr != null)
			{
				if (corr.contains("<START:artist>"))
				{
					toPrint.addAll(addToModel(corr, artistForModel, "artist"));
				}
				if (corr.contains("<START:opera>"))
				{
					toPrint.addAll(addToModel(corr, opereForModel, "opera"));
				}
				corr = br.readLine();
			}
			for (int i = 0; i < toPrint.size(); i++)
			{
				if (toPrint.get(i).contains("OBJECT"))
				{
					for (String s2 : artistForModel)
					{
						String replace = toPrint.get(i).replaceAll("<START:artist> OBJECT", "<START:artist> " + s2);
						toPrint.set(i, replace);
					}

					for (String s3 : opereForModel)
					{
						String replace = toPrint.get(i).replaceAll("<START:opera> OBJECT", "<START:opera> " + s3);
						toPrint.set(i, replace);
					}

				}
				System.out.println("metto su modello");
				pw.println(toPrint.get(i));
			}
			br.close();
			pw.close();
		} catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "Errore nella creazione del modello");
		}
	}

}
