package latice.model;

import java.util.ArrayList;
import java.util.List;

public class Rack {
	private List<Tuile> tuiles;
	
	public Rack(Pioche pioches) {
		this.tuiles = new ArrayList<>();
	}
	
	public void ajoutTuile(Tuile tuile) {
		if (tuiles == null) {
			System.out.println("Erreur la liste est null");
		} else if (tuile != null) {
			tuiles.add(tuile);
		}
	}
	
	public void remplir(Pioche pioche, int joueurIndex) {
		while (tuiles.size() < 5 && pioche.taille(joueurIndex) >0) {
			Tuile tuile = pioche.piocher(joueurIndex);
			if (tuile != null) {
				tuiles.add(tuile);
			}
		}
	}
	
	public void echangerTout(Pioche pioche) {
		//TODO
	}
	
	public String afficherRack(String message) {
		String res = "";
		
		System.out.print(message);
		for (Tuile tuile : tuiles) {
			res = res + tuile.toString();
			res = res + " ";
		}
		return res + "\n";
	}

	public List<Tuile> getTuiles() {
		return tuiles;
	}
	
    public void retirerTuile(int index) {
        if (index >= 0 && index < tuiles.size()) {
            tuiles.remove(index);
        }
    }
	
	
	
	
}
