import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.io.*;

public class SpracovatelPreukazu extends Spracovatel {
		private ArrayList <Ziadost> ZiadostiNaSpracovanie = new ArrayList<Ziadost>();
		
		private String Heslo;
		
		public void setHeslo(String Heslo) {
			this.Heslo = Heslo;
		}
		public String getHeslo() {
			return Heslo;
		}
		public SpracovatelPreukazu(String Meno, String Heslo, int PoradoveCislo) {
			this.setMenoOsoby(Meno);
			this.setHeslo(Heslo);
			this.PoradoveCislo = PoradoveCislo;
			this.setIdentifikacia("SP");
		}
		
		public void prijmiZiadost(Ziadost ziadost) {
			ziadost.setSpracovatel(this);
			this.ZiadostiNaSpracovanie.add(ziadost);
			ziadost.setStav("�iados� bola prijat� spracovate�om preukazu");
		}
		
		public void vratZiadost(ZiadostOPreukaz HladanaZiadost) {
			Stage inputStage = new Stage();
			inputStage.initModality(Modality.WINDOW_MODAL);
			
			Label label = new Label("Nap�te, ak� �daje m� �iadate� doplni�");
			label.setPadding(new Insets(5,0,5,0));
			
			TextField input = new TextField();
			input.setMaxWidth(150);
			
			Button returnToSender = new Button("Odosla�");
			returnToSender.setPadding(new Insets(5,5,5,5));
			
			VBox vBox = new VBox(15);
			vBox.getChildren().addAll(label, input, returnToSender);
			vBox.setAlignment(Pos.TOP_CENTER);
			
			Scene inputScene = new Scene(vBox, 200, 200);
			inputStage.setScene(inputScene);
			inputStage.showAndWait();
			
			returnToSender.setOnAction(e -> {
				try {
					if(input.getText().equals("")) throw new IOException();
					HladanaZiadost.setStav("�iados� o preukaz bola spracovate�om odoslan� u��vate�ovi na doplnenie �daju: "
							+ input.getText());
					inputStage.close();
				}
				catch (IOException error){
					Alert v = new Alert(AlertType.ERROR);
					v.setTitle("Chyba vstupu");
					v.setContentText("IO Prazdny");
					v.showAndWait();
				}
			});
		}
 
		
		public void spracovanieZiadosti(SpracovatelPreukazu spracovatelPreukazu, ZiadostOPreukaz HladanaZiadost, Stage logWindow, Scene tableScene, ArrayList<Osoba> osoby) {
			logWindow.setTitle("Spracovanie �iadosti o preukaz");

			Button returnToSender = new Button("Odosla� �iados� sp� �iadate�ovi za��elom doplnenia inform�ci�");
			returnToSender.setPadding(new Insets(5,5,5,5));
			Button pitchRequest = new Button("Posun�� �iados� schva�ovate�ovi");
			pitchRequest.setPadding(new Insets(5,5,5,5));
			Button returnButton = new Button("Nasp�");
			returnButton.setPadding(new Insets(5,5,5,5));
			
			VBox vBox = new VBox(10);
			vBox.setPadding(new Insets(20,20,20,20));
			vBox.setAlignment(Pos.CENTER);
			vBox.getChildren().addAll(returnToSender, pitchRequest, returnButton);
			
			Scene sendScene = new Scene(vBox, 400, 400);
			logWindow.setScene(sendScene);
			
			returnToSender.setOnAction(e -> spracovatelPreukazu.vratZiadost(HladanaZiadost));
			pitchRequest.setOnAction(e -> choiceOfApprover(sendScene, spracovatelPreukazu, HladanaZiadost, logWindow, osoby));
			returnButton.setOnAction(e -> logWindow.setScene(tableScene));
			
			int cinnost = 3;
			int poradoveCislo = 0;
			Schvalovatel HladanySchvalovatel = null;
			System.out.print("Zadajte:\n1) pre odoslanie ziadosti ziadatelovi za ucelom doplnenia udajov"
					+ "2) pre poslanie ziadosti schvalovatelovi 3) pre zrusenie cinnosti");
			switch (cinnost) {
			case 1 : spracovatelPreukazu.vratZiadost(HladanaZiadost);
					break;
			case 2 :for(Osoba osoba: osoby) 
						if(osoba instanceof Schvalovatel && osoba.getIdentifikacia().equals("SC")) 
							System.out.println(osoba.getPoradoveCislo()+" "+osoba.getMenoOsoby()+" "+osoba.getIdentifikacia()+" Heureka!");
				 	System.out.println("Zvolte cislo schvalovatela, ktoremu chcete poslat ziadost na schvalenie:");
					HladanySchvalovatel = (Schvalovatel) osoby.get(poradoveCislo);
					HladanySchvalovatel.prijmiZiadost(HladanaZiadost);
					HladanaZiadost.vypisUdajeZiadosti();
					break;
			case 3 : System.out.println("Zvolili ste zrusenie cinnosti.");
					break;
			default: System.out.println("Neznama cinnost."); 
					break;
			}
		}
		
