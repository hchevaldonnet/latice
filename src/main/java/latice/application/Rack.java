package latice.application;

import java.util.ArrayList;
import java.util.List;

import latice.application.Pioche;
import latice.application.Tuile;

public class Rack {
	private List<Tuile> tuiles;
	
	public Rack() {
		this.tuiles = new ArrayList<>();
	}
	
	public void ajoutTuile(Tuile tuile) {
		if (tuiles == null) {
			System.out.println("Erreur la liste est null");
		} else if (tuile != null) {
			tuiles.add(tuile);
		}
	}
	
	public void remplir(Pioche pioche) {
		while (tuiles.size() < 5 && pioche.taille() >0) {
			Tuile tuile = pioche.piocher();
			if (tuile != null) {
				tuiles.add(tuile);
			}
		}
	}
	
	public void echangerTout(Pioche pioche) {
		//TODO
	}
	
	public String afficherRack() {
		String res = "";
		for (Tuile tuile : tuiles) {
			res = res + tuile.toString();
			res = res + " ";
		}
		return res;
	}

	public List<Tuile> getTuiles() {
		return tuiles;
	}
	
	
}
