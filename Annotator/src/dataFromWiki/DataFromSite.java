package dataFromWiki;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public DataFromSite()
	{
		try
		{
			MySQLResourcesAcquisition rs = new MySQLResourcesAcquisition();
			Connection connection = rs.getConnection();

			List<String> artist = rs.getDataFromDb(connection, "artist", "name", "");
			List<String> opere = rs.getDataFromDb(connection, "opere", "operename", "");
			List<String> dataset = new ArrayList<String>();

			dataset.addAll(artist);
			dataset.addAll(opere);

			AnnotationStrategy as = new RightMatchAnnotator(dataset, rs.getMappedTypes());

			Interpreter i = new Interpreter(new File("src/core/annotator.py"));

			HashMap<String, String> mt = rs.getMappedTypes();

			List<String> toPrint = new ArrayList<String>();

			String regex = "\\[.+\\]";

			File f = new File("src/dataFromWiki/prova.txt");
			PrintWriter pw = new PrintWriter(f);
			Document doc = Jsoup.connect("https://it.wikipedia.org/wiki/Guernica_(Picasso)").get();
			Elements e = doc.getAllElements().select("p");
			for (Element e2 : e)
			{
				String processedInput = e2.text().replaceAll(regex, "");
				i.setDataExtra(processedInput.toLowerCase());

				HashMap<String, List<Pair<Integer, Integer>>> myRes = (HashMap<String, List<Pair<Integer, Integer>>>) as.annotatorStrategy(i);
				for (Map.Entry<String, List<Pair<Integer, Integer>>> entry : myRes.entrySet())
				{
					String tmp = processedInput.toLowerCase();

					if (mt.get(entry.getKey()).equals("opere"))
					{
						tmp = processedInput.toLowerCase();
						tmp = tmp.replaceAll(entry.getKey(), "<START:opera> OBJECT <END>");
						pw.println(tmp);
					}

					else if (mt.get(entry.getKey()).equals("artist"))
					{
						tmp = processedInput.toLowerCase();
						tmp = tmp.replaceAll(entry.getKey(), "<START:artist> OBJECT <END>");
						pw.println(tmp);
					}

				}

			}
			pw.close();
			GenerateModel gm = new GenerateModel();
			gm.addToTemplate(f, new File("src/dataFromWiki/bu.txt"));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
