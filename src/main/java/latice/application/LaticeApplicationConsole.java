package latice.application;

import java.util.Scanner;

import latice.model.ActionSpeciale;
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
    
    // ANSI color codes for player differentiation //TODO mettre les couleurs dans la classe TexteConsole
    private static final String RESET = "\u001B[0m";
    private static final String PLAYER1 = "\u001B[34m"; // Blue for player 1
    private static final String PLAYER2 = "\u001B[35m"; // Purple for player 2
    private static final String HIGHLIGHT = "\u001B[1;33m"; // Bold yellow for highlights
    
    public static void main(String[] args) {
        // Créer une pioche pour 2 joueurs
        Pioche pioche = new Pioche(2);
        Arbitre arbitre = new Arbitre(2);
        boolean premierCoup = true;
        boolean afficherIndices = false;

        Rack rackJoueur1 = new Rack(pioche);
        rackJoueur1.remplir(pioche, 0);
        Rack rackJoueur2 = new Rack(pioche);
        rackJoueur2.remplir(pioche, 1);
        
        Plateau plateau = new Plateau();
        
        boolean quitter = false;
        boolean tourSupplementaireActif = false;
        Scanner scanner = new Scanner(System.in);
        
        // Clear screen and show title
        clearScreen();
        System.out.println(HIGHLIGHT + "==================================" + RESET);
        System.out.println(HIGHLIGHT + "          LATICE GAME            " + RESET);
        System.out.println(HIGHLIGHT + "==================================" + RESET);
        System.out.println();
        
        // Demander si les joueurs veulent voir les indices des lignes et colonnes
        System.out.println("Voulez-vous afficher les indices des lignes et colonnes? (O/N)");
        String reponse = scanner.nextLine().trim().toUpperCase();
        afficherIndices = reponse.equals("O") || reponse.equals("OUI");
        
        System.out.println(PLAYER1 + "Joueur 1 :" + RESET);
        String nom1 = SaisieConsole.saisieChar();
        System.out.println();
        System.out.println(PLAYER2 + "Joueur 2 :" + RESET);
        String nom2 = SaisieConsole.saisieChar();
        System.out.println();
        
        // Détermine aléatoirement l'ordre des joueurs
        String[] ordreJoueurs = GameSetup.ordreJoueur(nom1, nom2);
        Joueur joueur1 = new Joueur(ordreJoueurs[0], rackJoueur1, pioche); 
        Joueur joueur2 = new Joueur(ordreJoueurs[1], rackJoueur2, pioche);
        
        String colorJ1 = (joueur1.getName().equals(nom1)) ? PLAYER1 : PLAYER2;
        String colorJ2 = (joueur2.getName().equals(nom1)) ? PLAYER1 : PLAYER2;
        
        System.out.println(HIGHLIGHT + "Premier joueur : " + colorJ1 + joueur1.getName() + RESET);
        System.out.println(HIGHLIGHT + "Second joueur  : " + colorJ2 + joueur2.getName() + RESET);
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
            System.out.println(HIGHLIGHT + "==================================" + RESET);
            String currentPlayerColor = (joueurCourant == joueur1) ? colorJ1 : colorJ2;
            System.out.println(HIGHLIGHT + "TOUR DE " + currentPlayerColor + joueurCourant.getName() + RESET);
            System.out.println(HIGHLIGHT + "==================================" + RESET);
            System.out.println();
            
            // Afficher les scores
            System.out.println(colorJ1 + joueur1.getName() + " : " + arbitre.getScore(0) + " points" + RESET);
            System.out.println(colorJ2 + joueur2.getName() + " : " + arbitre.getScore(1) + " points" + RESET);
            System.out.println();
            
            // Afficher le plateau
            System.out.println(HIGHLIGHT + "PLATEAU DE JEU:" + RESET);
            PlateauView plateauView = new PlateauViewConsole();
            
            PlateauViewConsole plateauViewConsole = (PlateauViewConsole) plateauView;
            plateauViewConsole.afficherPlateau(plateau, afficherIndices);
            
            System.out.println();
            
            // Afficher le rack du joueur courant
            if (joueurCourant.equals(joueur1)) {
                System.out.println(rackJoueur1.afficherRack(currentPlayerColor + "C'est au tour de " + joueur1.getName() + ", voici votre rack :" + RESET));
            } else {
                System.out.println(rackJoueur2.afficherRack(currentPlayerColor + "C'est au tour de " + joueur2.getName() + ", voici votre rack :" + RESET));
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
                        System.out.println(HIGHLIGHT + "Premier coup: vous devez jouer au centre (position 5,5)" + RESET);
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
                            System.out.println(currentPlayerColor + "Tuile placée avec succès!" + RESET);
                            
                         // Points gagnés
                            if (resultat > 0) {
                                System.out.println(HIGHLIGHT + "Correspondances: " + resultat + RESET);
                                float pointsGagnes = 0;
                                
                                if (resultat == 2) {
                                    pointsGagnes = 0.5f;
                                    System.out.println(HIGHLIGHT + "Double! +0.5 point" + RESET);
                                } else if (resultat == 3) {
                                    pointsGagnes = 1;
                                    System.out.println(HIGHLIGHT + "Trefoil! +1 point" + RESET);
                                } else if (resultat == 4) {
                                    pointsGagnes = 2;
                                    System.out.println(HIGHLIGHT + "Latice! +2 points" + RESET);
                                }
                                
                                // Vérifier les cases spéciales
                                if (plateau.caseIsSunStones(pos)) {
                                    TexteConsole.caseSunStone();
                                    System.out.println(HIGHLIGHT + "Bonus soleil: +1 point" + RESET);
                                    pointsGagnes += 1;
                                } else {
                                    TexteConsole.notCaseSunStone();
                                }
                                
                                if (plateau.caseIsMoon(pos)) {
                                    TexteConsole.caseMoonStone();
                                } else {
                                    TexteConsole.notCaseMoonStone();
                                }
                                
                                System.out.println(HIGHLIGHT + "Total des points gagnés: " + pointsGagnes + RESET);
                                
                                // Convert to integer for adding to the score
                                int pointsToAdd = Math.round(pointsGagnes);
                                arbitre.ajouterPoints(joueurIndex, pointsToAdd);
                                
                                // Handle rule: if more than 3 points in one turn, discard extra points
                                if (pointsToAdd > 3) {
                                    System.out.println(HIGHLIGHT + "Maximum de 3 points par tour! Points en excès perdus." + RESET);
                                    arbitre.ajouterPoints(joueurIndex, 3 - pointsToAdd); // Adjust to max 3 points
                                }
                            }

                            
                            premierCoup = false;
                            
                            // Pause for readability
                            waitForEnter();
                            
                            // Changer de joueur
                            if (tourSupplementaireActif) {
                                tourSupplementaireActif = false;
                                System.out.println(HIGHLIGHT + "Vous jouez votre tour supplémentaire." + RESET);
                            } else {
                                joueurCourant = (joueurCourant == joueur1) ? joueur2 : joueur1;
                            }

                        } else {
                            System.out.println();
                            if (premierCoup) {
                                System.out.println(HIGHLIGHT + "Le premier coup doit être joué au centre (position 5,5). Veuillez réessayer." + RESET);
                            } else {
                                System.out.println(HIGHLIGHT + "Placement impossible selon les règles du jeu. Veuillez réessayer." + RESET);
                                System.out.println(HIGHLIGHT + "Rappel: La tuile doit correspondre à au moins une tuile adjacente (même couleur ou même symbole)." + RESET);
                            }
                            waitForEnter();
                        }
                    } else {
                        System.out.println();
                        System.out.println(HIGHLIGHT + "Tuile invalide. Veuillez réessayer." + RESET);
                        waitForEnter();
                    }
                    break;
                    
                case 2: // Piocher une nouvelle main
                    System.out.println();
                    joueurCourant.getRack().vider();
                    
                    int idxJoueur = (joueurCourant == joueur1) ? 0 : 1;
                    joueurCourant.getRack().remplir(pioche, idxJoueur);
                    
                    System.out.println(currentPlayerColor + "Vous avez pioché une nouvelle main." + RESET);
                    waitForEnter();
                    
                    // Changer de joueur
                    joueurCourant = (joueurCourant == joueur1) ? joueur2 : joueur1;
                    break;
                    
                case 3: // Passer son tour
                    System.out.println();
                    System.out.println(currentPlayerColor + joueurCourant.getName() + " passe son tour." + RESET);
                    waitForEnter();
                    
                    // Changer de joueur
                    joueurCourant = (joueurCourant == joueur1) ? joueur2 : joueur1;
                    break;
                    
                case 4: // Règles du jeu
                    System.out.println();
                    TexteConsole.afficherRegles();
                    waitForEnter();
                    break;
                    
                
                case 5:
                	System.out.println();
                    // Appeler la méthode existante du joueur pour acheter un tour supplémentaire
                    boolean achatReussi = joueurCourant.jouerActionSpeciale(
                        (joueurCourant == joueur1) ? 0 : 1,  // index du joueur actuel
                        ActionSpeciale.ACTION_SUPPLEMENTAIRE,
                        joueurCourant.getRack(),
                        joueurCourant.getPioche(),
                        arbitre
                    );
                    
                    if (achatReussi) {
                        System.out.println(currentPlayerColor + "Tour supplémentaire acheté avec succès (-2 points)." + RESET);
                        tourSupplementaireActif = true;
                    } else {
                        System.out.println(HIGHLIGHT + "Vous n'avez pas assez de points pour acheter un tour supplémentaire." + RESET);
                    }
                    waitForEnter();
                    break;
                	
                
                case 6: // Quitter la partie
                    System.out.println();
                    System.out.println(HIGHLIGHT + "Merci d'avoir joué !" + RESET);
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
                
                System.out.println(HIGHLIGHT + "==================================" + RESET);
                System.out.println(HIGHLIGHT + "         FIN DE PARTIE           " + RESET);
                System.out.println(HIGHLIGHT + "==================================" + RESET);
                System.out.println();
                System.out.println("Scores finaux:");
                System.out.println(colorJ1 + joueur1.getName() + ": " + arbitre.getScore(0) + " points" + RESET);
                System.out.println(colorJ2 + joueur2.getName() + ": " + arbitre.getScore(1) + " points" + RESET);
                System.out.println();
                System.out.println(HIGHLIGHT + "Le gagnant est: " + gagnantColor + gagnantNom + RESET);
                
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
        System.out.println(HIGHLIGHT + "Appuyez sur Entrée pour continuer..." + RESET);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}







