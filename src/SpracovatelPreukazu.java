import java.util.ArrayList;
import java.util.Scanner;

public class SpracovatelPreukazu extends Spracovatel {
		private ArrayList <ZiadostOPreukaz> ZiadostiNaSpracovanie = new ArrayList<ZiadostOPreukaz>();
		
		public SpracovatelPreukazu(String Meno, String IdentifikacneCislo, int PoradoveCislo) {
			this.setMenoOsoby(Meno);
			this.setIdentifikacneCislo(IdentifikacneCislo);
			this.PoradoveCislo = PoradoveCislo;
			this.setIdentifikacia("SP");
		}
		
		public void prijmiZiadost(ZiadostOPreukaz ziadost) {
			this.ZiadostiNaSpracovanie.add(ziadost);
			ziadost.setSpracovatel(this);
			ziadost.pridajInformovanuOsobu(this);
			ziadost.setStav("Ziadost bola prijata spracovatelom.");
		}
		
		public void vratZiadost(ZiadostOPreukaz HladanaZiadost, Scanner scanner) {
			String doplnenie = "";
			System.out.println("Napiste, ktore udaje ma ziadatel doplnit: ");
			scanner.nextLine();
			doplnenie = scanner.nextLine();
			System.out.println("Ziadost bola spracovatelom odoslana naspat uzivatelovi na doplnenie udaju:" + doplnenie);
			HladanaZiadost.setStav("Ziadost bola spracovatelom odoslana uzivatelovi na doplnenie udaju: " + doplnenie);
		}
		
		public ArrayList<ZiadostOPreukaz> getZiadostiNaSpracovanie() {
			return ZiadostiNaSpracovanie;
		}
		public void setZiadostiNaSpracovanie(ArrayList<ZiadostOPreukaz> ziadostiNaSpracovanie) {
			ZiadostiNaSpracovanie = ziadostiNaSpracovanie;
		}
}
