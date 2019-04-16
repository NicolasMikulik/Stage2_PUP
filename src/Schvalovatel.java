import java.util.ArrayList;
import java.util.Scanner;

public class Schvalovatel extends Spracovatel{	
	private ArrayList <Ziadost> ZiadostiNaSpracovanie = new ArrayList<Ziadost>();
	private String Heslo;
	
	public Notifikacia getNotifikaciaOsoby() {
		return NotifikaciaOsoby;
	}
	public void setNotifikaciaOsoby(Notifikacia notifikaciaOsoby) {
		NotifikaciaOsoby = notifikaciaOsoby;
	}
	
	public Schvalovatel(String MenoOsoby, String IdentifikacneCislo, int PoradoveCislo) {
		this.setMenoOsoby(MenoOsoby);
		this.setIdentifikacneCislo(IdentifikacneCislo);
		this.setPoradoveCislo(PoradoveCislo);
		this.setIdentifikacia("SC");
	}
	
	public void prijmiZiadost(Ziadost ziadost) {
		this.ZiadostiNaSpracovanie.add(ziadost);
		ziadost.setSchvalovatel(this);
		ziadost.setStav("Ziadost bola prijata SCHVALOVATELOM.");
		System.out.print("Ziadost bola prijata SCHVALOVATELOM."+ziadost.getSchvalovatel().getMenoOsoby());
	}
	
	public void vratZiadostSpracovatelovi(Ziadost HladanaZiadost, String comment) {
		System.out.print("Ziadost bola SCHVALOVATELOM odoslana naspat spracovatelovi. " + comment);
		HladanaZiadost.setStav("Ziadost bola SCHVALOVATELOM odoslana spracovatelovi na doplnenie udaju: "+comment);
		HladanaZiadost.setDoplnenie(true);
		HladanaZiadost.setSchvalovatel(null);
		this.ZiadostiNaSpracovanie.remove(HladanaZiadost);
	}
	
	public void vratZiadostSpracovatelovi(Ziadost HladanaZiadost, Scanner scanner) {
		System.out.println("Napiste, ktore udaje ma spracovatel k ziadosti doplnit: ");
		scanner.nextLine();
		String doplnenie = scanner.nextLine();
		System.out.print("Ziadost bola SCHVALOVATELOM odoslana naspat spracovatelovi.");
		HladanaZiadost.setStav("Ziadost bola SCHVALOVATELOM odoslana spracovatelovi na doplnenie udaju: "+doplnenie);
		HladanaZiadost.setSchvalovatel(null);
		this.ZiadostiNaSpracovanie.remove(HladanaZiadost);
	}
	
	public void pridelZiadostSpracovatelovi(Ziadost HladanaZiadost, String comment) {
		System.out.print("Ziadost bola SCHVALOVATELOM pridelena spracovatelovi. "+comment);
		HladanaZiadost.setStav("Ziadost bola SCHVALOVATELOM pridelena spracovatelovi na doplnenie udaju: "+comment);
		HladanaZiadost.setSchvalovatel(null);
		this.ZiadostiNaSpracovanie.remove(HladanaZiadost);
	}
	
	public void pridelZiadostSpracovatelovi(ZiadostOPreukaz HladanaZiadost, Scanner scanner, ArrayList <Osoba> osoby) {
		System.out.print("Napiste, ktore udaje ma spracovatel doplnit: ");
		String doplnenie = scanner.nextLine();
		System.out.print("Ziadost bola SCHVALOVATELOM pridelena spracovatelovi.");
		HladanaZiadost.setStav("Ziadost bola SCHVALOVATELOM pridelena spracovatelovi na doplnenie udaju: "+doplnenie);
		HladanaZiadost.setSchvalovatel(null);
		this.ZiadostiNaSpracovanie.remove(HladanaZiadost);
	}
	
	public void zamietniZiadost(Ziadost HladanaZiadost, ArrayList<Ziadost> ziadosti, String comment) {
		System.out.print("Ziadost bola SCHVALOVATELOM posudena (ukoncena) a poslana naspat ziadatelovi. "+comment);
		HladanaZiadost.setStav("Ziadost bola SCHVALOVATELOM zamietnuta z dovodu: "+comment); 
		HladanaZiadost.setSchvalovatel(null);
		HladanaZiadost.setSpracovatel(null);
		HladanaZiadost.setAktivna(false);
		this.ZiadostiNaSpracovanie.remove(HladanaZiadost);
		ziadosti.remove(HladanaZiadost);
	}
	
	public void zamietniZiadost(Ziadost HladanaZiadost, ArrayList<Ziadost> ziadosti, Scanner scanner) {
		System.out.println("Napiste, z akeho dovodu ziadost zamietate: ");
		scanner.nextLine();
		String zamietnutie = scanner.nextLine();
		System.out.print("Ziadost bola SCHVALOVATELOM posudena (ukoncena) a poslana naspat ziadatelovi.");
		HladanaZiadost.setStav("Ziadost bola SCHVALOVATELOM zamietnuta z dovodu: "+zamietnutie);
		HladanaZiadost.setSchvalovatel(null);
		HladanaZiadost.setSpracovatel(null);
		HladanaZiadost.setAktivna(false);
		this.ZiadostiNaSpracovanie.remove(HladanaZiadost);
		ziadosti.remove(HladanaZiadost);
	}
	
	public void schvalZiadost(Ziadost HladanaZiadost, ArrayList<Ziadost> ziadosti, String comment) {
		System.out.println("Ziadost bola SCHVALOVATELOM posudena (ukoncena) a poslana naspat ziadatelovi. "+comment);
		HladanaZiadost.setStav("Ziadost bola SCHVALOVATELOM posudena (ukoncena) a schvalena s vyjadrenim: "+comment);
		HladanaZiadost.setSchvalovatel(null);
		HladanaZiadost.setSpracovatel(null);
		HladanaZiadost.setAktivna(false);
		this.ZiadostiNaSpracovanie.remove(HladanaZiadost);
		ziadosti.remove(HladanaZiadost);
	}
	
	public void schvalZiadost(Ziadost HladanaZiadost, ArrayList<Ziadost> ziadosti, Scanner scanner) {
		System.out.print("Ziadost bola SCHVALOVATELOM posudena (ukoncena) a poslana naspat ziadatelovi.");
		HladanaZiadost.setStav("Ziadost bola SCHVALOVATELOM posudena (ukoncena) a schvalena.");
		HladanaZiadost.setSchvalovatel(null);
		HladanaZiadost.setSpracovatel(null);
		HladanaZiadost.setAktivna(false);
		this.ZiadostiNaSpracovanie.remove(HladanaZiadost);
		ziadosti.remove(HladanaZiadost);
	}

	public ArrayList<Ziadost> getZiadostiNaSpracovanie() {
		return ZiadostiNaSpracovanie;
	}
	
	public void setZiadostiNaSpracovanie(ArrayList<Ziadost> ziadostiNaSpracovanie) {
		ZiadostiNaSpracovanie = ziadostiNaSpracovanie;
	}
	
	public void setHeslo(String Heslo) {
		this.Heslo = Heslo;
	}
	public String getHeslo() {
		return Heslo;
	}
}
