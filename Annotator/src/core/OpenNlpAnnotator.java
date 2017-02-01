package core;

import java.util.ArrayList;
import java.util.List;

import opennlp.ItalianTokenizer;
import opennlp.ItalianNER;
import opennlp.TrainModel;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

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

	@Override
	public Object annotatorStrategy(Object o, String question)
	{
		List<String> results = new ArrayList<String>();
		String[] tokens = ItalianTokenizer.getInstance().tokenize(question);
		ItalianNER ner = new ItalianNER(new NameFinderME(model));
		System.out.println("********************************************************************************:P********************************************************************************");
		System.out.println("RESULT FOR "+ '"' + question + '"');
		for (Span span : ner.entityRecognition(tokens))
		{
			System.out.println("NER result: " + span);
			results.add(span.toString());
		}
		return results;
	}

}
