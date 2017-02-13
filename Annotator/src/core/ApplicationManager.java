package core;

import java.io.File;
import java.sql.Connection;

import javax.swing.JOptionPane;

import dataFromWiki.DataFromSite;
import opennlp.tools.namefind.TokenNameFinderModel;
import utility.AnnotatorTest;
import utility.KCrossValidation;
import utility.MySQLResourcesAcquisition;
import utility.QuestionInput;

public class ApplicationManager
{
	private ApplicationManager()
	{

	}

	private static ApplicationManager instance = new ApplicationManager();

	public static ApplicationManager getInstance()
	{
		return instance;
	}

	public Object execute3(String text)
	{

		try
		{
			MySQLResourcesAcquisition rs = new MySQLResourcesAcquisition();

			Connection connection = rs.getConnection();

			rs.getData().addAll(rs.getDataFromDb(connection, "artist", "name", ""));
			rs.getData().addAll(rs.getDataFromDb(connection, "opere", "operename", ""));

			File f = new File("src/models/it-ner-art.bin");
			TokenNameFinderModel model = new TokenNameFinderModel(f);
			OpenNlpAnnotator as = new OpenNlpAnnotator(model);
			return as.charactherResulsts(text);
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public void execuete2(String type)
	{
		switch (type)
		{
			case "Annotator":
				annotator();
				break;
			case "Generate Test Case":
				generateTestCase();
				break;
			case "Test":
				test();
				break;
			case "TrainModel":
				trainModel();
				break;
			case "ExtendsCorpora":
				extendsCorpora();
				break;
			case "Cross":
				KCrossValidation.crossValidation();
				break;
			default:
				break;
		}
	}

	private void annotator()
	{
		try
		{
			String[] possibleValues = { "OpenNlp", "PatternsMatching" };
			String type = JOptionPane.showInputDialog(null, "Choose an annotator", "Annotator", JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]).toString();

			MySQLResourcesAcquisition rs = new MySQLResourcesAcquisition();

			Connection connection = rs.getConnection();

			rs.getData().addAll(rs.getDataFromDb(connection, "artist", "name", ""));
			rs.getData().addAll(rs.getDataFromDb(connection, "opere", "operename", ""));

			if (type.equals("OpenNlp"))
			{
				File f = new File("src/models/it-ner-art.bin");
				TokenNameFinderModel model = new TokenNameFinderModel(f);
				AnnotationStrategy as = new OpenNlpAnnotator(model);
				as.annotatorStrategy("src/models/it-ner-art.bin", QuestionInput.insertInput());
			} else if (type.equals("PatternsMatching"))
			{
				AnnotationStrategy as = new RightMatchAnnotator(rs.getData(), rs.getMappedTypes());
				as.annotatorStrategy(new Interpreter(new File("src/core/annotator.py"), false), QuestionInput.insertInput());
			}

			connection.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void generateTestCase()
	{
		String[] options = { "src/models/modelSparql.txt", "src/models/model2.txt", "src/models/model.txt" };
		AnnotatorTest at = new AnnotatorTest(AnnotatorTest.OPEN_NLP_ANNOTATOR);
		at.generateTestCase(new File(JOptionPane.showInputDialog(null, "Select a model for test case", "GenTestCase", JOptionPane.INFORMATION_MESSAGE, null, options, options[0]).toString()), new File("src/models/testCase4.txt"));
	}

	private void extendsCorpora()
	{
		 DataFromSite.dataAcqusition();
		 //TODO MODELLO CON ARTICOLI
//		HashMap<String, String> articlesMap = DataFromSite.articlesMapSparQL();
	}

	private void trainModel()
	{
		String model = "src/models/model.txt";
		/*
		 * String [] options = {"type 1" , "type 2"}; String option =
		 * JOptionPane.showInputDialog(null, "Select parameters",
		 * "TrainParameters", JOptionPane.INFORMATION_MESSAGE, null, options,
		 * options[0]).toString();
		 * 
		 * switch (option) { case "type 1": GenerateModel gm = new
		 * GenerateModel(GenerateModel.MODEL1);
		 * gm.miniModelExecution(rs.getDataFromDb(rs.returnStaticConnection(),
		 * "artist", "name", ""), rs.getDataFromDb(rs.returnStaticConnection(),
		 * "opere", "operename", "")); break; case "type 2": GenerateModel gm2 =
		 * new GenerateModel(GenerateModel.MODEL2);
		 * gm2.miniModelExecution(rs.getDataFromDb(rs.returnStaticConnection(),
		 * "artist", "name", ""), rs.getDataFromDb(rs.returnStaticConnection(),
		 * "opere", "operename", "")); default: break; }
		 */
		OpenNlpAnnotator.trainModel(model, "src/models/it-ner-art.bin");
	}

	private void test()
	{
		String[] options = { "test 1", "test 2", "test 3", "test 4" };

		String option = JOptionPane.showInputDialog(null, "Select a test case", "TestCase", JOptionPane.INFORMATION_MESSAGE, null, options, options[0]).toString();

		AnnotatorTest at = new AnnotatorTest(AnnotatorTest.OPEN_NLP_ANNOTATOR);
		String file = "src/models/";
		switch (option)
		{
			case "test 1":
				file = file + "testCase.txt";
				break;
			case "test 2":
				file = file + "testCase2.txt";
				break;
			case "test 3":
				file = file + "testCase3.txt";
				break;
			case "test 4":
				file = file + "testCase4.txt";
				break;
			default:
				break;
		}
		at.readTestCase(new File(file));
		at.compute(false);
		at.evalutate(at.getAnnotationResults());
	}

}
