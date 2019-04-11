import java.util.ArrayList;
import java.util.Scanner;

public class SpracovatelPrispevku extends Spracovatel {
		private ArrayList <ZiadostOPrispevok> ZiadostiNaSpracovanie = new ArrayList<ZiadostOPrispevok>();
		
		public SpracovatelPrispevku(String Meno, String IdentifikacneCislo, int PoradoveCislo) {
			this.setMenoOsoby(Meno);
			this.setIdentifikacneCislo(IdentifikacneCislo);
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
		
		public ArrayList<ZiadostOPrispevok> getZiadostiNaSpracovanie() {
			return ZiadostiNaSpracovanie;
		}
		public void setZiadostiNaSpracovanie(ArrayList<ZiadostOPrispevok> ziadostiNaSpracovanie) {
			ZiadostiNaSpracovanie = ziadostiNaSpracovanie;
		}
}
