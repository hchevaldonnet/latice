package latice.model;

public enum Symbole {
	 FLEUR("fleur"),
	 LEZARD("lezard"),
	 PLUME("plume"),
	 OISEAU("oiseau"),
	 DAUPHIN("dauphin"),
	 TORTUE("tortue");
	
	private final String symbole;
	
	private Symbole(String symbole) {
		this.symbole = symbole;
	}
		
	public String getSymbole() {
		return this.symbole;
	}
		
}

