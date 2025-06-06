package latice.ihm;

import java.util.Scanner;
import latice.model.Arbitre;
import latice.model.Joueur;

public class TexteConsole {
	
	// TODO: Mettre les attributs en private 
	
	public static final String RESET = "\u001B[0m";
    public static final String PLAYER1 = "\u001B[34m"; // Blue for player 1
    public static final String PLAYER2 = "\u001B[35m"; // Purple for player 2
    public static final String HIGHLIGHT = "\u001B[1;33m"; // Bold yellow for highlights
    
    
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String WHITE = "\u001B[37m";  // C'est un blanc clair, souvent utilisé comme "gris"
    public static final String CYAN = "\u001B[36m";

    // Styles
    public static final String BOLD = "\u001B[1m";    // Texte en gras
    
    // Séparateurs pour l'affichage
    public static final String SEPARATEURFORT = "==================================";
    public static final String SEPARATEURFIN = "───────────────────────────────────────────────────────────────────";
    public static final String SEPARATEURDECALE = "  ────────────────────────────────────────────────────────────";
	
	 public static void afficherBienvenue() {
	        System.out.println("Bienvenue dans le jeu de cartes Latice !");
	        sautDeLigne();
	    }

	 public static void afficherMenu() { 
		 	System.out.println("1. Placer une tuile");
		    System.out.println("2. Piocher une nouvelle main (-1 point)");
		    System.out.println("3. Passer son tour");
		    System.out.println("4. Afficher les règles");
		    System.out.println("5. Acheter un tour supplémentaire (-2 points)");
		    System.out.println("6. Quitter\n");
		}

