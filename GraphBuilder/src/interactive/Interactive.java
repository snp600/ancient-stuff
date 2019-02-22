package interactive;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import graph.Graph;
import static tools.InteractiveCommands.saveGraphConfig;

public class Interactive {

	private Graph g;
	final int N = 7; //amountOfPatterns
	Pattern[] patterns;

	public Interactive(Graph g) {
		patterns = new Pattern[N];
		initializePatterns();
		this.g = g;
	}

	public void parseCommand(String command) {
		int key = -1;
		for (int i = 0; i < N; i++)
			if (patterns[i].matcher(command).matches() == true) {
				key = i;
				break;
			}	
		executeCommand(key, command);
	}

	private void executeCommand(int key, String command) {
		switch (key) {
		case -1:
			System.out.println("The command '" + command + "' does not exist");
			break;
		case 0:
			System.out.println(help());
			break;
		case 1:
			break;
		case 2:
			addBridge(command);
			break;
		case 3:
			addVertex(command);
			break;
		case 4:
			setWeight(command);
			break;
		case 5:
			load(command);
			break;
		case 6:
			saveGraphConfig(g, command.substring(8));
			break;
		case 7:
			break;
		default:
			break;
		}
	}
	
	/* Patterns */
	public Pattern helpPattern() {
		String pattern = "\\s*help\\s*";
		return Pattern.compile(pattern);
	}
	
	public Pattern exitPattern() {
		String pattern = "\\s*exit\\s*";
		return Pattern.compile(pattern);
	}
	
	public Pattern addBridgePattern() {
		String pattern = "add bridge [0-9]+[.][0-9]+ from [0-9]+ to [0-9]+";
		return Pattern.compile(pattern);
	}

	public Pattern addVertexPattern() {
		String pattern = "add vertex [0-9]+";
		return Pattern.compile(pattern);
	}

	public Pattern setWeightPattern() {
		String pattern = "set [0-9]+ weight as [0-9]+[.][0-9]+";
		return Pattern.compile(pattern);
	}

	public Pattern loadPattern() {
		String pattern = "load [0-9a-zA-Z]+[.][a-z]+";
		return Pattern.compile(pattern);
	}

	public Pattern savePattern() {
		String pattern = "save as [0-9a-zA-Z]+[.][a-z]+";
		return Pattern.compile(pattern);
	}

	
	private void initializePatterns() {
		patterns[0] = helpPattern();
		patterns[1] = exitPattern();
		patterns[2] = addBridgePattern();
		patterns[3] = addVertexPattern();
		patterns[4] = setWeightPattern();
		patterns[5] = loadPattern();
		patterns[6] = savePattern();
	}
	
	private String help() {
		String s = "1) exit\n"
				+ "2) add bridge 'value' from 'id1' to 'id2'\n"
				+ "3) add vertex 'id'\n"
				+ "4) set 'id' weight as 'value'\n"
				+ "5) save as 'filename.extension'\n"
				+ "6) load 'filename.extension'\n";
		return s;
	}
	
	private void addBridge(String command) {
		StringTokenizer words = new StringTokenizer(command, " \n");
		Integer id1;
		Integer id2;
		Double value;
		words.nextToken();
		words.nextToken();
		value = Double.parseDouble(words.nextToken());
		words.nextToken();
		id1 = Integer.parseInt(words.nextToken());
		words.nextToken();
		id2 = Integer.parseInt(words.nextToken());
		g.addDirectedBridge(id1, id2, value);
	}
	
	private void addVertex(String command) {
		StringTokenizer words = new StringTokenizer(command, " \n");
		words.nextToken();
		words.nextToken();
		g.addVertex(Integer.parseInt(words.nextToken()));
	}
	
	private void setWeight(String command) {
		StringTokenizer words = new StringTokenizer(command, " \n");
		words.nextToken();
		Integer id = Integer.parseInt(words.nextToken());
		words.nextToken();
		words.nextToken();
		g.setVertexWeight(id, Double.parseDouble(words.nextToken()));
	}
	
	public void load(String command) {
		String filename = command.substring(5);
		String data = new String();
		FileInputStream fis;
		try {
			fis = new FileInputStream(filename);
			int size = fis.available();
			for (int i = 0; i < size; i++) 
				data += (char) fis.read();
		} catch (FileNotFoundException e) {
			System.out.println("No such file");
		} catch (IOException e) {
		}
		StringTokenizer commands = new StringTokenizer(data, "\n\r\t");
		while(commands.hasMoreTokens())
			parseCommand(commands.nextToken());
	}
}
