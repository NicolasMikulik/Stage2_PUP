import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class PrihlasenieSchvalovatela {

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