import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Modality;
import java.util.ArrayList;

public class PrihlasenieSpracovatela extends Stage{

	public PrihlasenieSpracovatela(SpracovatelPreukazu Spracovatel, ArrayList<Ziadost> ziadosti, Stage logWindow, ArrayList<Osoba> osoby) {
		setTitle("Prihl·senie spracovateæa ûiadostÌ o preukaz");
		initModality(Modality.WINDOW_MODAL);
		setOnCloseRequest(e -> {
			e.consume();
			closeWindow();
		});

		Button existingRequests = new Button("Zobraziù vöetky podanÈ ûiadosti");
		existingRequests.setPadding(new Insets(5,5,5,5));
		Button ownRequests = new Button("Zobraziù Vami spracov·vanÈ ûiadosti");
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
		setScene(scene1);
		
		existingRequests.setOnAction(e -> showExistingRequests(scene1, Spracovatel, ziadosti, logWindow, osoby));
		ownRequests.setOnAction(e -> showOwnRequests(scene1, Spracovatel, Spracovatel.getZiadostiNaSpracovanie(), logWindow));
		
		showAndWait();
	}
	
	public void closeWindow() {
		boolean answer = ConfirmBox.display("Potvrdenie odhl·senia", "Chcete sa odhl·siù?");
		if(answer)
			close();
	}
	
public void showExistingRequests(Scene scene1, SpracovatelPreukazu spracovatel, ArrayList<Ziadost> ziadosti, Stage logWindow, ArrayList<Osoba> osoby) {
		
		Button returnButton = new Button("Nasp‰ù");
		returnButton.setPadding(new Insets(10,10,10,10));
		Button assignButton = new Button("Prijaù");
		assignButton.setPadding(new Insets(10,10,10,10));
		
		Label label = new Label("Kliknite na ûiadosù, ktor˙ chcete spracovaù a stlaËte tlaËidlo ¥Prijaù¥");
		label.setPadding(new Insets(10,0,10,0));
		
		TableView<Ziadost> table = new TableView<>();
		
		HBox hBox = new HBox(10);
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().addAll(assignButton, returnButton);
		
		VBox vBox = new VBox(10);
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(table, hBox);
		
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
		table.setPlaceholder(new Label("Nem·te podanÈ ûiadne ûiadosti."));
		
		Scene tableScene = new Scene(vBox, 400, 400);
		setScene(tableScene);
		
		returnButton.setOnAction(e -> setScene(scene1));
		assignButton.setOnAction(e -> assignRequest(table.getSelectionModel().getSelectedItem(), spracovatel, logWindow, tableScene, osoby));
}

public void showOwnRequests(Scene scene1, SpracovatelPreukazu spracovatel, ArrayList<Ziadost> ziadosti, Stage logWindow) {
	
	Button returnButton = new Button("Nasp‰ù");
	returnButton.setPadding(new Insets(10,10,10,10));
	Button processButton = new Button("Spracovaù");
	processButton.setPadding(new Insets(10,10,10,10));
	
	Label label = new Label("Kliknite na ûiadosù, ktor˙ chcete spracovaù a stlaËte tlaËidlo ¥Spracovaù¥");
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
	
	table.setItems(getRequests(ziadosti)); //pretypovanie pomocou vlastnej metody, samotne pretypovanie na Observable List zobrazovalo varovanie
	table.getColumns().add(typeColumn);
	table.getColumns().add(statusColumn);
	table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	table.setPlaceholder(new Label("Nem·te podanÈ ûiadne ûiadosti."));
	
	Scene tableScene = new Scene(vBox, 400, 400);
	setScene(tableScene);
	
	returnButton.setOnAction(e -> setScene(scene1));
}

	public ObservableList<Ziadost> getRequests(ArrayList<Ziadost> ziadosti) {
		ObservableList<Ziadost> requests = FXCollections.observableArrayList();
		for(Ziadost ziadost: ziadosti) {
			requests.add(ziadost);
		}
		return requests;
	}

