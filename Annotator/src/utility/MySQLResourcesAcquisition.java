package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;

public class MySQLResourcesAcquisition implements ResourcesAcquisitionInterface
{
	private List<String> data;
	private String username="root";
	private String password="checkmate_10";
	private String dbAddress="jdbc:mysql://127.0.0.1:3306/tesi";
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private HashMap <String,String> mappedTypes;
	
	public MySQLResourcesAcquisition()
	{
		this.data = new ArrayList<String>();
		this.mappedTypes = new HashMap<String,String>();
	}

	public void getByFile(File file)
	{
		String path = file.getAbsolutePath();
		try
		{
			data = new ArrayList<String>();
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			while (br.readLine() != null)
			{
				String [] model = br.readLine().split("::");
				data.add(model[0]);
				mappedTypes.put(model[0].toLowerCase(), model[1].toLowerCase());
			}
			br.close();
			fr.close();

		} catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "Errore durante la lettura dei dati");
		}
	}

	
	public Connection getConnection()
	{
		
		Connection connection = null;
		try
		{
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(this.dbAddress, this.username, this.password);
		} catch (SQLException | ClassNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return connection;
	}

	/*
	@Override
	public List<String> getOpere(Connection connection, String table, String name)
	{
		List<String> opere = new ArrayList<String>();
		try
		{
			Statement cmd= connection.createStatement();;
			String query = "SELECT "+ name +" FROM "+ table;
			ResultSet res = cmd.executeQuery(query);
			while (res.next())
			{
				opere.add(res.getString(1));
			}
			
		} catch (SQLException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		return opere;
	}

	@Override
	public List<String> getArtists(Connection connection, String table, String name)
	{
		List<String> artist = new ArrayList<String>();
		try
		{
			Statement cmd= connection.createStatement();;
			String query = "SELECT "+ name +" FROM "+ table;
			ResultSet res = cmd.executeQuery(query);
			while (res.next())
			{
				artist.add(res.getString(1));
			}
			
		} catch (SQLException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		return artist;
	}
*/
	public List<String> getData()
	{
		return data;
	}

	public void setData(List<String> data)
	{
		this.data = data;
	}

	@Override
	public List<String> getDataFromDb(Connection connection, String table, String name, String type)
	{
		List<String> dataFromDb = new ArrayList<String>();
		try
		{
			Statement cmd= connection.createStatement();
			String query = "SELECT * " + "FROM "+ table;
			ResultSet res = cmd.executeQuery(query);
			while (res.next())
			{
				dataFromDb.add(res.getString(name).toLowerCase());
				mappedTypes.put(res.getString(name).toLowerCase(), table.toLowerCase());
			}
			
		} catch (SQLException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		return dataFromDb;
	}

	public HashMap<String, String> getMappedTypes()
	{
		return mappedTypes;
	}

	public void setMappedTypes(HashMap<String, String> mappedTypes)
	{
		this.mappedTypes = mappedTypes;
	}
	

}
