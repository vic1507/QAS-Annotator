package core;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.python.core.PyDictionary;
import org.python.util.PythonInterpreter;

import utility.Pair;

public class Interpreter 
{
	File myFile;
	HashMap<String, List<Pair<Integer, Integer>>> annotatedElements;

	public Interpreter(File file)
	{
		myFile = file;
		annotatedElements = new HashMap<>();
	}

	public void execute(List<String> data, HashMap<String,String> mappedElements)
	{
		PythonInterpreter pi = new PythonInterpreter();
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
				System.out.println("ho annotato " + entry.getKey() + " ("+ mappedElements.get(entry.getKey()) +") nella posizione: " + pair.values());
			}
		}
		
		System.out.println("tempo di esecuzione annotazione :" + (final_time - begin_time));

		pi.close();
	}


}