	public void assignRequest(Ziadost ziadost, SpracovatelPreukazu spracovatel, Stage logWindow, Scene tableScene, ArrayList<Osoba> osoby) {
		try {
			if(false == ziadost instanceof ZiadostOPreukaz) throw new NeplatnaVolba();
			ziadost.kontrolaZiadosti(ziadost, spracovatel, logWindow, tableScene, osoby);
		}
		catch (NeplatnaVolba warning){
			Alert incorrect = new Alert(AlertType.WARNING);
			incorrect.setTitle("Neplatn· voæba");
			incorrect.setContentText("Vami zvolen· ûiadosù nie je ûiadosù o preukaz, nemÙûe V·m byù pridelen·.");
			incorrect.showAndWait();
		}
	}
	
	public boolean prvePrihlasenie(Scanner scanner) {
		String prvykrat = "";
		boolean prihlasovanie = true;
		while (prihlasovanie) {
		System.out.println("Prihlasujete sa po prvykrat ako spracovatel? [A/N] ");
		prvykrat = scanner.nextLine();
		if (prvykrat.equals("A")) {return true;}
		if (prvykrat.equals("N")) {return false;}
		}
		return false;
	}
	
	public void prihlasenieOsoby(Spracovatel osoba, ArrayList<Osoba> osoby, Scanner scanner, ArrayList<Ziadost> ziadosti) {
		String inputMena = osoba.getMenoOsoby();
		String inputCisla = "";
		String cinnost = "";
		System.out.print("Aky spracovatel sa prihlasuje? 1) preukaz 2) prispevok 3) rocny vykaz");
		cinnost = scanner.nextLine();
		System.out.print("Zadajte, prosim, Vase meno: ");
		inputMena = scanner.nextLine();
		System.out.print("Zadajte Vase identifikacne cislo: ");
		inputCisla = scanner.nextLine();
		switch (cinnost) {
		case "1" : if (osoba instanceof SpracovatelPreukazu) {
					   if(osoba.getMenoOsoby().equals(inputMena) && osoba.getIdentifikacneCislo().equals(inputCisla)) {
							onlineOsoba((SpracovatelPreukazu) osoba, osoby, scanner, ziadosti);}
					   else {System.out.println("Nespravne prihlasovacie udaje.");
							return;}
						}
				   else {System.out.println("Spracovatel s Vami zadanym menom nie je spracovatel preukazu.");}
				   break;
		case "2" : if (osoba instanceof SpracovatelPrispevku) {
					   if(osoba.getMenoOsoby().equals(inputMena) && osoba.getIdentifikacneCislo().equals(inputCisla)) {
						onlineOsoba((SpracovatelPrispevku) osoba, osoby, scanner, ziadosti);}
					   else {System.out.println("Nespravne prihlasovacie udaje.");
							return;}
					   }
				   else {System.out.println("Spracovatel s Vami zadanym menom nie je spracovatel prispevku.");}
				   break;
		case "3" : if (osoba instanceof SpracovatelVykazu) {
					   if(osoba.getMenoOsoby().equals(inputMena) && osoba.getIdentifikacneCislo().equals(inputCisla)) {
							onlineOsoba((SpracovatelVykazu) osoba, osoby, scanner, ziadosti);}
					   else {System.out.println("Nespravne prihlasovacie udaje.");
							return;}
					   }
				   else {System.out.println("Spracovatel s Vami zadanym menom nie je spracovatel rocneho vykazu.");}
				   break;
		default: break;
		}
		return;
	}
	
