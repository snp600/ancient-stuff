package graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;

import vertex.Vertex;
import sql.DatabaseConnection;

public class Graph {
	private int N; // number of vertexes
	private Map<Integer, Vertex> vertexes;

	public Graph() {
		vertexes = new HashMap<Integer, Vertex>();
		this.N = 0;
	}

	public void addVertex(Integer id) {
		vertexes.put(id, new Vertex(id));
	}
	
	public void addVertexes(int amountOfVertexes) { //
		for (int i = 1; i <= amountOfVertexes; i++)
			vertexes.put(i, new Vertex(i));
		N = amountOfVertexes;
	}

	public void addBridge(Integer x, Integer y, Double value) {
		vertexes.get(x).addBridge(y, value); // x ---> y
		vertexes.get(y).addBridge(x, value); // y ---> x
	}

	public void addDirectedBridge(Integer x, Integer y, Double value) {
		vertexes.get(x).addBridge(y, value); // x ---> y
	}

	public void setVertexWeight(Integer id, Double w) {
		vertexes.get(id).setWeight(w);
	}
	// public void addBridges(Integer[] ids1, Integer[] ids2, Double[] values) {
	// if (ids1.length != ids2.length || values.length != ids1.length)
	// throw new IllegalArgumentException();
	// int N = values.length;
	// for (int i = 1; i <= N; i++) {
	// vertexes.get(ids1[i]).addBridge(ids2[i], values[i]); // ids1 ---> ids2
	// vertexes.get(ids2[i]).addBridge(ids1[i], values[i]); // ids2 ---> ids1
	// }
	// }

	public void removeVertex(Integer id) {
		for (int i = 0; i < N; i++)
			vertexes.get(i).removeBridge(id); // burn all bridges
		vertexes.remove(id);
		N--;
	}

	public void setGraphFromDatabase(String url, String username, String password) {
		int N = 0; //amount of vertexes
		DatabaseConnection dc = new DatabaseConnection(url, username, password);
		ResultSet rs = dc.getData("vertexes");
		try {
			while(rs.next())
				N++;
			this.addVertexes(N);
			rs.first();
			while(rs != null) {
//				System.out.println(rs.getInt(1));
//				System.out.println(rs.getDouble(2));
				this.setVertexWeight(rs.getInt(1), rs.getDouble(2));
				if (!rs.next())
					break;
			}
			rs = (new DatabaseConnection(url, username, password)).getData("links");
			rs.first();
//			System.out.println(N);
			while(rs != null) {
				this.addDirectedBridge(rs.getInt(2), rs.getInt(3), rs.getDouble(4));
				if (!rs.next())
					break;
			}
		} catch (SQLException e) {
			System.out.println("SQLException\n" + e.getMessage());
		}	
	}
	public void setGraphByAdjacencyMatrix(Double[][] m) { // with values!
		vertexes.clear();
		int length = m.length;
		addVertexes(length);
		for (int i = 0; i < length; i++)
			for (int j = i + 1; j < length; j++)
				if (m[i][j] > 0)
					addBridge(i + 1, j + 1, m[i][j]);
	}

	public void setBridgesFromKeyboard() { /*
											 * format: number of bridges id1,
											 * id2, value ...
											 */
		@SuppressWarnings("resource")
		Scanner console = new Scanner(System.in);
		Integer amountOfBridges = console.nextInt();
		for (int i = 0; i < amountOfBridges; i++)
			addBridge(console.nextInt(), console.nextInt(),
					console.nextDouble());
	}

	public void setVertexesWeightsFromKeyboard() { /*
													 * format: number of
													 * vertexes (new vertexes
													 * won't be created) id,
													 * value ...
													 */
		@SuppressWarnings("resource")
		Scanner console = new Scanner(System.in);
		Integer amountOfVertexes = console.nextInt();
		for (int i = 0; i < amountOfVertexes; i++)
			vertexes.get(console.nextInt()).setWeight(console.nextDouble());
	}

	public void showAllBridges() { // with values!
		Iterator<Entry<Integer, Vertex>> it1 = vertexes.entrySet().iterator();
		while (it1.hasNext()) {

			Map.Entry<Integer, Vertex> currentVertex = it1.next();

			Map<Integer, Double> bridgesOfCurrentVertex = currentVertex
					.getValue().getBridges();
			Iterator<Entry<Integer, Double>> it2 = bridgesOfCurrentVertex
					.entrySet().iterator();

			while (it2.hasNext()) { // print all bridges of current vertex
				Map.Entry<Integer, Double> currentBridge = it2.next();
				System.out.print("from ");
				System.out.print(currentVertex.getValue().getId());
				System.out.print(" to ");
				System.out.print(currentBridge.getKey());
				System.out.print(" is ");
				System.out.println(currentBridge.getValue());
			}
		}
	}

	public void showWeights() {
		for (Map.Entry<Integer, Vertex> vertex : vertexes.entrySet()) {
			System.out.println("id: " + vertex.getKey() + " weight: "
					+ vertex.getValue().getWeight());
		}
	}

	public Map<Integer, Vertex> getVertexes() {
		return this.vertexes;
	}

}
