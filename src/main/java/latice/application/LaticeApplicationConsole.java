package latice.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

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
			
			System.out.print("Quels actions voulez-vous faire ? ");
			if (scanner.hasNextInt()) {
				 choix = scanner.nextInt();
			}
			
			
			switch (choix) {
				
			case 1:
				
				System.out.print("Sur quelle ligne voulez-vous placez la tuile : ");
				if (scanner.hasNextInt()) {
					 ligne = scanner.nextInt();
				}
			
				System.out.print("Sur quelle colonne voulez-vous placez la tuile : ");
				if (scanner.hasNextInt()) {
					colonne = scanner.nextInt(); 
				}
				
				System.out.println("Veuillez choisir une tuile entre de 1 à 5 : ");
				if (scanner.hasNextInt()) {
					choixCoup = scanner.nextInt()-1;
				}
				PositionTuiles pos = new PositionTuiles(ligne, colonne);
				System.out.println(pos.getX());
				plateau.placerTuile(rackJoueur1.getTuiles().get(choixCoup), pos);
				if (plateau.caseIsSunStones(pos)){
					System.out.println("c'est une case soleil");
				}
				else {
					System.out.println("ce n'est pas une case soleil");
					
				}
				if (plateau.caseIsMoon(pos)) {
					System.out.println("c'est une case lune");
				}
				else {
					System.out.println("ce n'est pas une case lune");
				}
				
			default:
				quitter = true;
				
			}
			scanner.close();
			
		}
		
		
	}

}
