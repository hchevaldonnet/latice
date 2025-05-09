package latice.model.setup;

import java.util.ArrayList;
import java.util.Collections;

import latice.ihm.SaisieConsole;
import latice.ihm.TexteConsole;
import latice.model.Couleur;
import latice.model.Joueur;
import latice.model.Pioche;
import latice.model.Rack;
import latice.model.Symbole;
import latice.model.Tuile;

public class GameSetup {
	
	
	
	public static Joueur initaliseJoueur() { //TODO à finaliser
		String nom = "";
		Joueur j = new Joueur(SaisieConsole.saisieChar(), ); //TODO compléter les paramètres de joeuurs
		
		
		return j;
		
	
	}
	
	public static ArrayList<Tuile> creerTuile() {
		ArrayList<Tuile> tuiles = new ArrayList<>();
		for (Couleur couleur : Couleur.values()) {
			for (Symbole symbole : Symbole.values()) {
				tuiles.add(new Tuile(couleur, symbole));
				tuiles.add(new Tuile(couleur, symbole));
			}
		}
		
		Collections.shuffle(tuiles);
		return tuiles;
	}
	
	public static void creerPiocheJoueur(ArrayList<Tuile> tuiles, ArrayList<Tuile> piocheJoueurList) {
		for (int i = 0; i< tuiles.size(); i++) {
		 piocheJoueurList.add(tuiles.get(i));	
	}
	
	}
	
	public static void creerRack(Pioche piocheJoueur,  Rack rackJoueur) {
		for (int i = 0; i< 5; i++) {
			rackJoueur.ajoutTuile(piocheJoueur.piocher());
		}
	}
	

}
