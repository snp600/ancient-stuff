package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class GraphSetterDB {
	
	private Connection c;
	private Statement st;
	
	private int N; //amount of Vertices
	private int M; //amount of Edges
	
	public GraphSetterDB(int N, int M) {
		this.N = N;
		this.M = M;
		this.c = this.getConnection();
		try {
			this.st = c.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setGraph() {
		setOT();
		setAttributes();
		setObjects();
		setValues();
	}
	
	private Connection getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@127.0.0.1:1521:XE",
					"graphEAV",
					"graphEAV");
			return connection;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	private void setOT() {
		try {
			st.executeUpdate("INSERT INTO OBJECT_TYPES VALUES (1, 'Vertex')");
			st.executeUpdate("INSERT INTO OBJECT_TYPES VALUES (2, 'Edge')");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	private void setAttributes() {
		try {
			st.executeUpdate("INSERT INTO GRAPH_ATTRIBUTES VALUES (1, 1, 'weight')");
			st.executeUpdate("INSERT INTO GRAPH_ATTRIBUTES VALUES (2, 2, 'range')");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	private void setObjects() {
		int[][] edges = getEdges();
		String update = "INSERT INTO OBJECTS VALUES (?,?)";
		try {
			PreparedStatement ps = c.prepareStatement(update);
			//vertices
			ps.setInt(2, 1);
			for (int i = 0; i < N; i++) {
				ps.setInt(1, i + 1);
				ps.executeUpdate();
			}
			//edges
			ps.setInt(2, 2);
			for (int i = 0; i < M; i++) {
				ps.setInt(1, (edges[0][i] << 10) + edges[1][i]);
				ps.executeUpdate();
				ps.setInt(1, (edges[1][i] << 10) + edges[0][i]);
				ps.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void setValues() {
		String update = "INSERT INTO PARAMS VALUES (?, ?, ?)";
		try {
			PreparedStatement ps = c.prepareStatement(update);
			//vertices' weights
			ps.setInt(1, 1);
			double[] vertexWeight = getWeights();
			for (int i = 0; i < N; i++) {
				ps.setInt(2, i + 1);
				ps.setDouble(3, vertexWeight[i]);
				ps.executeUpdate();
			}
			//edges
			ps.setInt(1, 2);
			int[] edgeID = getOEdgesObjIds();
			double[] ranges = getRanges();
			for (int i = 0; i < 2 * M; i++) {
				ps.setInt(2, edgeID[i]);
				ps.setDouble(3, ranges[i]);
				ps.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private int[][] getEdges() {
		int[][] edges = new int[2][M];
		edges[0][0] = 1;
		edges[1][0] = 2;
		edges[0][1] = 1;
		edges[1][1] = 3;
		edges[0][2] = 1;
		edges[1][2] = 6;
		edges[0][3] = 2;
		edges[1][3] = 3;
		edges[0][4] = 2;
		edges[1][4] = 4;
		edges[0][5] = 3;
		edges[1][5] = 4;
		edges[0][6] = 3;
		edges[1][6] = 6;
		edges[0][7] = 4;
		edges[1][7] = 5;
		edges[0][8] = 5;
		edges[1][8] = 6;
		return edges;
	}
	
	private int[] getOEdgesObjIds() {
		int[] ids = new int[2 * M];
		ids[0] = 1026;
		ids[1] = 2049;
		ids[2] = 1027;
		ids[3] = 3073;
		ids[4] = 1030;
		ids[5] = 6145;
		ids[6] = 2051;
		ids[7] = 3074;
		ids[8] = 2052;
		ids[9] = 4098;
		ids[10] = 3076;
		ids[11] = 4099;
		ids[12] = 3078;
		ids[13] = 6147;
		ids[14] = 4101;
		ids[15] = 5124;
		ids[16] = 5126;
		ids[17] = 6149;
		return ids;
	}
	
	private double[] getRanges() {
		double[] range = new double[2 * M];
		range[0] = 7.0;
		range[1] = 7.0;
		range[2] = 9.0;
		range[3] = 9.0;
		range[4] = 14.0;
		range[5] = 14.0;
		range[6] = 10.0;
		range[7] = 10.0;
		range[8] = 15.0;
		range[9] = 15.0;
		range[10] = 11.0;
		range[11] = 11.0;
		range[12] = 2.0;
		range[13] = 2.0;
		range[14] = 6.0;
		range[15] = 6.0;
		range[16] = 9.0;
		range[17] = 9.0;
		return range;
	}
	
	private double[] getWeights() {
		double[] weight = new double[N];
		weight[0] = 5.7;
		weight[1] = 3.6;
		weight[2] = 7.8;
		weight[3] = 15.2;
		weight[4] = 13.9;
		weight[5] = 9.1;
		return weight;
	}
}
