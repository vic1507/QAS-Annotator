package utility;

public class RegexUtility
{
	public static final String OPENNLP_TYPE = ".*?\\)\\s(.+?)";
	public static final String OPENNLP_ENTITY_START = ".*?\\[(.+?)[.].*";
	public static final String OPENNLP_ENTITY_END = ".*?\\..(.+?)\\).*";
	
	private RegexUtility()
	{
		
	}
	
	private static RegexUtility instance = new RegexUtility();

	public static RegexUtility getInstance()
	{
		return instance;
	}

	public String openNLPEntityType ()
	{
		return OPENNLP_TYPE;
	}
	
	public String openNLPEntityStart ()
	{
		return OPENNLP_ENTITY_START;
	}
	
	public String openNLPEntityEnd()
	{
		return OPENNLP_ENTITY_END;
	}
}
