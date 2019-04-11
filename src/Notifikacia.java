
public class Notifikacia {
	private boolean Zobraz = true;
	private String notifikacia = "";
	
	public Notifikacia(String input) {
		setNotifikacia(input);
		setZobraz(true);
	}
	
	public void zobrazNotifikaciu() {
		System.out.print(this.getNotifikacia());
		System.out.println("Aj tu by mala byt notifikacia.");
		this.setZobraz(false);
	}
	
	public void updateNotifikacie(String Update) {
		String pripojenie = this.getNotifikacia();
		pripojenie = pripojenie.concat(Update);
		System.out.println(pripojenie);
		this.setNotifikacia(pripojenie);
		this.setZobraz(true);
	}
	
	public boolean isZobraz() {
		return Zobraz;
	}

	public void setZobraz(boolean zobrazena) {
		Zobraz = zobrazena;
		this.setNotifikacia("");
	}

	public String getNotifikacia() {
		return notifikacia;
	}

	public void setNotifikacia(String notifikacia) {
		this.notifikacia = notifikacia;
	}
}
