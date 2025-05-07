package latice.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import latice.model.Couleur;
import latice.model.Joueur;
import latice.model.Pioche;
import latice.model.Plateau;
import latice.model.PositionTuiles;
import latice.model.Rack;
import latice.model.Symbole;
import latice.model.Tuile;
import latice.ihm.*;

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
		
		Plateau plateau = new Plateau();
		
		
		int choix = -1; 
		boolean quitter = false;
		Scanner scanner = new Scanner(System.in);
		int ligne = -1;
		int colonne = -1;
		int choixCoup = -1;
		
		
		
		
		//TODO modifier le code 
		while (!quitter) {
			
			Joueur joueur = new Joueur();
			
			TexteConsole.afficherBienvenue(); //TODO remplacer par autre message de bienvenue fait au début de la SAE
			TexteConsole.afficherMenu();
		
			choix = SaisieConsole.saisieChoix();
			
			switch (choix) {
				
			case 1:
				
				choixCoup = SaisieConsole.saisieTuiles();
				ligne = SaisieConsole.saisieEntier("ligne");
				colonne = SaisieConsole.saisieEntier("colonne");
				
			
				PositionTuiles pos = new PositionTuiles(ligne, colonne);
				plateau.placerTuile(rackJoueur1.getTuiles().get(choixCoup), pos);
				
				if (plateau.caseIsSunStones(pos)){
					TexteConsole.caseSunStone();
				}
				else {
					TexteConsole.notCaseSunStone();
					
				}
				if (plateau.caseIsMoon(pos)) {
					TexteConsole.caseMoonStone();
				}
				else {
					TexteConsole.notCaseMoonStone();
				}
				
			default:
				quitter = true;
				
			}
			scanner.close();
			
		}
		
		
	}

}
