import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;

public class PrihlaseniePO extends PrihlasenieZiadatela {

	public void prihlasenieOsoby(PravnickaOsoba osoba, ArrayList<Osoba> list, Scanner scanner, ArrayList<Ziadost> ziadosti) {
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
	
	public void onlineOsoba(PravnickaOsoba osoba, ArrayList<Osoba> list, Scanner scanner, ArrayList <Ziadost> ziadosti) {
		boolean online = true;
		boolean dopln = false;
		boolean zmazanie = true;
		int cinnost = 0;
		int poradieZiadosti = 0;
		String spracovatel = "";
		String stiahnutie = "";
		while (online) {
			System.out.println("Zadajte: 1) pre zadanie novej ziadosti 2) pre zistenie stavu Vasej existujucej ziadosti 3) pre odhlasenie");
			cinnost = scanner.nextInt();
			switch (cinnost) {
			case 1 : poradieZiadosti = osoba.vytvorZiadost(scanner); 
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
					if (osoba.getZiadosti().get(poradieZiadosti).getStav().contains("doplnenie")) osoba.doplnUdaje(osoba, osoba.getZiadosti().get(poradieZiadosti), scanner);
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
		System.out.print("Zadajte meno ziadatela [PO]: ");
		String menoZiadatela = scanner.nextLine();
	    while (iterator.hasNext()) {
	        Osoba HladanyZiadatel = iterator.next();
	        if (HladanyZiadatel.getMenoOsoby().equals(menoZiadatela) && HladanyZiadatel instanceof PravnickaOsoba) {
	        	System.out.println("Ziadatel [PO] bol najdeny.");
	            return HladanyZiadatel;
	        }
	    }
	    System.out.println("Ziadatel nebol najdeny.");
	    return null;
	}
	
}
