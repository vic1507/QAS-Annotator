package core;

import java.io.File;
import java.sql.Connection;

import utility.MySQLResourcesAcquisition;

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
				GenerateModel gm = new GenerateModel();
				gm.execute(rs.getDataFromDb(connection, "artist", "name", ""),rs.getDataFromDb(connection,  "opere", "operename", ""));
				AnnotationStrategy as = new OpenNlpAnnotator("src/core/it-ner-art.bin", "src/core/model.txt");
				as.annotatorStrategy("src/core/it-ner-art.bin.bin");
			}
			else 
			{
				AnnotationStrategy as = new RightMatchAnnotator(rs.getData(), rs.getMappedTypes());
				as.annotatorStrategy(new Interpreter(new File("src/core/annotator.py")) );
			}
			
			connection.close();
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
