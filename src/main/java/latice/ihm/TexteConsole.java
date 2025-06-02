package latice.ihm;

import latice.model.Joueur;

public class TexteConsole {
	
	 public static void afficherBienvenue() {
	        System.out.println("Bienvenue dans le jeu de cartes Latice !");
	    }

	 public static void afficherMenu() {
		    System.out.println("1. Placer une tuile");
		    System.out.println("2. Piocher une nouvelle main");
		    System.out.println("3. Passer son tour");
		    System.out.println("4. Afficher les règles");
		    System.out.println("5. Quitter\n");
		}

	    public static void afficherRegles() {
	        System.out.println("Voici les règles du jeu : ...");
	    }

	    public static void afficherErreurSaisie() {
	        System.out.println("Saisie invalide, veuillez réessayer.");
	    }
	    
	    public static void demanderAction() {
	        System.out.print("Quelle action voulez-vous faire ? ");
	    }

	    public static void demanderEmplacement(String emplacement) { 
	        System.out.print("Sur quelle " + emplacement + " voulez-vous placer la tuile (1-8) : ");
	    }


	    public static void demanderTuile() {
	        System.out.print("Veuillez choisir une tuile entre 1 et 5 : ");
	    }

	    public static void caseSunStone() { //TODO Metre des arguments String pour éviter la dupliactio,
	        System.out.println("C'est une case soleil !");
	    }
	    
	    public static void notCaseSunStone() {
	    	System.out.println("Ce n'est pas une case soleil");
	    }
	    
	    public static void caseMoonStone() {
	        System.out.println("C'est la case lune !");
	    }
	    
	    public static void notCaseMoonStone() {
	    	System.out.println("Ce n'est pas la case lune");
	    }
	    
	    public static void tourJoueur(Joueur joueur) {
	    	System.out.println("C'est à " + joueur.getName());
	    }
	    
	    public static void demanderNom() {
	    	System.out.println("Veuillez entrer votre nom");
	    }

	    
}


