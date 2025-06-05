package latice.application;

import java.util.List;
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
    

    public static void main(String[] args) {
        // Créer une pioche pour 2 joueurs
        Pioche pioche = new Pioche(2);
        Arbitre arbitre = new Arbitre(2);
        boolean premierCoup = true;
        boolean afficherIndices = false;
        int totalTours = 0;
        final int MAX_TOURS = 10;
        String reponse;

        Rack rackJoueur1 = new Rack(pioche);
        rackJoueur1.remplir(pioche, 0);
        Rack rackJoueur2 = new Rack(pioche);
        rackJoueur2.remplir(pioche, 1);
        
        Plateau plateau = new Plateau();
        
        boolean quitter = false;
        boolean tourSupplementaireActif = false;
        Scanner scanner = new Scanner(System.in);
        
        // Clear screen and show title
        TexteConsole.clearScreen();
        TexteConsole.afficherTitre();
        
        // Demander si les joueurs veulent voir les indices des lignes et colonnes
        do {
            System.out.println("Voulez-vous afficher les indices des lignes et colonnes? (O/N)");
            reponse = scanner.nextLine().trim().toUpperCase();
        } while (!reponse.equals("O") && !reponse.equals("N"));
        afficherIndices = reponse.equals("O");
        
        TexteConsole.formatPlayer(TexteConsole.PLAYER1, "Joueur 1 :");
        String nom1 = SaisieConsole.saisieChar(); 
        TexteConsole.sautDeLigne();
        TexteConsole.formatPlayer(TexteConsole.PLAYER2, "Joueur 2 :");
        String nom2 = SaisieConsole.saisieChar(); 
        
        // Vérifier que les noms des joueurs sont différents
        while (nom2.equals(nom1)) {
            System.out.println(TexteConsole.HIGHLIGHT + "Les noms des joueurs doivent être différents. Veuillez choisir un autre nom." + TexteConsole.RESET);
            TexteConsole.formatPlayer(TexteConsole.PLAYER2, "Joueur 2 :");
            nom2 = SaisieConsole.saisieChar();
        }
        System.out.println();
        
        // Détermine aléatoirement l'ordre des joueurs
        String[] ordreJoueurs = GameSetup.ordreJoueur(nom1, nom2);
        Joueur joueur1 = new Joueur(ordreJoueurs[0], rackJoueur1, pioche); 
        Joueur joueur2 = new Joueur(ordreJoueurs[1], rackJoueur2, pioche);
        
        // Determiner la couleur des joueurs par rapport à leur nom
        String colorJ1 = (joueur1.getName().equals(nom1)) ? TexteConsole.PLAYER1 : TexteConsole.PLAYER2;
        String colorJ2 = (joueur2.getName().equals(nom1)) ? TexteConsole.PLAYER1 : TexteConsole.PLAYER2;
        
        TexteConsole.affichageJoueurs(joueur1, joueur2, colorJ1, colorJ2);
        
        TexteConsole.afficherBienvenue();
        TexteConsole.afficherMenu();
        
        // Initialiser le joueur courant
        Joueur joueurCourant = joueur1;
        
        while (!quitter) {
            // Clear screen at the start of each turn
            TexteConsole.clearScreen();
            
            // Display game state info
            String currentPlayerColor = (joueurCourant == joueur1) ? colorJ1 : colorJ2;
            TexteConsole.affichageEtatJeu(joueurCourant, currentPlayerColor);
            
            // Afficher les scores
            TexteConsole.affichageScore(arbitre, joueur1, joueur2, colorJ1, colorJ2);
            
            // Afficher le plateau
            System.out.println(TexteConsole.HIGHLIGHT + "PLATEAU DE JEU:" + TexteConsole.RESET);
            PlateauView plateauView = new PlateauViewConsole();
            
            PlateauViewConsole plateauViewConsole = (PlateauViewConsole) plateauView;
            plateauViewConsole.afficherPlateau(plateau, afficherIndices);
            
            System.out.println();
            
            // Afficher le rack du joueur courant
            if (joueurCourant.equals(joueur1)) {
                System.out.println(rackJoueur1.afficherRack(currentPlayerColor + "C'est au tour de " + joueur1.getName() + ", voici votre rack :" + TexteConsole.RESET));
            } else {
                System.out.println(rackJoueur2.afficherRack(currentPlayerColor + "C'est au tour de " + joueur2.getName() + ", voici votre rack :" + TexteConsole.RESET));
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
                        System.out.println(TexteConsole.HIGHLIGHT + "Premier coup: vous devez jouer au centre (position 5,5)" + TexteConsole.RESET);
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
                            System.out.println(currentPlayerColor + "Tuile placée avec succès!" + TexteConsole.RESET);
                            
                         // Points gagnés
                            if (resultat > 0) {
                                System.out.println(TexteConsole.HIGHLIGHT + "Correspondances: " + resultat + TexteConsole.RESET);
                                float pointsGagnes = 0;
                                
                                if (resultat == 2) {
                                    pointsGagnes = 0.5f;
                                    System.out.println(TexteConsole.HIGHLIGHT + "Double! +0.5 point" + TexteConsole.RESET);
                                } else if (resultat == 3) {
                                    pointsGagnes = 1;
                                    System.out.println(TexteConsole.HIGHLIGHT + "Trefoil! +1 point" + TexteConsole.RESET);
                                } else if (resultat == 4) {
                                    pointsGagnes = 2;
                                    System.out.println(TexteConsole.HIGHLIGHT + "Latice! +2 points" + TexteConsole.RESET);
                                }
                                
                                // Vérifier les cases spéciales
                                if (plateau.caseIsSunStones(pos)) {
                                    TexteConsole.caseSunStone();
                                    System.out.println(TexteConsole.HIGHLIGHT + "Bonus soleil: +1 point" + TexteConsole.HIGHLIGHT);
                                    pointsGagnes += 1;
                                } else {
                                    TexteConsole.notCaseSunStone();
                                }
                                
                                if (plateau.caseIsMoon(pos)) {
                                    TexteConsole.caseMoonStone();
                                } else {
                                    TexteConsole.notCaseMoonStone();
                                }
                                
                                System.out.println(TexteConsole.HIGHLIGHT + "Total des points gagnés: " + pointsGagnes + TexteConsole.HIGHLIGHT);
                                
                                // Convert to integer for adding to the score
                                int pointsToAdd = Math.round(pointsGagnes);
                                arbitre.ajouterPoints(joueurIndex, pointsToAdd);
                                
                                // Handle rule: if more than 3 points in one turn, discard extra points
                                if (pointsToAdd > 3) {
                                    System.out.println(TexteConsole.HIGHLIGHT + "Maximum de 3 points par tour! Points en excès perdus." + TexteConsole.RESET);
                                    arbitre.ajouterPoints(joueurIndex, 3 - pointsToAdd); // Adjust to max 3 points
                                }
                            }

                            
                            premierCoup = false;
                            
                            // Pause for readability
                            TexteConsole.waitForEnter();
                            
                            // Changer de joueur
                            if (tourSupplementaireActif) {
                                tourSupplementaireActif = false;
                                System.out.println(TexteConsole.HIGHLIGHT + "Vous jouez votre tour supplémentaire." + TexteConsole.RESET);
                            } else {
                                joueurCourant = (joueurCourant == joueur1) ? joueur2 : joueur1;
                                if (joueurCourant == joueur1) {
                                    totalTours++;
                                }
                            }

                        } else {
                            System.out.println();
                            if (premierCoup) {
                                System.out.println(TexteConsole.HIGHLIGHT + "Le premier coup doit être joué au centre (position 5,5). Veuillez réessayer." + TexteConsole.RESET);
                            } else {
                                TexteConsole.placementImpossible();
                            }
                            TexteConsole.waitForEnter();
                        }
                    } else {
                        TexteConsole.tuileInvalide();
                    }
                    break;
                    
                case 2: // Piocher une nouvelle main
                    System.out.println();
                    joueurCourant.getRack().vider();
                    
                    int idxJoueur = (joueurCourant == joueur1) ? 0 : 1;
                    joueurCourant.getRack().remplir(pioche, idxJoueur);
                    
                    System.out.println(currentPlayerColor + "Vous avez pioché une nouvelle main." + TexteConsole.RESET);
                    TexteConsole.waitForEnter();
                    
                    // Changer de joueur
                    joueurCourant = (joueurCourant == joueur1) ? joueur2 : joueur1;
                    break;
                    
                case 3: // Passer son tour
                	TexteConsole.passerTour(joueurCourant, currentPlayerColor);
                    TexteConsole.waitForEnter();
                    
                    // Changer de joueur
                    joueurCourant = (joueurCourant == joueur1) ? joueur2 : joueur1;
                    break;
                    
                case 4: // Règles du jeu
                    System.out.println();
                    TexteConsole.afficherRegles();
                    TexteConsole.waitForEnter();
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
                        System.out.println(currentPlayerColor + "Tour supplémentaire acheté avec succès (-2 points)." + TexteConsole.RESET);
                        tourSupplementaireActif = true;
                    } else {
                        System.out.println(TexteConsole.HIGHLIGHT + "Vous n'avez pas assez de points pour acheter un tour supplémentaire." + TexteConsole.RESET);
                    }
                    TexteConsole.waitForEnter();
                    break;
                	
                
                case 6: // Quitter la partie
                	TexteConsole.remerciement();
                    quitter = true;
                    break;
                    
                default:
                    TexteConsole.afficherErreurSaisie();
                    TexteConsole.waitForEnter();
                    break;
            }
            
            // Vérifier la fin de partie
            if (arbitre.finDePartie(new Rack[]{rackJoueur1, rackJoueur2}, totalTours, MAX_TOURS)) {
                TexteConsole.clearScreen();
                
                Joueur[] joueurs = {joueur1, joueur2};
                List<Integer> gagnants = arbitre.getGagnants(joueurs);
                int gagnant = gagnants.get(0);
                String gagnantNom = (gagnant == 0) ? joueur1.getName() : joueur2.getName();
                String gagnantColor = (gagnant == 0) ? colorJ1 : colorJ2;
                
                TexteConsole.finPartie(arbitre, joueur1, joueur2, colorJ1, colorJ2, gagnantNom, gagnantColor);
                quitter = true;
            }

        }
        
        scanner.close();
    }

	

	

}







