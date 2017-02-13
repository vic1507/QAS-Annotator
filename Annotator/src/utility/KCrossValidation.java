package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

import core.OpenNlpAnnotator;

public class KCrossValidation
{
	public static final int K = 10;

	public static void crossValidation()
	{
		try
		{
			int startRange = 0;
			int finalRange = startRange + K;

			int modelSize = 0;
			int iterations = 0;
			double prec = 0;
			double rec = 0;
			double f1Score = 0;
			FileReader fr = new FileReader(new File("src/models/model.txt"));
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while (line != null)
			{
				modelSize++;
				line = br.readLine();
				
			}
			br.close();
			fr.close();
			
			
			while (finalRange <= modelSize)
			{
				FileReader fr2 = new FileReader(new File ("src/models/model.txt"));
				BufferedReader br2 = new BufferedReader(fr2);
				iterations ++;
				PrintWriter pw = new PrintWriter(new File ("src/models/kvalTest.txt"));
				PrintWriter pw2 = new PrintWriter (new File("src/models/kvalMod.txt"));
				int counter = 1;
				String line2 = br2.readLine();
				while (line2!=null)
				{
					System.out.println("Creo i test case ed il modello " + counter);
					if (counter >= startRange && counter <= finalRange)
					{
						pw.println(line2);
					}
					else
					{
						pw2.println(line2);
					}
					counter++;
					line2 = br2.readLine();
				}
				br2.close();
				fr2.close();
				pw.close();
				pw2.close();
				startRange += K+1;
				finalRange = startRange+K;
				OpenNlpAnnotator.trainModel("src/models/kvalMod.txt", "src/models/it-ner-art-tmp.bin");
				AnnotatorTest at = new AnnotatorTest(AnnotatorTest.OPEN_NLP_ANNOTATOR);
				at.generateTestCase(new File("src/models/kvalTest.txt"), new File ("src/models/testCaseK.txt"));
				at.readTestCase(new File("src/models/testCaseK.txt"));
				at.compute(true);
				at.evalutate(at.getAnnotationResults());
				prec+=at.getPrecision();
				rec+=at.getRecall();
				f1Score+=at.getF1Score();
			}
			
			System.err.println("FINAL Precision " + prec/iterations);
			System.err.println("FINAL Recall " + rec/iterations);
			System.err.println("FINAL F1 score " + f1Score/iterations);
			
			
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
