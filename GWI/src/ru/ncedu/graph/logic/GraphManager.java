package ru.ncedu.graph.logic;

import java.util.List;

import ru.ncedu.graph.logic.Edge;

public interface GraphManager {

	// get all vertices from database
	public List<Vertex> getVertices();

	// dijkstra
	public Path getPath(int start, int destination);

	// get all edges of vertex with id == 'id'
	public List<Edge> getEdges(int id);

	// DataExporter.java
	public void setVertices();

	public boolean vertexExist(int vertexId);

	public boolean edgeExist(int startId, int finishId);

	public void addVertex(int vertexIdToAdd, double vertexDelayToAdd);

	public void deleteVertex(int vertexIdToDelete);

	// startAdd - start's id
	public void addEdge(int startAdd, int finishAdd, double range);

	// -//-
	public void deleteEdge(int startDelete, int finishDelete);
}
