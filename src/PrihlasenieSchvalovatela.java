import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.CheckBox;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PrihlasenieSchvalovatela {
	private Stage processStage;
	
	public PrihlasenieSchvalovatela (Schvalovatel schvalovatel, ArrayList<Ziadost> ziadosti, ArrayList<Osoba> osoby) {
		processStage = new Stage();
		processStage.setTitle("Prihl·senie SCHVALOVATELA ûiadostÌ");
		processStage.initModality(Modality.APPLICATION_MODAL);
		processStage.setOnCloseRequest(e -> {
			e.consume();
			closeWindow();
		});
		
		Button existingRequests = new Button("Zobraziù vöetky podanÈ ûiadosti");
		existingRequests.setPadding(new Insets(5,5,5,5));
		Button ownRequests = new Button("Zobraziù V·m poslanÈ ûiadosti");
		ownRequests.setPadding(new Insets(5,5,5,5));
		Button signOutButton = new Button("Odhl·siù");
		signOutButton.setPadding(new Insets(5,5,5,5));
		signOutButton.setOnAction(e -> closeWindow());
		
		VBox vBox = new VBox(10);
		vBox.setPadding(new Insets(20,20,20,20));
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(existingRequests, ownRequests);
		
		BorderPane borderPane = new BorderPane();
		borderPane.setBottom(signOutButton);
		borderPane.setPadding(new Insets(10,10,10,10));
		BorderPane.setAlignment(signOutButton, Pos.BOTTOM_RIGHT);
		borderPane.setCenter(vBox);
		
		Scene scene1 = new Scene(borderPane, 400, 400);
		Stage signedStage = new Stage();
		signedStage.setScene(scene1);
		processStage.setScene(scene1);
		
		existingRequests.setOnAction(e -> showExistingRequests(scene1, schvalovatel, ziadosti, processStage, osoby));
		ownRequests.setOnAction(e -> showOwnRequests(scene1, schvalovatel, schvalovatel.getZiadostiNaSpracovanie(), processStage, osoby));
		
		processStage.showAndWait();
	}
	
	public void closeWindow() {
		boolean answer = ConfirmBox.display("Potvrdenie odhl·senia", "Chcete sa odhl·siù?");
		if(answer)
			processStage.close();
	}
	
public void showExistingRequests(Scene scene1, Schvalovatel schvalovatel, ArrayList<Ziadost> ziadosti, Stage processStage, ArrayList<Osoba> osoby) {
	
		Button returnButton = new Button("Nasp‰ù");
		returnButton.setPadding(new Insets(10,10,10,10));
		Button assignButton = new Button("Prideliù ûiadosù spracovateæovi");
		assignButton.setPadding(new Insets(10,10,10,10));
		
		TextField input = new TextField();
		input.setPromptText("⁄daje, ktorÈ potrebujete od ûiadateæa");
		input.setMaxWidth(150);
		input.setEditable(false);
		
		Button writeAndReturn = new Button("Odoslaù");
		writeAndReturn.setPadding(new Insets(5,5,5,5));
		writeAndReturn.setVisible(false);
		
		Label label = new Label("Kliknite na ûiadosù, ktor˙ chcete prideliù spracovateæovi a stlaËte tlaËidlo ¥Prideliù ûiadosù spracovateæovi¥");
		label.setPadding(new Insets(10,0,10,0));
		
		TableView<Ziadost> table = new TableView<>();
		
		ChoiceBox<String> choiceBox = new ChoiceBox<>();
		for(Osoba hladanySpracovatel:osoby) {
			if(hladanySpracovatel instanceof SpracovatelPreukazu || hladanySpracovatel instanceof SpracovatelPrispevku || hladanySpracovatel instanceof SpracovatelVykazu) {
				choiceBox.getItems().add(hladanySpracovatel.getMenoOsoby());
			}
		}
		choiceBox.setVisible(false);
		
		CheckBox checkBox = new CheckBox("Doplnenie ˙dajov");
		HBox assignmentHBox = new HBox(10);
		assignmentHBox.setAlignment(Pos.CENTER);
		assignmentHBox.getChildren().addAll(choiceBox, input, writeAndReturn, checkBox);
		
		HBox hBox = new HBox(10);
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().addAll(assignButton, returnButton);
		
		VBox vBox = new VBox(10);
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(table, assignmentHBox, hBox);
		
		TableColumn<Ziadost, String> typeColumn = new TableColumn<>("Typ ûiadosti");
		typeColumn.setMinWidth(50);
		typeColumn.setCellValueFactory(new PropertyValueFactory<Ziadost, String>("Druh"));
		
		TableColumn<Ziadost, String> statusColumn = new TableColumn<>("Stav");
		statusColumn.setMinWidth(300);
		statusColumn.setCellValueFactory(new PropertyValueFactory<Ziadost, String>("Stav"));
		
		table.setItems(getRequests(ziadosti)); //pretypovanie pomocou vlastnej metody, samotne pretypovanie na Observable List zobrazovalo varovanie
		table.getColumns().add(typeColumn);
		table.getColumns().add(statusColumn);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setPlaceholder(new Label("Neboli V·m poslanÈ ûiadne ûiadosti"));
		
		Scene tableScene = new Scene(vBox, 400, 400);
		processStage.setScene(tableScene);
		
		returnButton.setOnAction(e -> processStage.setScene(scene1));
		assignButton.setOnAction(e -> {
			writeAndReturn.setVisible(true);
			input.setEditable(true);
			choiceBox.setVisible(true);
		});
		
		writeAndReturn.setOnAction(e -> {
			try {
				Ziadost tempRequest = null;
				Osoba spracovatel = null;
				if(choiceBox.getSelectionModel().isEmpty() || table.getSelectionModel().isEmpty()) throw new IOException();
				tempRequest = table.getSelectionModel().getSelectedItem();
				for(Osoba osoba: osoby) {
					if(osoba.getMenoOsoby().equals(choiceBox.getSelectionModel().getSelectedItem())) {
						spracovatel = (Spracovatel) osoba;
						break;
					}
				}
				if (tempRequest instanceof ZiadostOPreukaz) {
					if (null != ((SpracovatelPreukazu) tempRequest.getSpracovatel()))
					((SpracovatelPreukazu) tempRequest.getSpracovatel()).getZiadostiNaSpracovanie().remove(tempRequest); //odobratie ziadosti povodnemu spracovatelovi
					((SpracovatelPreukazu) spracovatel).prijmiZiadost((ZiadostOPreukaz) tempRequest);
				}
				if (tempRequest instanceof ZiadostOPrispevok) {
					if (null != ((SpracovatelPrispevku) tempRequest.getSpracovatel()))
					((SpracovatelPrispevku) tempRequest.getSpracovatel()).getZiadostiNaSpracovanie().remove(tempRequest); //odobratie ziadosti povodnemu spracovatelovi
					((SpracovatelPrispevku) spracovatel).prijmiZiadost((ZiadostOPrispevok) tempRequest);
				}
				if (tempRequest instanceof RocnyVykazCinnosti) {
					if (null != ((SpracovatelPrispevku) tempRequest.getSpracovatel()))
					((SpracovatelVykazu) tempRequest.getSpracovatel()).getZiadostiNaSpracovanie().remove(tempRequest); //odobratie ziadosti povodnemu spracovatelovi
					((SpracovatelVykazu) spracovatel).prijmiZiadost((RocnyVykazCinnosti) tempRequest);
				}
				if(checkBox.isSelected()) {
					if(input.getText().isEmpty()) throw new IOException();
					tempRequest.setStav("éiadosù bola SCHVALOVATELOM pridelen· spracovateæovi na doplnenie ˙daju: "+input.getText());
					tempRequest.setDoplnenie(true);
				}
				else {
					tempRequest.setStav("éiadosù bola SCHVALOVATELOM pridelen· spracovateæovi");
				}
				
				tempRequest.setSchvalovatel(null);
				if (true == schvalovatel.getZiadostiNaSpracovanie().contains(tempRequest)) schvalovatel.getZiadostiNaSpracovanie().remove(tempRequest);
				
				writeAndReturn.setVisible(false);
				input.setEditable(false);
				choiceBox.setVisible(false);
			}
			catch (IOException error){
				Alert v = new Alert(AlertType.ERROR);
				v.setTitle("Chyba vstupu");
				v.setContentText("IO Prazdny");
				v.showAndWait();
			}
		});
}

public void showOwnRequests(Scene scene1, Schvalovatel schvalovatel, ArrayList<Ziadost> ziadosti, Stage processStage,  ArrayList<Osoba> osoby) {
	
	Button returnButton = new Button("Nasp‰ù");
	returnButton.setPadding(new Insets(10,10,10,10));
	Button processButton = new Button("Pos˙diù");
	processButton.setPadding(new Insets(10,10,10,10));
	
	Label label = new Label("Kliknite na ûiadosù, ktor˙ chcete pos˙diù a stlaËte tlaËidlo ¥Pos˙diù¥");
	label.setPadding(new Insets(10,0,10,0));
	
	TableView<Ziadost> table = new TableView<>();
	
	HBox hBox = new HBox(10);
	hBox.setAlignment(Pos.CENTER);
	hBox.getChildren().addAll(processButton, returnButton);
	
	VBox vBox = new VBox(10);
	vBox.setAlignment(Pos.CENTER);
	vBox.getChildren().addAll(table, label, hBox);
	
	TableColumn<Ziadost, String> typeColumn = new TableColumn<>("Typ ûiadosti");
	typeColumn.setMinWidth(50);
	typeColumn.setCellValueFactory(new PropertyValueFactory<Ziadost, String>("Druh"));
	
	TableColumn<Ziadost, String> statusColumn = new TableColumn<>("Stav");
	statusColumn.setMinWidth(300);
	statusColumn.setCellValueFactory(new PropertyValueFactory<Ziadost, String>("Stav"));
	
	table.setItems(getRequests(schvalovatel.getZiadostiNaSpracovanie())); //pretypovanie pomocou vlastnej metody, samotne pretypovanie na Observable List zobrazovalo varovanie
	table.getColumns().add(typeColumn);
	table.getColumns().add(statusColumn);
	table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	table.setPlaceholder(new Label("Neboli V·m poslanÈ ûiadne ûiadosti."));
	
	Scene tableScene = new Scene(vBox, 400, 400);
	processStage.setScene(tableScene);
	
	returnButton.setOnAction(e -> processStage.setScene(scene1));
	processButton.setOnAction(e -> reviewRequest(schvalovatel, table.getSelectionModel().getSelectedItem(), ziadosti, processStage, tableScene));
}


	public ObservableList<Ziadost> getRequests(ArrayList<Ziadost> ziadosti) {
		ObservableList<Ziadost> requests = FXCollections.observableArrayList();
		for(Ziadost ziadost: ziadosti) {
			requests.add(ziadost);
		}
		return requests;
	}


	public void reviewRequest(Schvalovatel schvalovatel, Ziadost ziadost, ArrayList<Ziadost> ziadosti, Stage processStage, Scene tableScene) {
		Label requestDescription = new Label("Posudzovanie ûiadosti: "+ziadost.getDruh()+" "+ziadost.getMenoZiadatela()
		+" "+ziadost.getStav()+" "+ziadost.getDoplnujuceInformacie());

		Button cancel = new Button("Zruöiù Ëinnosù");
		
		ChoiceBox<String> choiceBox = new ChoiceBox<>();
		choiceBox.getItems().addAll("Schv·liù", "Zamietnuù", "Vr·tiù ûiadosù spracovateæovi");
		
		TextField comment = new TextField();
		comment.setPromptText("Vyjadrenie k ûiadosti");
		
		Button updateRequest = new Button("Odoslaù");
		
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.TOP_CENTER);
		gridPane.getChildren().addAll(requestDescription, choiceBox, comment, updateRequest, cancel);
		
		GridPane.setMargin(requestDescription, new Insets(10,10,10,10));
		GridPane.setConstraints(requestDescription, 1, 0);
		
		GridPane.setMargin(choiceBox, new Insets(10,10,10,10));
		GridPane.setConstraints(choiceBox, 0, 1);
		
		GridPane.setMargin(comment, new Insets(10,10,10,10));
		GridPane.setConstraints(comment, 1, 1);
		GridPane.setMargin(updateRequest, new Insets(10,10,10,10));
		GridPane.setConstraints(updateRequest, 2, 1);
		
		GridPane.setMargin(cancel, new Insets(50,10,10,40));
		GridPane.setConstraints(cancel, 1, 2);
		
		Scene reviewScene = new Scene(gridPane, 400, 400);
		processStage.setScene(reviewScene);
		updateRequest.setOnAction(e -> {
			switch (choiceBox.getSelectionModel().getSelectedItem()) {
			case "Schv·liù": schvalovatel.schvalZiadost(ziadost, ziadosti, comment.getText());
							 break;
			case "Zamietnuù": schvalovatel.zamietniZiadost(ziadost, ziadosti, comment.getText());
							  break;
			case "Vr·tiù ûiadosù spracovateæovi":schvalovatel.vratZiadostSpracovatelovi(ziadost, comment.getText()); 
												 break;
			}
		});
		
		cancel.setOnAction(e -> processStage.setScene(tableScene));
	}
	
	public boolean prvePrihlasenie(Scanner scanner) {
		String prvykrat = "";
		boolean prihlasovanie = true;
		while (prihlasovanie) {
		System.out.println("Prihlasujete sa po prvykrat ako SCHVALOVATEL? [A/N] ");
		prvykrat = scanner.nextLine();
		if (prvykrat.equals("A")) {return true;}
		if (prvykrat.equals("N")) {return false;}
		}
		return false;
	}
	
	public void prihlasenieOsoby(Schvalovatel schvalovatel, ArrayList<Osoba> osoby, Scanner scanner, ArrayList<Ziadost> ziadosti) {
		String inputMena = schvalovatel.getMenoOsoby();
		String inputCisla = "";
		
		System.out.print("Zadajte, prosim, Vase meno: ");
		inputMena = scanner.nextLine();
		System.out.print("Zadajte Vase identifikacne cislo: ");
		inputCisla = scanner.nextLine();
		if(schvalovatel.getMenoOsoby().equals(inputMena) && schvalovatel.getIdentifikacneCislo().equals(inputCisla)) {
			onlineOsoba(schvalovatel, osoby, scanner, ziadosti);}
		else {System.out.println("Nespravne prihlasovacie udaje.");
			  return;}
		return;
	}
	
	public void onlineOsoba(Schvalovatel schvalovatel, ArrayList<Osoba> osoby, Scanner scanner, ArrayList<Ziadost> ziadosti) {
		boolean online = true;
		int poradieZiadosti = 0;
		int cinnost = 0;
		String spracovanie = "";
		while (online) {
			System.out.println("Zadajte:\n1) pre zoznam vsetkych existujucich ziadosti 2) pre zoznam Vam poslanych ziadosti 3) pre odhlasenie");
			cinnost = scanner.nextInt();
			switch (cinnost) {
			case 1 : if(false == ziadosti.isEmpty()) {
					for(Ziadost ziadost: ziadosti) {
					System.out.println((poradieZiadosti++)+"  "+ziadost.getMenoZiadatela()+"  "+ziadost.getStav());
					}
					poradieZiadosti = 0; //vynulovanie pocitadla pre pripad opakovaneho nacitania zoznamu existujucich ziadosti
					System.out.println("Zelate si prejst k pridelovaniu ziadosti spracovatelom? [A/N]");
					scanner.nextLine();
					spracovanie = scanner.nextLine();
					if(spracovanie.equals("A")) pridelSpracovatelovi(schvalovatel, ziadosti, osoby, scanner);
					}
					else System.out.println("Neboli podane ziadne ziadosti.");
					break;
			case 2:	if(schvalovatel.getZiadostiNaSpracovanie().isEmpty() == false) {
						for(Ziadost ziadost: schvalovatel.getZiadostiNaSpracovanie()) {
							System.out.print(poradieZiadosti++);
							ziadost.vypisUdajeZiadosti();
							}
						poradieZiadosti = 0;
						System.out.println("Zelate si prejst k spracovaniu ziadosti? [A/N]");
						scanner.nextLine();
						spracovanie = scanner.nextLine();
						if(spracovanie.equals("A")) spracovanieZiadosti(schvalovatel, osoby, ziadosti, scanner);}
					break;
			case 3 : System.out.println("Budete odhlaseny. Dakujeme za vyuzitie portalu.");
					online = false;
					break;
			default: System.out.println("Neznama cinnost."); 
					break;
			}
		}
	}
	
	public Osoba najdiSchvalovatela(ArrayList<Osoba> osoby, Scanner scanner) {
		Iterator <Osoba> iterator = osoby.iterator();
		System.out.print("Zadajte meno SCHVALOVATELA: ");
		String menoSchvalovatela = scanner.nextLine();
	    while (iterator.hasNext()) {
	        Osoba HladanySchvalovatel = iterator.next();
	        if (HladanySchvalovatel.getMenoOsoby().equals(menoSchvalovatela) && HladanySchvalovatel instanceof Schvalovatel
	        	&& HladanySchvalovatel.getIdentifikacia().equals("SC")) {
	        	System.out.println("SCHVALOVATEL bol najdeny.");
	            return HladanySchvalovatel;
	        }
	    }
	    System.out.println("SCHVALOVATEL nebol najdeny.");
	    return null;
	}
	
	public Osoba najdiSpracovatela(ArrayList<Osoba> osoby, Ziadost HladanaZiadost, Scanner scanner) {
		int pocetNajdenychSP = 0;
		int cisloSpracovatela = 0;
		
		for(Osoba osoba: osoby) {
			if (osoba instanceof SpracovatelPreukazu  && HladanaZiadost instanceof ZiadostOPreukaz   ||
				osoba instanceof SpracovatelPrispevku && HladanaZiadost instanceof ZiadostOPrispevok || 
				osoba instanceof SpracovatelVykazu    && HladanaZiadost instanceof RocnyVykazCinnosti) {
				System.out.println(osoba.getPoradoveCislo()+" "+osoba.getMenoOsoby()+" "+osoba.getIdentifikacia());
				pocetNajdenychSP++;
			}
		}
		
	    if(pocetNajdenychSP>0) {
		    System.out.println("Zadajte cislo spracovatela, ktoremu chcete ziadost pridelit."
		    		+ " Ak ziadost doteraz spracovaval iny spracovatel, bude mu ziadost odobrata.");
		    cisloSpracovatela = scanner.nextInt();
		    if (cisloSpracovatela < osoby.size()) {
		    	if (osoby.get(cisloSpracovatela) instanceof SpracovatelPreukazu  && HladanaZiadost instanceof ZiadostOPreukaz   ||
		    		osoby.get(cisloSpracovatela) instanceof SpracovatelPrispevku && HladanaZiadost instanceof ZiadostOPrispevok || 
		    		osoby.get(cisloSpracovatela) instanceof SpracovatelVykazu    && HladanaZiadost instanceof RocnyVykazCinnosti) 	
		    		 {return osoby.get(cisloSpracovatela);}
		    	else {System.out.println("Zvolili ste neplatnu kombinaciu ziadosti a spracovatela.");}}
		    }
	    else {
	    	System.out.println("Neboli najdeni ziadni spracovatelia.");
	    	return null;
	    }
		return null;
	}
	
	public void pridelSpracovatelovi(Schvalovatel schvalovatel, ArrayList<Ziadost> ziadosti, ArrayList<Osoba> osoby, Scanner scanner) {
		String doplnenie = "";
		int cisloZiadosti = 0;
		Ziadost HladanaZiadost = null;
		
		System.out.println("Zadajte cislo ziadosti, ktoru chcete pridelit spracovatelovi: ");
		cisloZiadosti = scanner.nextInt();
		HladanaZiadost = ziadosti.get(cisloZiadosti);
		
		Osoba HladanySpracovatel = najdiSpracovatela(osoby, HladanaZiadost, scanner);
		if(null != HladanySpracovatel) {
	    	System.out.println("Napiste, ktore udaje ma spracovatel doplnit: ");
	    	scanner.nextLine();
			doplnenie = scanner.nextLine();
			System.out.print("Zvolena ziadost bude SCHVALOVATELOM pridelena spracovatelovi cislo "+HladanySpracovatel.getPoradoveCislo());
			if (HladanaZiadost instanceof ZiadostOPreukaz) {
				if (null != ((SpracovatelPreukazu) HladanaZiadost.getSpracovatel()))
				((SpracovatelPreukazu) HladanaZiadost.getSpracovatel()).getZiadostiNaSpracovanie().remove(HladanaZiadost); //odobratie ziadosti povodnemu spracovatelovi
				((SpracovatelPreukazu) HladanySpracovatel).prijmiZiadost((ZiadostOPreukaz) HladanaZiadost);
			}
			if (HladanaZiadost instanceof ZiadostOPrispevok) {
				if (null != ((SpracovatelPrispevku) HladanaZiadost.getSpracovatel()))
				((SpracovatelPrispevku) HladanaZiadost.getSpracovatel()).getZiadostiNaSpracovanie().remove(HladanaZiadost); //odobratie ziadosti povodnemu spracovatelovi
				((SpracovatelPrispevku) HladanySpracovatel).prijmiZiadost((ZiadostOPrispevok) HladanaZiadost);
			}
			if (HladanaZiadost instanceof RocnyVykazCinnosti) {
				if (null != ((SpracovatelPrispevku) HladanaZiadost.getSpracovatel()))
				((SpracovatelVykazu) HladanaZiadost.getSpracovatel()).getZiadostiNaSpracovanie().remove(HladanaZiadost); //odobratie ziadosti povodnemu spracovatelovi
				((SpracovatelVykazu) HladanySpracovatel).prijmiZiadost((RocnyVykazCinnosti) HladanaZiadost);
			}
			HladanaZiadost.setStav("Ziadost bola SCHVALOVATELOM pridelena spracovatelovi na doplnenie udaju: "+doplnenie);
			HladanaZiadost.setSchvalovatel(null);
			if (true == schvalovatel.getZiadostiNaSpracovanie().contains(HladanaZiadost)) schvalovatel.getZiadostiNaSpracovanie().remove(HladanaZiadost);
		    }
	    else {System.out.println("Neboli najdeni ziadni spracovatelia.");
	    	  return;}
	}
	
	public void spracovanieZiadosti(Schvalovatel schvalovatel, ArrayList<Osoba> osoby, ArrayList<Ziadost> ziadosti, Scanner scanner) {
		int cinnost = 0;
		int cisloZiadosti = 0;
		Ziadost HladanaZiadost = null;
		
		System.out.println("Zadajte cislo ziadosti, ktoru chcete spracovat: ");
		cisloZiadosti = scanner.nextInt();
		HladanaZiadost = ziadosti.get(cisloZiadosti);
		System.out.print(cisloZiadosti); HladanaZiadost.vypisUdajeZiadosti();
		System.out.println("Zadajte:\n1) pre vratenie ziadosti spracovatelovi 2) pre zamietnutie ziadosti"
				+ " 3) pre schvalenie ziadosti 4) pre zrusenie cinnosti");
		cinnost = scanner.nextInt();
		switch (cinnost) {
		case 1: schvalovatel.vratZiadostSpracovatelovi(HladanaZiadost, scanner);
				break;
		case 2: schvalovatel.zamietniZiadost(HladanaZiadost, ziadosti, scanner);
				break;
		case 3: schvalovatel.schvalZiadost(HladanaZiadost, ziadosti, scanner);
				break;
		case 4: System.out.println("Zvolili ste zrusenie cinnosti, stav ziadosti sa nezmeni."); 
				break;
		default:System.out.println("Neznama cinnost."); 
				break;
		}
	}	
}