		public void choiceOfApprover(Scene sendScene, SpracovatelPreukazu spracovatel, ZiadostOPreukaz ziadost, Stage logWindow, ArrayList<Osoba> osoby) {
			
			Button returnButton = new Button("Nasp�");
			returnButton.setPadding(new Insets(10,10,10,10));
			Button processButton = new Button("Odosla�");
			processButton.setPadding(new Insets(10,10,10,10));
			
			Label label = new Label("Kliknite na schva�ovate�a, ktor�mu chcete �iados� o preukaz odosla� a stla�te "
					+ "tla�idlo �Odosla��");
			label.setPadding(new Insets(10,0,10,0));
			
			TableView<Osoba> table = new TableView<>();
			
			HBox hBox = new HBox(10);
			hBox.setAlignment(Pos.CENTER);
			hBox.getChildren().addAll(processButton, returnButton);
			
			VBox vBox = new VBox(10);
			vBox.setAlignment(Pos.CENTER);
			vBox.getChildren().addAll(table, label, hBox);
			
			TableColumn<Osoba, String> nameColumn = new TableColumn<>("Meno schva�ovate�a");
			nameColumn.setMinWidth(50);
			nameColumn.setCellValueFactory(new PropertyValueFactory<Osoba, String>("MenoOsoby"));
			
			TableColumn<Osoba, String> IDColumn = new TableColumn<>("Identifik�cia");
			IDColumn.setMinWidth(300);
			IDColumn.setCellValueFactory(new PropertyValueFactory<Osoba, String>("Identifikacia"));
			
			table.setItems(getApprovers(osoby)); //pretypovanie pomocou vlastnej metody, samotne pretypovanie na Observable List zobrazovalo varovanie
			table.getColumns().add(nameColumn);
			table.getColumns().add(IDColumn);
			table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			table.setPlaceholder(new Label("Nie s� prihl�sen� �iadni schva�ovatelia."));
			
			Scene tableScene = new Scene(vBox, 400, 400);
			logWindow.setScene(tableScene);
			
			returnButton.setOnAction(e -> logWindow.setScene(sendScene));
			processButton.setOnAction(e -> {
				((Schvalovatel) table.getSelectionModel().getSelectedItem()).prijmiZiadost(ziadost);
				logWindow.setScene(sendScene);	
			});
			}
		
		public ObservableList<Osoba> getApprovers(ArrayList<Osoba> osoby) {
			ObservableList<Osoba> approvers = FXCollections.observableArrayList();
			for(Osoba osoba: osoby) {
				if(osoba instanceof Schvalovatel && osoba.getIdentifikacia().equals("SC")) 
					approvers.add(osoba);
			}
			return approvers;
		}
		
		public ArrayList<Ziadost> getZiadostiNaSpracovanie() {
			return ZiadostiNaSpracovanie;
		}
		public void setZiadostiNaSpracovanie(ArrayList<Ziadost> ziadostiNaSpracovanie) {
			ZiadostiNaSpracovanie = ziadostiNaSpracovanie;
		}
}
