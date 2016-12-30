package core;

public class RegexProva
{
	public static void main (String [] args)
	{
		String prova = "\\[.+\\]";
		String x = "Quello che [3]";
		
		String result = x.replaceAll(prova, "");
		System.out.println(result);
	}
}
