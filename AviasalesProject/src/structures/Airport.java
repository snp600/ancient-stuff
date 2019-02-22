package structures;

import java.util.List;

public class Airport {

	private int key;
	private String id;
	private List<Flight> flights;

	public Airport(int key, String id, List<Flight> flights) {
		this.setKey(key);
		this.setId(id);
		this.setFlights(flights);
	}

	/* getters and setters */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Flight> getFlights() {
		return flights;
	}

	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

}
