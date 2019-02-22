package tools;

import java.util.List;

public class Vertex {

	private int id;
	// it contains ids of Vertices to which you can directly go from this vertex
	private List<Edge> edges;

	public Vertex(int id, List<Edge> edges) {
		this.setId(id);
		this.setEdges(edges);
	}

	/* getters and setters section */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}

}

