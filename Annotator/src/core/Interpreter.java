package core;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.python.core.PyDictionary;
import org.python.util.PythonInterpreter;

import opennlp.ItalianTokenizer;
import utility.Pair;

public class Interpreter
{
	File myFile;
	HashMap<String, List<Pair<Integer, Integer>>> annotatedElements;
	boolean writeOnModel;
	PythonInterpreter pi;
	
	public Interpreter(File file, boolean writeOnModel)
	{
		pi = new PythonInterpreter();
		myFile = file;
		annotatedElements = new HashMap<>();
		this.writeOnModel = writeOnModel;
	}

	public List<String> generateTokens(String input)
	{
		List<String> tokens = new ArrayList<String>();
		for (String s : ItalianTokenizer.getInstance().tokenize(input))
			tokens.add(s);
		return tokens;
	}
	
	public HashMap<String, List<Pair<Integer, Integer>>> execute(List<String> data, HashMap<String, String> mappedElements, String input)
	{
		List<String> words = generateTokens(input);
		pi.set("writeOnModel", this.writeOnModel);
		pi.set("myQuestion", input);
		pi.set("words", words);
		pi.set("dataset", data);
		pi.execfile(myFile.getAbsolutePath());
		PyDictionary pd = (PyDictionary) pi.get("solutionArray");
		Set<?> set = pd.keySet();
		for (Object key : set)
		{
			List<Pair<Integer, Integer>> positionsList = new ArrayList<>();
			String[] elements = pd.get(key).toString().split(",");
			String regex = ".+?['](.+[0-9]?)-(.+[0-9]?)['].*";

			for (String s : elements)
				positionsList.add(new Pair<Integer, Integer>(Integer.parseInt(s.replaceAll(regex, "$1")), Integer.parseInt(s.replaceAll(regex, "$2"))));

			annotatedElements.put(key.toString(), positionsList);
		}

		double final_time = Double.parseDouble(pi.get("final_time").toString());
		double begin_time = Double.parseDouble(pi.get("begin_time").toString());

		for (Map.Entry<String, List<Pair<Integer, Integer>>> entry : annotatedElements.entrySet())
		{
			for (Pair<Integer, Integer> pair : entry.getValue())
			{
				System.out.println("ho annotato " + entry.getKey() + " (" + mappedElements.get(entry.getKey()) + ") nella posizione: " + pair.values());
			}
		}

		System.out.println("tempo di esecuzione annotazione :" + (final_time - begin_time));

		pi.close();

		return annotatedElements;
	}

}
