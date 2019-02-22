package tools;

import graph.Graph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import vertex.Vertex;

public class InteractiveCommands {

	public static void saveGraphConfig(Graph g, String filename) {
		FileWriter writeFile = null;
		try {
			File file = new File(filename);
			@SuppressWarnings("resource")
			Scanner console = new Scanner(System.in);
			if (file.exists()) {
				System.out.println("Rewrite? y/n");
				if (console.next().equals("y")) 
					file.delete();
			}
			writeFile = new FileWriter(filename, true);
			Map<Integer, Vertex> vertexes = g.getVertexes();
			String lineSeparator = System.getProperty("line.separator");
			for (Map.Entry<Integer, Vertex> vertex : vertexes.entrySet()) {
				writeFile.append("add vertex ");
				writeFile.append(vertex.getKey().toString() + lineSeparator);
				if (vertex.getValue().getWeight() > 0.0) {
					writeFile.append("set ");
					writeFile.append(vertex.getKey().toString());
					writeFile.append(" weight as ");
					writeFile.append(vertex.getValue().getWeight().toString());
					writeFile.append(lineSeparator);
				}
				for (Map.Entry<Integer, Double> bridge : vertex.getValue()
						.getBridges().entrySet()) {
					writeFile.append("add bridge ");
					writeFile.append(bridge.getValue().toString());
					writeFile.append(" from ");
					writeFile.append(vertex.getKey().toString());
					writeFile.append(" to ");
					writeFile.append(bridge.getKey().toString());
					writeFile.append(lineSeparator);
				}
				writeFile.append(lineSeparator);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writeFile != null) {
				try {
					writeFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
