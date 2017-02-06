package utility;

import java.io.File;
import java.sql.Connection;
import java.util.List;

public interface ResourcesAcquisitionInterface
{
	List<String> getDataFromDb (Connection connection, String table, String name, String type);
	List<String> getByFile (File file);
	Connection getConnection();
}
