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
		logWindow.setTitle("Prihlásenie do e-UPSVaR");
		
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.setPromptText("Kto sa prihlasuje?");
		comboBox.getItems().addAll("Fyzická osoba", "Právnická osoba", "Spracovate¾ preukazu", "Spracovate¾ príspevku",
				"Spracovate¾ výkazu", "Schva¾ovate¾");
		
		TextField nameInput = new TextField();
		nameInput.setPromptText("Meno a priezvisko");
		TextField passInput = new TextField();
		passInput.setPromptText("Heslo");
		
		button = new Button("Prihlási");
		button.setOnAction(e -> prihlasenie(comboBox, nameInput, passInput, logWindow));
		
		VBox vBox = new VBox(10);
		vBox.setPadding(new Insets(10, 10, 10, 10));
		vBox.getChildren().addAll(comboBox, nameInput, passInput, button);
		vBox.setAlignment(Pos.TOP_CENTER);
		
		scene = new Scene(vBox, 250, 250);
		logWindow.setScene(scene);
		logWindow.show();
	}
	
	public void prihlasenie(ComboBox<String> comboBox, TextField nameInput, TextField passInput, Stage logWindow) {
		String choice = comboBox.getValue();
		String name = nameInput.getText();
		String password = passInput.getText();
		System.out.println(choice);
		boolean found = false;
		switch(choice) {
		case "Fyzická osoba": for(Osoba osoba : osoby) {
									if(osoba.getMenoOsoby().equals(name) && osoba instanceof FyzickaOsoba) {
										if(((FyzickaOsoba) osoba).getHeslo().equals(password)) {
											found = true;
											new PrihlasenieZiadatela((FyzickaOsoba) osoba, ziadosti);
											break;
										}
										else {
											found = true;
											System.out.println("Nesprávne meno FO alebo heslo.");
											break;
										}
									}
								}
								if(false == found) {
									poradie = osoby.size();
									osoby.add(new FyzickaOsoba(nameInput.getText(), passInput.getText(), poradie));
									new PrihlasenieZiadatela((FyzickaOsoba) osoby.get(poradie), ziadosti);
								}
								break;
		case "Právnická osoba": for(Osoba osoba : osoby) {
									if(osoba.getMenoOsoby().equals(name) && osoba instanceof PravnickaOsoba) {
											if(((PravnickaOsoba) osoba).getHeslo().equals(password)) {
												found = true;
												new PrihlasenieZiadatela((PravnickaOsoba) osoba, ziadosti);
												break;
											}
											else {
												found = true;
												System.out.println("Nesprávne meno PO alebo heslo.");
												break;
											}
										}
									}
									if(false == found) {
										poradie = osoby.size();
										osoby.add(new PravnickaOsoba(nameInput.getText(), passInput.getText(), poradie));
										new PrihlasenieZiadatela((PravnickaOsoba) osoby.get(poradie), ziadosti);
									}
								break;
		case "Spracovate¾ preukazu": for(Osoba osoba : osoby) {
										if(osoba.getMenoOsoby().equals(name) && osoba instanceof SpracovatelPreukazu) {
												if(((SpracovatelPreukazu) osoba).getHeslo().equals(password)) {
													found = true;
													new PrihlasenieSpracovatela((SpracovatelPreukazu) osoba, ziadosti, osoby);
													break;
												}
												else {
													found = true;
													System.out.println("Nesprávne meno alebo heslo spracovate¾a.");
													break;
												}
											}
										}
										if(false == found) {
											poradie = osoby.size();
											osoby.add(new SpracovatelPreukazu(nameInput.getText(), passInput.getText(), poradie));
											new PrihlasenieSpracovatela((SpracovatelPreukazu) osoby.get(poradie), ziadosti, osoby);
										}
									break;
		case "Schva¾ovate¾": for(Osoba osoba : osoby) {
										if(osoba.getMenoOsoby().equals(name) && osoba instanceof Schvalovatel) {
												if(((Schvalovatel) osoba).getHeslo().equals(password)) {
													found = true;
													new PrihlasenieSchvalovatela((Schvalovatel) osoba, ziadosti, osoby);
													break;
												}
												else {
													found = true;
													System.out.println("Nesprávne meno alebo heslo SCHVALOVATELA.");
													break;
												}
											}
										}
										if(false == found) {
											poradie = osoby.size();
											osoby.add(new Schvalovatel(nameInput.getText(), passInput.getText(), poradie));
											new PrihlasenieSchvalovatela((Schvalovatel) osoby.get(poradie), ziadosti, osoby);
										}
									break;
		default: break;
		}
	}
}
