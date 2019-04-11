import java.util.ArrayList;
import java.util.Scanner;

public class Log {
	
	public static void log(String[] args) {
		boolean stav = true;
		String volba = "";
		String inputMena = "";
		String inputCisla = "";
		int poradieOsoby = 0;
		Scanner scanner = new Scanner(System.in);
		PrihlasenieFO prihlasenieFO = new PrihlasenieFO();
		PrihlaseniePO prihlaseniePO = new PrihlaseniePO();
		PrihlasenieSpracovatela prihlasenieSP = new PrihlasenieSpracovatela();
		PrihlasenieSchvalovatela prihlasenieSC = new PrihlasenieSchvalovatela();
		ArrayList <Osoba> osoby = new ArrayList<Osoba>();
		ArrayList <Ziadost> ziadosti = new ArrayList<Ziadost>();
		FyzickaOsoba ziadatelFO = null;
		PravnickaOsoba ziadatelPO = null;
		Schvalovatel schvalovatel = null;
		Osoba osoba = null;
		while (stav) {
			System.out.println("Zadajte: 1) pre prihlasenie FO 2) pre prihlasenie PO 3) pre prihlasenie spracovatela 4) Pre prihlasenie schvalovatela");
			volba = scanner.nextLine();
			switch (volba) {
			case "1" :  if (true == prihlasenieFO.prvePrihlasenie(scanner)) {
						System.out.print("Zadajte, prosim, Vase meno: "); 
						inputMena = scanner.nextLine();
						System.out.print("Zadajte Vase identifikacne cislo: ");
						inputCisla = scanner.nextLine();
						
						osoby.add(new FyzickaOsoba(inputMena, inputCisla, poradieOsoby));
						System.out.println("Vase meno je " + osoby.get(poradieOsoby).getMenoOsoby()+" a cislo Vasho OP je: "+osoby.get(poradieOsoby).getIdentifikacneCislo());
						
					  	prihlasenieFO.onlineOsoba((FyzickaOsoba) osoby.get(poradieOsoby), osoby, scanner, ziadosti);
						}
						else {
							ziadatelFO = (FyzickaOsoba) prihlasenieFO.najdiZiadatela(osoby, scanner);
							if (null != ziadatelFO) {
							prihlasenieFO.prihlasenieOsoby(ziadatelFO, osoby, scanner, ziadosti);
							}
						}
					  	break;
			case "2" :  if (true == prihlaseniePO.prvePrihlasenie(scanner)) {
						System.out.print("Zadajte, prosim, Vase meno: "); 
						inputMena = scanner.nextLine();
						System.out.print("Zadajte Vase identifikacne cislo: ");
						inputCisla = scanner.nextLine();
						
						osoby.add(new PravnickaOsoba(inputMena, inputCisla, poradieOsoby));
						System.out.println("Vase meno je " + osoby.get(poradieOsoby).getMenoOsoby()+" a Vase heslo je: "+osoby.get(poradieOsoby).getIdentifikacneCislo());
						
					  	prihlaseniePO.onlineOsoba((PravnickaOsoba) osoby.get(poradieOsoby), osoby, scanner, ziadosti);
						}
						else {
							ziadatelPO = (PravnickaOsoba) prihlaseniePO.najdiZiadatela(osoby, scanner);
							if (null != ziadatelPO) {
							prihlaseniePO.prihlasenieOsoby(ziadatelPO, osoby, scanner, ziadosti);
							}
						}
					  	break;
			case "3" :  String cinnost = "";
						System.out.println("Aky spracovatel sa prihlasuje? 1) preukaz 2) prispevok 3) rocny vykaz");
						cinnost = scanner.nextLine();
						if(cinnost.equals("1")) { if (true == prihlasenieSP.prvePrihlasenie(scanner)) {
									System.out.print("Zadajte, prosim, Vase meno: ");
									inputMena = scanner.nextLine();
									System.out.print("Zadajte Vase identifikacne cislo: ");
									inputCisla = scanner.nextLine();
									
									osoba = new SpracovatelPreukazu(inputMena, inputCisla, poradieOsoby);
									osoby.add(osoba);
									prihlasenieSP.onlineOsoba((SpracovatelPreukazu) osoba, osoby, scanner, ziadosti);
									}
								   else {
									   osoba = prihlasenieSP.najdiSpracovatela(osoby, scanner);
									   if (null != osoba) {
										   prihlasenieSP.prihlasenieOsoby((SpracovatelPreukazu) osoba, osoby, scanner, ziadosti);
									   }
								   }
						}
						if(cinnost.equals("2")) {  if (true == prihlasenieSP.prvePrihlasenie(scanner)) {
									System.out.print("Zadajte, prosim, Vase meno: ");
									inputMena = scanner.nextLine();
									System.out.print("Zadajte Vase identifikacne cislo: ");
									inputCisla = scanner.nextLine();
									
									osoba = new SpracovatelPrispevku(inputMena, inputCisla, poradieOsoby);
									osoby.add(osoba);
									prihlasenieSP.onlineOsoba((SpracovatelPrispevku) osoba, osoby, scanner, ziadosti);
									}
								   else {
									   osoba = prihlasenieSP.najdiSpracovatela(osoby, scanner);
									   if (null != osoba) {
										   prihlasenieSP.prihlasenieOsoby((SpracovatelPrispevku) osoba, osoby, scanner, ziadosti);
									   }
								   }
						}
						if(cinnost.equals("3")) { if (true == prihlasenieSP.prvePrihlasenie(scanner)) {
									System.out.print("Zadajte, prosim, Vase meno: ");
									inputMena = scanner.nextLine();
									System.out.print("Zadajte Vase identifikacne cislo: ");
									inputCisla = scanner.nextLine();
									
									osoba = new SpracovatelVykazu(inputMena, inputCisla, poradieOsoby);
									osoby.add(osoba);
									prihlasenieSP.onlineOsoba((SpracovatelVykazu) osoba, osoby, scanner, ziadosti);
									}
								   else {
									   osoba = prihlasenieSP.najdiSpracovatela(osoby, scanner);
									   if (null != osoba) {
										   prihlasenieSP.prihlasenieOsoby((SpracovatelVykazu) osoba, osoby, scanner, ziadosti);
									   }
								   }
						}
						break;
			case "4" :if (true == prihlasenieSC.prvePrihlasenie(scanner)) {
						System.out.print("Zadajte, prosim, Vase meno: "); 
						inputMena = scanner.nextLine();
						System.out.print("Zadajte Vase identifikacne cislo: ");
						inputCisla = scanner.nextLine();
						
						osoby.add(new Schvalovatel(inputMena, inputCisla, poradieOsoby));
						System.out.println("Vase meno je " + osoby.get(poradieOsoby).getMenoOsoby()+" a Vase zamestnanecke cislo je: "+osoby.get(poradieOsoby).getIdentifikacneCislo());
						
					  	prihlasenieSC.onlineOsoba((Schvalovatel) osoby.get(poradieOsoby), osoby, scanner, ziadosti);
						}
						else {
							schvalovatel = (Schvalovatel) prihlasenieSC.najdiSchvalovatela(osoby, scanner);
							if (null != schvalovatel) {
							prihlasenieSC.prihlasenieOsoby(schvalovatel, osoby, scanner, ziadosti);
							}
						}	 
						break;
			case "0" : stav = false;
					  	break;
			}
			if(osoby.size() > 0) poradieOsoby = osoby.size();
		}
		scanner.close();	
	}
}