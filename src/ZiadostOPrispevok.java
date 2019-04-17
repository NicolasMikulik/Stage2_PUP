import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ZiadostOPrispevok implements Ziadost {
		private String MenoZiadatela = "";
		private String Stav = "";
		private int VyskaPrispevku;
		private String Druh = "";
		private String DoplnujuceInformacie = "";
		private String Adresa;
		private String IdentifikacneCisloZiadatela;
		private int PoradoveCislo = 0;
		private boolean Aktivna = false; 
		private boolean Doplnenie;
		private Osoba Ziadatel = null;
		private Osoba Spracovatel = null;
		private Osoba Schvalovatel = null;
		private ArrayList<Osoba> InformovaneOsoby= new ArrayList<Osoba>();
		
		public ZiadostOPrispevok(String name, String address, String securityNumber, int amount) {
			setMenoZiadatela(name);
			setAdresa(address);
			setIdentifikacneCisloZiadatela(securityNumber);
			setVyskaPrispevku(amount);
			setDruh("Pr�spevok");
			setStav("�iados� o pr�spevok bola podan�.");
			setAktivna(true);
		}
		
		public void vypisUdajeZiadosti() {
			String MenoSpracovatela = "";
			if (null == getSpracovatel()) {MenoSpracovatela = "--";} else MenoSpracovatela = getSpracovatel().getMenoOsoby();
			String MenoSchvalovatela = "";
			if (null == getSchvalovatel()) {MenoSchvalovatela = "--";} else MenoSchvalovatela = getSchvalovatel().getMenoOsoby();
			System.out.println(getPoradoveCislo()+" "+getVyskaPrispevku()+" "+getStav()+" "+getMenoZiadatela()+" "+MenoSpracovatela
					+" "+MenoSchvalovatela+" "+getDoplnujuceInformacie()+" "+getDruh());
		}
		
		public void kontrolaZiadosti(Ziadost ziadost, Spracovatel spracovatel, Stage processStage, Scene tableScene, ArrayList<Osoba> osoby) {
			boolean process = false;
				if (null == ziadost.getSpracovatel()) {
					boolean assign = ConfirmBox.display("Potvrdenie prijatia", "Zvolen� �iados� o pr�spevok nikto"
							+ " nespracov�va. Chcete prija� t�to �iados�?\n�iadate� bude informovan� o prijat�.");
					if (assign) {
						 spracovatel.prijmiZiadost((ZiadostOPrispevok) ziadost);
						 process = ConfirmBox.display("Spracovanie", "�iados� o pr�spevok je V�m pridelen�, �el�te si prejs� k jej spracovaniu?");
							if(process) {
								((SpracovatelPrispevku) spracovatel).spracovanieZiadosti((SpracovatelPrispevku) spracovatel, (ZiadostOPrispevok) ziadost, processStage, tableScene, osoby);
							}
					}
					else {AlertBox.display("Zamietnutie prijatia", "�iados� o pr�spevok V�m nebude pridelen�.");}
					}
				else {
					if (false == ziadost.getSpracovatel().getMenoOsoby().equals(spracovatel.getMenoOsoby())) {
						AlertBox.display("Neplatn� vo�ba", "�iados� o pr�spevok spracov�va in� spracovate�,"
								+ " teda ju nem��ete prija�.");}
					else {
						process = ConfirmBox.display("Neplatn� vo�ba", "�iados� o pr�spevok je V�m u� pridelen�, �el�te si prejs� k jej spracovaniu?");
						if(process) {
							((SpracovatelPrispevku) spracovatel).spracovanieZiadosti((SpracovatelPrispevku) spracovatel, (ZiadostOPrispevok) ziadost, processStage, tableScene, osoby);
						}
					}
				}
			}
		
		public void pridajInformovanuOsobu(Osoba osoba) {
			this.getInformovaneOsoby().add(osoba);
		}
		
		public void odoberInformovanuOsobu(Osoba osoba) {
			this.getInformovaneOsoby().remove(osoba);
		}
		
		public void upovedomInformovaneOsoby(String stav) {
			for(Osoba osoba: this.getInformovaneOsoby()) {
				osoba.getNotifikaciaOsoby().updateNotifikacie("Stav ziadosti evidovanej na urade pod cislom "
				+this.getPoradoveCislo()+" sa zmenil na "+stav+"\n");
			}
		}
		
		public String getMenoZiadatela() {
			return MenoZiadatela;
		}
		public void setMenoZiadatela(String menoZiadatela) {
			MenoZiadatela = menoZiadatela;
		}
		public String getStav() {
			return Stav;
		}
		public void setStav(String stav) {
			Stav = stav;
			upovedomInformovaneOsoby(stav);
		}
		public Osoba getZiadatel() {
			return Ziadatel;
		}
		public void setZiadatel(Osoba ziadatel) {
			Ziadatel = ziadatel;
		}
		public Osoba getSpracovatel() {
			return Spracovatel;
		}
		public void setSpracovatel(Osoba spracovatel) {
			Spracovatel = spracovatel;
		}
		public Osoba getSchvalovatel() {
			return this.Schvalovatel;
		}
		public void setSchvalovatel(Osoba schvalovatel) {
			Schvalovatel = schvalovatel;
		}
		public String getDoplnujuceInformacie() {
			return DoplnujuceInformacie;
		}
		public void setDoplnujuceInformacie(String doplnujuceInformacie) {
			DoplnujuceInformacie = doplnujuceInformacie;
		}

		public int getVyskaPrispevku() {
			return VyskaPrispevku;
		}

		public void setVyskaPrispevku(int vyskaPrispevku) {
			VyskaPrispevku = vyskaPrispevku;
		}

		public String getDruh() {
			return Druh;
		}

		public void setDruh(String druh) {
			Druh = druh;
		}
		public boolean isAktivna() {
			return Aktivna;
		}
		public void setAktivna(boolean aktivna) {
			Aktivna = aktivna;
		}
		public int getPoradoveCislo() {
			return PoradoveCislo;
		}
		public void setPoradoveCislo(int poradoveCislo) {
			PoradoveCislo = poradoveCislo;
		}
		public ArrayList<Osoba> getInformovaneOsoby() {
			return InformovaneOsoby;
		}
		public void setInformovaneOsoby(ArrayList<Osoba> informovaneOsoby) {
			InformovaneOsoby = informovaneOsoby;
		}

		public String getAdresa() {
			return Adresa;
		}

		public void setAdresa(String adresa) {
			Adresa = adresa;
		}

		public String getIdentifikacneCisloZiadatela() {
			return IdentifikacneCisloZiadatela;
		}

		public void setIdentifikacneCisloZiadatela(String identifikacneCisloZiadatela) {
			IdentifikacneCisloZiadatela = identifikacneCisloZiadatela;
		}

		public boolean isDoplnenie() {
			return Doplnenie;
		}

		public void setDoplnenie(boolean doplnenie) {
			Doplnenie = doplnenie;
		}
}