import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import java.io.*;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class PrihlasenieZiadatela extends Stage{
	
	public PrihlasenieZiadatela(FyzickaOsoba Ziadatel, ArrayList<Ziadost> ziadosti) {
		setTitle("Prihl�senie fyzickej osoby");
		initModality(Modality.APPLICATION_MODAL);
		setOnCloseRequest(e -> {
			e.consume();
			closeWindow();
		});

		Button raiseNewRequest = new Button("Poda� nov� �iados�");
		raiseNewRequest.setPadding(new Insets(5,5,5,5));
		Button checkRequestStatus = new Button("Stav Va�ich �iadost�");
		checkRequestStatus.setPadding(new Insets(5,5,5,5));
		Button signOutButton = new Button("Odhl�si�");
		signOutButton.setPadding(new Insets(5,5,5,5));
		signOutButton.setOnAction(e -> closeWindow());
		
		VBox vBox = new VBox(10);
		vBox.setPadding(new Insets(20,20,20,20));
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(raiseNewRequest, checkRequestStatus);
		
		BorderPane borderPane = new BorderPane();
		borderPane.setBottom(signOutButton);
		borderPane.setPadding(new Insets(10,10,10,10));
		BorderPane.setAlignment(signOutButton, Pos.BOTTOM_RIGHT);
		borderPane.setCenter(vBox);
		
		Scene scene1 = new Scene(borderPane, 400, 400);
		setScene(scene1);
		
		raiseNewRequest.setOnAction(e -> createNewRequest(scene1, Ziadatel, ziadosti));
		checkRequestStatus.setOnAction(e -> viewOwnRequests(scene1, Ziadatel, ziadosti));
		
		showAndWait();
	}
	
	public PrihlasenieZiadatela(PravnickaOsoba Ziadatel, ArrayList<Ziadost> ziadosti) {
		setTitle("Prihl�senie pr�vnickej osoby");
		initModality(Modality.APPLICATION_MODAL);
		setOnCloseRequest(e -> {
			e.consume();
			closeWindow();
		});

		Button raiseNewRequest = new Button("Poda� nov� �iados�");
		raiseNewRequest.setPadding(new Insets(5,5,5,5));
		Button checkRequestStatus = new Button("Stav Va�ich �iadost�");
		checkRequestStatus.setPadding(new Insets(5,5,5,5));
		Button signOutButton = new Button("Odhl�si�");
		signOutButton.setPadding(new Insets(5,5,5,5));
		signOutButton.setOnAction(e -> closeWindow());
		
		VBox vBox = new VBox(10);
		vBox.setPadding(new Insets(20,20,20,20));
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(raiseNewRequest, checkRequestStatus);
		
		BorderPane borderPane = new BorderPane();
		borderPane.setBottom(signOutButton);
		borderPane.setPadding(new Insets(10,10,10,10));
		BorderPane.setAlignment(signOutButton, Pos.BOTTOM_RIGHT);
		borderPane.setCenter(vBox);
		
		Scene scene1 = new Scene(borderPane, 400, 400);
		setScene(scene1);
		
		raiseNewRequest.setOnAction(e -> createNewRequest(scene1, Ziadatel, ziadosti));
		checkRequestStatus.setOnAction(e -> viewOwnRequests(scene1, Ziadatel, ziadosti));
		
		showAndWait();
	}
	
	public void closeWindow() {
		boolean answer = ConfirmBox.display("Potvrdenie odhl�senia", "Chcete sa odhl�si�?");
		if(answer)
			close();
	}
	
	public void createNewRequest(Scene scene1, FyzickaOsoba Ziadatel, ArrayList<Ziadost> ziadosti) {
		Button licenseRequest = new Button("�iados� o preukaz (zdravotn�, opatrovate�sk�...)");
		licenseRequest.setPadding(new Insets(5,5,5,5));
		
		Button contributionRequest = new Button("�iados� o pr�spevok");
		contributionRequest.setPadding(new Insets(5,5,5,5));
		
		Button statementOfActivities = new Button("Podanie ro�n�ho v�kazu �innost�");
		statementOfActivities.setPadding(new Insets(5,5,5,5));
		
		Button cancelCreation = new Button("Nasp�");
		cancelCreation.setPadding(new Insets(5,5,5,5));
		cancelCreation.setOnAction(e -> setScene(scene1));
		
		VBox vBox = new VBox(10);
		vBox.setPadding(new Insets(20,20,20,20));
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(licenseRequest, contributionRequest, statementOfActivities,cancelCreation);
		Scene requestTypeScene = new Scene(vBox, 400, 400);
		setScene(requestTypeScene);
		
		licenseRequest.setOnAction(e -> createLicenseRequest(Ziadatel, requestTypeScene, ziadosti));
		contributionRequest.setOnAction(e -> createContributionRequest(Ziadatel, requestTypeScene, ziadosti));
		statementOfActivities.setOnAction(e -> createStatementOfActivities(Ziadatel, requestTypeScene, ziadosti));
	}
	
	public void createNewRequest(Scene scene1, PravnickaOsoba Ziadatel, ArrayList<Ziadost> ziadosti) {
		Button licenseRequest = new Button("�iados� o preukaz na vykon�vanie starostlivosti");
		licenseRequest.setPadding(new Insets(5,5,5,5));
		
		Button contributionRequest = new Button("�iados� o pr�spevok");
		contributionRequest.setPadding(new Insets(5,5,5,5));
		
		Button statementOfActivities = new Button("Podanie ro�n�ho v�kazu �innost�");
		statementOfActivities.setPadding(new Insets(5,5,5,5));
		
		Button cancelCreation = new Button("Nasp�");
		cancelCreation.setPadding(new Insets(5,5,5,5));
		cancelCreation.setOnAction(e -> setScene(scene1));
		
		VBox vBox = new VBox(10);
		vBox.setPadding(new Insets(20,20,20,20));
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(licenseRequest, contributionRequest, statementOfActivities,cancelCreation);
		Scene requestTypeScene = new Scene(vBox, 400, 400);
		setScene(requestTypeScene);
		
		licenseRequest.setOnAction(e -> createLicenseRequest(Ziadatel, requestTypeScene, ziadosti));
		contributionRequest.setOnAction(e -> createContributionRequest(Ziadatel, requestTypeScene, ziadosti));
		statementOfActivities.setOnAction(e -> createStatementOfActivities(Ziadatel, requestTypeScene, ziadosti));
	}
	
	public void createLicenseRequest(FyzickaOsoba Ziadatel, Scene requestTypeScene, ArrayList<Ziadost> ziadosti) {
		TextField name = new TextField();
		name.setPromptText("Meno �iadate�a");
		name.setText(Ziadatel.getMenoOsoby());
		
		TextField address = new TextField();
		address.setPromptText("Adresa �iadate�a");
		
		TextField securityNumber = new TextField();
		securityNumber.setPromptText("��slo OP �iadate�a");
		
		TextField reason = new TextField();
		reason.setPromptText("D�vod �iadosti o preukaz");
		
		Button submit = new Button("Odosla� �iados�");
		Button cancel = new Button("Zru�i� �innos�");
		
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.TOP_CENTER);
		gridPane.getChildren().addAll(name, address, securityNumber, reason, submit, cancel);
		GridPane.setMargin(name, new Insets(10,10,10,10));
		GridPane.setConstraints(name, 0, 0);
		GridPane.setMargin(address, new Insets(10,10,10,40));
		GridPane.setConstraints(address, 1, 0);
		GridPane.setMargin(securityNumber, new Insets(10,10,10,10));
		GridPane.setConstraints(securityNumber, 0, 1);
		GridPane.setMargin(reason, new Insets(10,10,10,40));
		GridPane.setConstraints(reason, 1, 1);
		
		GridPane.setMargin(submit, new Insets(50,10,10,10));
		GridPane.setConstraints(submit, 0, 2);
		GridPane.setMargin(cancel, new Insets(50,10,10,40));
		GridPane.setConstraints(cancel, 1, 2);
		
		Scene userInputScene = new Scene(gridPane, 400, 400);
		setScene(userInputScene);
		
		submit.setOnAction(e -> {
			try { 
				if(name.getText().equals("") || address.getText().equals("") || 
				   securityNumber.getText().equals("") || reason.getText().equals("") ||
				   false == name.getText().equals(Ziadatel.getMenoOsoby())) throw new IOException();
						int requestNumber = ((FyzickaOsoba) Ziadatel).vytvorZiadost(name.getText(), address.getText(),
						securityNumber.getText(), reason.getText());
						ziadosti.add(((FyzickaOsoba) Ziadatel).getZiadosti().get(requestNumber));
						System.out.println("�iados� bola podan�.");
						setScene(requestTypeScene);
			} catch (IOException eIO) {
				AlertBox.display("Neplatn� vstup", "Kol�nky mena, adresy, identifik�cie a d�vodu nem��u by� pr�zdne!");
			}
		});
		
		cancel.setOnAction(e -> setScene(requestTypeScene));
	}
	
	public void createLicenseRequest(PravnickaOsoba Ziadatel, Scene requestTypeScene, ArrayList<Ziadost> ziadosti) {
		TextField name = new TextField();
		name.setPromptText("Meno �iadate�a/n�zov spolo�nosti");
		name.setText(Ziadatel.getMenoOsoby());
		
		TextField address = new TextField();
		address.setPromptText("Adresa �iadate�a");
		
		TextField securityNumber = new TextField();
		securityNumber.setPromptText("I�O �iadate�a");
		
		TextField reason = new TextField();
		reason.setPromptText("D�vod �iadosti o preukaz");
		
		Button submit = new Button("Odosla� �iados�");
		Button cancel = new Button("Zru�i� �innos�");
		
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.TOP_CENTER);
		gridPane.getChildren().addAll(name, address, securityNumber, reason, submit, cancel);
		GridPane.setMargin(name, new Insets(10,10,10,10));
		GridPane.setConstraints(name, 0, 0);
		GridPane.setMargin(address, new Insets(10,10,10,40));
		GridPane.setConstraints(address, 1, 0);
		GridPane.setMargin(securityNumber, new Insets(10,10,10,10));
		GridPane.setConstraints(securityNumber, 0, 1);
		GridPane.setMargin(reason, new Insets(10,10,10,40));
		GridPane.setConstraints(reason, 1, 1);
		
		GridPane.setMargin(submit, new Insets(50,10,10,10));
		GridPane.setConstraints(submit, 0, 2);
		GridPane.setMargin(cancel, new Insets(50,10,10,40));
		GridPane.setConstraints(cancel, 1, 2);
		
		Scene userInputScene = new Scene(gridPane, 400, 400);
		setScene(userInputScene);
		
		submit.setOnAction(e -> {
			try { 
				if(name.getText().equals("") || address.getText().equals("") || 
				   securityNumber.getText().equals("") || reason.getText().equals("") ||
				   false == name.getText().equals(Ziadatel.getMenoOsoby())) throw new IOException();
						int requestNumber = ((PravnickaOsoba) Ziadatel).vytvorZiadost(name.getText(), address.getText(),
						securityNumber.getText(), reason.getText());
						ziadosti.add(((PravnickaOsoba) Ziadatel).getZiadosti().get(requestNumber));
						System.out.println("�iados� bola podan�.");
						setScene(requestTypeScene);
			} catch (IOException eIO) {
				AlertBox.display("Neplatn� vstup", "Kol�nky mena, adresy, identifik�cie a d�vodu nem��u by� pr�zdne!");
			}
		});
		
		cancel.setOnAction(e -> setScene(requestTypeScene));
	}
	
	public void createContributionRequest(FyzickaOsoba Ziadatel, Scene requestTypeScene, ArrayList<Ziadost> ziadosti) {
		TextField name = new TextField();
		name.setPromptText("Meno �iadate�a");
		name.setText(Ziadatel.getMenoOsoby());
		
		TextField address = new TextField();
		address.setPromptText("Adresa �iadate�a");
		
		TextField securityNumber = new TextField();
		securityNumber.setPromptText("��slo OP �iadate�a");
		
		TextField amount = new TextField();
		amount.setPromptText("V��ka pr�spevku");
		
		Button submit = new Button("Odosla� �iados�");
		Button cancel = new Button("Zru�i� �innos�");
		
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.TOP_CENTER);
		gridPane.getChildren().addAll(name, address, securityNumber, amount, submit, cancel);
		GridPane.setMargin(name, new Insets(10,10,10,10));
		GridPane.setConstraints(name, 0, 0);
		GridPane.setMargin(address, new Insets(10,10,10,40));
		GridPane.setConstraints(address, 1, 0);
		GridPane.setMargin(securityNumber, new Insets(10,10,10,10));
		GridPane.setConstraints(securityNumber, 0, 1);
		GridPane.setMargin(amount, new Insets(10,10,10,40));
		GridPane.setConstraints(amount, 1, 1);
		
		GridPane.setMargin(submit, new Insets(50,10,10,10));
		GridPane.setConstraints(submit, 0, 2);
		GridPane.setMargin(cancel, new Insets(50,10,10,40));
		GridPane.setConstraints(cancel, 1, 2);
		
		Scene userInputScene = new Scene(gridPane, 400, 400);
		setScene(userInputScene);
		
		submit.setOnAction(e -> {
			try { 
				Integer.parseInt(amount.getText());
				if(name.getText().equals("") || address.getText().equals("") || 
				   securityNumber.getText().equals("") ||
				   false == name.getText().equals(Ziadatel.getMenoOsoby())) throw new IOException();
						int requestNumber = ((FyzickaOsoba) Ziadatel).vytvorZiadost(name.getText(), address.getText(),
						securityNumber.getText(), Integer.parseInt(amount.getText()));
						ziadosti.add(((FyzickaOsoba) Ziadatel).getZiadosti().get(requestNumber));
						System.out.println("�iados� bola podan�.");
						setScene(requestTypeScene);
			} catch (IOException eIO) {
				AlertBox.display("Neplatn� vstup", "Kol�nky mena, adresy, identifik�cie a v��ky pr�sevku nem��u by� pr�zdne!");
			} catch (NumberFormatException eNFE) {
				AlertBox.display("Neplatn� vstup", "Kol�nka v��ky pr�sevku nesmie by� pr�zdna!");
			}
			
		});
		
		cancel.setOnAction(e -> setScene(requestTypeScene));
	}
	
	public void createContributionRequest(PravnickaOsoba Ziadatel, Scene requestTypeScene, ArrayList<Ziadost> ziadosti) {
		TextField name = new TextField();
		name.setPromptText("Meno �iadate�a/n�zov spolo�nosti");
		name.setText(Ziadatel.getMenoOsoby());
		
		TextField address = new TextField();
		address.setPromptText("Adresa �iadate�a");
		
		TextField securityNumber = new TextField();
		securityNumber.setPromptText("I�O �iadate�a");
		
		TextField amount = new TextField();
		amount.setPromptText("V��ka pr�spevku");
		
		Button submit = new Button("Odosla� �iados�");
		Button cancel = new Button("Zru�i� �innos�");
		
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.TOP_CENTER);
		gridPane.getChildren().addAll(name, address, securityNumber, amount, submit, cancel);
		GridPane.setMargin(name, new Insets(10,10,10,10));
		GridPane.setConstraints(name, 0, 0);
		GridPane.setMargin(address, new Insets(10,10,10,40));
		GridPane.setConstraints(address, 1, 0);
		GridPane.setMargin(securityNumber, new Insets(10,10,10,10));
		GridPane.setConstraints(securityNumber, 0, 1);
		GridPane.setMargin(amount, new Insets(10,10,10,40));
		GridPane.setConstraints(amount, 1, 1);
		
		GridPane.setMargin(submit, new Insets(50,10,10,10));
		GridPane.setConstraints(submit, 0, 2);
		GridPane.setMargin(cancel, new Insets(50,10,10,40));
		GridPane.setConstraints(cancel, 1, 2);
		
		Scene userInputScene = new Scene(gridPane, 400, 400);
		setScene(userInputScene);
		
		submit.setOnAction(e -> {
			try { 
				Integer.parseInt(amount.getText());
				if(name.getText().equals("") || address.getText().equals("") || 
				   securityNumber.getText().equals("") ||
				   false == name.getText().equals(Ziadatel.getMenoOsoby())) throw new IOException();
						int requestNumber = ((PravnickaOsoba) Ziadatel).vytvorZiadost(name.getText(), address.getText(),
						securityNumber.getText(), Integer.parseInt(amount.getText()));
						ziadosti.add(((PravnickaOsoba) Ziadatel).getZiadosti().get(requestNumber));
						System.out.println("�iados� bola podan�.");
						setScene(requestTypeScene);
			} catch (IOException eIO) {
				AlertBox.display("Neplatn� vstup", "Kol�nky mena, adresy, identifik�cie a v��ky pr�sevku nem��u by� pr�zdne!");
			} catch (NumberFormatException eNFE) {
				AlertBox.display("Neplatn� vstup", "Kol�nka v��ky pr�sevku nesmie by� pr�zdna!");
			}
			
		});
		
		cancel.setOnAction(e -> setScene(requestTypeScene));
	}
	
	public void createStatementOfActivities(FyzickaOsoba Ziadatel, Scene requestTypeScene, ArrayList<Ziadost> ziadosti) {
		TextField name = new TextField();
		name.setPromptText("Meno �iadate�a");
		name.setText(Ziadatel.getMenoOsoby());
		
		TextField address = new TextField();
		address.setPromptText("Adresa �iadate�a");
		
		TextField securityNumber = new TextField();
		securityNumber.setPromptText("��slo OP �iadate�a");
		
		TextField hours = new TextField();
		hours.setPromptText("Po�et obdr�an�ch hod�n starostlivosti (ambulantne + v ter�ne)");
		
		CheckBox institution = new CheckBox("Starostlivos� v zariaden�");
		CheckBox socialAssistant = new CheckBox("Starostlivos� soci�lneho asistenta");
		institution.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue	<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				socialAssistant.setSelected(!newValue);
			}
		});
		socialAssistant.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue	<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				institution.setSelected(!newValue);
			}
		});
		
		Button submit = new Button("Odosla� �iados�");
		Button cancel = new Button("Zru�i� �innos�");
		
		institution.setStyle("-fx-min-width: 80;");
		
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.TOP_CENTER);
		gridPane.getChildren().addAll(name, address, securityNumber, hours, institution, socialAssistant, submit, cancel);
		GridPane.setMargin(name, new Insets(10,10,10,10));
		GridPane.setConstraints(name, 0, 0);
		GridPane.setMargin(address, new Insets(10,10,10,40));
		GridPane.setConstraints(address, 1, 0);
		GridPane.setMargin(securityNumber, new Insets(10,10,10,10));
		GridPane.setConstraints(securityNumber, 0, 1);
		GridPane.setMargin(hours, new Insets(10,10,10,40));
		GridPane.setConstraints(hours, 1, 1);

		GridPane.setMargin(institution, new Insets(20,10,10,10));
		GridPane.setConstraints(institution, 0, 2);
		GridPane.setMargin(socialAssistant, new Insets(20,10,10,10));
		GridPane.setConstraints(socialAssistant, 1, 2);
		
		GridPane.setMargin(submit, new Insets(50,10,10,10));
		GridPane.setConstraints(submit, 0, 3);
		GridPane.setMargin(cancel, new Insets(50,10,10,40));
		GridPane.setConstraints(cancel, 1, 3);
		
		Scene userInputScene = new Scene(gridPane, 400, 400);
		setScene(userInputScene);
		
		submit.setOnAction(e -> {
			try { 
				Integer.parseInt(hours.getText());
				String typeOfAssistance = "";
				if( (false == institution.isSelected() && false == socialAssistant.isSelected()) ||
					name.getText().equals("") || address.getText().equals("") || 
				    securityNumber.getText().equals("") || hours.getText().equals("") ||
				    false == name.getText().equals(Ziadatel.getMenoOsoby())) throw new IOException();
						if(institution.isSelected()) {
							typeOfAssistance = "Starostlivos� v zariaden�";}
						if(socialAssistant.isSelected()) {
							typeOfAssistance = "Starostlivos� asistenta";}
						int requestNumber = Ziadatel.vytvorZiadost(name.getText(), address.getText(),
						securityNumber.getText(), Integer.parseInt(hours.getText()), typeOfAssistance);
						ziadosti.add(Ziadatel.getZiadosti().get(requestNumber));
						System.out.println("Ro�n� v�kaz �innost� bol podan�.");
						setScene(requestTypeScene);
			} catch (IOException eIO) {
				AlertBox.display("Neplatn� vstup", "Kol�nky mena, adresy, ��sla OP a po�tu hod�n nem��u by� pr�zdne!\n"
						+ "Mus� by� zvolen� typ starostlivosti: starostlivos� v zariaden� alebo starostlivos� soc. asistenta.");
			} catch (NumberFormatException eNFE) {
				AlertBox.display("Neplatn� vstup", "Kol�nka po�tu hod�n nesmie by� pr�zdna!");}
		});
		
		cancel.setOnAction(e -> setScene(requestTypeScene));
	}
	
	public void createStatementOfActivities(PravnickaOsoba Ziadatel, Scene requestTypeScene, ArrayList<Ziadost> ziadosti) {
		TextField name = new TextField();
		name.setPromptText("Meno �iadate�a/n�zov spolo�nosti");
		name.setText(Ziadatel.getMenoOsoby());
		
		TextField address = new TextField();
		address.setPromptText("Adresa �iadate�a");
		
		TextField securityNumber = new TextField();
		securityNumber.setPromptText("I�O �iadate�a/spolo�nosti");
		
		TextField hours = new TextField();
		hours.setPromptText("Po�et obdr�an�ch hod�n starostlivosti (ambulantne + v ter�ne)");
		
		CheckBox institution = new CheckBox("Starostlivos� v zariaden�");
		CheckBox socialAssistant = new CheckBox("Starostlivos� soci�lneho asistenta");
		institution.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue	<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				socialAssistant.setSelected(!newValue);
			}
		});
		socialAssistant.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue	<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				institution.setSelected(!newValue);
			}
		});
		
		Button submit = new Button("Odosla� �iados�");
		Button cancel = new Button("Zru�i� �innos�");
		
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.TOP_CENTER);
		gridPane.getChildren().addAll(name, address, securityNumber, hours, institution, socialAssistant, submit, cancel);
		GridPane.setMargin(name, new Insets(10,10,10,10));
		GridPane.setConstraints(name, 0, 0);
		GridPane.setMargin(address, new Insets(10,10,10,40));
		GridPane.setConstraints(address, 1, 0);
		GridPane.setMargin(securityNumber, new Insets(10,10,10,10));
		GridPane.setConstraints(securityNumber, 0, 1);
		GridPane.setMargin(hours, new Insets(10,10,10,40));
		GridPane.setConstraints(hours, 1, 1);
		
		GridPane.setMargin(institution, new Insets(20,10,10,10));
		GridPane.setConstraints(institution, 0, 2);
		GridPane.setMargin(socialAssistant, new Insets(20,10,10,10));
		GridPane.setConstraints(socialAssistant, 1, 2);
		
		GridPane.setMargin(submit, new Insets(50,10,10,10));
		GridPane.setConstraints(submit, 0, 3);
		GridPane.setMargin(cancel, new Insets(50,10,10,40));
		GridPane.setConstraints(cancel, 1, 3);
		
		Scene userInputScene = new Scene(gridPane, 400, 400);
		setScene(userInputScene);
		
		submit.setOnAction(e -> {
			try { 
				Integer.parseInt(hours.getText());
				String typeOfAssistance = "";
				if( (false == institution.isSelected() && false == socialAssistant.isSelected()) ||
					name.getText().equals("") || address.getText().equals("") || 
				    securityNumber.getText().equals("") || hours.getText().equals("") ||
				    false == name.getText().equals(Ziadatel.getMenoOsoby())) throw new IOException();
						if(institution.isSelected()) {
							typeOfAssistance = "Starostlivos� v zariaden�";}
						if(socialAssistant.isSelected()) {
							typeOfAssistance = "Starostlivos� asistenta";}
						int requestNumber = Ziadatel.vytvorZiadost(name.getText(), address.getText(),
						securityNumber.getText(), Integer.parseInt(hours.getText()), typeOfAssistance);
						ziadosti.add(Ziadatel.getZiadosti().get(requestNumber));
						System.out.println("Ro�n� v�kaz �innost� bol podan�.");
						setScene(requestTypeScene);
			} catch (IOException eIO) {
				AlertBox.display("Neplatn� vstup", "Kol�nky mena, adresy, I�O a po�tu hod�n nem��u by� pr�zdne!\n"
						+ "Mus� by� zvolen� typ starostlivosti: starostlivos� v zariaden� alebo starostlivos� soc. asistenta.");
			} catch (NumberFormatException eNFE) {
				AlertBox.display("Neplatn� vstup", "Kol�nka po�tu hod�n nesmie by� pr�zdna!");}
		});
		
		cancel.setOnAction(e -> setScene(requestTypeScene));
	}
	
	public void viewOwnRequests(Scene scene1, FyzickaOsoba Ziadatel, ArrayList<Ziadost> ziadosti) {
		
		Button returnButton = new Button("Nasp�");
		returnButton.setPadding(new Insets(10,10,10,10));
		Button cancelRequestButton = new Button("Stiahnu� �iados�");
		cancelRequestButton.setPadding(new Insets(10,10,10,10));
		Button updateRequest = new Button("Doplni� �daje");
		updateRequest.setPadding(new Insets(10,10,10,10));
		Button writeAndReturn = new Button("Odosla�");
		writeAndReturn.setPadding(new Insets(5,5,5,5));
		writeAndReturn.setVisible(false);
		
		TextField input = new TextField();
		input.setMaxWidth(150);
		input.setEditable(false);
		
		TableView<Ziadost> table = new TableView<>();
		
		HBox hBox = new HBox(10);
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().addAll(returnButton, cancelRequestButton, updateRequest, input, writeAndReturn);
		
		VBox vBox = new VBox(10);
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(table, hBox);
		
		TableColumn<Ziadost, String> typeColumn = new TableColumn<>("Typ �iadosti");
		typeColumn.setMinWidth(50);
		typeColumn.setCellValueFactory(new PropertyValueFactory<Ziadost, String>("Druh"));
		
		TableColumn<Ziadost, String> statusColumn = new TableColumn<>("Stav");
		statusColumn.setMinWidth(300);
		statusColumn.setCellValueFactory(new PropertyValueFactory<Ziadost, String>("Stav"));
		
		table.setItems(getRequests(Ziadatel.getZiadosti())); //pretypovanie pomocou vlastnej metody, samotne pretypovanie na Observable List zobrazovalo varovanie
		table.getColumns().add(typeColumn);
		table.getColumns().add(statusColumn);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setPlaceholder(new Label("Nem�te podan� �iadne �iadosti."));
		
		Scene tableScene = new Scene(vBox, 400, 400);
		setScene(tableScene);
		
		returnButton.setOnAction(e -> setScene(scene1));
		cancelRequestButton.setOnAction(e -> cancelRequest(table, ziadosti));
		updateRequest.setOnAction(e ->{
			if(table.getSelectionModel().getSelectedItem().isDoplnenie()) {
			writeAndReturn.setVisible(true);
			input.setEditable(true);
			}
		});
		writeAndReturn.setOnAction(e -> {
			try {
				if(input.getText().equals("")) throw new IOException();
				System.out.println("Update �iadosti "+input.getText());
				table.getSelectionModel().getSelectedItem().setStav("�iados� o preukaz bola �iadate�om doplnen� o �daj: "
						+ input.getText());
				table.getSelectionModel().getSelectedItem().setDoplnujuceInformacie(input.getText());
				table.getSelectionModel().getSelectedItem().setDoplnenie(false);
				input.clear();
				input.setEditable(false);

			}	catch (IOException error){
				Alert v = new Alert(AlertType.ERROR);
				v.setTitle("Chyba vstupu");
				v.setContentText("IO Prazdny");
				v.showAndWait();
			}
		});
	}
	
