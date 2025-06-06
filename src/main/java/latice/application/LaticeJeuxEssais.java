package latice.application;

import latice.model.*;
import latice.ihm.TexteConsole;

/**
 * Tests et jeux d'essais pour vérifier le bon fonctionnement du jeu Latice
 */
public class LaticeJeuxEssais {

    public static void main(String[] args) {
        System.out.println("=== DÉBUT DES TESTS DE LATICE ===");
        
        // Test 1: Création et vérification de la pioche
        testCreationPioche();
        
        // Test 2: Distribution des tuiles aux joueurs
        testDistributionTuiles();
        
        // Test 3: Test de placement de tuiles
        testPlacementTuiles();
        
        // Test 4: Test des pioches vides (test aux limites)
        testPiocheVide();
        
        System.out.println("\n=== FIN DES TESTS DE LATICE ===");
    }
    
    private static void testCreationPioche() {
        System.out.println("\n--- Test de création de la pioche ---");
        
        // Création d'une pioche pour 2 joueurs
        Pioche pioche = new Pioche(2);
        
        // Vérification du nombre total de tuiles: 6 symboles * 6 couleurs * 2 exemplaires = 72 tuiles
        // Chaque joueur devrait avoir 36 tuiles
        int tailleJoueur1 = pioche.taille(0);
        int tailleJoueur2 = pioche.taille(1);
        
        System.out.println("Taille de la pioche du joueur 1: " + tailleJoueur1);
        System.out.println("Taille de la pioche du joueur 2: " + tailleJoueur2);
        
        // Vérification de l'égalité des pioches
        if (tailleJoueur1 == tailleJoueur2) {
            TexteConsole.mettreEnSurbrillance("✓ OK: Les deux pioches ont la même taille");
        } else {
            TexteConsole.mettreEnSurbrillance("✗ ERREUR: Les pioches n'ont pas la même taille");
        }
        
        // Vérification du nombre total (36 tuiles par joueur attendues)
        int tailleAttendue = 36; // 72 tuiles / 2 joueurs
        if (tailleJoueur1 == tailleAttendue) {
            TexteConsole.mettreEnSurbrillance("✓ OK: Chaque pioche contient le nombre correct de tuiles (" + tailleAttendue + ")");
        } else {
            TexteConsole.mettreEnSurbrillance("✗ ERREUR: Les pioches n'ont pas le nombre correct de tuiles");
        }
    }
    
    private static void testDistributionTuiles() {
        System.out.println("\n--- Test de distribution des tuiles ---");
        
        // Création d'une pioche pour 2 joueurs
        Pioche pioche = new Pioche(2);
        
        // Création des racks pour chaque joueur
        Rack rackJoueur1 = new Rack(pioche);
        Rack rackJoueur2 = new Rack(pioche);
        
        // Remplissage des racks
        rackJoueur1.remplir(pioche, 0);
        rackJoueur2.remplir(pioche, 1);
        
        // Vérification du nombre de tuiles dans chaque rack (devrait être 5)
        System.out.println("Nombre de tuiles dans le rack du joueur 1: " + rackJoueur1.getTuiles().size());
        System.out.println("Nombre de tuiles dans le rack du joueur 2: " + rackJoueur2.getTuiles().size());
        
        // Vérification de la diminution des pioches
        System.out.println("Taille de la pioche du joueur 1 après distribution: " + pioche.taille(0));
        System.out.println("Taille de la pioche du joueur 2 après distribution: " + pioche.taille(1));
        
        // Vérification que les racks contiennent bien 5 tuiles
        if (rackJoueur1.getTuiles().size() == 5 && rackJoueur2.getTuiles().size() == 5) {
           TexteConsole.mettreEnSurbrillance("✓ OK: Les deux racks contiennent 5 tuiles chacun");
        } else {
           TexteConsole.mettreEnSurbrillance("✗ ERREUR: Les racks ne contiennent pas 5 tuiles chacun");
        }
        
        // Affichage des tuiles de chaque rack
        System.out.println("\nContenu du rack du joueur 1:");
        System.out.println(rackJoueur1.afficherRack(""));
        
        System.out.println("Contenu du rack du joueur 2:");
        System.out.println(rackJoueur2.afficherRack(""));
    }
    
    
    private static void testPlacementTuiles() {
        System.out.println("\n--- Test de placement des tuiles ---");
        
        Plateau plateau = new Plateau();
        
        // Création de tuiles pour le test
        Tuile tuile1 = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        Tuile tuile2 = new Tuile(Couleur.BLEU, Symbole.LEZARD);
        
        // Placement de la première tuile
        PositionTuiles position1 = new PositionTuiles(4, 4); // Case lune (centre)
        plateau.placerTuile(tuile1, position1);
        
        // Vérification du placement
        System.out.println("Tuile 1 placée en (4,4): " + (plateau.getTuile(position1) != null));
        
        // Tentative de placement sur une case déjà occupée
        plateau.placerTuile(tuile2, position1);
        
        // Vérification que la tuile 1 est toujours présente (la tuile 2 ne doit pas l'avoir remplacée)
        boolean tuile1Toujours = plateau.getTuile(position1).equals(tuile1);
        System.out.println("La tuile 1 est toujours en (4,4) après tentative de placement sur case occupée: " + tuile1Toujours);
        
        // Placement sur une case libre
        PositionTuiles position2 = new PositionTuiles(4, 5); // Case adjacente
        plateau.placerTuile(tuile2, position2);
        
        // Vérification du second placement
        System.out.println("Tuile 2 placée en (4,5): " + (plateau.getTuile(position2) != null));
    }
    
    private static void testPiocheVide() {
        System.out.println("\n--- Test des pioches vides (test aux limites) ---");
        
        // Création d'une pioche pour 2 joueurs
        Pioche pioche = new Pioche(2);
        
        System.out.println("Taille initiale de la pioche du joueur 1: " + pioche.taille(0));
        
        // Vider complètement la pioche du joueur 1
        while (!pioche.estVide(0)) {
            pioche.piocher(0);
        }
        
        System.out.println("Après avoir tout pioché, taille de la pioche du joueur 1: " + pioche.taille(0));
        System.out.println("La pioche du joueur 1 est vide: " + pioche.estVide(0));
        
        // Essayer de piocher dans une pioche vide
        Tuile tuilePiochee = pioche.piocher(0);
        System.out.println("Résultat de pioche dans une pioche vide: " + (tuilePiochee == null ? "null (correct)" : "une tuile (incorrect)"));
        
        // Test de remplissage d'un rack avec une pioche vide
        Rack rack = new Rack(pioche);
        rack.remplir(pioche, 0);
        System.out.println("Nombre de tuiles dans le rack après tentative de remplissage avec une pioche vide: " + rack.getTuiles().size());
    }
    
    
}

