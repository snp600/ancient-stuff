package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

public class DatabaseConnection {
	
	final private static String driverName = "oracle.jdbc.driver.OracleDriver";
	private String url;
//	final private static String server = "127.0.0.1";
//	final private static String port = "1521";
//	final private static String sid = "XE";
	private String username = "graph";
	private String password = "graph";
	private Connection connection;
	
	public DatabaseConnection(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public ResultSet getData(String key) {
		try {
//			url = "jdbc:oracle:thin:@" + server + ":" + port + ":" + sid;
//			System.out.println(url);
			Locale.setDefault(Locale.ENGLISH);
			Class.forName(driverName);
			connection = DriverManager.getConnection(url, username, password);
			System.out.println("connecting: " + url);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException");
		} catch (SQLException e) {
			System.out.println("SQLException\n" + e.getMessage());
		}
		Statement statement = null;
		try {
			statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			System.out.println("SQLException\n" + e.getMessage());
		}
//		ResultSetMetaData metaData = resultSet.getMetaData();
//		while(resultSet.next()) {
//			for (int i = 1; i <= metaData.getColumnCount(); i++) {
//				System.out.print(resultSet.getString(i) + " ");
//			}
//			System.out.println();
//		}
		try {
			if (key.equals("links"))
				return statement.executeQuery("SELECT * FROM links");
			else if (key.equals("vertexes"))
				return statement.executeQuery("SELECT * FROM vertexes");
			else 
				throw new IllegalArgumentException();
		} catch (SQLException e) {
			System.out.println("SQLException\n" + e.getMessage());
		}
		return null;
	}
}
