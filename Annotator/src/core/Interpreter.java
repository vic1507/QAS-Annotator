package core;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.python.core.PyDictionary;
import org.python.util.PythonInterpreter;

import opennlp.ItalianTokenizer;
import utility.Pair;

public class Interpreter
{
	File myFile;
	HashMap<String, List<Pair<Integer, Integer>>> annotatedElements;
	private boolean input;
	private String myQuestion;

	public Interpreter(File file)
	{
		myFile = file;
		annotatedElements = new HashMap<>();
		input = false;
	}

	public List<String> generateTokens (String input)
	{
		List<String> tokens = new ArrayList<String>();
		for (String s : ItalianTokenizer.getInstance().tokenize(input))
			tokens.add(s);
		return tokens;
	}
	
	public void setDataExtra(String analyze)
	{
		this.input = true;
		this.myQuestion = analyze;
	}

	public HashMap<String, List<Pair<Integer, Integer>>> execute(List<String> data, HashMap<String, String> mappedElements)
	{

		PythonInterpreter pi = new PythonInterpreter();

		if (input)
		{
			pi.set("myQuestion", this.myQuestion);
			pi.set("words", ItalianTokenizer.getInstance().tokenize(this.myQuestion));
		} else
		{
			Scanner in = new Scanner(System.in);
			System.out.println("Inserisci la domanda");
			String pass = in.nextLine();
			in.close();
			pi.set("myQuestion", pass);
			List<String> words = generateTokens(pass);
			pi.set("words", words);
		}
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