	public void onlineOsoba(SpracovatelPreukazu spracovatelPreukazu, ArrayList<Osoba> osoby, Scanner scanner, ArrayList<Ziadost> ziadosti) {
		boolean online = true;
		int poradieZiadosti = 0;
		int cinnost = 0;
		int cisloZiadosti = 0;
		String inputZiadosti = "";
		Ziadost kontrolnaZiadost = null;
		ZiadostOPreukaz HladanaZiadost = null;
		System.out.println("Ste prihlaseny ako spracovatel preukazu:");
		while (online) {
			System.out.println("Zadajte:\n1) pre zoznam vsetkych existujucich ziadosti o preukaz 2) pre zoznam Vami spracovavanych ziadosti 3) pre odhlasenie");
			cinnost = scanner.nextInt();
			switch (cinnost) {
			case 1 : if(false == ziadosti.isEmpty()) {
						for(Ziadost ziadost: ziadosti) {
						if (ziadost instanceof ZiadostOPreukaz) System.out.print(poradieZiadosti++);
						ziadost.vypisUdajeZiadosti();
						}
						poradieZiadosti = 0; //vynulovanie pocitadla pre pripad opakovaneho nacitania zoznamu existujucich ziadosti
						System.out.print("Zadajte cislo ziadosti, ktoru chcete spracovat/prijat: ");
						cisloZiadosti = scanner.nextInt(); 
						kontrolnaZiadost = ziadosti.get(cisloZiadosti);
						if (kontrolnaZiadost instanceof ZiadostOPreukaz) {
						HladanaZiadost = (ZiadostOPreukaz) ziadosti.get(cisloZiadosti);
						if (null == HladanaZiadost.getSpracovatel()) {
								System.out.println("Ziadost o preukaz nikto nespracovava. Chcete prijat ziadost? [A/N]");
								scanner.nextLine();
								inputZiadosti = scanner.nextLine();
								if (inputZiadosti.equals("A")) {
									spracovatelPreukazu.prijmiZiadost(HladanaZiadost);
									spracovanieZiadosti(spracovatelPreukazu, HladanaZiadost, osoby, scanner);}
								else {
									System.out.println("Ziadost o preukaz Vam nebude pridelena.");
									break;
								}
						}
						else {
								if (false == HladanaZiadost.getSpracovatel().getMenoOsoby().equals(spracovatelPreukazu.getMenoOsoby())) {
									System.out.print("Ziadost o preukaz spracovava iny spracovatel, teda ju nemozete prijat.");
									break;}
								else {System.out.print("Ziadost o preukaz je Vam uz pridelena, prejdete k jej spracovaniu.");
									  spracovanieZiadosti(spracovatelPreukazu, HladanaZiadost, osoby, scanner);}
						}
						}
						else {System.out.print("Nejde o ziadost o preukaz, takze Vam nemoze byt pridelena.");
							 break;
						}
					}
					else System.out.println("Nie su podane ziadne ziadosti.");
					break;
			case 2: if(false == spracovatelPreukazu.getZiadostiNaSpracovanie().isEmpty()) {
						for(Ziadost ziadost: spracovatelPreukazu.getZiadostiNaSpracovanie()) {
							System.out.print(poradieZiadosti++);
							ziadost.vypisUdajeZiadosti();}
						poradieZiadosti = 0;
						System.out.print("Zadajte cislo ziadosti o preukaz, ktoru chcete spracovat: ");
						cisloZiadosti = scanner.nextInt(); 
						HladanaZiadost = (ZiadostOPreukaz) ziadosti.get(cisloZiadosti);
						spracovanieZiadosti(spracovatelPreukazu, HladanaZiadost, osoby, scanner);
						}
					else System.out.println("Nespracovavate ziadne ziadosti o preukaz.");
					break;
			case 3 : System.out.println("Budete odhlaseny. Dakujeme za vyuzitie portalu.");
					online = false;
					break;
			default: System.out.println("Neznama cinnost."); 
					break;
			}
		}
	}
	
