import java.util.Scanner;
import java.util.ArrayList;

public class PravnickaOsoba extends VlastnostiOsoby implements Osoba{
		private ArrayList<Ziadost> Ziadosti = new ArrayList<Ziadost>();
		
		public Notifikacia getNotifikaciaOsoby() {
			return NotifikaciaOsoby;
		}
		public void setNotifikaciaOsoby(Notifikacia notifikaciaOsoby) {
			NotifikaciaOsoby = notifikaciaOsoby;
		}
		public void setMenoOsoby(String MenoOsoby) {
			this.MenoOsoby = MenoOsoby;
		}
		public String getMenoOsoby() {
			return this.MenoOsoby;
		}

		public int getPoradoveCislo() {
			return PoradoveCislo;
		}
		public void setPoradoveCislo(int poradoveCislo) {
			PoradoveCislo = poradoveCislo;
		}
		public String getIdentifikacneCislo() {
			return IdentifikacneCislo;
		}
		public void setIdentifikacneCislo(String IdentifikacneCislo) {
			this.IdentifikacneCislo = IdentifikacneCislo;
		}
		public String getIdentifikacia() {
			return Identifikacia;
		}
		public void setIdentifikacia(String identifikacia) {
			Identifikacia = identifikacia;
		}
		public ArrayList<Ziadost> getZiadosti() {
			return Ziadosti;
		}
		public void setZiadosti(ArrayList<Ziadost> ziadosti) {
			Ziadosti = ziadosti;
		}
		
		public PravnickaOsoba(String MenoOsoby, String IdentifikacneCislo, int PoradoveCislo) {
			this.MenoOsoby = MenoOsoby;
			this.IdentifikacneCislo = IdentifikacneCislo;
			this.PoradoveCislo = PoradoveCislo;
			this.setIdentifikacia("PO");
		}
		
		public int vytvorZiadost(Scanner scanner) {
			System.out.println("Zadajte\n1) pre ziadost o preukaz 2) pre ziadost o prispevok 3) pre podanie rocneho vykazu");
			scanner.nextLine();
			String inputZiadosti = "";
			String TypZiadosti = scanner.nextLine();
			boolean tvorenieZiadosti = true;
			while (tvorenieZiadosti) 
			switch (TypZiadosti) {
				case "1":System.out.println("Ziadost o preukaz: Zadajte typ preukazu:");
						inputZiadosti = scanner.nextLine();
						this.getZiadosti().add(new ZiadostOPreukaz(this.getMenoOsoby(), inputZiadosti));
						this.getZiadosti().get(this.getZiadosti().size()-1).setZiadatel(this);
						((ZiadostOPreukaz) this.getZiadosti().get(this.getZiadosti().size()-1)).pridajInformovanuOsobu(this);
						tvorenieZiadosti = false;
						break;
				case "2":System.out.println("Ziadost o prispevok: Zadajte vysku prispevku:");
						inputZiadosti = scanner.nextLine();
						this.getZiadosti().add(new ZiadostOPrispevok(this.getMenoOsoby(), inputZiadosti));
						this.getZiadosti().get(this.getZiadosti().size()-1).setZiadatel(this);
						((ZiadostOPrispevok) this.getZiadosti().get(this.getZiadosti().size()-1)).pridajInformovanuOsobu(this);
						tvorenieZiadosti = false;
						break;
				case "3":System.out.println("Podanie rocneho vykazu cinnosti. Zadajte pocet hodin:");
						inputZiadosti = scanner.nextLine();
						this.getZiadosti().add(new RocnyVykazCinnosti(this.getMenoOsoby(), inputZiadosti));
						this.getZiadosti().get(this.getZiadosti().size()-1).setZiadatel(this);
						((RocnyVykazCinnosti) this.getZiadosti().get(this.getZiadosti().size()-1)).pridajInformovanuOsobu(this);
						tvorenieZiadosti = false;
						break;
				default:System.out.println("Neplatny vstup.");
						break;
				}
			return (this.getZiadosti().size()-1);
		}
		
		public void doplnUdaje(PravnickaOsoba osoba, Ziadost ziadost, Scanner scanner) {
			String doplnenie = "";
			System.out.println("Boli ste spracovatelom poziadany o doplnenie udajov. Prosim, napiste ziadane udaje:");
			scanner.nextLine();
			doplnenie = scanner.nextLine();
			ziadost.setDoplnujuceInformacie(doplnenie);
			ziadost.setStav("Ziadatel doplnil udaje v ziadosti a poslal ziadost naspat spracovatelovi (vid doplnujuce informacie).");
			System.out.println("Ziadatel doplnil udaje v ziadosti a poslal ziadost naspat spracovatelovi.");
		}
		
		public void stiahnutieZiadosti(ArrayList<Ziadost> ziadosti, Scanner scanner) {
			int poradieZiadosti = 0;
			Ziadost StahovanaZiadost = null;
			System.out.println("Zadajte cislo ziadosti, ktoru si zelate zmazat:");
			poradieZiadosti = scanner.nextInt();
			if(false == this.getZiadosti().get(poradieZiadosti).isAktivna()) {
				System.out.println("Dana ziadost uz nie je aktivna, takze nemoze byt stiahnuta.");
				return;
			}
			if(null != this.getZiadosti().get(poradieZiadosti) && true == this.getZiadosti().get(poradieZiadosti).isAktivna()) {
				StahovanaZiadost = this.getZiadosti().get(poradieZiadosti);
				if(null != StahovanaZiadost.getSchvalovatel()) {
					((Schvalovatel) StahovanaZiadost.getSchvalovatel()).getZiadostiNaSpracovanie().remove(StahovanaZiadost);
				}
				StahovanaZiadost.setSchvalovatel(null);
				if(null != StahovanaZiadost.getSpracovatel()) {
					if (StahovanaZiadost.getSpracovatel() instanceof SpracovatelPreukazu ) 
					((SpracovatelPreukazu) StahovanaZiadost.getSpracovatel()).getZiadostiNaSpracovanie().remove(StahovanaZiadost);
					if (StahovanaZiadost.getSpracovatel() instanceof SpracovatelPrispevku) 
					((SpracovatelPrispevku) StahovanaZiadost.getSpracovatel()).getZiadostiNaSpracovanie().remove(StahovanaZiadost);
					if (StahovanaZiadost.getSpracovatel() instanceof SpracovatelVykazu)
					  ((SpracovatelVykazu) StahovanaZiadost.getSpracovatel()).getZiadostiNaSpracovanie().remove(StahovanaZiadost);
				}
				StahovanaZiadost.setSpracovatel(null);
				ziadosti.remove(StahovanaZiadost);
				StahovanaZiadost.setAktivna(false);
				StahovanaZiadost.setInformovaneOsoby(null);
				StahovanaZiadost.setStav("Ziadost bola stiahnuta ziadatelom.");
			}
		}
}
