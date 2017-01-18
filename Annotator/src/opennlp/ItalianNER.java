package opennlp;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.util.Span;

public class ItalianNER
{
	NameFinderME nameFinder;
	
	public ItalianNER (NameFinderME nameFinder)
	{
		this.nameFinder = nameFinder;
	}
	
	public Span [] entityRecognition (String [] sentence)
	{
		return nameFinder.find(sentence);
	}
}