	public void onlineOsoba(SpracovatelPrispevku spracovatelPrispevku, ArrayList<Osoba> osoby, Scanner scanner, ArrayList<Ziadost> ziadosti) {
		boolean online = true;
		int cinnost = 0;
		int cisloZiadosti = 0;
		String inputZiadosti = "";
		Ziadost kontrolnaZiadost = null;
		ZiadostOPrispevok HladanaZiadost = null;
		System.out.println("Ste prihlaseny ako spracovatel prispevku:");
		while (online) {
			System.out.println("Zadajte:\n1) pre zoznam vsetkych existujucich ziadosti o prispevok 2) pre zoznam Vami spracovavanych ziadosti 3) pre odhlasenie");
			cinnost = scanner.nextInt();
			switch (cinnost) {
			case 1 : if(false == ziadosti.isEmpty()) {
					for(Ziadost ziadost: ziadosti) {
					ziadost.vypisUdajeZiadosti();
					}
					System.out.print("Zadajte cislo ziadosti o prispevok, ktoru chcete spracovat/prijat: ");
					cisloZiadosti = scanner.nextInt(); 
					kontrolnaZiadost = ziadosti.get(cisloZiadosti);
					if (kontrolnaZiadost instanceof ZiadostOPrispevok) {
						HladanaZiadost = (ZiadostOPrispevok) ziadosti.get(cisloZiadosti);
						if (null == HladanaZiadost.getSpracovatel()) {
								System.out.println("Ziadost o prispevok nikto nespracovava. Chcete prijat ziadost? [A/N]");
								scanner.nextLine();
								inputZiadosti = scanner.nextLine();
								if (inputZiadosti.equals("A")) {
									spracovatelPrispevku.prijmiZiadost(HladanaZiadost);
									spracovanieZiadosti(spracovatelPrispevku, HladanaZiadost, osoby, scanner);}
								else {
									System.out.println("Ziadost o prispevok Vam nebude pridelena.");
									break;
								}
						}
						else {
								if (false == HladanaZiadost.getSpracovatel().getMenoOsoby().equals(spracovatelPrispevku.getMenoOsoby())) {
									System.out.print("Danu ziadost o prispevok spracovava iny spracovatel, teda ju nemozete prijat.");
									break;}
								else {System.out.print("Danu ziadost o prispevok je Vam uz pridelena, prejdete k jej spracovaniu.");
									  spracovanieZiadosti(spracovatelPrispevku, HladanaZiadost, osoby, scanner);}
						}
						}
					else {System.out.println("Nejde o ziadost o prispevok, takze Vam nemoze byt pridelena.");}
					}
					else System.out.println("Nie su podane ziadne ziadosti.");
					break;
			case 2: if(false == spracovatelPrispevku.getZiadostiNaSpracovanie().isEmpty()) {
						for(Ziadost ziadost: spracovatelPrispevku.getZiadostiNaSpracovanie()) {
							ziadost.vypisUdajeZiadosti();}
						System.out.print("Zadajte cislo ziadosti o prispevok, ktoru chcete spracovat: ");
						cisloZiadosti = scanner.nextInt(); 
						HladanaZiadost = (ZiadostOPrispevok) ziadosti.get(cisloZiadosti);
						spracovanieZiadosti(spracovatelPrispevku, HladanaZiadost, osoby, scanner);
						}
					else System.out.println("Nespracovavate ziadne ziadosti o prispevok.");
					break;
			case 3 : System.out.println("Budete odhlaseny. Dakujeme za vyuzitie portalu.");
					online = false;
					break;
			default: System.out.println("Neznama cinnost."); 
					break;
			}
		}
	}
	
