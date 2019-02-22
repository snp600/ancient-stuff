package ru.ncedu.graph.ui;

import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import ru.ncedu.graph.logic.GraphManager;
import ru.ncedu.graph.logic.RandomDataGenerator;
import ru.ncedu.graph.logic.Vertex;

@ManagedBean(name = "dataExporter")
@RequestScoped
public class DataExporter {

	private List<Vertex> vertices;

	public List<Vertex> getVertices() {
		return vertices;
	}

	public void setVertices(List<Vertex> vertices) {
		this.vertices = vertices;
	}

	@PostConstruct
	public void getVerticesFromDatabase() {
		GraphManager gt = new RandomDataGenerator();
		vertices = gt.getVertices();
	}
}
