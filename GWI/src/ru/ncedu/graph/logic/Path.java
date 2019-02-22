package ru.ncedu.graph.logic;

public class Path {

	private int[] ids;
	private double[] distances;
	public int pathLength;

	public int[] getIds() {
		return ids;
	}

	public void setIds(int[] ids) {
		this.ids = ids;
		this.pathLength = ids.length;
	}

	public double[] getDistances() {
		return distances;
	}

	public void setDistances(double[] distances) {
		this.distances = distances;
	}

	public int getId(int index) {
		return ids[index];
	}

	public double getDistance(int index) {
		return distances[index];
	}

}
