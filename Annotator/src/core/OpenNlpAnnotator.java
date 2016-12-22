package core;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Scanner;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
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

	@SuppressWarnings({ "deprecation", "unused" })
	@Override
	public void annotatorStrategy(Object o)
	{
		try
		{
			Charset charset = Charset.forName("UTF-8");
			ObjectStream<String> lineStream = new PlainTextByLineStream(new FileInputStream(trainingDataFilePath), charset);
			ObjectStream<NameSample> sampleStream = new NameSampleDataStream(lineStream);
			TokenNameFinderModel model = null;

			try
			{
				model = NameFinderME.train("en", "drugs", sampleStream, Collections.<String, Object>emptyMap(), 100, 4);
			} finally
			{
				sampleStream.close();
			}
			BufferedOutputStream modelOut = null;
			try
			{
				modelOut = new BufferedOutputStream(new FileOutputStream(onlpModelPath));
				model.serialize(modelOut);
			} finally
			{
				if (modelOut != null)
					modelOut.close();
			}

			System.err.println("inserisci la domanda");
			Scanner input = new Scanner(System.in);
			String s = input.nextLine();
			input.close();

			InputStream modelIn = new FileInputStream(o.toString());
			TokenNameFinderModel modelForTokenFinder = null;
			try
			{
				modelForTokenFinder = new TokenNameFinderModel(modelIn);
			} finally
			{
				if (modelIn != null)
				{
					try
					{
						modelIn.close();
					} catch (IOException e)
					{
					}
				}

			}
			
			NameFinderME nameFinder = new NameFinderME(model);
			String sentence[] = s.split(" ");
			Span nameSpans[] = nameFinder.find(sentence);
		
			for (Span span : nameSpans)
				System.out.println(span);
		
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
