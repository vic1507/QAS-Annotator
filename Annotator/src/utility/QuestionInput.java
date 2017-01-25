package utility;

import java.util.Scanner;

public class QuestionInput
{
	public static String insertInput ()
	{
		System.err.println("Inserisci la domanda");
		Scanner s = new Scanner(System.in);
		String input = s.nextLine();
		s.close();
		return input;
	}
}
