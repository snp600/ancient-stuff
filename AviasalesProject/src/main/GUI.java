package main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

import structures.Airport;
import structures.Flight;
import tools.BFS;

public class GUI extends Application {

	public static List<List<Flight>> path;

	public static void main(String[] args) {
		Aviasales as = new Aviasales(BFS.getExampleGraph());
		path = as.getExamplePath();
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("KEK Aviasaler");

		GridPane gridPane = addGridPane();
		Label depLabel = new Label("Departure");
		final TextField depInput = new TextField();
		depInput.setMinWidth(200);
		Label approxyLabel = new Label("Destination:");
		final TextField destInput = new TextField();
		destInput.setMinWidth(200);
		Label dateLabel = new Label("Date:");
		final TextField dateInput = new TextField();
		destInput.setMinWidth(200);
		final CheckBox checkBox = new CheckBox("Return");
		Button launchButton = new Button("Search");

		gridPane.add(depLabel, 0, 0);
		gridPane.add(depInput, 0, 1);
		gridPane.add(approxyLabel, 1, 0);
		gridPane.add(destInput, 1, 1);
		gridPane.add(dateLabel, 0, 2);
		gridPane.add(dateInput, 0, 3);
		gridPane.add(checkBox, 1, 3);
		gridPane.add(launchButton, 1, 4);

		launchButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				String dep = depInput.getText();
				String dest = destInput.getText();
				String date = dateInput.getText();
				boolean ret = checkBox.isSelected();
				showResultStage(dep, dest, date, ret);
			}
		});

		Scene scene = new Scene(gridPane, 500, 200);
		scene.getStylesheets().add(
				GUI.class.getResource("JMetroDarkTheme.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void showResultStage(String dep, String dest, String date,
			boolean ret) {
		Stage resultStage = new Stage();
		resultStage.setTitle("Search results");

		Node[] nodes = getNodes();
		GridPane gp = addNodes(nodes);
		Scene resultScene = new Scene(gp, 1000, 400);

		resultScene.getStylesheets().add(
				GUI.class.getResource("JMetroDarkTheme.css").toExternalForm());

		resultStage.setScene(resultScene);
		resultStage.show();
	}

	private GridPane addNodes(Node[] nodes) {
		GridPane gp = addGridPane();
		Label title = new Label("Available flights:");
		title.setScaleX(2.5);
		title.setScaleY(2.5);
		gp.add(title, 3, 0);

		Button d1 = getDetailsButton();
		Button d2 = getDetailsButton();
		Button d3 = getDetailsButton();
		Button d4 = getDetailsButton();

		gp.addRow(1, nodes[0], getArrow(), nodes[1], getArrow(), nodes[2],
				getArrow());
		gp.addRow(2, nodes[3], getArrow(), nodes[4], getArrow(), nodes[5],
				getArrow());
		gp.addRow(3, nodes[6], getArrow(), nodes[7], getArrow(), nodes[8],
				getArrow());
		gp.addRow(4, nodes[9], getArrow(), nodes[10], getArrow(), nodes[11],
				getArrow());

		for (int i = 0; i < 4; i++) {
			ToggleButton x = new ToggleButton("REK");
			x.setMinSize(100, 30);
			x.setMaxSize(100, 30);
			gp.add(x, 6, i + 1);
		}

		gp.add(d1, 8, 1);
		gp.add(d2, 8, 2);
		gp.add(d3, 8, 3);
		gp.add(d4, 8, 4);

		d1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				showDetails();
			}
		});

		return gp;
	}

	private void showDetails() {
		final Stage detailsStage = new Stage();
		detailsStage.setTitle("Details");
		GridPane gp = getDetailsGP();
		Scene detailsScene = new Scene(gp, 750, 400);

		detailsScene.getStylesheets().add(
				GUI.class.getResource("JMetroDarkTheme.css").toExternalForm());
		Button purchase = new Button("Purchase");
		gp.add(purchase, 6, 6);
		detailsStage.setScene(detailsScene);
		detailsStage.show();

		purchase.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				detailsStage.close();
			}
		});
	}

	private GridPane getDetailsGP() {
		GridPane gp = getGridPaneWithHeader();
		List<Flight> p = GUI.path.get(0);
		Label title = new Label("Flight details");
		title.setScaleX(2);
		title.setScaleY(2);
		gp.add(title, 3, 0);

		for (int i = 0; i < 3; i++) {
			Flight f = p.get(i);
			Label flightID = new Label(f.getFlightID());
			Label dep = new Label(f.getDepID());
			Label arr = new Label(f.getArrID());
			Label depTime = new Label(f.getDepTime());
			Label arrTime = new Label(f.getArrTime());
			Label cost = new Label(String.valueOf(f.getCost()) + " $");
			Label places = new Label(String.valueOf(f.getPlaces()));
			gp.addRow(i + 2, flightID, dep, arr, depTime, arrTime, cost, places);
		}
		Label fullCostTitle = new Label("Full cost:");
		Label fullCost = new Label("3620 $");
		Label fullFlightTimeTitle = new Label("Full flight time:");
		Label fullFlightTime = new Label("33h 05m");
		gp.add(fullCostTitle, 4, 5);
		gp.add(fullCost, 5, 5);
		gp.add(fullFlightTimeTitle, 0, 5);
		gp.add(fullFlightTime, 1, 5);

		return gp;
	}

	private GridPane getGridPaneWithHeader() {
		GridPane gp = addGridPaneWide();
		Label[] header = new Label[7];
		header[0] = new Label("Flight");
		header[1] = new Label("Dep");
		header[2] = new Label("Arr");
		header[3] = new Label("Dep. date");
		header[4] = new Label("Arr. date");
		header[5] = new Label("Cost");
		header[6] = new Label("Places");
		for (int i = 0; i < 7; i++)
			gp.add(header[i], i, 1);
		return gp;
	}

	private Label getArrow() {
		Label limg = new Label();
		Image image = new Image(
				(this.getClass().getResource("arrow.png").toString()));
		ImageView arrow = new ImageView(image);
		arrow.setScaleX(0.4);
		arrow.setScaleY(0.3);
		limg.setGraphic(arrow);
		return limg;
	}

	private Button getDetailsButton() {
		Button b = new Button("Details");
		b.setMinSize(100, 30);
		b.setMaxSize(100, 30);
		b.setStyle("-fx-background-color: #00008b");
		return b;
	}

	private Node[] getNodes() {
		Node[] nodes = new Node[16];
		int it = 0;

		for (List<Flight> l : path)
			for (Flight f : l) {
				String s = f.getDepID();
				ToggleButton x = new ToggleButton(s);
				x.setMinSize(100, 30);
				x.setMaxSize(100, 30);
				nodes[it++] = x;
			}

		return nodes;
	}

	public GridPane addGridPane() {
		GridPane gridPane = new GridPane();
		gridPane.setStyle("-fx-background-color: #7fc7ff");
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(8);
		gridPane.setVgap(8);
		return gridPane;
	}

	public GridPane addGridPaneWide() {
		GridPane gridPane = new GridPane();
		gridPane.setStyle("-fx-background-color: #7fc7ff");
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(35);
		gridPane.setVgap(35);
		return gridPane;
	}

	public static void show() {
		List<Airport> l = BFS.getExampleGraph();
		for (Airport a : l) {
			System.out.println("Airport " + a.getId());

			for (Flight f : a.getFlights()) {
				System.out.print(f.getFlightID() + " ");
				System.out.print(f.getDepID() + " ");
				System.out.print(f.getArrID() + " ");
				System.out.print(f.getDepTime() + " ");
				System.out.print(f.getArrTime() + " ");
				System.out.print(f.getCost() + " ");
				System.out.println();
			}
			System.out.println();
		}
	}

}
