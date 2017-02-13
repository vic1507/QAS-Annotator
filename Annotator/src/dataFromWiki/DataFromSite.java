package dataFromWiki;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import core.AnnotationStrategy;
import core.GenerateModel;
import core.Interpreter;
import core.RightMatchAnnotator;
import utility.MySQLResourcesAcquisition;
import utility.Pair;

public class DataFromSite
{
	private static final String regex = ".+?[\"](.+?)[\"].*";
	private static final String regex2 = "(.+?)[(].*";

	public static void dataAcqusition()
	{
		try
		{
			MySQLResourcesAcquisition rs = new MySQLResourcesAcquisition();
			List<String> dataset = new ArrayList<String>();
			dataset.add("Guernica");
			rs.getMappedTypes().put("Guernica", "opere");
			AnnotationStrategy as = new RightMatchAnnotator(dataset, rs.getMappedTypes());

			Interpreter i = new Interpreter(new File("src/core/annotator.py"), true);

			HashMap<String, String> mt = rs.getMappedTypes();

			String regex = "\\[.+\\]";

			File f = new File("src/dataFromWiki/dataFromWiki.txt");
			PrintWriter pw = new PrintWriter(f);
			Document doc = Jsoup.connect("https://it.wikipedia.org/wiki/Guernica_(Picasso)").get();
			Elements e = doc.getAllElements().select("p");
			for (Element e2 : e)
			{
				Set<String> firstList = new TreeSet<String>();
				String processedInput = e2.text().replaceAll(regex, " ");

				@SuppressWarnings("unchecked")
				HashMap<String, List<Pair<Integer, Integer>>> myRes = (HashMap<String, List<Pair<Integer, Integer>>>) as.annotatorStrategy(i, processedInput);
				for (Map.Entry<String, List<Pair<Integer, Integer>>> entry : myRes.entrySet())
				{
					String tmp = processedInput;

					if (mt.get(entry.getKey()).equals("opere"))
					{
						tmp = processedInput;
						tmp = tmp.replaceAll(entry.getKey(), "<START:opera> Guernica <END>");
						firstList.add(tmp);
					}

					else if (mt.get(entry.getKey()).equals("artist"))
					{
						tmp = processedInput;
						tmp = tmp.replaceAll(entry.getKey(), "<START:artist> Guernica <END>");
						firstList.add(tmp);
					}

				}

				List<String> toPrint = new ArrayList<String>();
				for (String s : firstList)
				{
					String[] tmp = s.split("\\. ");
					for (String s2 : tmp)
					{
						toPrint.add(s2);
					}
				}

				for (String s : toPrint)
				{
					pw.println(s);
				}
			}
			pw.close();
			GenerateModel gm = new GenerateModel(GenerateModel.MODEL1);
			gm.addToTemplate(f, "src/models/questionTemplateWiki.txt");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{

		DataFromSite.articlesMapSparQL();
	}

	// TODO FUNZIONE PER ARTICOLI DINAMICI
	public static HashMap<String, String> articlesMapSparQL()
	{
		HashMap<String, String> articlesMap = new HashMap<String, String>();
		try
		{
			String articleRegex = ".*?[\"](.+?)[\\s\'].*";
			Document doc = Jsoup.parse(new File("src/dataFromWiki/sparqlArticles.html"), "UTF-8");
			Elements elements = doc.getAllElements().select("td");
			int index = 0;
			String key = "";
			for (Element e : elements)
			{
				if (index % 2 == 0)
				{
					String tmp = e.toString().replaceAll(regex, "$1");
					key = tmp.replaceAll(regex2, "$1");
					if (key.endsWith(" "))
					{
						key = key.substring(0, key.length() - 1);
					}
				} else
				{

					String insert = e.toString().replaceAll(articleRegex, "$1");
					articlesMap.put(key, insert);
				}
				index++;
			}
/*
			for (Map.Entry<String, String> entry : articlesMap.entrySet())
			{
				boolean stamp = false;
				if (entry.getValue().toLowerCase().equals("le") || entry.getValue().toLowerCase().equals("gli") || entry.getValue().toLowerCase().equals("il") || entry.getValue().toLowerCase().equals("la") || entry.getValue().toLowerCase().equals("lo") || entry.getValue().toLowerCase().equals("l") || entry.getValue().toLowerCase().equals("i"))
				{
					String first = "(.+?)[\\s\'].*";
					String firstFromKey = entry.getKey().replaceAll(first, "$1");
					if (firstFromKey.equals(entry.getValue()))
					{
						System.err.println("Corrispondenza di articolo per " + entry.getKey());
					} else
					{
						System.err.println("Articolo ma non nel nome " + entry.getKey());
						stamp = true;
					}

				} else if (!stamp)
				{
					System.out.println("nessun articolo previsto per " + entry.getKey());
				}

			}*/
		} catch (IOException e)
		{
			e.printStackTrace();
			return articlesMap;
		}

		return articlesMap;
	}

	public static List<String> opereFromSparQL()
	{

		List<String> opere = new ArrayList<String>();
		try
		{
			Document e5 = Jsoup.parse(new File("src/dataFromWiki/sparqlOpere.html"), "UTF-8");
			Elements e6 = e5.getAllElements().select("pre");
			for (Element e11 : e6)
			{
				String tmp = e11.toString().replaceAll(regex, "$1");
				String tmp2 = tmp.replaceAll(regex2, "$1");
				if (tmp2.endsWith(" "))
				{
					tmp2 = tmp2.substring(0, tmp2.length() - 1);
				}
				opere.add(tmp2);
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return opere;

	}

	public static List<String> artistFromSparQL()
	{

		List<String> artist = new ArrayList<String>();
		try
		{
			Document e5 = Jsoup.parse(new File("src/dataFromWiki/sparqlArtist.html"), "UTF-8");
			Elements e6 = e5.getAllElements().select("pre");
			for (Element e11 : e6)
			{
				String tmp = e11.toString().replaceAll(regex, "$1");
				String tmp2 = tmp.replaceAll(regex2, "$1");
				if (tmp2.endsWith(" "))
				{
					tmp2 = tmp2.substring(0, tmp2.length() - 1);
				}
				artist.add(tmp2);
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return artist;
	}

}
