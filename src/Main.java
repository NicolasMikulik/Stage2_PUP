import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Main extends Application{
	Stage logWindow;
	Scene scene;
	Button button;
	ArrayList<Osoba> osoby = new ArrayList<Osoba>();
	ArrayList<Ziadost> ziadosti = new ArrayList<Ziadost>();
	public int poradie = 0;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage primaryStage) throws Exception{
		logWindow = primaryStage;
		logWindow.setTitle("Prihlasenie do e-UPSVaR");
		
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.setPromptText("Kto sa prihlasuje?");
		comboBox.getItems().addAll("Fyzicka osoba", "Pravnicka osoba", "Spracovatel preukazu", "Spracovatel prispevku",
				"Spracovatel vykazu", "Schvalovatel");
		
		TextField nameInput = new TextField();
		nameInput.setPromptText("Meno a priezvisko");
		TextField passInput = new TextField();
		passInput.setPromptText("Heslo");
		
		button = new Button("Prihlasit");
		button.setOnAction(e -> prihlasenie(comboBox, nameInput, passInput));
		
		VBox vBox = new VBox(10);
		vBox.setPadding(new Insets(10, 10, 10, 10));
		vBox.getChildren().addAll(comboBox, nameInput, passInput, button);
		vBox.setAlignment(Pos.TOP_CENTER);
		
		scene = new Scene(vBox, 250, 250);
		logWindow.setScene(scene);
		logWindow.show();
	}
	
	public void prihlasenie(ComboBox<String> comboBox, TextField nameInput, TextField passInput) {
		String choice = comboBox.getValue();
		String name = nameInput.getText();
		String password = passInput.getText();
		System.out.println(choice);
		boolean found = false;
		switch(choice) {
		case "Fyzicka osoba": for(Osoba osoba : osoby) {
									if(osoba.getMenoOsoby().equals(name)) {
										if(((FyzickaOsoba) osoba).getHeslo().equals(password)) {
											found = true;
											new PrihlasenieFO((FyzickaOsoba) osoba, ziadosti);
											break;
										}
										else {
											found = true;
											System.out.println("Nespravne meno FO alebo heslo.");
											break;
										}
									}
								}
								if(false == found) {
									poradie = osoby.size();
									osoby.add(new FyzickaOsoba(nameInput.getText(), passInput.getText(), poradie));
									new PrihlasenieFO((FyzickaOsoba) osoby.get(poradie), ziadosti);
								}
								break;
		default: break;
		}
	}
}
