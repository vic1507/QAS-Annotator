package core;

import java.util.Scanner;

import opennlp.ItalianTokenizer;
import opennlp.ItalianNER;
import opennlp.TrainModel;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.util.Span;

public class OpenNlpAnnotator extends AnnotationStrategy
{

	private String onlpModelPath;
	private String trainingDataFilePath;

	public OpenNlpAnnotator(String onlpModelPath, String trainingDataFilePath)
	{
		this.onlpModelPath = onlpModelPath;
		this.trainingDataFilePath = trainingDataFilePath;
	}

	@Override
	public Object annotatorStrategy(Object o)
	{
		System.err.println("inserisci la domanda");
		Scanner input = new Scanner(System.in);
		String s = input.nextLine();
		input.close();
		String[] tokens = ItalianTokenizer.getInstance().tokenize(s);
		for (String s2 : tokens)
		{
			System.out.println(s2);
		}
		ItalianNER ner = new ItalianNER(new NameFinderME(TrainModel.train(trainingDataFilePath, onlpModelPath)));
		for (Span span : ner.entityRecognition(tokens))
			System.out.println(span);

		return null;
	}

}
