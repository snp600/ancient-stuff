package tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Dijkstra {

	private Map<Integer, Double> marks;
	private Map<Integer, Boolean> visited;
	private List<Vertex> vertices;

	public Dijkstra(List<Vertex> vertices) {
		this.vertices = vertices;
		this.marks = new HashMap<Integer, Double>();
		this.visited = new HashMap<Integer, Boolean>();
		for (int i = 0; i < this.vertices.size(); i++) {
			this.marks.put(vertices.get(i).getId(), Double.MAX_VALUE);
			this.visited.put(vertices.get(i).getId(), false);
		}
	}

	public Map<Integer, Double> getPaths(int dep) {
		int n = this.marks.size();
		marks.put(dep, 0.0);

		for (int i = 0; i < n - 1; i++) {
			int curDepVert = findMinMark();

			List<Edge> edges = getVertexById(curDepVert).getEdges();

			for (int j = 0; j < edges.size(); j++) {
				int curDestVert = edges.get(j).getDest();
				// 'mpew' == mark plus edge' weight
				Double mpew = edges.get(j).getWeight() + marks.get(curDepVert);
				// 'cdm' == current destination mark
				Double cdm = marks.get(curDestVert);
				if (mpew < cdm)
					marks.put(curDestVert, mpew);
			}
		}
		
		
		return marks;
	}

	private int findMinMark() {
		double min = Double.MAX_VALUE;
		int id = -1;
		for (Entry<Integer, Double> mark : this.marks.entrySet())
			if ((mark.getValue() <= min) && (visited.get(mark.getKey()) == false)) {
				min = mark.getValue();
				id = mark.getKey();
			}
		visited.put(id, true);
		return id;
	}

	private Vertex getVertexById(int id) {
		for (Vertex vertex : this.vertices)
			if (vertex.getId() == id)
				return vertex;
		return null;
	}
}
