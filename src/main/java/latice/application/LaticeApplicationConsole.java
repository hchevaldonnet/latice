package latice.application;

import java.util.ArrayList;
import java.util.Collections;

public class LaticeApplicationConsole {

	public static void main(String[] args) {
		ArrayList<Tuile> tuiles = new ArrayList<>();
		for (Couleur couleur : Couleur.values()) {
			for (Symbole symbole : Symbole.values()) {
				tuiles.add(new Tuile(couleur, symbole));
				tuiles.add(new Tuile(couleur, symbole));
			}
		}
		
		Collections.shuffle(tuiles);
		
		ArrayList<Tuile> piocheJoueur1List = new ArrayList<>();
		ArrayList<Tuile> piocheJoueur2List = new ArrayList<>();
		for (int i = 0; i< tuiles.size(); i++) {
			if (i % 2 ==0) {
				piocheJoueur1List.add(tuiles.get(i));
			} else {
				piocheJoueur2List.add(tuiles.get(i));
			}
		}
		
		Pioche piocheJoueur1 = new Pioche(piocheJoueur1List);
		Pioche piocheJoueur2 = new Pioche(piocheJoueur2List);
		
		Rack rackJoueur1 = new Rack();
		Rack rackJoueur2 = new Rack();
		for (int i = 0; i< 5; i++) {
			rackJoueur1.ajoutTuile(piocheJoueur1.piocher());
			rackJoueur2.ajoutTuile(piocheJoueur2.piocher());
		}
		
		System.out.println("J1 :");
		System.out.println(rackJoueur1.afficherRack());
		System.out.println("J2 :");
		System.out.println(rackJoueur2.afficherRack());
	}

}
