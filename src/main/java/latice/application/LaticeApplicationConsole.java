package latice.application;

import java.util.Scanner;
import latice.model.Arbitre;
import latice.model.Joueur;
import latice.model.Pioche;
import latice.model.Plateau;
import latice.model.PositionTuiles;
import latice.model.Rack;
import latice.model.Tuile;
import latice.model.setup.GameSetup;
import latice.ihm.*;

public class LaticeApplicationConsole {
    
    // ANSI color codes for player differentiation
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_PLAYER1 = "\u001B[34m"; // Blue for player 1
    private static final String ANSI_PLAYER2 = "\u001B[35m"; // Purple for player 2
    private static final String ANSI_HIGHLIGHT = "\u001B[1;33m"; // Bold yellow for highlights
    
    public static void main(String[] args) {
        // Créer une pioche pour 2 joueurs
        Pioche pioche = new Pioche(2);
        Arbitre arbitre = new Arbitre(2);
        boolean premierCoup = true;

        Rack rackJoueur1 = new Rack(pioche);
        rackJoueur1.remplir(pioche, 0);
        Rack rackJoueur2 = new Rack(pioche);
        rackJoueur2.remplir(pioche, 1);
        
        Plateau plateau = new Plateau();
        
        boolean quitter = false;
        Scanner scanner = new Scanner(System.in);
        
        // Clear screen and show title
        clearScreen();
        System.out.println(ANSI_HIGHLIGHT + "==================================" + ANSI_RESET);
        System.out.println(ANSI_HIGHLIGHT + "          LATICE GAME            " + ANSI_RESET);
        System.out.println(ANSI_HIGHLIGHT + "==================================" + ANSI_RESET);
        System.out.println();
        
        System.out.println(ANSI_PLAYER1 + "Joueur 1 :" + ANSI_RESET);
        String nom1 = SaisieConsole.saisieChar();
        System.out.println();
        System.out.println(ANSI_PLAYER2 + "Joueur 2 :" + ANSI_RESET);
        String nom2 = SaisieConsole.saisieChar();
        System.out.println();
        
        // Détermine aléatoirement l'ordre des joueurs
        String[] ordreJoueurs = GameSetup.ordreJoueur(nom1, nom2);
        Joueur joueur1 = new Joueur(ordreJoueurs[0], rackJoueur1, pioche); 
        Joueur joueur2 = new Joueur(ordreJoueurs[1], rackJoueur2, pioche);
        
        String colorJ1 = (joueur1.getName().equals(nom1)) ? ANSI_PLAYER1 : ANSI_PLAYER2;
        String colorJ2 = (joueur2.getName().equals(nom1)) ? ANSI_PLAYER1 : ANSI_PLAYER2;
        
        System.out.println(ANSI_HIGHLIGHT + "Premier joueur : " + colorJ1 + joueur1.getName() + ANSI_RESET);
        System.out.println(ANSI_HIGHLIGHT + "Second joueur  : " + colorJ2 + joueur2.getName() + ANSI_RESET);
        System.out.println();
        
        TexteConsole.afficherBienvenue();
        System.out.println();
        TexteConsole.afficherMenu();
        System.out.println();
        
        // Initialiser le joueur courant
        Joueur joueurCourant = joueur1;
        
        while (!quitter) {
            // Clear screen at the start of each turn
            clearScreen();
            
            // Display game state info
            System.out.println(ANSI_HIGHLIGHT + "==================================" + ANSI_RESET);
            String currentPlayerColor = (joueurCourant == joueur1) ? colorJ1 : colorJ2;
            System.out.println(ANSI_HIGHLIGHT + "TOUR DE " + currentPlayerColor + joueurCourant.getName() + ANSI_RESET);
            System.out.println(ANSI_HIGHLIGHT + "==================================" + ANSI_RESET);
            System.out.println();
            
            // Afficher les scores
            System.out.println(colorJ1 + joueur1.getName() + " : " + arbitre.getScore(0) + " points" + ANSI_RESET);
            System.out.println(colorJ2 + joueur2.getName() + " : " + arbitre.getScore(1) + " points" + ANSI_RESET);
            System.out.println();
            
            // Afficher le plateau
            System.out.println(ANSI_HIGHLIGHT + "PLATEAU DE JEU:" + ANSI_RESET);
            PlateauViewConsole pvc = new PlateauViewConsole();
            pvc.afficherPlateau(plateau);
            System.out.println();
            
            // Afficher le rack du joueur courant
            if (joueurCourant.equals(joueur1)) {
                System.out.println(rackJoueur1.afficherRack(currentPlayerColor + "C'est au tour de " + joueur1.getName() + ", voici votre rack :" + ANSI_RESET));
            } else {
                System.out.println(rackJoueur2.afficherRack(currentPlayerColor + "C'est au tour de " + joueur2.getName() + ", voici votre rack :" + ANSI_RESET));
            }
            System.out.println();
            
            // Afficher les options
            TexteConsole.afficherMenu();
            System.out.println();
            
            int choix = SaisieConsole.saisieChoix() + 1;
            
            switch (choix) {
                case 1: // Placer une tuile
                    System.out.println();
                    int choixTuile = SaisieConsole.saisieTuiles();
                    
                    if (premierCoup) {
                        System.out.println(ANSI_HIGHLIGHT + "Premier coup: vous devez jouer au centre (position 5,5)" + ANSI_RESET);
                        System.out.println();
                    }
                    
                    int ligne = SaisieConsole.saisieLigneColonne("ligne");
                    int colonne = SaisieConsole.saisieLigneColonne("colonne");
                    
                    Rack rackCourant = joueurCourant.getRack();
                    int joueurIndex = (joueurCourant == joueur1) ? 0 : 1;
                    
                    // Vérifier si l'indice est valide
                    if (choixTuile >= 0 && choixTuile < rackCourant.getTuiles().size()) {
                        Tuile tuileSelectionnee = rackCourant.getTuiles().get(choixTuile);
                        
                        // Utiliser l'arbitre pour vérifier le coup
                        int resultat = arbitre.verifierCoup(ligne, colonne, tuileSelectionnee, plateau.getCases(), premierCoup, joueurIndex);
                        
                        if (resultat >= 0) {
                            // Placer la tuile sur le plateau
                            PositionTuiles pos = new PositionTuiles(ligne, colonne);
                            plateau.placerTuile(tuileSelectionnee, pos);
                            rackCourant.retirerTuile(choixTuile);
                            rackCourant.remplir(pioche, joueurIndex);
                            
                            // Feedback sur le coup joué
                            System.out.println();
                            System.out.println(currentPlayerColor + "Tuile placée avec succès!" + ANSI_RESET);
                            
                            // Points gagnés
                            if (resultat > 0) {
                                System.out.println(ANSI_HIGHLIGHT + "Points gagnés: " + resultat + ANSI_RESET);
                            }
                            
                            // Vérifier les cases spéciales
                            if (plateau.caseIsSunStones(pos)) {
                                TexteConsole.caseSunStone();
                            } else {
                                TexteConsole.notCaseSunStone();
                            }
                            
                            if (plateau.caseIsMoon(pos)) {
                                TexteConsole.caseMoonStone();
                            } else {
                                TexteConsole.notCaseMoonStone();
                            }
                            
                            premierCoup = false;
                            
                            // Pause for readability
                            waitForEnter();
                            
                            // Changer de joueur
                            joueurCourant = (joueurCourant == joueur1) ? joueur2 : joueur1;
                        } else {
                            System.out.println();
                            if (premierCoup) {
                                System.out.println(ANSI_HIGHLIGHT + "Le premier coup doit être joué au centre (position 5,5). Veuillez réessayer." + ANSI_RESET);
                            } else {
                                System.out.println(ANSI_HIGHLIGHT + "Placement impossible selon les règles du jeu. Veuillez réessayer." + ANSI_RESET);
                                System.out.println(ANSI_HIGHLIGHT + "Rappel: La tuile doit correspondre à au moins une tuile adjacente (même couleur ou même symbole)." + ANSI_RESET);
                            }
                            waitForEnter();
                        }
                    } else {
                        System.out.println();
                        System.out.println(ANSI_HIGHLIGHT + "Tuile invalide. Veuillez réessayer." + ANSI_RESET);
                        waitForEnter();
                    }
                    break;
                    
                case 2: // Piocher une nouvelle main
                    System.out.println();
                    joueurCourant.getRack().vider();
                    
                    int idxJoueur = (joueurCourant == joueur1) ? 0 : 1;
                    joueurCourant.getRack().remplir(pioche, idxJoueur);
                    
                    System.out.println(currentPlayerColor + "Vous avez pioché une nouvelle main." + ANSI_RESET);
                    waitForEnter();
                    
                    // Changer de joueur
                    joueurCourant = (joueurCourant == joueur1) ? joueur2 : joueur1;
                    break;
                    
                case 3: // Passer son tour
                    System.out.println();
                    System.out.println(currentPlayerColor + joueurCourant.getName() + " passe son tour." + ANSI_RESET);
                    waitForEnter();
                    
                    // Changer de joueur
                    joueurCourant = (joueurCourant == joueur1) ? joueur2 : joueur1;
                    break;
                    
                case 4: // Règles du jeu
                    System.out.println();
                    TexteConsole.afficherRegles();
                    waitForEnter();
                    break;
                    
                case 5: // Quitter la partie
                    System.out.println();
                    System.out.println(ANSI_HIGHLIGHT + "Merci d'avoir joué !" + ANSI_RESET);
                    quitter = true;
                    break;
                    
                default:
                    TexteConsole.afficherErreurSaisie();
                    waitForEnter();
                    break;
            }
            
            // Vérifier la fin de partie
            if (arbitre.finDePartie(new Rack[]{rackJoueur1, rackJoueur2}, pioche)) {
                clearScreen();
                int gagnant = arbitre.getGagnant(new Rack[]{rackJoueur1, rackJoueur2});
                String gagnantNom = (gagnant == 0) ? joueur1.getName() : joueur2.getName();
                String gagnantColor = (gagnant == 0) ? colorJ1 : colorJ2;
                
                System.out.println(ANSI_HIGHLIGHT + "==================================" + ANSI_RESET);
                System.out.println(ANSI_HIGHLIGHT + "         FIN DE PARTIE           " + ANSI_RESET);
                System.out.println(ANSI_HIGHLIGHT + "==================================" + ANSI_RESET);
                System.out.println();
                System.out.println("Scores finaux:");
                System.out.println(colorJ1 + joueur1.getName() + ": " + arbitre.getScore(0) + " points" + ANSI_RESET);
                System.out.println(colorJ2 + joueur2.getName() + ": " + arbitre.getScore(1) + " points" + ANSI_RESET);
                System.out.println();
                System.out.println(ANSI_HIGHLIGHT + "Le gagnant est: " + gagnantColor + gagnantNom + ANSI_RESET);
                
                quitter = true;
            }
        }
        
        scanner.close();
    }
    
    // Helper method to clear the screen
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    // Helper method to wait for user to press Enter
    private static void waitForEnter() {
        System.out.println();
        System.out.println(ANSI_HIGHLIGHT + "Appuyez sur Entrée pour continuer..." + ANSI_RESET);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}






