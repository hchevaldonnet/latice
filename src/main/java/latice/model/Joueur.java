package latice.model;

public class Joueur {
	private String name;
	private Rack rack;
	private Pioche pioche;
	private int score;
	private int nbTuilesPosees;
	
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

