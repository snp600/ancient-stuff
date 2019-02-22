package graphics;

import static output.OutputMethods.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import algo.Algorithm;

public class GUI extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Approximator 1.0");

		GridPane gridPane = addGridPane();
		Label sequenceLabel = new Label("Sequence:");
		gridPane.add(sequenceLabel, 0, 0);

		final TextField sequenceTextField = new TextField();
		gridPane.add(sequenceTextField, 1, 0);

		Label approxyLabel = new Label("Approximation:");
		gridPane.add(approxyLabel, 0, 1);

		final TextField approxyTextField = new TextField();
		approxyTextField.setMinWidth(300);
		gridPane.add(approxyTextField, 1, 1);

		Scene scene = new Scene(gridPane, 500, 190);
		scene.getStylesheets()
				.add(GUI.class.getResource("settingsMainScene.css")
						.toExternalForm());
		primaryStage.setScene(scene);

		Button launchButton = new Button("Go!");
		gridPane.add(launchButton, 2, 4);

		Label limg = new Label();
		Image image = new Image(
				(this.getClass().getResource("tooltip.jpg").toString()));
		ImageView question = new ImageView(image);

		Tooltip tooltip = new Tooltip();
		tooltip.setText("Enter some sequence here\n(first symbol - a number)");
		tooltip.setStyle("-fx-background-color:bisque;");
		limg.setGraphic(question);
		limg.setTooltip(tooltip);
		gridPane.add(limg, 2, 0);

		launchButton.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				String text = sequenceTextField.getText();
				if (checkCorrectness(text)) {
					double[] approxyCoeffs = (new Algorithm(text))
							.makeApproximation();
					approxyTextField.clear();
					approxyTextField.appendText(increasingOrder(approxyCoeffs));
				} else {
					Stage errorStage = new Stage();
					errorStage.setTitle("Error");
					BorderPane errorBP = new BorderPane();
					Label errorMessage = new Label("The data is incorrect");
					errorBP.setCenter(errorMessage);
					Scene errorScene = new Scene(errorBP, 230, 130);
					errorScene.getStylesheets().add(
							GUI.class.getResource("settingsErrorScene.css")
									.toExternalForm());
					errorStage.setScene(errorScene);
					errorStage.show();
				}
			}
		});
		primaryStage.show();
	}

	public GridPane addGridPane() {
		GridPane gridPane = new GridPane();
		gridPane.setStyle("-fx-background-color:Tan;");
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(8);
		gridPane.setVgap(8);
		return gridPane;
	}

	public boolean checkCorrectness(String text) {
		Pattern p = Pattern.compile("^[0-9]+([0-9]| )+");
		Matcher m = p.matcher(text);
		return m.matches();
	}
}