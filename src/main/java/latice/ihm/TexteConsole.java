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
	        System.out.println("Voici les règles du jeu : Règles du Jeu Latice\r\n"
	        		+ "Objectif : Être le premier à placer tous ses jetons en créant des correspondances.\r\n"
	        		+ "\r\n"
	        		+ "1. Préparation\r\n"
	        		+ "Mélanger tous les jetons et les distribuer équitablement.\r\n"
	        		+ "\r\n"
	        		+ "Chaque joueur pioche 5 jetons pour former sa main initiale.\r\n"
	        		+ "\r\n"
	        		+ "Le plus jeune joueur commence en plaçant un jeton sur la case centrale.\r\n"
	        		+ "\r\n"
	        		+ "2. Actions Possibles pendant un Tour\r\n"
	        		+ "Placer une tuile :\r\n"
	        		+ "\r\n"
	        		+ "Le jeton doit être adjacent (haut/bas/gauche/droite) à un autre.\r\n"
	        		+ "\r\n"
	        		+ "Doit correspondre par couleur ou forme.\r\n"
	        		+ "\r\n"
	        		+ "Piocher une nouvelle main :\r\n"
	        		+ "\r\n"
	        		+ "Si aucun placement n'est possible, échangez des jetons (coût en points).\r\n"
	        		+ "\r\n"
	        		+ "Passer son tour :\r\n"
	        		+ "\r\n"
	        		+ "Si aucune action n'est réalisable.\r\n"
	        		+ "\r\n"
	        		+ "Afficher les règles :\r\n"
	        		+ "\r\n"
	        		+ "Voir ce résumé à tout moment.\r\n"
	        		+ "\r\n"
	        		+ "Quitter :\r\n"
	        		+ "\r\n"
	        		+ "Abandonner la partie.\r\n"
	        		+ "\r\n"
	        		+ "3. Calcul des Points\r\n"
	        		+ "Correspondances	Points Normaux	Case Solaire (+1 pt)\r\n"
	        		+ "2 côtés	0,5 pt	1,5 pt\r\n"
	        		+ "3 côtés	1 pt	2 pts\r\n"
	        		+ "4 côtés	2 pts	3 pts\r\n"
	        		+ "4. Fin de la Partie\r\n"
	        		+ "Gagnant : Premier joueur à placer tous ses jetons.\r\n"
	        		+ "\r\n"
	        		+ "Blocage : Si tous sont bloqués, le joueur avec le moins de jetons gagne.\r\n"
	        		+ "\r\n"
	        		+ "Points : Les points excédant 3 sont perdus en fin de tour.");
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

	    public static void caseSunStone() { //TODO Metre des arguments String pour éviter la dupliaction,
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


