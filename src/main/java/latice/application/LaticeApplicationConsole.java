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
import latice.model.setup.GameSetup;
import latice.ihm.*;

public class LaticeApplicationConsole {

	public static void main(String[] args) {
		ArrayList<Tuile> tuiles = GameSetup.creerTuile();
		
		ArrayList<Tuile> piocheJoueur1List = new ArrayList<>();
		ArrayList<Tuile> piocheJoueur2List = new ArrayList<>();
		GameSetup.creerPiocheJoueur(tuiles, piocheJoueur1List);
		GameSetup.creerPiocheJoueur(tuiles, piocheJoueur2List);
		
		Pioche piocheJoueur1 = new Pioche(piocheJoueur1List);
		Pioche piocheJoueur2 = new Pioche(piocheJoueur2List);
		
		Rack rackJoueur1 = new Rack();
		Rack rackJoueur2 = new Rack();
		GameSetup.creerRack(piocheJoueur1, rackJoueur1);
		GameSetup.creerRack(piocheJoueur2, rackJoueur2);
		
		
		System.out.println(rackJoueur1.afficherRack("J1 : "));
		System.out.println(rackJoueur2.afficherRack("J2 : "));
		
		Plateau plateau = new Plateau();
		
		
		int choix = -1; 
		boolean quitter = false;
		Scanner scanner = new Scanner(System.in);
		int ligne = -1;
		int colonne = -1;
		int choixCoup = -1;
		
		String nom1 = SaisieConsole.saisieChar();
		String nom2 = SaisieConsole.saisieChar();
		
	
		
		Joueur joueur1 = new Joueur(GameSetup.ordreJoueur(nom1, nom2)[0], rackJoueur1, piocheJoueur1, 0, 0); 
		Joueur joueur2 = new Joueur(GameSetup.ordreJoueur(nom1, nom2)[1], rackJoueur2, piocheJoueur2, 0, 0);
		
		
		TexteConsole.afficherBienvenue(); //TODO remplacer par autre message de bienvenue fait au début de la SAE
		TexteConsole.afficherMenu();
		
		
		while (!quitter) {
			
			Joueur joueur = joueur1;
			
			if (joueur.equals(joueur1)) {
				System.out.println(rackJoueur1.afficherRack("C'est au tour du joueur 1, voici votre rack :\n"));
			}
			else {
				System.out.println(rackJoueur2.afficherRack("C'est au tour du joueur 2, voici votre rack :\n"));
			}
			
			
		
			choix = SaisieConsole.saisieChoix();
			
			switch (choix) {
				
			case 1:
				
				choixCoup = SaisieConsole.saisieTuiles();
				ligne = SaisieConsole.saisieLigneColonne("ligne");
				colonne = SaisieConsole.saisieLigneColonne("colonne");
				
			
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
				break;
				
			default:
				quitter = true;
				
				
			}
			if (joueur.equals(joueur1)) {
				joueur = joueur2;
			}
			else {
				joueur = joueur1;
			}
		
			
		}
		scanner.close();
		
		
	}

	

	

	

}

