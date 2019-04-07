import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application{
	Stage logWindow;
	Scene scene;
	Button button;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage primaryStage) throws Exception{
		logWindow = primaryStage;
		logWindow.setTitle("Prihlasenie do e-UPSVaR");
		
		ChoiceBox<String> choiceBox = new ChoiceBox<>();
		choiceBox.getItems().addAll("Fyzicka osoba", "Pravnicka osoba", "Spracovatel preukazu", "Spracovatel prispevku",
				"Spracovatel vykazu", "Schvalovatel");
		button = new Button("Prihlasit");
		
		VBox vBox = new VBox(10);
		vBox.getChildren().addAll(choiceBox, button);
		scene = new Scene(vBox, 250, 250);
		logWindow.setScene(scene);
		logWindow.show();
	}
}
