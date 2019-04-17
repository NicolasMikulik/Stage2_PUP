import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class RocnyVykazCinnosti implements Ziadost{
	private String MenoZiadatela = "";
	private String Stav = "";
	private String Druh = "";
	private String DoplnujuceInformacie = "";
	private String Adresa;
	private String IdentifikacneCisloZiadatela;
	private int PocetHodin;
	private String TypAsistencie;
	private int PoradoveCislo = 0;
	private boolean Aktivna = false;
	private boolean Doplnenie;
	private Osoba Ziadatel = null;
	private Osoba Spracovatel = null;
	private Osoba Schvalovatel = null;
	private ArrayList<Osoba> InformovaneOsoby= new ArrayList<Osoba>();
	
	public RocnyVykazCinnosti(String name, String address, String securityNumber, int hours, String typeOfAssistance) {
		setMenoZiadatela(name);
		setAdresa(address);
		setIdentifikacneCisloZiadatela(securityNumber);
		setPocetHodin(hours);
		setTypAsistencie(typeOfAssistance);
		setStav("RoËn˝ v˝kaz ËinnostÌ bol podan˝.");
		setDruh("V˝kaz");
		setAktivna(true);
	}
	
	public void vypisUdajeZiadosti() {
		String MenoSpracovatela = "";
		if (null == getSpracovatel()) {MenoSpracovatela = "--";} else MenoSpracovatela = getSpracovatel().getMenoOsoby();
		String MenoSchvalovatela = "";
		if (null == getSchvalovatel()) {MenoSchvalovatela = "--";} else MenoSchvalovatela = getSchvalovatel().getMenoOsoby();
		System.out.println(getPoradoveCislo()+" "+getPocetHodin()+" "+getStav()+" "+getMenoZiadatela()+" "+MenoSpracovatela
				+" "+MenoSchvalovatela+" "+getDoplnujuceInformacie()+" "+getDruh());
	}
	
	public void kontrolaZiadosti(Ziadost ziadost, Spracovatel spracovatel, Stage processStage, Scene tableScene, ArrayList<Osoba> osoby) {
		boolean process = false;
			if (null == ziadost.getSpracovatel()) {
				boolean assign = ConfirmBox.display("Potvrdenie prijatia", "Zvolen˝ roËn˝ v˝kaz ËinnostÌ nikto"
						+ " nespracov·va. Chcete prijaù tento v˝kaz? éiadateæ bude informovan˝ o prijatÌ.");
				if (assign) {
					 spracovatel.prijmiZiadost((RocnyVykazCinnosti) ziadost);
					 process = ConfirmBox.display("Spracovanie", "Zvolen˝ v˝kaz je V·m pridelen˝, ûel·te si prejsù k jeho spracovaniu?");
						if(process) {
							((SpracovatelVykazu) spracovatel).spracovanieZiadosti((SpracovatelVykazu) spracovatel, (RocnyVykazCinnosti) ziadost, processStage, tableScene, osoby);
						}
				}
				else {AlertBox.display("Zamietnutie prijatia", "Zvolen˝ v˝kaz V·m nebude pridelen˝.");}
				}
			else {
				if (false == ziadost.getSpracovatel().getMenoOsoby().equals(spracovatel.getMenoOsoby())) {
					AlertBox.display("Neplatn· voæba", "Zvolen˝ v˝kaz spracov·va in˝ spracovateæ,"
							+ " teda ho nemÙûete prijaù.");}
				else {
					process = ConfirmBox.display("Neplatn· voæba", "Zvolen˝ v˝kaz je V·m uû pridelen˝, ûel·te si prejsù k jeho spracovaniu?");
					if(process) {
						((SpracovatelVykazu) spracovatel).spracovanieZiadosti((SpracovatelVykazu) spracovatel, (RocnyVykazCinnosti) ziadost, processStage, tableScene, osoby);
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

	public int getPocetHodin() {
		return PocetHodin;
	}

	public void setPocetHodin(int pocetHodin) {
		PocetHodin = pocetHodin;
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

	public String getTypAsistencie() {
		return TypAsistencie;
	}

	public void setTypAsistencie(String typAsistencie) {
		TypAsistencie = typAsistencie;
	}

	public boolean isDoplnenie() {
		return Doplnenie;
	}

	public void setDoplnenie(boolean doplnenie) {
		Doplnenie = doplnenie;
	}
}
