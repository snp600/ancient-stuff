package algorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import tools.Criterias;
import vertex.Vertex;

public class Dijkstra {

	/* DIJKSTRA */
	/* returns the lowest ranges (by criteria) from vertex 'id' to all vertexes */
	private static Map<Integer, Vertex> vertexes; // copy

	private static void createCopyOfGraph(Map<Integer, Vertex> vertexezz) {
		vertexes = new HashMap<Integer, Vertex>();
		for (Entry<Integer, Vertex> cv : vertexezz.entrySet()) {
			Vertex tempVertex = new Vertex(cv.getValue().getId());
			tempVertex.setWeight(cv.getValue().getWeight());
			for (Entry<Integer, Double> cb : cv.getValue().getBridges()
					.entrySet())
				tempVertex.addBridge(cb.getKey(), cb.getValue());
			vertexes.put(cv.getValue().getId(), tempVertex);
		}
	}

	public static void addDirectedBridge(Integer x, Integer y, Double value) {
		vertexes.get(x).addBridge(y, value); // x ---> y
	}

	public static Map<LinkedList<Integer>, Double> dijkstra(Integer id,
			Criterias criteria, Map<Integer, Vertex> vertexezz) {
		createCopyOfGraph(vertexezz);
		modifyGraph(criteria);
		Integer N = vertexes.size();
		Map<Integer, Boolean> visited = InitializeVisited();
		Map<Integer, Double> mark = InitializeMark();
		Map<Integer, List<Integer>> way = new HashMap<Integer, List<Integer>>();
		way.put(id, new LinkedList<Integer>());
		way.get(id).add(id);
		mark.put(id, 0.0);
		for (int i = 0; i < N - 1; i++) {
			Integer currentID = findMinimal(mark, visited); // id of current
															// vertex
			for (Entry<Integer, Double> currentBridge : vertexes.get(currentID)
					.getBridges().entrySet()) {
				Integer bridgeID = currentBridge.getKey(); /*
															 * id if bridge is
															 * id of vertex
															 * which we go from
															 * 'CurrentID'
															 * vertex
															 */
				Double distance = mark.get(currentID)
						+ currentBridge.getValue(); // currentID's mark + value
													// of bridge
				if (distance < mark.get(bridgeID)) {
					mark.put(bridgeID, distance);
					List<Integer> _way = new LinkedList<Integer>(
							way.get(currentID));
					_way.add(bridgeID);
					way.remove(bridgeID); // to prevent from occurance of extra
											// links
					way.put(bridgeID, _way);
				}

			}
			visited.remove(currentID, false);
			visited.put(currentID, true); // replace with (currentID, false)
		}
		mark.remove(id);
		way.remove(id);
		Map<LinkedList<Integer>, Double> ret = new HashMap<LinkedList<Integer>, Double>();
		Set<Integer> keys = way.keySet();
		for (int key : keys)
			ret.put((LinkedList<Integer>) way.get(key), mark.get(key));
		return ret;
	}

	public static Map<Integer, Boolean> InitializeVisited() { // this method is
																// only for
																// 'dijkstra'
																// method
		Map<Integer, Boolean> visited = new HashMap<Integer, Boolean>();
		for (Entry<Integer, Vertex> currentVertex : vertexes.entrySet())
			visited.put(currentVertex.getKey(), false);
		return visited;
	}

	public static Map<Integer, Double> InitializeMark() { // this method is only
															// for 'dijkstra'
															// method
		Map<Integer, Double> mark = new HashMap<Integer, Double>();
		final Double giantConstant = 1.0E+45;
		for (Entry<Integer, Vertex> currentVertex : vertexes.entrySet())
			mark.put(currentVertex.getKey(), giantConstant);
		return mark;
	}

	public static Integer findMinimal(Map<Integer, Double> mark,
			Map<Integer, Boolean> visited) {
		Double min = -1.0;
		Integer id = -1;
		for (Entry<Integer, Double> currentMark : mark.entrySet())
			if (visited.get(currentMark.getKey()) == false) {
				min = currentMark.getValue();
				id = currentMark.getKey();
				break;
			}
		for (Entry<Integer, Double> currentMark : mark.entrySet())
			if (visited.get(currentMark.getKey()) == false
					&& currentMark.getValue() < min) {
				min = currentMark.getValue();
				id = currentMark.getKey();
			}
		return id;
	}

	public static void modifyGraph(Criterias criteria) {
		for (Entry<Integer, Vertex> vertex : vertexes.entrySet())
			for (Map.Entry<Integer, Double> bridge : vertex.getValue()
					.getBridges().entrySet())
				addDirectedBridge(
						vertex.getKey(),
						bridge.getKey(),
						criteria.getWeight(bridge.getValue(),
								vertexes.get(bridge.getKey()).getWeight()));
	}
	/* DIJKSTRA END */
}
