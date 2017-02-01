package core;

import java.io.File;
import java.sql.Connection;

import opennlp.tools.namefind.TokenNameFinderModel;
import utility.MySQLResourcesAcquisition;
import utility.QuestionInput;

public class ApplicationManager
{
	public void execute(String type){
		try
		{
			MySQLResourcesAcquisition rs = new MySQLResourcesAcquisition();

			Connection connection = rs.getConnection();
			
			rs.getData().addAll(rs.getDataFromDb(connection, "artist", "name", ""));
			rs.getData().addAll(rs.getDataFromDb(connection, "opere", "operename", ""));
		
//			rs.getData().addAll(rs.getByFile(new File("src/core/modello")));
			
			if (type.equals("OpenNlp"))
			{
				GenerateModel gm = new GenerateModel(GenerateModel.MODEL1);
				gm.execute(rs.getDataFromDb(connection, "artist", "name", ""),rs.getDataFromDb(connection,  "opere", "operename", ""));
				OpenNlpAnnotator.trainModel("src/models/model.txt", "src/models/it-ner-art.bin");
				File f = new File("src/models/it-ner-art.bin");
				TokenNameFinderModel model = new TokenNameFinderModel(f);
				AnnotationStrategy as = new OpenNlpAnnotator(model);
				as.annotatorStrategy("src/models/it-ner-art.bin", QuestionInput.insertInput());
			}
			else 
			{
				AnnotationStrategy as = new RightMatchAnnotator(rs.getData(), rs.getMappedTypes());
				as.annotatorStrategy(new Interpreter(new File("src/core/annotator.py"),false) , QuestionInput.insertInput());
			}
			
			connection.close();
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
