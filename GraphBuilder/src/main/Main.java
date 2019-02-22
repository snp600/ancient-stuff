package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import interactive.Interactive;
import algorithms.Dijkstra;
import graph.Graph;
import sql.DatabaseConnection;
import sql.GetterDB;
import sql.GraphSetterDB;
import tools.Criterias;

public class Main {

	public static void main(String[] args) {
		workbench();
	}
	
	public static void workbench() {
		Graph g = new Graph();
		GraphSetterDB gs = new GraphSetterDB(6, 9);
		gs.setGraph();
		
		
//		test();
//		Integer a = new Integer(5);
//		System.out.println(a << 10);
		/* this configuration is saved in file config.txt */
		
//		Double[][] m = { { 0.0, 7.0, 9.0, 0.0, 0.0, 14.0 },
//				{ 7.0, 0.0, 10.0, 15.0, 0.0, 0.0 },
//				{ 9.0, 10.0, 0.0, 11.0, 0.0, 2.0 },
//				{ 0.0, 15.0, 11.0, 0.0, 6.0, 0.0 },
//				{ 0.0, 0.0, 0.0, 6.0, 0.0, 9.0 },
//				{ 14.0, 0.0, 2.0, 0.0, 9.0, 0.0 }, };
//		g.setGraphByAdjacencyMatrix(m);
//		g.setVertexWeight(1, 5.7);
//		g.setVertexWeight(4, 8.3);
//		g.setVertexWeight(5, 14.5);
		/*		
		 * SQL connection test									   	*/
//		String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
//		String username = "graph";
//		String password = "graph";
//		g.setGraphFromDatabase(url, username, password);
//		Interactive inter = new Interactive(g);
//		inter.parseCommand("save as configSQLtest.txt");
		
//		Interactive i = new Interactive(g);
//		i.parseCommand("load config.txt");
//		
//		System.out.println("criteria: bridges' range");
//		System.out.println(Dijkstra.dijkstra(1, Criterias.RANGE,
//				g.getvertices()));
//		System.out.println();
/*
		System.out.println("criteria: amount of visited vertices");
		System.out.println(Dijkstra.dijkstra(1, Criterias.QUANTITY,
				g.getvertices()));
		System.out.println();
		
		i.parseCommand("save as config2.txt"); //generally it's copy of config.txt
		
		i.parseCommand("add bridge 231231.0 from 3 to 5");
		i.parseCommand("add vertex 17");
		
		i.parseCommand("save as config3.txt"); //modified graph
		*/
		
		// Graph gg = new Graph(); //example in 'readme.txt'
		// gg.addvertices(6);
		// gg.setverticesWeightsFromKeyboard();
		// gg.setBridgesFromKeyboard();
		// System.out.println("criteria: vertices' weight (delay)");
		// System.out.println(gg.dijkstra(1, 2));
	}
	
	public static void test() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@127.0.0.1:1521:XE",
					"graph",
					"graph");
			
			GetterDB gdb = new GetterDB(connection);
			gdb.getEdges(1);
			PreparedStatement ps = 
					connection.prepareStatement("SELECT * FROM vertices WHERE VERTEX_ID < ?");
//			ps.setString(1, "vertices");
			ps.setInt(1, 5);
			ResultSet rs = ps.executeQuery();
//			while(rs.next())
//				System.out.println("ABC");
			rs.next();
			System.out.println(rs.getDouble(1));
//			rs.next();
//			System.out.println(rs.getDouble(1));

			
			// /**********************************************************/

			/* works
			PreparedStatement ps1 = 
					connection.prepareStatement("UPDATE links SET DISTANCE = ? WHERE EDGE_ID = ?");
			ps1.setInt(2, 3);
			ps1.setDouble(1, 14.0);
			ps1.executeUpdate();
			*/ /**********************************************************/
			
			/* works 
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs2 = statement.executeQuery("SELECT WEIGHT FROM vertices WHERE VERTEX_ID = 3");
			rs2.first();
			System.out.println(rs2.getString(1));
			*/ /**********************************************************/
			
		} catch (SQLException e) {
			System.out.println("SQLException");
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println("other");
		}
	}
}
