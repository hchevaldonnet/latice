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
		    System.out.println("5. Acheter un tour supplémentaire (-2 points)");
		    System.out.println("6. Quitter\n");
		}

	 public static void afficherRegles() {
		 	// Codes ANSI pour les couleurs et le style
		    final String RESET = "\u001B[0m";   //TODO ajouter les couleurs qui sont dans le main ici et les regrouper dans une énumération
		    final String BLACK = "\u001B[30m";
		    final String RED = "\u001B[31m";
		    final String GREEN = "\u001B[32m";
		    final String YELLOW = "\u001B[33m";
		    final String WHITE = "\u001B[37m";  
		    final String CYAN = "\u001B[36m";

		    // Styles
		    final String BOLD = "\u001B[1m";    // Texte en gras 
		    System.out.println(CYAN + BOLD + "╔═══════════════════════════════════════════════════════════════╗" + RESET);
	        System.out.println(CYAN + BOLD + "║                     RÈGLES DU JEU LATICE                      ║" + RESET);
	        System.out.println(CYAN + BOLD + "╚═══════════════════════════════════════════════════════════════╝" + RESET);
	        System.out.println("\n"); // Extra newline for spacing

	        System.out.println(GREEN + BOLD + "OBJECTIF :" + RESET);
	        System.out.println(WHITE + "  Soyez le premier à placer tous vos jetons en créant des correspondances." + RESET);
	        System.out.println("\n");

	        System.out.println(YELLOW + BOLD + "1. PRÉPARATION" + RESET);
	        System.out.println(WHITE + "  - Mélangez tous les jetons et distribuez-les équitablement." + RESET);
	        System.out.println(WHITE + "  - Chaque joueur pioche 5 jetons pour former sa main initiale." + RESET);
	        System.out.println(WHITE + "  - Le plus jeune joueur commence en plaçant un jeton sur la case centrale." + RESET);
	        System.out.println("\n");

	        System.out.println(YELLOW + BOLD + "2. ACTIONS POSSIBLES PENDANT UN TOUR" + RESET);
	        System.out.println(WHITE + "  A. Placer une tuile :" + RESET);
	        System.out.println(WHITE + "     - Le jeton doit être adjacent (haut/bas/gauche/droite) à un autre jeton déjà placé." + RESET);
	        System.out.println(WHITE + "     - Le jeton doit correspondre par couleur OU par forme avec les jetons adjacents." + RESET);
	        System.out.println("\n");

	        System.out.println(WHITE + "  B. Piocher une nouvelle main :" + RESET);
	        System.out.println(WHITE + "     - Si aucun placement n'est possible, échangez des jetons (coût en points)." + RESET);
	        System.out.println("\n");

	        System.out.println(WHITE + "  C. Passer son tour :" + RESET);
	        System.out.println(WHITE + "     - Si aucune action n'est réalisable." + RESET);
	        System.out.println("\n");

	        System.out.println(WHITE + "  D. Afficher les règles :" + RESET);
	        System.out.println(WHITE + "     - Voir ce résumé à tout moment." + RESET);
	        System.out.println("\n");

	        System.out.println(WHITE + "  E. Quitter :" + RESET);
	        System.out.println(WHITE + "     - Abandonner la partie en cours." + RESET);
	        System.out.println("\n");

	        System.out.println(YELLOW + BOLD + "3. CALCUL DES POINTS" + RESET);
	        System.out.println(CYAN + "  ────────────────────────────────────────────────────────────" + RESET);
	        System.out.println(YELLOW + BOLD + "  Correspondances " + RED + BOLD + "    Points Normaux " + GREEN + BOLD + "    Case Solaire (+1 pt)" + RESET);
	        System.out.println(CYAN + "  ────────────────────────────────────────────────────────────" + RESET);
	        System.out.println(WHITE + "  2 côtés              " + RED + "0,5 pt             " + GREEN + "1,5 pt" + RESET);
	        System.out.println(WHITE + "  3 côtés              " + RED + "1 pt               " + GREEN + "2 pts" + RESET);
	        System.out.println(WHITE + "  4 côtés              " + RED + "2 pts              " + GREEN + "3 pts" + RESET);
	        System.out.println(CYAN + "  ────────────────────────────────────────────────────────────" + RESET);
	        System.out.println("\n");

	        System.out.println(YELLOW + BOLD + "4. FIN DE LA PARTIE" + RESET);
	        System.out.println(WHITE + "  - Le Gagnant : Le premier joueur à placer tous ses jetons." + RESET);
	        System.out.println(WHITE + "  - En cas de Blocage : Si tous les joueurs sont bloqués, le joueur avec le moins de jetons restants gagne." + RESET);
	        System.out.println(WHITE + "  - Points Excédents : Les points excédant 3 sont perdus en fin de tour." + RESET);
	        System.out.println("\n");

	        System.out.println(GREEN + "───────────────────────────────────────────────────────────────────" + RESET);
	        System.out.println(GREEN + "           Amusez-vous bien et bonne chance !" + RESET);
	        System.out.println(GREEN + "───────────────────────────────────────────────────────────────────" + RESET);
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


