import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SpracovatelPrispevku extends Spracovatel {
		
		public SpracovatelPrispevku(String Meno, String Heslo, int PoradoveCislo) {
			this.setMenoOsoby(Meno);
			this.setHeslo(Heslo);
			this.PoradoveCislo = PoradoveCislo;
			this.setIdentifikacia("SP");
		}
		
		public void prijmiZiadost(ZiadostOPrispevok ziadost) {
			this.ZiadostiNaSpracovanie.add(ziadost);
			ziadost.setSpracovatel(this);
			ziadost.pridajInformovanuOsobu(this);
			ziadost.setStav("Ziadost bola prijata spracovatelom.");
		}
		
		public void vratZiadost(ZiadostOPrispevok HladanaZiadost, Scanner scanner) {
			String doplnenie = "";
			System.out.println("Napiste, ktore udaje ma ziadatel doplnit: ");
			scanner.nextLine();
			doplnenie = scanner.nextLine();
			System.out.println("Ziadost o prispevok bola spracovatelom odoslana naspat uzivatelovi na doplnenie udaju:" + doplnenie);
			HladanaZiadost.setStav("Ziadost o prispevok bola spracovatelom odoslana uzivatelovi na doplnenie udaju: " + doplnenie);
		}
		
		public void spracovanieZiadosti(SpracovatelPrispevku spracovatelPrispevku, ZiadostOPrispevok HladanaZiadost, Stage processStage, Scene tableScene, ArrayList<Osoba> osoby) {
			processStage.setTitle("Spracovanie �iadosti o pr�spevok");

			Button returnToSender = new Button("Odosla� �iados� sp� �iadate�ovi za ��elom doplnenia inform�ci�");
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
					HladanaZiadost.setStav("�iados� o pr�spevok bola spracovate�om odoslan� u��vate�ovi na doplnenie �daju: "
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
			pitchRequest.setOnAction(e -> choiceOfApprover(sendScene, spracovatelPrispevku, HladanaZiadost, processStage, osoby));
			returnButton.setOnAction(e -> processStage.setScene(tableScene));
		}
		
public void choiceOfApprover(Scene sendScene, SpracovatelPrispevku spracovatel, ZiadostOPrispevok ziadost, Stage processStage, ArrayList<Osoba> osoby) {
			
			Button returnButton = new Button("Nasp�");
			returnButton.setPadding(new Insets(10,10,10,10));
			Button processButton = new Button("Odosla�");
			processButton.setPadding(new Insets(10,10,10,10));
			
			Label label = new Label("Kliknite na schva�ovate�a, ktor�mu chcete �iados� o pr�spevok odosla� a stla�te "
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
