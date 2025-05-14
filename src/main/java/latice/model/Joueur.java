package latice.model;

public class Joueur {
	private String name;
	private Rack rack;
	private Pioche pioche;
	private int score;
	private int nbTuilesPosees;
	
	
	
	public Joueur(String name, Rack rack, Pioche pioche, int score, int nbTuilesPosees) {
		this.name = name;
		this.rack = rack;
		this.pioche = pioche;
		this.score = score;
		this.nbTuilesPosees = nbTuilesPosees;
	}

	public Rack getRack() {
		return rack;
	}

	public void setRack(Rack rack) {
		this.rack = rack;
	}

	public Pioche getPioche() {
		return pioche;
	}

	public void setPioche(Pioche pioche) {
		this.pioche = pioche;
	}

	public void jouerTuile(Tuile tuile, PositionTuiles position, Arbitre arbitre) {
		//TODO
	}
	
	public boolean acheterAction() {
		return true;
		//TODO
	}
	
	public void passerTour() {
		//TODO
	}
	
	public String getName() {
		return this.name;
	}
}

