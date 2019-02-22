package structures;

import java.util.List;

public class World {

	private static World world;
	private List<Airport> airports;

	// singleton class
	private World(List<Airport> airportsDB) {
		this.setAirports(airportsDB);
	}

	public static World getWorld(List<Airport> airportsDB) {
		if (world == null)
			world = new World(airportsDB);
		return world;
	}

	/* getters and setters */
	public List<Airport> getAirports() {
		return this.airports;
	}

	public void setAirports(List<Airport> airports) {
		this.airports = airports;
	}

	public Airport findAirport(String from) {
		for (Airport a : airports)
			if (a.getId().equals(from))
				return a;
		return null;
	}

}
