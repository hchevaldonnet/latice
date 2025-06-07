package latice.ihm;

import java.util.Scanner;
import latice.model.Arbitre;
import latice.model.Joueur;

public class TexteConsole {
	
	// TODO: Mettre les attributs en private 
	
	public static final String REINITIALISATION = "\u001B[0m";
    public static final String JOUEUR1 = "\u001B[34m"; 
    public static final String JOUEUR2 = "\u001B[35m"; 
    public static final String SURBRILLANCE = "\u001B[1;33m"; 
    
    
    public static final String NOIR = "\u001B[30m";
    public static final String ROUGE = "\u001B[31m";
    public static final String VERT = "\u001B[32m";
    public static final String JAUNE = "\u001B[33m";
    public static final String BLANC = "\u001B[37m";  
    public static final String CYAN = "\u001B[36m";

    // Styles
    public static final String GRAS = "\u001B[1m";    
    
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

		    // Styles
		    final String BOLD = "\u001B[1m";    // Texte en gras 
		    System.out.println(CYAN + BOLD + "╔═══════════════════════════════════════════════════════════════╗" + REINITIALISATION);
	        System.out.println(CYAN + BOLD + "║                     RÈGLES DU JEU LATICE                      ║" + REINITIALISATION);
	        System.out.println(CYAN + BOLD + "╚═══════════════════════════════════════════════════════════════╝" + REINITIALISATION);
	        System.out.println("\n"); // Extra newline for spacing

	        System.out.println(VERT + BOLD + "OBJECTIF :" + REINITIALISATION);
	        System.out.println(BLANC + "  Soyez le premier à placer tous vos jetons en créant des correspondances." + REINITIALISATION);
	        System.out.println("\n");

	        System.out.println(JAUNE + BOLD + "1. PRÉPARATION" + REINITIALISATION);
	        System.out.println(BLANC + "  - Mélangez tous les jetons et distribuez-les équitablement." + REINITIALISATION);
	        System.out.println(BLANC + "  - Chaque joueur pioche 5 jetons pour former sa main initiale." + REINITIALISATION);
	        System.out.println(BLANC + "  - Le plus jeune joueur commence en plaçant un jeton sur la case centrale." + REINITIALISATION);
	        System.out.println("\n");

	        System.out.println(JAUNE + BOLD + "2. ACTIONS POSSIBLES PENDANT UN TOUR" + REINITIALISATION);
	        System.out.println(BLANC + "  A. Placer une tuile :" + REINITIALISATION);
	        System.out.println(BLANC + "     - Le jeton doit être adjacent (haut/bas/gauche/droite) à un autre jeton déjà placé." + REINITIALISATION);
	        System.out.println(BLANC + "     - Le jeton doit correspondre par couleur OU par forme avec les jetons adjacents." + REINITIALISATION);
	        System.out.println("\n");

	        System.out.println(BLANC + "  B. Piocher une nouvelle main :" + REINITIALISATION);
	        System.out.println(BLANC + "     - Si aucun placement n'est possible, échangez des jetons (coût en points)." + REINITIALISATION);
	        System.out.println("\n");

	        System.out.println(BLANC + "  C. Passer son tour :" + REINITIALISATION);
	        System.out.println(BLANC + "     - Si aucune action n'est réalisable." + REINITIALISATION);
	        System.out.println("\n");

	        System.out.println(BLANC + "  D. Afficher les règles :" + REINITIALISATION);
	        System.out.println(BLANC + "     - Voir ce résumé à tout moment." + REINITIALISATION);
	        System.out.println("\n");

	        System.out.println(BLANC + "  E. Quitter :" + REINITIALISATION);
	        System.out.println(BLANC + "     - Abandonner la partie en cours." + REINITIALISATION);
	        System.out.println("\n");

	        System.out.println(JAUNE + BOLD + "3. CALCUL DES POINTS" + REINITIALISATION);
	        System.out.println(CYAN + SEPARATEURDECALE + REINITIALISATION);
	        System.out.println(JAUNE + BOLD + "  Correspondances " + ROUGE + BOLD + "    Points Normaux " + VERT + BOLD + "    Case Solaire (+1 pt)" + REINITIALISATION);
	        System.out.println(CYAN + SEPARATEURDECALE + REINITIALISATION);
	        System.out.println(BLANC + "  2 côtés              " + ROUGE + "0,5 pt             " + VERT + "1,5 pt" + REINITIALISATION);
	        System.out.println(BLANC + "  3 côtés              " + ROUGE + "1 pt               " + VERT + "2 pts" + REINITIALISATION);
	        System.out.println(BLANC + "  4 côtés              " + ROUGE + "2 pts              " + VERT + "3 pts" + REINITIALISATION);
	        System.out.println(CYAN + SEPARATEURDECALE + REINITIALISATION);
	        System.out.println("\n");

	        System.out.println(JAUNE + BOLD + "4. FIN DE LA PARTIE" + REINITIALISATION);
	        System.out.println(BLANC + "  - Le Gagnant : Le premier joueur à placer tous ses jetons." + REINITIALISATION);
	        System.out.println(BLANC + "  - En cas de Blocage : Si tous les joueurs sont bloqués, le joueur avec le moins de jetons restants gagne." + REINITIALISATION);
	        System.out.println(BLANC + "  - Points Excédents : Les points excédant 3 sont perdus en fin de tour." + REINITIALISATION);
	        System.out.println("\n");

