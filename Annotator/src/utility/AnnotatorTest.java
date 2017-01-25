package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JOptionPane;


public class AnnotatorTest
{
	private int precision = 0;
	private int recall = 0;
	private int f1Score = 0;

	public static final int OPEN_NLP_ANNOTATOR = 0;
	public static final int PATTERN_RESEARCH_ANNOTATOR = 1;

	private HashMap<String, String> testCase;
	private HashMap<String, String> annotationResults;

	private int applicationTipe;
	
	public AnnotatorTest(int applicationTipe)
	{
		this.testCase = new HashMap<String, String>();
		this.annotationResults = new HashMap<String, String>();
		this.applicationTipe = applicationTipe;
	}

	public void readTestCase(File template)
	{
		try
		{
			FileReader fr = new FileReader(template);
			BufferedReader br = new BufferedReader(fr);
			String s = br.readLine();
			while (s != null)
			{
				String[] testCase = s.split("::");
				this.testCase.put(testCase[0], testCase[1]);
				s = br.readLine();
			}
			br.close();
			fr.close();
		} catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "Errore nella lettura dei test case");
		}
	}
	
	public void evalutate (HashMap<String,String> annotatorResult)
	{
		switch (applicationTipe)
		{
			case OPEN_NLP_ANNOTATOR:
				break;
			case PATTERN_RESEARCH_ANNOTATOR:
				break;
			default:
				break;
		}
	}
	

	public int getPrecision()
	{
		return precision;
	}

	public int getRecall()
	{
		return recall;
	}

	public int getF1Score()
	{
		return f1Score;
	}

	public HashMap<String, String> getTestCase()
	{
		return testCase;
	}

	public HashMap<String, String> getAnnotationResults()
	{
		return annotationResults;
	}

}
