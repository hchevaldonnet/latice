package latice.application;

public enum Symbole {
	 FLEUR("🌸"),
	 LEZARD("🦎"),
	 PLUME("🪶"),
	 OISEAU("🐦"),
	 DAUPHIN("🐬"),
	 TORTUE("🐢");
	
	private final String symbole;
	
	private Symbole(String symbole) {
		this.symbole = symbole;
	}
		
	public String getSymbole() {
		return this.symbole;
	}
		
}

