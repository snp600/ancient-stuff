package sql;
/*
 * !!! For NON-EAV DATABASE 
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class GetterDB {
	
	private Connection c;
	private PreparedStatement psVW; //is used to get vertex's weight
	private PreparedStatement psEDGES; //is used to get nth vertex's edges with weights
	
	public GetterDB(Connection con) {
		this.c = con;
		try {
			this.psVW = c.prepareStatement("select WEIGHT from VERTEXES where VERTEX_ID = ?");
			this.psEDGES = c.prepareStatement("select FINISH_ID, DISTANCE from LINKS where START_ID = ?");	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("null")
	public double getWeight(int id) {
		try {
			psVW.setInt(1, id);
			ResultSet rs = psVW.executeQuery();
			rs.next();
			return rs.getDouble(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (Double) null;
	}
	
	public Map<Integer, Double> getEdges(int vertexId) {
		Map<Integer, Double> edges = new HashMap<Integer, Double>();
		try {
			psEDGES.setInt(1, vertexId);
			ResultSet rs = psEDGES.executeQuery();
			while(rs.next())
				edges.put(rs.getInt("FINISH_ID"), rs.getDouble("DISTANCE"));	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(edges);
		return edges;
	}
}