	public void onlineOsoba(SpracovatelVykazu spracovatelVykazu, ArrayList<Osoba> osoby, Scanner scanner, ArrayList<Ziadost> ziadosti) {
		boolean online = true;
		int poradieZiadosti = 0;
		int cinnost = 0;
		int cisloZiadosti = 0;
		String inputZiadosti = "";
		Ziadost kontrolnaZiadost=null;
		RocnyVykazCinnosti RocnyVykaz = null;
		System.out.println("Ste prihlaseny ako spracovatel vykazu:");
		while (online) {
			System.out.println("Zadajte:\n1) pre zoznam vsetkych existujucich ziadosti o prispevok 2) pre zoznam Vami spracovavanych ziadosti 3) pre odhlasenie");
			cinnost = scanner.nextInt();
			switch (cinnost) {
			case 1 : if(false == ziadosti.isEmpty()) {
					for(Ziadost ziadost: ziadosti) {
					System.out.print(poradieZiadosti++);
					ziadost.vypisUdajeZiadosti();
					}
					poradieZiadosti = 0; //vynulovanie pocitadla pre pripad opakovaneho nacitania zoznamu existujucich ziadosti
					System.out.print("Zadajte cislo ziadosti o prispevok, ktoru chcete spracovat/prijat: ");
					cisloZiadosti = scanner.nextInt(); 
					kontrolnaZiadost = ziadosti.get(cisloZiadosti);
					if (kontrolnaZiadost instanceof RocnyVykazCinnosti) {
					RocnyVykaz = (RocnyVykazCinnosti) ziadosti.get(cisloZiadosti);
					if (null == RocnyVykaz.getSpracovatel()) {
							System.out.println("Dany rocny vykaz cinnosti nikto nespracovava. Chcete prijat ziadost? [A/N]");
							scanner.nextLine();
							inputZiadosti = scanner.nextLine();
							if (inputZiadosti.equals("A")) {
								spracovatelVykazu.prijmiZiadost(RocnyVykaz);
								spracovanieZiadosti(spracovatelVykazu, RocnyVykaz, osoby, scanner);}
							else {
								System.out.println("Dany rocny vykaz cinnosti Vam nebude prideleny.");
								break;
							}
					}
					else {
							if (false == RocnyVykaz.getSpracovatel().getMenoOsoby().equals(spracovatelVykazu.getMenoOsoby())) {
								System.out.print("Dany rocny vykaz cinnosti spracovava iny spracovatel, teda ho nemozete prijat.");
								break;}
							else {System.out.print("Dany rocny vykaz cinnosti je Vam uz prideleny, prejdete k jeho spracovaniu.");
								  spracovanieZiadosti(spracovatelVykazu, RocnyVykaz, osoby, scanner);}
					}
					}
					else { System.out.println("Nejde o podanie rocneho vykazu cinnosti, takze Vam ziadost nemoze byt pridelena.");}
					}
					else System.out.println("Nie su podane ziadne rocne vykazy cinnosti.");
					break;
			case 2: if(false == spracovatelVykazu.getZiadostiNaSpracovanie().isEmpty()) {
						for(Ziadost ziadost: spracovatelVykazu.getZiadostiNaSpracovanie()) {
							System.out.print(poradieZiadosti++);
							ziadost.vypisUdajeZiadosti();}
						poradieZiadosti = 0;
						System.out.print("Zadajte cislo ziadosti, ktoru chcete spracovat: ");
						cisloZiadosti = scanner.nextInt(); 
						RocnyVykaz = (RocnyVykazCinnosti) ziadosti.get(cisloZiadosti);
						spracovanieZiadosti(spracovatelVykazu, RocnyVykaz, osoby, scanner);
						}
					else System.out.println("Nespracovavate ziadne ziadosti.");
					break;
			case 3 : System.out.println("Budete odhlaseny. Dakujeme za vyuzitie porvtalu.");
					online = false;
					break;
			default: System.out.println("Neznama cinnost."); 
					break;
			}
		}
	}
	
