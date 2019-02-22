package main;

import java.util.LinkedList;
import java.util.List;

import structures.Airport;
import structures.Flight;
import structures.World;

public class Aviasales {

	private List<Airport> airports;

	public Aviasales(List<Airport> airports) {
		this.setAirports(World.getWorld(airports).getAirports());
	}

	public void showOptions(List<List<Flight>> paths) {

		for (List<Flight> path : paths) {

			System.out.print(path.get(0).getDepID() + " -> ");
			System.out.print(path.get(1).getDepID() + " -> ");
			System.out.print(path.get(2).getDepID() + " -> ");
			System.out.println(path.get(2).getArrID());

		}
	}

	public List<List<Flight>> getExamplePath() {
		List<List<Flight>> path = new LinkedList<List<Flight>>();
		List<Flight> p1 = new LinkedList<Flight>();
		p1.add(airports.get(0).getFlights().get(0));
		p1.add(airports.get(1).getFlights().get(0));
		p1.add(airports.get(6).getFlights().get(0));

		List<Flight> p2 = new LinkedList<Flight>();
		p2.add(airports.get(0).getFlights().get(0));
		p2.add(airports.get(2).getFlights().get(0));
		p2.add(airports.get(5).getFlights().get(0));

		List<Flight> p3 = new LinkedList<Flight>();
		p3.add(airports.get(0).getFlights().get(0));
		p3.add(airports.get(2).getFlights().get(1));
		p3.add(airports.get(4).getFlights().get(0));

		List<Flight> p4 = new LinkedList<Flight>();
		p4.add(airports.get(0).getFlights().get(0));
		p4.add(airports.get(3).getFlights().get(0));
		p4.add(airports.get(4).getFlights().get(0));

		path.add(p1);
		path.add(p2);
		path.add(p3);
		path.add(p4);
		return path;
	}

	/* getters and setters */
	public List<Airport> getAirports() {
		return airports;
	}

	public void setAirports(List<Airport> airports) {
		this.airports = airports;
	}

}