	        System.out.println(VERT + SEPARATEURFIN + REINITIALISATION);
	        System.out.println(VERT + "           Amusez-vous bien et bonne chance !" + REINITIALISATION);
	        System.out.println(VERT + SEPARATEURFIN + REINITIALISATION);
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
	            System.out.println(SURBRILLANCE + "C'est une case soleil !" + REINITIALISATION);
	            System.out.println(TexteConsole.SURBRILLANCE + "Bonus soleil: +" + pointsBonusSoleil + " point" + TexteConsole.REINITIALISATION);
	        } else {
	            System.out.println(SURBRILLANCE + "Ce n'est pas une case soleil !" + REINITIALISATION);
	        }
	    }

	    
	    public static void tourJoueur(Joueur joueur) {
	    	System.out.println("C'est à " + joueur.getName());
	    }
	    
	    public static void demanderNom() {
	    	System.out.println("Veuillez entrer votre nom");
	    }
	    
	    public static void mettreEnSurbrillance(String text) {
	    	System.out.println(SURBRILLANCE + text + REINITIALISATION);
	    }
	    
	    public static void formatJoueur(String couleur,String text) {
	    	System.out.println(couleur + text + REINITIALISATION);
	    }
	    
	    
	    public static void effacerEcran() {
	        System.out.print("\033[H\033[2J");
	        System.out.flush();
	    }
	    
	 
	    public static void appuyerEnter() {
	        System.out.println();
	        System.out.println(SURBRILLANCE + "Appuyez sur Entrée pour continuer..." + REINITIALISATION);
	        Scanner scanner = new Scanner(System.in);
	        scanner.nextLine();
	    }
	    
	    public static void afficherTitre() {
			System.out.println(SURBRILLANCE + SEPARATEURFORT + REINITIALISATION);
	        System.out.println(SURBRILLANCE + "          LATICE GAME            " + REINITIALISATION);
	        System.out.println(SURBRILLANCE + SEPARATEURFORT + REINITIALISATION);
	        System.out.println();
		}
	    
	    public static void tuileInvalide() {
			System.out.println();
			System.out.println(SURBRILLANCE + "Tuile invalide. Veuillez réessayer." + REINITIALISATION);
			appuyerEnter();
		}
	    
	    public static void finPartie(Arbitre arbitre, Joueur joueur1, Joueur joueur2, String colorJ1, String colorJ2,
				String gagnantNom, String gagnantColor) {
			System.out.println(SURBRILLANCE + SEPARATEURFORT + REINITIALISATION);
			System.out.println(SURBRILLANCE + "         FIN DE PARTIE           " + REINITIALISATION);
			System.out.println(SURBRILLANCE + SEPARATEURFORT + REINITIALISATION);
			System.out.println();
			System.out.println("Scores finaux:");
			System.out.println(colorJ1 + joueur1.getName() + ": " + arbitre.getScore(0) + " points" + REINITIALISATION);
			System.out.println(colorJ2 + joueur2.getName() + ": " + arbitre.getScore(1) + " points" + REINITIALISATION);
			System.out.println();
			System.out.println(SURBRILLANCE + "Le gagnant est: " + gagnantColor + gagnantNom + REINITIALISATION);
		}
	    
	    public static void remerciement() {
			System.out.println();
			System.out.println(SURBRILLANCE + "Merci d'avoir joué !" + REINITIALISATION);
		}
	    
	    public static void passerTour(Joueur joueurCourant, String currentPlayerColor) {
			System.out.println();
			System.out.println(currentPlayerColor + joueurCourant.getName() + " passe son tour." + REINITIALISATION);
		}
	    
	    public static void placementImpossible() {
			System.out.println(SURBRILLANCE + "Placement impossible selon les règles du jeu. Veuillez réessayer." + REINITIALISATION);
			System.out.println(SURBRILLANCE + "Rappel: La tuile doit correspondre à au moins une tuile adjacente (même couleur ou même symbole)." + REINITIALISATION);
		}
	    
	    public static void sautDeLigne() {
	        System.out.println();
	    }
	    
	    public static void affichageJoueurs(Joueur joueur1, Joueur joueur2, String colorJ1, String colorJ2) {
			System.out.println(SURBRILLANCE + "Premier joueur : " + colorJ1 + joueur1.getName() + REINITIALISATION);
	        System.out.println(SURBRILLANCE + "Second joueur  : " + colorJ2 + joueur2.getName() + REINITIALISATION);
	        sautDeLigne();
		}
	    
	    public static void affichageEtatJeu(Joueur joueurCourant, String currentPlayerColor) {
			System.out.println(SURBRILLANCE + SEPARATEURFORT + REINITIALISATION);
			System.out.println(SURBRILLANCE + "TOUR DE " + currentPlayerColor + joueurCourant.getName() + TexteConsole.REINITIALISATION);
			System.out.println(SURBRILLANCE + SEPARATEURFORT + REINITIALISATION);
			sautDeLigne();
		}
	    
	    public static void affichageScore(Arbitre arbitre, Joueur joueur1, Joueur joueur2, String colorJ1, String colorJ2) {
			System.out.println(colorJ1 + joueur1.getName() + " : " + arbitre.getScore(0) + " points" + TexteConsole.REINITIALISATION);
			System.out.println(colorJ2 + joueur2.getName() + " : " + arbitre.getScore(1) + " points" + TexteConsole.REINITIALISATION);
			sautDeLigne();
		}
	    
	    public static void affichageTour(int totalTours, final int MAX_TOURS) {
			System.out.println(SURBRILLANCE + "TOUR " + totalTours + "/" + MAX_TOURS + REINITIALISATION);
		}
	    	    
}