	 public static void afficherRegles() {
		 	// Codes ANSI pour les couleurs et le style
		     //TODO ajouter les couleurs qui sont dans le main ici et les regrouper dans une énumération
		    

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
	        System.out.println(CYAN + SEPARATEURDECALE + RESET);
	        System.out.println(YELLOW + BOLD + "  Correspondances " + RED + BOLD + "    Points Normaux " + GREEN + BOLD + "    Case Solaire (+1 pt)" + RESET);
	        System.out.println(CYAN + SEPARATEURDECALE + RESET);
	        System.out.println(WHITE + "  2 côtés              " + RED + "0,5 pt             " + GREEN + "1,5 pt" + RESET);
	        System.out.println(WHITE + "  3 côtés              " + RED + "1 pt               " + GREEN + "2 pts" + RESET);
	        System.out.println(WHITE + "  4 côtés              " + RED + "2 pts              " + GREEN + "3 pts" + RESET);
	        System.out.println(CYAN + SEPARATEURDECALE + RESET);
	        System.out.println("\n");

	        System.out.println(YELLOW + BOLD + "4. FIN DE LA PARTIE" + RESET);
	        System.out.println(WHITE + "  - Le Gagnant : Le premier joueur à placer tous ses jetons." + RESET);
	        System.out.println(WHITE + "  - En cas de Blocage : Si tous les joueurs sont bloqués, le joueur avec le moins de jetons restants gagne." + RESET);
	        System.out.println(WHITE + "  - Points Excédents : Les points excédant 3 sont perdus en fin de tour." + RESET);
	        System.out.println("\n");

	        System.out.println(GREEN + SEPARATEURFIN + RESET);
	        System.out.println(GREEN + "           Amusez-vous bien et bonne chance !" + RESET);
	        System.out.println(GREEN + SEPARATEURFIN + RESET);
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

	    
	    public static void afficherSiCaseSoleil(boolean estCorrect, int pointsBonusSoleil) {
	        if (estCorrect) {
	            System.out.println(HIGHLIGHT + "C'est une case soleil !" + RESET);
	            System.out.println(TexteConsole.HIGHLIGHT + "Bonus soleil: +" + pointsBonusSoleil + " point" + TexteConsole.RESET);
	        } else {
	            System.out.println(HIGHLIGHT + "Ce n'est pas une case soleil !" + RESET);
	        }
	    }

	    
	    public static void tourJoueur(Joueur joueur) {
	    	System.out.println("C'est à " + joueur.getName());
	    }
	    
	    public static void demanderNom() {
	    	System.out.println("Veuillez entrer votre nom");
	    }
	    
	    public static void mettreEnSurbrillance(String text) {
	    	System.out.println(HIGHLIGHT + text + RESET);
	    }
	    
	    public static void formatJoueur(String couleur,String text) {
	    	System.out.println(couleur + text + RESET);
	    }
	    
	    
	    public static void effacerEcran() {
	        System.out.print("\033[H\033[2J");
	        System.out.flush();
	    }
	    
	 
	    public static void appuyerEnter() {
	        System.out.println();
	        System.out.println(HIGHLIGHT + "Appuyez sur Entrée pour continuer..." + RESET);
	        Scanner scanner = new Scanner(System.in);
	        scanner.nextLine();
	    }
	    
	    public static void afficherTitre() {
			System.out.println(HIGHLIGHT + SEPARATEURFORT + RESET);
	        System.out.println(HIGHLIGHT + "          LATICE GAME            " + RESET);
	        System.out.println(HIGHLIGHT + SEPARATEURFORT + RESET);
	        System.out.println();
		}
	    
	    public static void tuileInvalide() {
			System.out.println();
			System.out.println(HIGHLIGHT + "Tuile invalide. Veuillez réessayer." + RESET);
			appuyerEnter();
		}
	    
	    public static void finPartie(Arbitre arbitre, Joueur joueur1, Joueur joueur2, String colorJ1, String colorJ2,
				String gagnantNom, String gagnantColor) {
			System.out.println(HIGHLIGHT + SEPARATEURFORT + RESET);
			System.out.println(HIGHLIGHT + "         FIN DE PARTIE           " + RESET);
			System.out.println(HIGHLIGHT + SEPARATEURFORT + RESET);
			System.out.println();
			System.out.println("Scores finaux:");
			System.out.println(colorJ1 + joueur1.getName() + ": " + arbitre.getScore(0) + " points" + RESET);
			System.out.println(colorJ2 + joueur2.getName() + ": " + arbitre.getScore(1) + " points" + RESET);
			System.out.println();
			System.out.println(HIGHLIGHT + "Le gagnant est: " + gagnantColor + gagnantNom + RESET);
		}
	    
	    public static void remerciement() {
			System.out.println();
			System.out.println(HIGHLIGHT + "Merci d'avoir joué !" + RESET);
		}
	    
	    public static void passerTour(Joueur joueurCourant, String currentPlayerColor) {
			System.out.println();
			System.out.println(currentPlayerColor + joueurCourant.getName() + " passe son tour." + RESET);
		}
	    
	    public static void placementImpossible() {
			System.out.println(HIGHLIGHT + "Placement impossible selon les règles du jeu. Veuillez réessayer." + RESET);
			System.out.println(HIGHLIGHT + "Rappel: La tuile doit correspondre à au moins une tuile adjacente (même couleur ou même symbole)." + RESET);
		}
	    
	    public static void sautDeLigne() {
	        System.out.println();
	    }
	    
	    public static void affichageJoueurs(Joueur joueur1, Joueur joueur2, String colorJ1, String colorJ2) {
			System.out.println(HIGHLIGHT + "Premier joueur : " + colorJ1 + joueur1.getName() + RESET);
	        System.out.println(HIGHLIGHT + "Second joueur  : " + colorJ2 + joueur2.getName() + RESET);
	        sautDeLigne();
		}
	    
	    public static void affichageEtatJeu(Joueur joueurCourant, String currentPlayerColor) {
			System.out.println(HIGHLIGHT + SEPARATEURFORT + RESET);
			System.out.println(HIGHLIGHT + "TOUR DE " + currentPlayerColor + joueurCourant.getName() + TexteConsole.RESET);
			System.out.println(HIGHLIGHT + SEPARATEURFORT + RESET);
			sautDeLigne();
		}
	    
	    public static void affichageScore(Arbitre arbitre, Joueur joueur1, Joueur joueur2, String colorJ1, String colorJ2) {
			System.out.println(colorJ1 + joueur1.getName() + " : " + arbitre.getScore(0) + " points" + TexteConsole.RESET);
			System.out.println(colorJ2 + joueur2.getName() + " : " + arbitre.getScore(1) + " points" + TexteConsole.RESET);
			sautDeLigne();
		}
	    
	    public static void affichageTour(int totalTours, final int MAX_TOURS) {
			System.out.println(HIGHLIGHT + "TOUR " + totalTours + "/" + MAX_TOURS + RESET);
		}
	    	    
}


