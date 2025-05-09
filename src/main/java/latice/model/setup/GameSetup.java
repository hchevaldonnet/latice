package latice.model.setup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import latice.ihm.SaisieConsole;
import latice.ihm.TexteConsole;
import latice.model.Couleur;
import latice.model.Joueur;
import latice.model.Pioche;
import latice.model.Rack;
import latice.model.Symbole;
import latice.model.Tuile;

public class GameSetup {
	

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
	
	public static String[]  ordreJoueur(String nom1, String nom2){
		
		Random random = new Random();
		String[] list = new String[2];
		
		boolean choix = random.nextBoolean();
		
		if(choix) {
			list[0] = nom1;
			list[1] = nom2;
		}
		else {
			list[0] = nom2;
			list[1] = nom1;
		}
		
		return list;
		
	}
	

}
