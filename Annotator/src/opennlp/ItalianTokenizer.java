package opennlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class ItalianTokenizer
{
	private ItalianTokenizer()
	{

	}

	public static ItalianTokenizer instance = new ItalianTokenizer();

	public String[] tokenize(String input)
	{
		String [] tokens = null;
		try
		{
			InputStream modelIn = new FileInputStream("libs/italianModels/it-token.bin");

			try
			{
				TokenizerModel model = new TokenizerModel(modelIn);
				Tokenizer tokenizer = new TokenizerME(model);
				tokens = tokenizer.tokenize(input);
			} catch (IOException e)
			{
				e.printStackTrace();
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
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return tokens;

	}

	public static ItalianTokenizer getInstance()
	{
		return instance;
	}

}
