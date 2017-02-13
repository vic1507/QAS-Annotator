package opennlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JOptionPane;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class ItalianTokenizer
{
	private ItalianTokenizer()
	{
		try
		{
			InputStream modelIn = new FileInputStream("libs/italianModels/it-token.bin");

			try
			{
				TokenizerModel model = new TokenizerModel(modelIn);
				this.tokenizer = new TokenizerME(model);
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
						JOptionPane.showMessageDialog(null, "Errore nell'elaborazione del modello");
					}
				}
			}
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "Errore nella lettura del modello");
		}

	}

	private Tokenizer tokenizer;

	public static ItalianTokenizer instance = new ItalianTokenizer();

	public String[] tokenize(String input)
	{
		return tokenizer.tokenize(input);
	}

	public static ItalianTokenizer getInstance()
	{
		return instance;
	}

}
