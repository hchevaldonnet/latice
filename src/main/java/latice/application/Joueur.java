package latice.application;

import latice.application.Arbitre;
import latice.application.Pioche;
import latice.application.PositionTuiles;
import latice.application.Rack;
import latice.application.Tuile;

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
}
