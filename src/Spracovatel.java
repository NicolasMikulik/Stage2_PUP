import java.util.ArrayList;

public class Spracovatel extends VlastnostiOsoby implements Osoba{
	
	protected ArrayList <Ziadost> ZiadostiNaSpracovanie = new ArrayList<Ziadost>();
	
	public void prijmiZiadost(Ziadost ziadost) {
		ziadost.setSpracovatel(this);
	}
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
	public void setHeslo(String Heslo) {
		this.Heslo = Heslo;
	}
	public String getHeslo() {
		return Heslo;
	}
	public ArrayList<Ziadost> getZiadostiNaSpracovanie() {
		return ZiadostiNaSpracovanie;
	}
	public void setZiadostiNaSpracovanie(ArrayList<Ziadost> ziadostiNaSpracovanie) {
		ZiadostiNaSpracovanie = ziadostiNaSpracovanie;
	}
}