	public void spracovanieZiadosti(SpracovatelPreukazu spracovatelPreukazu, ZiadostOPreukaz HladanaZiadost, ArrayList<Osoba> osoby, Scanner scanner) {
		int cinnost = 0;
		int poradoveCislo = 0;
		Schvalovatel HladanySchvalovatel = null;
		System.out.print("Zadajte:\n1) pre odoslanie ziadosti ziadatelovi za ucelom doplnenia udajov"
				+ "2) pre poslanie ziadosti schvalovatelovi 3) pre zrusenie cinnosti");
		cinnost = scanner.nextInt();
		switch (cinnost) {
		case 1 : spracovatelPreukazu.vratZiadost(HladanaZiadost);
				break;
		case 2 :for(Osoba osoba: osoby) 
					if(osoba instanceof Schvalovatel && osoba.getIdentifikacia().equals("SC")) 
						System.out.println(osoba.getPoradoveCislo()+" "+osoba.getMenoOsoby()+" "+osoba.getIdentifikacia()+" Heureka!");
			 	System.out.println("Zvolte cislo schvalovatela, ktoremu chcete poslat ziadost na schvalenie:");
				poradoveCislo = scanner.nextInt();
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
	
	public void spracovanieZiadosti(SpracovatelPrispevku spracovatelPrispevku, ZiadostOPrispevok HladanaZiadost, ArrayList<Osoba> osoby, Scanner scanner) {
		int cinnost = 0;
		int poradoveCislo = 0;
		Schvalovatel HladanySchvalovatel = null;
		System.out.print("Zadajte:\n1) pre odoslanie ziadosti ziadatelovi za ucelom doplnenia udajov"
				+ "2) pre poslanie ziadosti schvalovatelovi 3) pre zrusenie cinnosti");
		cinnost = scanner.nextInt();
		switch (cinnost) {
		case 1 : spracovatelPrispevku.vratZiadost(HladanaZiadost, scanner);
				break;
		case 2 :for(Osoba osoba: osoby) 
					if(osoba instanceof Schvalovatel && osoba.getIdentifikacia().equals("SC")) 
						System.out.println(osoba.getPoradoveCislo()+" "+osoba.getMenoOsoby()+" "+osoba.getIdentifikacia()+" Heureka!");
			 	System.out.println("Zvolte cislo schvalovatela, ktoremu chcete poslat ziadost na schvalenie:");
				poradoveCislo = scanner.nextInt();
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
	
	public void spracovanieZiadosti(SpracovatelVykazu spracovatelVykazu, RocnyVykazCinnosti RocnyVykaz, ArrayList<Osoba> osoby, Scanner scanner) {
		int cinnost = 0;
		int poradoveCislo = 0;
		Schvalovatel HladanySchvalovatel = null;
		System.out.print("Zadajte:\n1) pre odoslanie rocneho vykazu cinnosti ziadatelovi za ucelom doplnenia udajov"
				+ "2) pre poslanie vykazu schvalovatelovi 3) pre zrusenie cinnosti");
		cinnost = scanner.nextInt();
		switch (cinnost) {
		case 1 : spracovatelVykazu.vratZiadost(RocnyVykaz, scanner);
				break;
		case 2 :for(Osoba osoba: osoby) 
					if(osoba instanceof Schvalovatel && osoba.getIdentifikacia().equals("SC")) 
						System.out.println(osoba.getPoradoveCislo()+" "+osoba.getMenoOsoby()+" "+osoba.getIdentifikacia()+" Heureka!");
			 	System.out.println("Zvolte cislo schvalovatela, ktoremu chcete poslat vykaz na schvalenie:");
				poradoveCislo = scanner.nextInt();
				HladanySchvalovatel = (Schvalovatel) osoby.get(poradoveCislo);
				HladanySchvalovatel.prijmiZiadost(RocnyVykaz);
				RocnyVykaz.vypisUdajeZiadosti();
				break;
		case 3 : System.out.println("Zvolili ste zrusenie cinnosti.");
				break;
		default: System.out.println("Neznama cinnost."); 
				break;
		}
	}
	
	public Spracovatel najdiSpracovatela(ArrayList<Osoba> osoby, Scanner scanner) {
		System.out.print("Zadajte meno spracovatela: ");
		String menoSpracovatela = scanner.nextLine();
		for(Osoba HladanySpracovatel: osoby) {
			if (HladanySpracovatel.getMenoOsoby().equals(menoSpracovatela) && HladanySpracovatel instanceof Spracovatel 
	        		&& HladanySpracovatel.getIdentifikacia().equals("SP")) {
	        	System.out.println("Spracovatel bol najdeny.");
	            return (Spracovatel) HladanySpracovatel;
	        }
	    }
	    System.out.println("Spracovatel nebol najdeny.");
	    return null;
		}
	
}
