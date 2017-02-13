package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

public class MySQLResourcesAcquisition implements ResourcesAcquisitionInterface
{
	private List<String> data;
	private String username = "root";
	private String password = "checkmate_10";
	private String dbAddress = "jdbc:mysql://127.0.0.1:3306/tesi";
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private HashMap<String, String> mappedTypes;
	private static Connection connection;

	public MySQLResourcesAcquisition()
	{
		this.data = new ArrayList<String>();
		this.mappedTypes = new HashMap<String, String>();
	}

	public void insertAllArtist(List<String> data, String table, Connection connection)
	{
		Set<String> noDuplicate = new HashSet<String>();
		for (String s : data)
		{
			noDuplicate.add(s);
		}

		for (String s : noDuplicate)
		{
			try
			{
				String insert = "insert into artist(name) values (?)";
				PreparedStatement statement = connection.prepareStatement(insert);
				statement.setString(1, s);
				statement.executeUpdate();
				System.out.println("inserisco");
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void insertAllOpere(List<String> data, String table, Connection connection)
	{
		Set<String> noDuplicate = new HashSet<String>();
		for (String s : data)
		{
			noDuplicate.add(s);
		}

		for (String s : noDuplicate)
		{
			try
			{
				String insert = "insert into opere(operename) values (?)";
				PreparedStatement statement = connection.prepareStatement(insert);
				statement.setString(1, s);
				statement.executeUpdate();
				System.out.println("inserisco");
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static MySQLResourcesAcquisition createResources()
	{
		MySQLResourcesAcquisition rs = new MySQLResourcesAcquisition();

		Connection connection2 = rs.getConnection();
		connection = connection2;
		rs.getData().addAll(rs.getDataFromDb(connection2, "artist", "name", ""));
		rs.getData().addAll(rs.getDataFromDb(connection2, "opere", "operename", ""));

		return rs;
	}

	public Connection returnStaticConnection()
	{
		return connection;
	}

	public List<String> getByFile(File file)
	{
		List<String> data = new ArrayList<>();
		String path = file.getAbsolutePath();
		try
		{
			data = new ArrayList<String>();
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			String s = br.readLine();
			while (s != null)
			{
				String[] model = s.split("::");
				data.add(model[0]);
				mappedTypes.put(model[0], model[1].toLowerCase());
				s = br.readLine();
			}
			br.close();
			fr.close();

		} catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "Errore durante la lettura dei dati");
		}
		return data;
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
			Statement cmd = connection.createStatement();
			String query = "SELECT * " + "FROM " + table;
			ResultSet res = cmd.executeQuery(query);
			while (res.next())
			{
				dataFromDb.add(res.getString(name));
				mappedTypes.put(res.getString(name), table.toLowerCase());
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
