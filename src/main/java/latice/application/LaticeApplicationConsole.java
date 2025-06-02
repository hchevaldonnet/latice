package latice.application;

import java.util.Scanner;
import latice.model.Joueur;
import latice.model.Pioche;
import latice.model.Plateau;
import latice.model.PositionTuiles;
import latice.model.Rack;
import latice.model.setup.GameSetup;
import latice.ihm.*;

public class LaticeApplicationConsole {
    
    public static void main(String[] args) {
        
        // Créer une pioche pour 2 joueurs
        Pioche pioche = new Pioche(2);

        Rack rackJoueur1 = new Rack(pioche);
        rackJoueur1.remplir(pioche, 0);
        Rack rackJoueur2 = new Rack(pioche);
        rackJoueur2.remplir(pioche, 1);
        
        Plateau plateau = new Plateau();
        
        boolean quitter = false;
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Joueur 1 :");
        String nom1 = SaisieConsole.saisieChar();
        System.out.println("Joueur 2 :");
        String nom2 = SaisieConsole.saisieChar();
        
        // Détermine aléatoirement l'ordre des joueurs
        String[] ordreJoueurs = GameSetup.ordreJoueur(nom1, nom2);
        Joueur joueur1 = new Joueur(ordreJoueurs[0], rackJoueur1, pioche); 
        Joueur joueur2 = new Joueur(ordreJoueurs[1], rackJoueur2, pioche);
        
        System.out.println("Premier joueur : " + joueur1.getName());
        System.out.println("Second joueur : " + joueur2.getName());
        
        TexteConsole.afficherBienvenue();
        TexteConsole.afficherMenu();
        
        // Initialiser le joueur courant
        Joueur joueurCourant = joueur1;
        
        while (!quitter) {
            
            PlateauViewConsole pvc = new PlateauViewConsole();
            pvc.afficherPlateau(plateau);
            
            // Afficher le rack du joueur courant
            if (joueurCourant.equals(joueur1)) {
                System.out.println(rackJoueur1.afficherRack("C'est au tour de " + joueur1.getName() + ", voici votre rack :\n"));
            } else {
                System.out.println(rackJoueur2.afficherRack("C'est au tour de " + joueur2.getName() + ", voici votre rack :\n"));
            }
            
            int choix = SaisieConsole.saisieChoix() + 1;
            
            switch (choix) {
                case 1: // Placer une tuile
                    int choixTuile = SaisieConsole.saisieTuiles();
                    int ligne = SaisieConsole.saisieLigneColonne("ligne");
                    int colonne = SaisieConsole.saisieLigneColonne("colonne");
                    
                    PositionTuiles pos = new PositionTuiles(ligne, colonne);
                    
                    // Utiliser le rack du joueur courant
                    Rack rackCourant = joueurCourant.getRack();
                    
                    // Vérifier si l'indice est valide
                    if (choixTuile >= 0 && choixTuile < rackCourant.getTuiles().size()) {
                        // Vérifier si la case est libre avant de placer
                        boolean placementPossible = plateau.caseLibre(pos);
                        
                        if (placementPossible) {
                            // Placer la tuile sur le plateau
                            plateau.placerTuile(rackCourant.getTuiles().get(choixTuile), pos);
                            
                            // Retirer la tuile du rack et piocher une nouvelle
                            rackCourant.retirerTuile(choixTuile);
                            
                            // Utiliser l'indice correct du joueur (0 pour joueur1, 1 pour joueur2)
                            int joueurIndex = (joueurCourant == joueur1) ? 0 : 1;
                            rackCourant.remplir(pioche, joueurIndex);
                            
                            // Vérifier les cases spéciales
                            if (plateau.caseIsSunStones(pos)) {
                                TexteConsole.caseSunStone();
                                // Ajouter un bonus pour la case soleil
                            } else {
                                TexteConsole.notCaseSunStone();
                            }
                            
                            if (plateau.caseIsMoon(pos)) {
                                TexteConsole.caseMoonStone();
                                // Ajouter un bonus pour la case lune
                            } else {
                                TexteConsole.notCaseMoonStone();
                            }
                            
                            // Changer de joueur
                            joueurCourant = (joueurCourant == joueur1) ? joueur2 : joueur1;
                        } else {
                            System.out.println("Placement impossible. Veuillez réessayer.");
                        }
                    } else {
                        System.out.println("Tuile invalide. Veuillez réessayer.");
                    }
                    break;
                    
                case 2: // Piocher une nouvelle main
                    joueurCourant.getRack().vider();
                    
                    // Utiliser l'indice correct du joueur
                    int joueurIndex = (joueurCourant == joueur1) ? 0 : 1;
                    joueurCourant.getRack().remplir(pioche, joueurIndex);
                    
                    System.out.println("Vous avez pioché une nouvelle main.");
                    
                    // Changer de joueur
                    joueurCourant = (joueurCourant == joueur1) ? joueur2 : joueur1;
                    break;
                    
                case 3: // Passer son tour
                    System.out.println(joueurCourant.getName() + " passe son tour.");
                    
                    // Changer de joueur
                    joueurCourant = (joueurCourant == joueur1) ? joueur2 : joueur1;
                    break;
                    
                case 4: // Règles du jeu
                    TexteConsole.afficherRegles();
                    break;
                    
                case 5: // Quitter la partie
                    quitter = true;
                    System.out.println("Merci d'avoir joué !");
                    break;
                    
                default:
                    TexteConsole.afficherErreurSaisie();
                    break;
            }
        }
        
        scanner.close();
    }
}





