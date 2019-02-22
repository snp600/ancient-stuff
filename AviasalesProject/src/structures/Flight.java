package structures;

public class Flight {

	private String flightID;
	private String depID;
	private String arrID;
	private String depTime;
	private String arrTime;
	private double cost;
	private int places;

	public Flight() {
	}

	public Flight(String flightID, String depID, String arrID) {
		this.setFlightID(flightID);
		this.setDepID(depID);
		this.setArrID(arrID);
	}

	public Flight(String flightID, String depID, String arrID, String depTime,
			String arrTime, double cost, int places) {
		this.setFlightID(flightID);
		this.setDepID(depID);
		this.setArrID(arrID);
		this.setDepTime(depTime);
		this.setArrTime(arrTime);
		this.setCost(cost);
		this.setPlaces(places);
	}

	/* getters and setters */
	public String getFlightID() {
		return flightID;
	}

	public void setFlightID(String flightID2) {
		this.flightID = flightID2;
	}

	public String getDepID() {
		return depID;
	}

	public void setDepID(String depID2) {
		this.depID = depID2;
	}

	public String getArrID() {
		return arrID;
	}

	public void setArrID(String arrID2) {
		this.arrID = arrID2;
	}

	public String getDepTime() {
		return depTime;
	}

	public void setDepTime(String depTime) {
		this.depTime = depTime;
	}

	public String getArrTime() {
		return arrTime;
	}

	public void setArrTime(String arrTime) {
		this.arrTime = arrTime;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getPlaces() {
		return places;
	}

	public void setPlaces(int places) {
		this.places = places;
	}

}
