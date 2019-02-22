package ru.ncedu.graph.logic;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import ru.ncedu.graph.logic.Edge;

public class RandomDataGenerator implements GraphManager {

	private static List<Vertex> vertices;
	private Path path;
	private List<Edge> edges;

	@Override
	public void setVertices() {
		final int N = 5;
		vertices = new LinkedList<Vertex>();
		for (int i = 0; i < N; i++)
			vertices.add(new Vertex((int) (Math.random() * 1000000), UUID
					.randomUUID().toString().substring(0, 8), (int) (Math
					.random() * 100)));
	}

	@Override
	public List<Vertex> getVertices() {
		if (vertices == null)
			this.setVertices();
		return vertices;
	}

	@Override
	public Path getPath(int start, int destination) {
		final int M = 8;
		int[] ids = new int[M];
		double[] distances = new double[M];
		ids[0] = 777;
		distances[0] = 0;
		for (int i = 1; i < M; i++) {
			ids[i] = (int) (Math.random() * 1000 + 1000);
			distances[i] = (int) (Math.random() * 20 + 5);
		}
		path = new Path();
		path.setIds(ids);
		path.setDistances(distances);
		return path;
	}

	@Override
	public List<Edge> getEdges(int id) {
		final int L = (int) (int) (Math.random() * 4 + 3);
		edges = new LinkedList<Edge>();
		for (int i = 0; i < L; i++) {
			int ID1 = (int) (Math.random() * 1000 + 1000);
			int ID2 = (int) (Math.random() * 1000 + 1000);
			int distance = (int) (Math.random() * 30 + 10);
			edges.add(new Edge(ID1, ID2, distance));
		}
		return edges;
	}

	@Override
	public boolean vertexExist(int vertexId) {
		return false;
	}

	@Override
	public boolean edgeExist(int startId, int finishId) {
		return false;
	}

	@Override
	public void addVertex(int vertexIdToAdd, double vertexDelayToAdd) {
	}

	@Override
	public void deleteVertex(int vertexIdToDelete) {
	}

	@Override
	public void addEdge(int startAdd, int finishAdd, double range) {
	}

	@Override
	public void deleteEdge(int startDelete, int finishDelete) {
	}

}
