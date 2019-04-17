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

public class SpracovatelVykazu extends Spracovatel{
		
		public SpracovatelVykazu(String meno, String heslo, int poradoveCislo) {
			this.setMenoOsoby(meno);
			this.setHeslo(heslo);
			this.PoradoveCislo = poradoveCislo;
			this.setIdentifikacia("SP");
		}
		
		public void prijmiZiadost(RocnyVykazCinnosti vykaz) {
			vykaz.setSpracovatel(this);
			this.ZiadostiNaSpracovanie.add(vykaz);
			vykaz.setStav("éiadosù bola prijat· spracovateæom v˝kazu.");
		}
		
		public void vratZiadost(RocnyVykazCinnosti RocnyVykaz, Scanner scanner) {
			String doplnenie = "";
			System.out.println("Napiste, ktore udaje ma ziadatel doplnit: ");
			scanner.nextLine();
			doplnenie = scanner.nextLine();
			System.out.println("Rocny vykaz cinnosti bol spracovatelom odoslany naspat uzivatelovi na doplnenie udaju:" + doplnenie);
			RocnyVykaz.setStav("Rocny vykaz cinnosti bol spracovatelom odoslany uzivatelovi na doplnenie udaju: " + doplnenie);
		}
		
		public void spracovanieZiadosti(SpracovatelVykazu spracovatelVykazu, RocnyVykazCinnosti HladanyVykaz, Stage processStage, Scene tableScene, ArrayList<Osoba> osoby) {
			processStage.setTitle("Spracovanie roËnÈho v˝kazu ËinnostÌ");

			Button returnToSender = new Button("Odoslaù v˝kaz sp‰ù ûiadateæovi za˙Ëelom doplnenia inform·ciÌ");
			returnToSender.setPadding(new Insets(5,5,5,5));
			Button pitchRequest = new Button("Posun˙ù v˝kaz schvaæovateæovi");
			pitchRequest.setPadding(new Insets(5,5,5,5));
			Button returnButton = new Button("Nasp‰ù");
			returnButton.setPadding(new Insets(5,5,5,5));
			
			TextField input = new TextField();
			input.setMaxWidth(150);
			input.setEditable(false);
			
			Button writeAndReturn = new Button("Odoslaù");
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
			
			returnToSender.setOnAction(e -> { //sprÌstupnenie kolÛnky a tlaËidla
				writeAndReturn.setVisible(true);
				input.setEditable(true);
			});
			
			writeAndReturn.setOnAction(e -> {
				try {
					if(input.getText().equals("")) throw new IOException();
					System.out.println("Vr·tenie v˝kazu "+input.getText());
					HladanyVykaz.setStav("RoËn˝ v˝kaz ËinnostÌ bol spracovateæom odoslan˝ uûÌvateæovi na doplnenie ˙daju: "
							+ input.getText());
					HladanyVykaz.setDoplnenie(true);
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
			pitchRequest.setOnAction(e -> choiceOfApprover(sendScene, spracovatelVykazu, HladanyVykaz, processStage, osoby));
			returnButton.setOnAction(e -> processStage.setScene(tableScene));
		}
		
		public void choiceOfApprover(Scene sendScene, SpracovatelVykazu spracovatel, RocnyVykazCinnosti ziadost, Stage processStage, ArrayList<Osoba> osoby) {
			
			Button returnButton = new Button("Nasp‰ù");
			returnButton.setPadding(new Insets(10,10,10,10));
			Button processButton = new Button("Odoslaù");
			processButton.setPadding(new Insets(10,10,10,10));
			
			Label label = new Label("Kliknite na schvaæovateæa, ktorÈmu chcete roËn˝ v˝kaz ËinnostÌ odoslaù a stlaËte "
					+ "tlaËidlo ¥Odoslaù¥");
			label.setPadding(new Insets(10,0,10,0));
			
			TableView<Osoba> table = new TableView<>();
			
			HBox hBox = new HBox(10);
			hBox.setAlignment(Pos.CENTER);
			hBox.getChildren().addAll(processButton, returnButton);
			
			VBox vBox = new VBox(10);
			vBox.setAlignment(Pos.CENTER);
			vBox.getChildren().addAll(table, label, hBox);
			
			TableColumn<Osoba, String> nameColumn = new TableColumn<>("Meno schvaæovateæa");
			nameColumn.setMinWidth(50);
			nameColumn.setCellValueFactory(new PropertyValueFactory<Osoba, String>("MenoOsoby"));
			
			TableColumn<Osoba, String> IDColumn = new TableColumn<>("Identifik·cia");
			IDColumn.setMinWidth(300);
			IDColumn.setCellValueFactory(new PropertyValueFactory<Osoba, String>("Identifikacia"));
			
			table.setItems(getApprovers(osoby)); //pretypovanie pomocou vlastnej metody, samotne pretypovanie na Observable List zobrazovalo varovanie
			table.getColumns().add(nameColumn);
			table.getColumns().add(IDColumn);
			table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			table.setPlaceholder(new Label("Nie s˙ prihl·senÌ ûiadni schvaæovatelia."));
			
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
