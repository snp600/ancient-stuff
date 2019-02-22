package vertex;

import java.util.HashMap;
import java.util.Map;

public class Vertex {
	private Integer id;
	private String name;
	private Double weight;
	private Map<Integer, Double> bridges;
	private String[] data;

	public Vertex(Integer id) {
		this.id = id;
		this.weight = 0.0;
		bridges = new HashMap<Integer, Double>();
	}

	/* 'set' methods */
	public void setName(String name) {
		this.name = name;
	}

	public void setWeight(Double w) {
		this.weight = w;
	}

	public void addBridge(Integer id, Double value) {
		this.bridges.put(id, value);
	}

	public void addBridges(Map<Integer, Double> bridges) {
		this.bridges = bridges;
	}

	public void addBridges(Integer[] ids, Double[] values) {
		if (ids.length != values.length)
			throw new IllegalArgumentException();
		for (int i = 0; i < ids.length; i++)
			bridges.put(ids[i], values[i]);
	}

	/* 'get' methods */
	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Double getWeight() {
		return this.weight;
	}

	public Map<Integer, Double> getBridges() {
		return this.bridges;
	}

	public String[] getData() {
		return this.data;
	}

	/*               */

	public void removeBridge(Integer id) {
		bridges.remove(id);
	}
}
