package tools;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import structures.Airport;
import structures.Flight;
import structures.World;

public class BFS {

	public int Fire[];
	public Airport startV;
	public Airport finishV;
	public int count;
	public int answer;
	public int index;
	public Airport current;

	private List<Airport> airports;

	public BFS() {
		this.setAirports(getExampleGraph());
	}

	public BFS(List<Airport> airports) {
		this.setAirports(airports);
	}

	public ArrayList<Airport> doBFS(String from, String to, World aviaWorld) {
		ArrayList<Airport> BFSQueue = new ArrayList<Airport>();
		startV = aviaWorld.findAirport(from);
		finishV = aviaWorld.findAirport(to);
		Fire = new int[aviaWorld.getAirports().size()];
		for (int i = 0; i < aviaWorld.getAirports().size(); i++) {
			Fire[i] = -1;
		}
		BFSQueue.add(startV);
		index = 0;
		current = BFSQueue.get(index);
		Fire[startV.getKey()] = 0;
		Airport[] parents = firing(BFSQueue, aviaWorld);
		ArrayList<Airport> path = findPath(parents, aviaWorld);
		return path;
	}

	public Airport[] firing(ArrayList<Airport> BFSQueue, World aviaWorld) {
		Airport[] parents = new Airport[aviaWorld.getAirports().size()];
		while (Fire[finishV.getKey()] == -1 && index < BFSQueue.size()) {
			for (int i = 0; i < current.getFlights().size(); i++) {
				Airport newAirport = BFSQueue.get(0);
				if (Fire[newAirport.getKey()] == -1) {
					Fire[newAirport.getKey()] = Fire[current.getKey()] + 1;
					BFSQueue.add(newAirport);
					parents[newAirport.getKey()] = current;
				}
			}
			index++;
			if (index < BFSQueue.size())
				current = BFSQueue.get(index);
		}
		if (Fire[finishV.getKey()] != -1) {
			return parents;
		} else
			return null;
	}

	public ArrayList<Airport> findPath(Airport[] parents, World aviaWorld) {
		if (parents != null) {
			ArrayList<Airport> invPath = new ArrayList<Airport>();
			ArrayList<Airport> path = new ArrayList<Airport>();
			Airport curV = finishV;
			invPath.add(curV);
			while (curV != startV) {
				curV = parents[parents.length];
				invPath.add(curV);
			}

			for (int i = 0; i < invPath.size(); i++) {
				path.add(invPath.get(invPath.size() - i - 1));
			}
			return path;
		} else
			return null;
	}

	public ArrayList<ArrayList<Airport>> findAllPath1(String from, String to,
			int numPath, World aviaWorld) {
		ArrayList<ArrayList<Airport>> allPath = new ArrayList<ArrayList<Airport>>();
		World tmpWorld = aviaWorld;
		allPath.add(doBFS(from, to, aviaWorld));
		for (int i = 1; i < allPath.get(numPath).size(); i++) {
			int ind = 0;
			Airport airFrom = allPath.get(numPath).get(i - 1);
			tmpWorld.getAirports().get(airFrom.getKey()).getFlights()
					.remove(ind);
			aviaWorld = tmpWorld;
			allPath.add(doBFS(from, to, aviaWorld));
		}
		return allPath;
	}

	public ArrayList<ArrayList<Airport>> findAllPath2(String from, String to,
			int numEl, World aviaWorld) {
		ArrayList<ArrayList<Airport>> allPath = new ArrayList<ArrayList<Airport>>();
		World tmpWorld = aviaWorld;
		aviaWorld = tmpWorld;
		allPath.add(doBFS(from, to, aviaWorld));
		int i = 0;
		boolean flag = true;
		while (flag) {
			i++;
			int ind = 0;
			Airport airFrom = allPath.get(i - 1).get(numEl);
			tmpWorld.getAirports().get(airFrom.getKey()).getFlights()
					.remove(ind);

			if (doBFS(from, to, tmpWorld) != null)
				allPath.add(doBFS(from, to, tmpWorld));
			else
				flag = false;
		}
		return allPath;
	}

	public boolean compare(ArrayList<Airport> list1, ArrayList<Airport> list2) {

		boolean result = true;
		if (list1.size() != list2.size()) {
			return false;
		} else {
			for (int i = 0; i < list1.size(); i++) {
				if (!list1.get(i).getId().equals(list2.get(i).getId())) {
					result = false;
					break;
				}
			}
		}
		return result;
	}

