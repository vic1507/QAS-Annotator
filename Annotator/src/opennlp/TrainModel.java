package opennlp;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import javax.swing.JOptionPane;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;
import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;
import opennlp.tools.util.featuregen.CachedFeatureGenerator;
import opennlp.tools.util.featuregen.OutcomePriorFeatureGenerator;
import opennlp.tools.util.featuregen.PreviousMapFeatureGenerator;
import opennlp.tools.util.featuregen.SentenceFeatureGenerator;
import opennlp.tools.util.featuregen.TokenClassFeatureGenerator;
import opennlp.tools.util.featuregen.TokenFeatureGenerator;
import opennlp.tools.util.featuregen.WindowFeatureGenerator;

public class TrainModel
{
	public static TokenNameFinderModel train(String trainingDataFilePath, String onlpModelPath)
	{
		TokenNameFinderModel model = null;
		try
		{
			Charset charset = Charset.forName("UTF-8");
			ObjectStream<String> lineStream = new PlainTextByLineStream(new FileInputStream(trainingDataFilePath), charset);
			ObjectStream<NameSample> sampleStream = new NameSampleDataStream(lineStream);
			try
			{
				AdaptiveFeatureGenerator featureGenerator = new CachedFeatureGenerator(
				         new AdaptiveFeatureGenerator[]{
				           new WindowFeatureGenerator(new TokenFeatureGenerator(), 3, 3),
				           new WindowFeatureGenerator(new TokenClassFeatureGenerator(true), 3, 3),
				           new OutcomePriorFeatureGenerator(),
				           new PreviousMapFeatureGenerator(),
//				           new BigramNameFeatureGenerator(),
//				           new PreviousTwoMapFeatureGenerator(),
				           new SentenceFeatureGenerator(true, false),
				           });
				model = NameFinderME.train("it", "artistic", sampleStream, new TrainingParameters(), featureGenerator, Collections.<String, Object>emptyMap());
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
		} catch (Exception e)
		{
			//TODO
			JOptionPane.showMessageDialog(null, e.getStackTrace());
		}

		return model;
	}
}
