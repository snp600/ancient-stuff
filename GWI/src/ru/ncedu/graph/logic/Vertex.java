package ru.ncedu.graph.logic;

import java.util.List;

public class Vertex {

	private int id;
	private String name;
	private double delay;
	private List<Edge> edges;

	public Vertex(int id, String name, int delay) {
		this.id = id;
		this.name = name;
		this.delay = delay;
	}

	public Vertex(int id, String name, int delay, List<Edge> edges) {
		this.id = id;
		this.name = name;
		this.delay = delay;
		this.edges = edges;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getDelay() {
		return delay;
	}

	public void setDelay(int weight) {
		this.delay = weight;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
