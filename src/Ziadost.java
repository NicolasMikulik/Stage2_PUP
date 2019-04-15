import java.util.ArrayList;
import javafx.stage.Stage;
import javafx.scene.Scene;

public interface Ziadost {
	
	public String getMenoZiadatela();
	public void setMenoZiadatela(String menoZiadatela);
	public String getStav();
	public void setStav(String stav);
	public Osoba getZiadatel();
	public void setZiadatel(Osoba ziadatel);
	public Osoba getSpracovatel();
	public void setSpracovatel(Osoba spracovatel);
	public Osoba getSchvalovatel();
	public void setSchvalovatel(Osoba schvalovatel);
	public String getDoplnujuceInformacie();
	public void setDoplnujuceInformacie(String doplnujuceInformacie);
	public void vypisUdajeZiadosti();
	public String getDruh();
	public void setDruh(String druh);
	public boolean isAktivna();
	public void setAktivna(boolean aktivna);
	public int getPoradoveCislo();
	public void setPoradoveCislo(int poradoveCislo);
	public String getAdresa();
	public void setAdresa(String adresa);
	public String getIdentifikacneCisloZiadatela();
	public void setIdentifikacneCisloZiadatela(String identifikacneCisloZiadatela);
	public void kontrolaZiadosti(Ziadost ziadost, Spracovatel spracovatel, Stage mainStage, Scene tableScene, ArrayList<Osoba> osoby);
}