public void viewOwnRequests(Scene scene1, PravnickaOsoba Ziadatel, ArrayList<Ziadost> ziadosti) {
		
		Button returnButton = new Button("Nasp�");
		returnButton.setPadding(new Insets(10,10,10,10));
		Button cancelRequestButton = new Button("Stiahnu� �iados�");
		cancelRequestButton.setPadding(new Insets(10,10,10,10));
		
		TableView<Ziadost> table = new TableView<>();
		
		HBox hBox = new HBox(10);
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().addAll(cancelRequestButton, returnButton);
		
		VBox vBox = new VBox(10);
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(table, hBox);
		
		TableColumn<Ziadost, String> typeColumn = new TableColumn<>("Typ �iadosti");
		typeColumn.setMinWidth(50);
		typeColumn.setCellValueFactory(new PropertyValueFactory<Ziadost, String>("Druh"));
		
		TableColumn<Ziadost, String> statusColumn = new TableColumn<>("Stav");
		statusColumn.setMinWidth(300);
		statusColumn.setCellValueFactory(new PropertyValueFactory<Ziadost, String>("Stav"));
		
		table.setItems(getRequests(Ziadatel.getZiadosti())); //pretypovanie pomocou vlastnej metody, samotne pretypovanie na Observable List zobrazovalo varovanie
		table.getColumns().add(typeColumn);
		table.getColumns().add(statusColumn);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setPlaceholder(new Label("Nem�te podan� �iadne �iadosti."));
		
		Scene tableScene = new Scene(vBox, 400, 400);
		setScene(tableScene);
		
		returnButton.setOnAction(e -> setScene(scene1));
		cancelRequestButton.setOnAction(e -> cancelRequest(table, ziadosti));
	}
	
	public ObservableList<Ziadost> getRequests(ArrayList<Ziadost> ziadosti) {
		ObservableList<Ziadost> requests = FXCollections.observableArrayList();
		for(Ziadost ziadost: ziadosti) {
			requests.add(ziadost);
		}
		return requests;
	}
	
	public void cancelRequest(TableView<Ziadost> table, ArrayList<Ziadost> ziadosti) {
		Ziadost requestSelected = table.getSelectionModel().getSelectedItem();
		Osoba requester = requestSelected.getZiadatel();
		if(requester instanceof FyzickaOsoba)
		((FyzickaOsoba) requestSelected.getZiadatel()).stiahnutieZiadosti(requestSelected, ziadosti);
		if(requester instanceof PravnickaOsoba)
		((PravnickaOsoba) requestSelected.getZiadatel()).stiahnutieZiadosti(requestSelected, ziadosti);
		
	}
	
	public boolean prvePrihlasenie(Scanner scanner) {
		String prvykrat = "";
		boolean prihlasovanie = true;
		while (prihlasovanie) {
		System.out.println("Prihlasujete sa po prvykrat? [A/N] ");
		prvykrat = scanner.nextLine();
		if (prvykrat.equals("A")) {return true;}
		if (prvykrat.equals("N")) {return false;}
		}
		return false;
	}
	
	public void prihlasenieOsoby(FyzickaOsoba osoba, ArrayList<Osoba> list, Scanner scanner, ArrayList<Ziadost> ziadosti) {
		String inputMena = osoba.getMenoOsoby();
		String inputCisla = "";
		
		System.out.print("Zadajte, prosim, Vase meno: ");
		inputMena = scanner.nextLine();
		System.out.print("Zadajte Vase identifikacne cislo: ");
		inputCisla = scanner.nextLine();
		if(osoba.getMenoOsoby().equals(inputMena) && osoba.getIdentifikacneCislo().equals(inputCisla)) {
			onlineOsoba(osoba, list, scanner, ziadosti);
			}
		else {
			System.out.println("Nespravne prihlasovacie udaje.");
			return;
		}
		return;		
}
	
	public void onlineOsoba(FyzickaOsoba osoba, ArrayList<Osoba> list, Scanner scanner, ArrayList <Ziadost> ziadosti) {
		boolean online = true;
		boolean dopln = false;
		boolean zmazanie = true;
		int cinnost = 0;
		int poradieZiadosti = 0;
		String spracovatel = "";
		String stiahnutie = "";
		while (online) {
			System.out.println("Zadajte: 1) pre zadanie novej ziadosti 2) pre zistenie stavu Vasich existujucich ziadosti 3) pre odhlasenie");
			cinnost = scanner.nextInt();
			switch (cinnost) {
			case 1 :poradieZiadosti = osoba.vytvorZiadost(scanner); 
					ziadosti.add(osoba.getZiadosti().get(poradieZiadosti));
					ziadosti.get(ziadosti.size()-1).setPoradoveCislo(ziadosti.size()-1);
					System.out.println("Ziadost bola podana.");
					poradieZiadosti = 0;
					break;
			case 2 :for (Ziadost ziadost: osoba.getZiadosti()) {
						if (null == ziadost.getSpracovatel()) {spracovatel = "Nikto";}
						else spracovatel = ziadost.getSpracovatel().getMenoOsoby();
						System.out.println(poradieZiadosti+" Stav Vasej ziadosti je '"+ziadost.getStav()+"' a na Vasej ziadosti pracuje: "
						+spracovatel);
						poradieZiadosti++;
						if (ziadost.getStav().contains("doplnenie")) dopln = true; // osoba.doplnUdaje(osoba, ziadost, scanner);
					}
					if(dopln == true) {
						System.out.println("Aspon jedna z Vasich ziadosti Vam bola poslana naspat za ucelom doplnenia udajov."
								+ " Zadajte cislo zodpovedajucej ziadosti, aby ste presli k doplneniu udajov.");
					poradieZiadosti = scanner.nextInt();
					if (poradieZiadosti > 0 && poradieZiadosti > osoba.getZiadosti().size() &&
						osoba.getZiadosti().get(poradieZiadosti).getStav().contains("doplnenie")) osoba.doplnUdaje(osoba, osoba.getZiadosti().get(poradieZiadosti), scanner);
					}
					dopln = false; poradieZiadosti = 0; //reset kontrolnych premennych
					while(zmazanie) {
					System.out.println("Zelate si stiahnut (ukoncit) niektoru z Vasich ziadosti? [A/N]");
					stiahnutie = scanner.nextLine();
					if (stiahnutie.equals("A")) {
						osoba.stiahnutieZiadosti(ziadosti, scanner);
						zmazanie = false;
					}
					if (stiahnutie.equals("N")) zmazanie = false;
					}
					break;
			case 3 : System.out.println("Budete odhlaseny. Dakujeme za vyuzitie portalu.");
					online = false;
					break;
			}
		}
	}
	
	public Osoba najdiZiadatela(ArrayList<Osoba> list, Scanner scanner) {
		Iterator <Osoba> iterator = list.iterator();
		System.out.print("Zadajte meno ziadatela [FO]: ");
		String menoZiadatela = scanner.nextLine();
	    while (iterator.hasNext()) {
	        Osoba HladanyZiadatel = iterator.next();
	        if (HladanyZiadatel.getMenoOsoby().equals(menoZiadatela) && HladanyZiadatel instanceof FyzickaOsoba) {
	        	System.out.println("Ziadatel [FO] bol najdeny.");
	            return HladanyZiadatel;
	        }
	    }
	    System.out.println("Ziadatel nebol najdeny.");
	    return null;
	}
}
