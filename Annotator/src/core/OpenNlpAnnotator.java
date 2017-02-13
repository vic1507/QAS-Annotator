package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import opennlp.ItalianTokenizer;
import opennlp.ItalianNER;
import opennlp.TrainModel;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;
import utility.Pair;

public class OpenNlpAnnotator extends AnnotationStrategy
{
	private TokenNameFinderModel model;

	public OpenNlpAnnotator(TokenNameFinderModel tnfm)
	{
		this.model = tnfm;
	}

	public static void trainModel(String trainingDataFilePath, String onlpModelPath)
	{
		TrainModel.train(trainingDataFilePath, onlpModelPath);
	}

	public String getType(String s)
	{
		String typeRegex = ".*?\\)\\s(.+?)";
		return s.replaceAll(typeRegex, "$1");
	}

	public Pair<Integer, Integer> getSpansIndex(String s)
	{
		if (s.equals(""))
			return null;
		String regexIndex1 = ".*?\\[(.+?)[.].*";
		String regexIndex2 = ".*?\\..(.+?)\\).*";
		String result1 = s.replaceAll(regexIndex1, "$1");
		String resutl2 = s.replaceAll(regexIndex2, "$1");
		return new Pair<Integer, Integer>(Integer.parseInt(result1), Integer.parseInt(resutl2));
	}

	public List<String> complexSpan(Span[] results)
	{
		boolean jump = false;
		List<String> complexResults = new ArrayList<String>();
		if (results.length > 1)
		{
			for (int i = 0; i < results.length; i++)
			{
				if (jump)
					jump = false;
				else 	
				{
					int j = i + 1;
					if (j < results.length)
					{
						Pair<Integer, Integer> indexForI = getSpansIndex(results[i].toString());
						Pair<Integer, Integer> indexForJ = getSpansIndex(results[j].toString());
						String typeForI = getType(results[i].toString());
						String typeForJ = getType(results[j].toString());
						if ((indexForI.getSecondElement() + 1 == indexForJ.getFirstElement()) && (typeForI.equals(typeForJ)))
						{
							complexResults.add("[" + indexForI.getFirstElement() + ".." + indexForJ.getSecondElement() + ") " + typeForI);
							jump = true;
						} else
						{
							complexResults.add(results[i].toString());
						}

					} else
						complexResults.add(results[i].toString());
				} 
			}
		} else if (results.length == 1)
			complexResults.add(results[0].toString());
		else
			complexResults.add("");
		return complexResults;
	}

	
	@SuppressWarnings("unchecked")
	public List<Pair<Integer,Integer>> charactherResulsts (String text)
	{
		List<Pair<Integer,Integer>> charachterIndex = new ArrayList<Pair<Integer,Integer>>();
		List<String> res = (List<String>) annotatorStrategy("src/models/it-ner-art.bin", text);
		String[] tokens = ItalianTokenizer.getInstance().tokenize(text);
		HashMap<String, Pair<Integer, Integer>> characterForTokens = new HashMap<String, Pair<Integer, Integer>>();
		int currentIndex = 0;
		for (int i = 0; i < tokens.length; i++)
		{
			int length = tokens[i].length();
			characterForTokens.put(tokens[i], new Pair<Integer, Integer>(currentIndex, currentIndex + length - 1));
			currentIndex += length;
			if (!tokens[i].contains("'"))
				currentIndex++;
		}
		
		for (String s : res)
		{
			Pair<Integer,Integer> startEnd = getSpansIndex(s);
			if (startEnd!=null)
				charachterIndex.add(characterForTokens.get(tokens[startEnd.getFirstElement()]));
			else
				JOptionPane.showMessageDialog(null, "Niente da annotare");
		}

		return charachterIndex;
	}
	
	@Override
	public Object annotatorStrategy(Object o, String question)
	{
		List<String> results = new ArrayList<String>();
		String[] tokens = ItalianTokenizer.getInstance().tokenize(question);
		ItalianNER ner = new ItalianNER(new NameFinderME(model));
		System.out.println("********************************************************************************:P********************************************************************************");
		System.out.println("RESULT FOR " + '"' + question + '"');

		List<String> res = complexSpan(ner.entityRecognition(tokens));
		for (String s : res)
		{
			System.out.println("NER result: " + s);
			results.add(s);
		}
		return results;
	}

}