	public ArrayList<ArrayList<Airport>> findAllPath(String from, String to,
			World aviaWorld) {
		ArrayList<ArrayList<Airport>> mainAllPath = new ArrayList<ArrayList<Airport>>();
		ArrayList<ArrayList<Airport>> allPath1 = findAllPath1(from, to, 0,
				aviaWorld);
		ArrayList<ArrayList<Airport>> allPath2 = findAllPath2(from, to, 0,
				aviaWorld);
		boolean isNew;
		for (int i = 0; i < allPath1.size(); i++) {
			isNew = true;
			if (allPath1.get(i) != null) {
				for (int j = 0; j < mainAllPath.size(); j++) {
					if (compare(allPath1.get(i), mainAllPath.get(j))) {
						isNew = false;
						break;
					}
				}
				if (isNew) {
					mainAllPath.add(allPath1.get(i));
				}
			}
		}
		for (int i = 0; i < allPath2.size(); i++) {
			isNew = true;
			if (allPath2.get(i) != null) {
				for (int j = 0; j < mainAllPath.size(); j++) {

					if (compare(allPath2.get(i), mainAllPath.get(j))) {
						isNew = false;
						break;
					}
				}
				if (isNew) {
					mainAllPath.add(allPath2.get(i));
				}
			}
		}

		return mainAllPath;
	}

	public static List<Airport> getExampleGraph() {
		Flight f0 = new Flight("SDKL", "SYD", "KUL", "22.07.2016 06:20",
				"22.07.16 15:10", 1050, 20);
		Flight f1 = new Flight("SDSN", "SYD", "SIN", "22.07.2016 05:10",
				"22.07.16 14:20", 980, 15);
		Flight f2 = new Flight("SDAH", "SYD", "AUH", "22.07.2016 03:35",
				"22.07.16 12:10", 1130, 63);
		Flight f3 = new Flight("KLAS", "KUL", "AMS", "22.07.2016 19:40",
				"23.07.16 08:55", 1780, 25);
		Flight f4 = new Flight("SNCH", "SIN", "CPH", "22.07.2016 20:05",
				"23.07.16 09:10", 1950, 53);
		Flight f5 = new Flight("SNBU", "SIN", "BRU", "22.07.2016 16:35",
				"23.07.16 05:45", 3400, 16);
		Flight f6 = new Flight("AHBU", "AUH", "BRU", "22.07.2016 15:20",
				"23.07.16 09:10", 2350, 17);
		Flight f7 = new Flight("BURK", "BRU", "REK", "23.07.2016 10:05",
				"23.07.16 13:10", 970, 84);
		Flight f8 = new Flight("CHRK", "CPH", "REK", "23.07.2016 14:35",
				"23.07.16 17:45", 670, 62);
		Flight f9 = new Flight("BURK", "AMS", "REK", "23.07.2016 12:20",
				"23.07.16 15:25", 790, 73);

		List<Flight> l1 = new LinkedList<Flight>();
		l1.add(f0);
		l1.add(f1);
		l1.add(f2);

		List<Flight> l2 = new LinkedList<Flight>();
		l2.add(f3);

		List<Flight> l3 = new LinkedList<Flight>();
		l3.add(f4);
		l3.add(f5);

		List<Flight> l4 = new LinkedList<Flight>();
		l4.add(f6);

		List<Flight> l5 = new LinkedList<Flight>();
		l5.add(f7);

		List<Flight> l6 = new LinkedList<Flight>();
		l6.add(f8);

		List<Flight> l7 = new LinkedList<Flight>();
		l7.add(f9);

		List<Flight> l8 = new LinkedList<Flight>();

		List<Airport> example = new LinkedList<Airport>();
		example.add(new Airport(1, "SYD", l1));
		example.add(new Airport(2, "KUL", l2));
		example.add(new Airport(3, "SIN", l3));
		example.add(new Airport(4, "AUH", l4));
		example.add(new Airport(5, "BRU", l5));
		example.add(new Airport(6, "CPH", l6));
		example.add(new Airport(7, "AMS", l7));
		example.add(new Airport(8, "REK", l8));

		return example;
	}

	public List<Airport> getAirports() {
		return airports;
	}

	public void setAirports(List<Airport> airports) {
		this.airports = airports;
	}
}
