package tools;

public class Edge {

	private int dep;
	private int dest;
	private double weight;

	public Edge(int dep, int dest, double weight) {
		this.setDep(dep);
		this.setDest(dest);
		this.setWeight(weight);
	}

	/* getters and setters section */
	public int getDep() {
		return dep;
	}

	public void setDep(int dep) {
		this.dep = dep;
	}

	public int getDest() {
		return dest;
	}

	public void setDest(int dest) {
		this.dest = dest;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

}