package tools;

import java.io.*;
import java.util.LinkedList;

import structures.Airport;
import structures.Flight;

public class Parser {

	@SuppressWarnings("resource")
	public static LinkedList<Airport> parse(String airportsFile)
			throws IOException {
		BufferedReader bufRAirports = new BufferedReader(new FileReader(
				new File("input.txt")));
		LinkedList<Airport> airpList = new LinkedList<Airport>();
		String airportString;
		int i = 0;
		while ((airportString = bufRAirports.readLine()) != null) {
			i++;
			String[] airportData = airportString.split(" ");

			LinkedList<Flight> flights = new LinkedList<Flight>();
			Airport air = new Airport(i, airportData[0], flights);
			airpList.add(air);
			String flight;
			BufferedReader bufRFlights = new BufferedReader(new FileReader(
					new File("Airports/" + airportData[0] + ".txt")));
			while ((flight = bufRFlights.readLine()) != null) {

				String[] flightData = flight.split(" ");
				// Объект рейс // da ladno 0))0)
				Flight flght = new Flight();
				flght.setCost(Double.parseDouble(flightData[1]));
				flights.add(flght);
			}

			air.setFlights(flights);
		}

		return airpList;
	}

}
