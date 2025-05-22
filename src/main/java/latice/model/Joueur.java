package latice.model;

import java.util.ArrayList;
import java.util.List;

public class Joueur {
	private String name;
	private Rack rack;
	private Pioche pioche;
	private int score;
	private int nbTuilesPosees;
	
	
	
	public Joueur(String string, Rack rack, Pioche pioche, int score, int nbTuilesPosees) {
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

    public boolean jouerActionSpeciale(int joueurActuel, ActionSpeciale action, Rack rack, Pioche pioche, Arbitre arbitre) {
	    switch (action) {
	        case ACTION_SUPPLEMENTAIRE:
	            if (arbitre.getScore(joueurActuel) >= 2) {
	                arbitre.pointsJoueur[joueurActuel] -= 2;
	                return true;
	            }
	            return false;

	        case PASSER_TOUR:
	            // Ne fait rien de particulier ici
	            return false;

	        case ECHANGER_RACK:
	            if (!pioche.estVide(joueurActuel)) {
	                List<Tuile> anciennesTuiles = new ArrayList<>(rack.getTuiles());
	                rack.vider();
	                for (int i = 0; i < anciennesTuiles.size(); i++) {
	                    Tuile nouvelle = pioche.piocher(joueurActuel);
	                    if (nouvelle != null) {
	                        rack.ajoutTuile(nouvelle);
	                    }
	                }
	                pioche.ajouterTout(anciennesTuiles, joueurActuel);
	                return false;
	            }
	            return false;

	        default:
	            return false;
	    }
	}
}

