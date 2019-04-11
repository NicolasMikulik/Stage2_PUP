import java.util.ArrayList;
import java.util.Scanner;

public class SpracovatelVykazu extends Spracovatel{
		private ArrayList <RocnyVykazCinnosti> ZiadostiNaSpracovanie = new ArrayList<RocnyVykazCinnosti>();
		
		public SpracovatelVykazu(String Meno, String IdentifikacneCislo, int PoradoveCislo) {
			this.setMenoOsoby(Meno);
			this.setIdentifikacneCislo(IdentifikacneCislo);
			this.PoradoveCislo = PoradoveCislo;
			this.setIdentifikacia("SP");
		}
		
		public void prijmiZiadost(RocnyVykazCinnosti ziadost) {
			this.ZiadostiNaSpracovanie.add(ziadost);
			ziadost.setSpracovatel(this);
			ziadost.pridajInformovanuOsobu(this);
			ziadost.setStav("Ziadost bola prijata spracovatelom.");
		}
		
		public void vratZiadost(RocnyVykazCinnosti RocnyVykaz, Scanner scanner) {
			String doplnenie = "";
			System.out.println("Napiste, ktore udaje ma ziadatel doplnit: ");
			scanner.nextLine();
			doplnenie = scanner.nextLine();
			System.out.println("Rocny vykaz cinnosti bol spracovatelom odoslany naspat uzivatelovi na doplnenie udaju:" + doplnenie);
			RocnyVykaz.setStav("Rocny vykaz cinnosti bol spracovatelom odoslany uzivatelovi na doplnenie udaju: " + doplnenie);
		}
		
		public ArrayList<RocnyVykazCinnosti> getZiadostiNaSpracovanie() {
			return ZiadostiNaSpracovanie;
		}
		public void setZiadostiNaSpracovanie(ArrayList<RocnyVykazCinnosti> ziadostiNaSpracovanie) {
			ZiadostiNaSpracovanie = ziadostiNaSpracovanie;
		}
}
