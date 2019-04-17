import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import java.io.*;

public class SpracovatelPreukazu extends Spracovatel {
		
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
			inputStage.setTitle("Zadanie �daju na doplnenie");
			
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
					System.out.println("Vr�tenie �iadosti "+input.getText());
					HladanaZiadost.setStav("�iados� o preukaz bola spracovate�om odoslan� u��vate�ovi na doplnenie �daju: "
							+ input.getText());
					inputStage.close();
			});
		}
 
		
		public void spracovanieZiadosti(SpracovatelPreukazu spracovatelPreukazu, ZiadostOPreukaz HladanaZiadost, Stage processStage, Scene tableScene, ArrayList<Osoba> osoby) {
			processStage.setTitle("Spracovanie �iadosti o preukaz");

			Button returnToSender = new Button("Odosla� �iados� sp� �iadate�ovi za��elom doplnenia inform�ci�");
			returnToSender.setPadding(new Insets(5,5,5,5));
			Button pitchRequest = new Button("Posun�� �iados� schva�ovate�ovi");
			pitchRequest.setPadding(new Insets(5,5,5,5));
			Button returnButton = new Button("Nasp�");
			returnButton.setPadding(new Insets(5,5,5,5));
			
			TextField input = new TextField();
			input.setMaxWidth(150);
			input.setEditable(false);
			
			Button writeAndReturn = new Button("Odosla�");
			writeAndReturn.setPadding(new Insets(5,5,5,5));
			writeAndReturn.setVisible(false);
			
			HBox hBox = new HBox(10);
			hBox.getChildren().addAll(input, writeAndReturn);
			
			VBox vBox = new VBox(10);
			vBox.setPadding(new Insets(20,20,20,20));
			vBox.setAlignment(Pos.CENTER);
			vBox.getChildren().addAll(returnToSender, hBox, pitchRequest, returnButton);
			
			Scene sendScene = new Scene(vBox, 400, 400);
			processStage.setScene(sendScene);
			
			returnToSender.setOnAction(e -> { //spr�stupnenie kol�nky a tla�idla
				writeAndReturn.setVisible(true);
				input.setEditable(true);
			});
			
			writeAndReturn.setOnAction(e -> {
				try {
					if(input.getText().equals("")) throw new IOException();
					System.out.println("Vr�tenie �iadosti "+input.getText());
					HladanaZiadost.setStav("�iados� o preukaz bola spracovate�om odoslan� u��vate�ovi na doplnenie �daju: "
							+ input.getText());
					HladanaZiadost.setDoplnenie(true);
					writeAndReturn.setVisible(false);
					input.clear();
					input.setEditable(false);
				}
				catch (IOException error){
					Alert v = new Alert(AlertType.ERROR);
					v.setTitle("Chyba vstupu");
					v.setContentText("IO Prazdny");
					v.showAndWait();
				}
			});
			pitchRequest.setOnAction(e -> choiceOfApprover(sendScene, spracovatelPreukazu, HladanaZiadost, processStage, osoby));
			returnButton.setOnAction(e -> processStage.setScene(tableScene));
		}
		
		public void choiceOfApprover(Scene sendScene, SpracovatelPreukazu spracovatel, ZiadostOPreukaz ziadost, Stage processStage, ArrayList<Osoba> osoby) {
			
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
			processStage.setScene(tableScene);
			
			returnButton.setOnAction(e -> processStage.setScene(sendScene));
			processButton.setOnAction(e -> {
				((Schvalovatel) table.getSelectionModel().getSelectedItem()).prijmiZiadost(ziadost);
				processStage.setScene(sendScene);	
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
}
