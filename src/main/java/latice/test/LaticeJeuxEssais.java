package latice.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import latice.ihm.PlateauVueConsole;
import latice.ihm.RackVueConsole;
import latice.model.*;
import latice.model.setup.PreparerJeu;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class LaticeJeuxEssais {
    
    @Test
    public void testCaseLuneNonValide() {
        PositionTuiles notMoonCase = new PositionTuiles(2, 4);
        assertFalse(notMoonCase.estUneCaseLune(notMoonCase.getX(), notMoonCase.getY()), 
                "La position (2,4) ne devrait pas être une case lune");
    }
    
    @Test
    public void testerCaseSoleilValide() {
        for (PositionCaseSoleil position : PositionCaseSoleil.values()) {
            assertTrue(PositionCaseSoleil.estUneCaseSoleil(position.getX(), position.getY()),
                    "La position (" + position.getX() + "," + position.getY() + ") devrait être une case soleil");
        }
    }
    
    @Test
    public void testCaseSoleilNonValide() {
        assertFalse(PositionCaseSoleil.estUneCaseSoleil(3, 3),
                "La position (3,3) ne devrait pas être une case soleil");
        assertFalse(PositionCaseSoleil.estUneCaseSoleil(1, 2),
                "La position (1,2) ne devrait pas être une case soleil");
        assertFalse(PositionCaseSoleil.estUneCaseSoleil(4, 4),
                "La position (4,4) ne devrait pas être une case soleil");
        assertFalse(PositionCaseSoleil.estUneCaseSoleil(2, 5),
                "La position (2,5) ne devrait pas être une case soleil");
    }
    
    @Test
    public void testPlacementTuileReussi() {
        Plateau plateau = new Plateau();
        Tuile tuile = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        PositionTuiles pos = new PositionTuiles(1, 1);

        plateau.placerTuile(tuile, pos);
        
        // Vérifier que la tuile a bien été placée
        assertSame(tuile, plateau.getTuile(pos), "La tuile devrait être placée avec succès.");
    }

    @Test
    public void testPlacementTuilePositionOccupee() {
        Plateau plateau = new Plateau();
        Tuile tuile1 = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        Tuile tuile2 = new Tuile(Couleur.VERT, Symbole.FLEUR);
        PositionTuiles pos = new PositionTuiles(2, 2);

        plateau.placerTuile(tuile1, pos);
        plateau.placerTuile(tuile2, pos);

        // Vérifier que la tuile1 est toujours à cet emplacement et pas tuile2
        assertSame(tuile1, plateau.getTuile(pos), "La position est déjà occupée, la deuxième tuile ne doit pas être placée.");
    }
    
    // V2 - Affichage du plateau en mode console
    @Test
    public void testAffichagePlateauConsole() {
        // Rediriger la sortie standard pour tester l'affichage
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        PrintStream sortieOriginale = System.out;
        System.setOut(new PrintStream(contenuSortie));
        
        try {
            // Créer et configurer le plateau
            Plateau plateau = new Plateau();
            
            // Placer quelques tuiles pour le test
            plateau.placerTuile(new Tuile(Couleur.ROUGE, Symbole.FLEUR), new PositionTuiles(4, 4));
            plateau.placerTuile(new Tuile(Couleur.BLEU, Symbole.LEZARD), new PositionTuiles(4, 5));
            
            // Afficher le plateau avec la vue console
            PlateauVueConsole plateauVue = new PlateauVueConsole();
            plateauVue.afficherPlateau(plateau);
            
            // Vérifier que la sortie contient des caractères attendus
            String sortie = contenuSortie.toString();
            assertTrue(sortie.contains("["), "L'affichage devrait contenir des crochets");
            assertTrue(sortie.length() > 100, "L'affichage devrait être assez long pour un plateau 9x9");
            
        } finally {
            // Restaurer la sortie standard
            System.setOut(sortieOriginale);
        }
    }

    @Test
    public void testAffichagePlateauConsoleAvecIndices() {
        // Rediriger la sortie standard
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        PrintStream sortieOriginale = System.out;
        System.setOut(new PrintStream(contenuSortie));
        
        try {
            Plateau plateau = new Plateau();
            PlateauVueConsole plateauVue = new PlateauVueConsole();
            
            // Afficher avec indices
            plateauVue.afficherPlateau(plateau, true);
            
            String sortie = contenuSortie.toString();
            assertTrue(sortie.contains("1"), "Les indices numériques devraient être affichés");
            assertTrue(sortie.contains("9"), "L'indice 9 devrait être présent pour une grille 9x9");
            
        } finally {
            System.setOut(sortieOriginale);
        }
    }

    @Test
    public void testAffichageRackConsole() {
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        PrintStream sortieOriginale = System.out;
        System.setOut(new PrintStream(contenuSortie));
        
        try {
            // Créer une pioche et un rack
            Pioche pioche = new Pioche(1);
            Rack rack = new Rack(pioche);
            
            // Ajouter des tuiles au rack
            rack.ajoutTuile(new Tuile(Couleur.ROUGE, Symbole.FLEUR));
            rack.ajoutTuile(new Tuile(Couleur.BLEU, Symbole.OISEAU));
            
            // Afficher le rack
            RackVueConsole rackVue = new RackVueConsole();
            rackVue.afficherRack(rack);
            
            String sortie = contenuSortie.toString();
            assertTrue(sortie.contains("Votre rack contient"), "L'affichage devrait indiquer le contenu du rack");
            
        } finally {
            System.setOut(sortieOriginale);
        }
    }

    
    // V4 - Initialisation de la partie dans l'interface graphique
    @Test
    public void testOrdreJoueurNom1EnPremier() {
        String nom1 = "Alice";
        String nom2 = "Bob";
        boolean trouve = false;

        for (int i = 0; i < 100; i++) {
            String[] ordre = PreparerJeu.ordreJoueur(nom1, nom2);
            if (ordre[0].equals(nom1)) {
                trouve = true;
                break;
            }
        }

        assertTrue(trouve, "Alice n'a jamais été en première position");
    }

    @Test
    public void testOrdreJoueurNom2EnPremier() {
        String nom1 = "Alice";
        String nom2 = "Bob";
        boolean trouve = false;

        for (int i = 0; i < 100; i++) {
            String[] ordre = PreparerJeu.ordreJoueur(nom1, nom2);
            if (ordre[0].equals(nom2)) {
                trouve = true;
                break;
            }
        }

        assertTrue(trouve, "Bob n'a jamais été en première position");
    }

    @Test
    public void testPiochesTailleTuile() {
        Pioche pioche = new Pioche(2);
        int tailleJoueur1 = pioche.taille(0);
        int tailleJoueur2 = pioche.taille(1);
        assertEquals(36, tailleJoueur1, "Joueur 1 doit avoir 36 tuiles");
        assertEquals(36, tailleJoueur2, "Joueur 2 doit avoir 36 tuiles");
        assertEquals(tailleJoueur1, tailleJoueur2, "Les deux joueurs doivent avoir le même nombre de tuiles");
    }
    
    // V5 - Tests de placement de tuiles via l'interface graphique
    // Tests unitaires pour contrôler le placement des tuiles
    @Test
    public void testPlacementTuilePremierCoup() {
        // Test du premier coup qui doit être au centre (4,4)
        Plateau plateau = new Plateau();
        Arbitre arbitre = new Arbitre(2);
        Tuile tuile = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        
        // Premier coup valide (au centre)
        int resultat = arbitre.verifierCoup(4, 4, tuile, plateau.getCases(), true);
        assertTrue(resultat >= 0, "Le premier coup au centre (4,4) devrait être valide");
        
        // Premier coup invalide (pas au centre)
        resultat = arbitre.verifierCoup(1, 1, tuile, plateau.getCases(), true);
        assertEquals(-1, resultat, "Le premier coup ailleurs qu'au centre devrait être invalide");
    }
    
    @Test
    public void testPlacementTuileCompatibilite() {
        // Test de compatibilité des tuiles adjacentes
        Plateau plateau = new Plateau();
        Arbitre arbitre = new Arbitre(2);
        
        // Placer une première tuile au centre (simuler que le premier coup a été joué)
        Tuile tuileCentre = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        plateau.placerTuile(tuileCentre, new PositionTuiles(4, 4));
        
        // Tuile compatible (même couleur)
        Tuile tuileCompatibleCouleur = new Tuile(Couleur.ROUGE, Symbole.LEZARD);
        int resultat = arbitre.verifierCoup(4, 5, tuileCompatibleCouleur, plateau.getCases(), false);
        assertTrue(resultat >= 0, "Une tuile de même couleur devrait être compatible");
        
        // Tuile compatible (même symbole)
        Tuile tuileCompatibleSymbole = new Tuile(Couleur.BLEU, Symbole.FLEUR);
        resultat = arbitre.verifierCoup(5, 4, tuileCompatibleSymbole, plateau.getCases(), false);
        assertTrue(resultat >= 0, "Une tuile de même symbole devrait être compatible");
        
        // Tuile incompatible (ni même couleur, ni même symbole)
        Tuile tuileIncompatible = new Tuile(Couleur.BLEU, Symbole.LEZARD);
        resultat = arbitre.verifierCoup(3, 4, tuileIncompatible, plateau.getCases(), false);
        assertEquals(-1, resultat, "Une tuile incompatible devrait être rejetée");
    }
    
    @Test
    public void testPlacementTuileHorsLimites() {
        // Test de placement hors des limites du plateau
        Plateau plateau = new Plateau();
        Arbitre arbitre = new Arbitre(2);
        Tuile tuile = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        
        // Placer une tuile hors des limites
        int resultat = arbitre.verifierCoup(-1, 4, tuile, plateau.getCases(), false);
        assertEquals(-1, resultat, "Le placement hors limites devrait être invalide");
        
        resultat = arbitre.verifierCoup(9, 4, tuile, plateau.getCases(), false);
        assertEquals(-1, resultat, "Le placement hors limites devrait être invalide");
    }
    
    @Test
    public void testPlacementTuileCasesSpeciales() {
        PositionTuiles posLune = new PositionTuiles(4, 4);
        assertTrue(posLune.estUneCaseLune(posLune.getX(), posLune.getY()), 
                "La position (4,4) devrait être une case lune");
        
        PositionTuiles posSoleil = new PositionTuiles(0, 0);
        assertTrue(PositionCaseSoleil.estUneCaseSoleil(posSoleil.getX(), posSoleil.getY()), 
                "La position (0,0) devrait être une case soleil");
    }
    
    @Test
    public void testPlacementTuileAdjacence() {
        // Test de l'adjacence requise pour placer une tuile
        Plateau plateau = new Plateau();
        Arbitre arbitre = new Arbitre(2);
        
        // Placer une première tuile
        Tuile tuileCentre = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        plateau.placerTuile(tuileCentre, new PositionTuiles(4, 4));
        
        // Tester une tuile compatible et adjacente
        Tuile tuileAdjacente = new Tuile(Couleur.ROUGE, Symbole.LEZARD);
        int resultat = arbitre.verifierCoup(4, 5, tuileAdjacente, plateau.getCases(), false);
        assertTrue(resultat >= 0, "Une tuile adjacente et compatible devrait être acceptée");
        
        // Tester une tuile compatible mais non adjacente
        Tuile tuileNonAdjacente = new Tuile(Couleur.ROUGE, Symbole.OISEAU);
        resultat = arbitre.verifierCoup(2, 2, tuileNonAdjacente, plateau.getCases(), false);
        assertEquals(-1, resultat, "Une tuile non adjacente devrait être rejetée");
    }
    
    @Test
    public void testCalculPointsLatice() {
        Plateau plateau = new Plateau();
        Arbitre arbitre = new Arbitre(2);
        
        // Placer une tuile au centre
        Tuile tuileCentre = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        plateau.placerTuile(tuileCentre, new PositionTuiles(4, 4));
        
        // Placer des tuiles autour d'une position qui sera le "Latice"
        plateau.placerTuile(new Tuile(Couleur.ROUGE, Symbole.LEZARD), new PositionTuiles(3, 5));  // Gauche
        plateau.placerTuile(new Tuile(Couleur.BLEU, Symbole.FLEUR), new PositionTuiles(3, 7));    // Haut
        plateau.placerTuile(new Tuile(Couleur.VERT, Symbole.FLEUR), new PositionTuiles(4, 6));    // Droite
        
        // La tuile à placer au centre du carré (devrait avoir 3 correspondances)
        Tuile tuileLatice = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        
        // Vérifier le placement sur une position libre (3,6) entourée de 3 tuiles compatibles
        int resultat = arbitre.verifierCoup(3, 6, tuileLatice, plateau.getCases(), false);
        
        // Vérifier le nombre de correspondances
        assertEquals(3, resultat, "Placer une tuile avec 3 correspondances");
    }

    @Test
    public void testPlacementTuileDiagonale() {
        Plateau plateau = new Plateau();
        Arbitre arbitre = new Arbitre(2);
        
        Tuile tuileCentre = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        plateau.placerTuile(tuileCentre, new PositionTuiles(4, 4));
        
        Tuile tuileDiagonale = new Tuile(Couleur.ROUGE, Symbole.LEZARD);
        int resultat = arbitre.verifierCoup(3, 5, tuileDiagonale, plateau.getCases(), false);
        assertEquals(-1, resultat, "Une tuile diagonale ne doit pas être acceptée");
    }
    
    @Test
    public void testFindePartie() {
        int nbJoueurs = 2;
        int maxTours = 10;
        Arbitre arbitre = new Arbitre(nbJoueurs);
        Rack[] racks = new Rack[nbJoueurs];
        Pioche pioche = new Pioche(nbJoueurs); // Create a pioche to initialize racks properly
        
        for (int i = 0; i < nbJoueurs; i++) {
            racks[i] = new Rack(pioche);
            // Add some tuiles to each rack to ensure they're not empty
            racks[i].ajoutTuile(new Tuile(Couleur.ROUGE, Symbole.FLEUR));
        }
        
        // Test with 5 tours - game should not be finished
        assertFalse(arbitre.finDePartie(racks, 5, maxTours), "Le jeu ne doit pas être terminé en 5 tours");
        
        // Test with max tours - game should be finished
        assertTrue(arbitre.finDePartie(racks, 10, maxTours), "Le jeu devrait être terminé à 10 tours");
        
        // Test with tours > maxTours - game should be finished
        assertTrue(arbitre.finDePartie(racks, 11, maxTours), "Le jeu devrait être terminé si le nombre de tours est supérieur au maximum");
    }

    
    @Test
    public void testEchangeRackJoueur() {
        Pioche pioche = new Pioche(1); // 1 player
        Rack rack = new Rack(pioche);
        Joueur joueur = new Joueur("TestJoueur", rack, pioche);
        Arbitre arbitre = new Arbitre(1); // Arbitre for 1 player
        
        // Donner des points au joueur (l'échange de rack coûte 1 point)
        arbitre.ajouterPoints(0, 2); // Player 0 gets 2 points
        
        // S'assurer que la pioche a suffisamment de tuiles pour l'échange et le remplissage initial.
        // La pioche est initialisée avec suffisamment de tuiles pour un joueur.
        
        rack.remplir(pioche, 0); // Fill rack for player 0
        assertEquals(5, rack.getTuiles().size(), "Le rack devrait contenir 5 tuiles avant l'action d'échange.");
        
        List<Tuile> tuilesDAvant = new ArrayList<>(rack.getTuiles());
        
        boolean resultat = joueur.jouerActionSpeciale(0, ActionSpeciale.ECHANGER_RACK, rack, pioche, arbitre);
        assertTrue(resultat, "L'échange de rack devrait réussir.");
        
        assertEquals(5, rack.getTuiles().size(), "Le rack devrait contenir 5 tuiles après l'échange.");
        assertEquals(1, arbitre.getScore(0), "Le joueur devrait avoir 1 point après l'échange (-1 point).");

        assertNotEquals(0, tuilesDAvant.size(), "Le rack initial (après remplissage) ne devrait pas être vide.");
        
        // Vérifier que les tuiles ont changé.
        // Il est possible, bien que très improbable avec une grande pioche, que les mêmes tuiles soient piochées.
        boolean tuilesIdentiques = true;
        // Les assertions précédentes garantissent que tuilesDAvant.size() et rack.getTuiles().size() sont toutes les deux 5.
        for (int i = 0; i < tuilesDAvant.size(); i++) {
            Tuile avant = tuilesDAvant.get(i);
            Tuile apres = rack.getTuiles().get(i);
            // Vérifie si la tuile à la même position est différente
            if (!avant.couleur.equals(apres.couleur) || !avant.symbole.equals(apres.symbole)) {
                tuilesIdentiques = false; // Au moins une tuile est différente, donc les racks sont différents
                break; 
            }
        }
        // Si la boucle se termine et tuilesIdentiques est toujours true, cela signifie que toutes les tuiles sont les mêmes.
        // Dans le contexte de l'échange, on s'attend à ce qu'elles soient différentes.
        
        assertFalse(tuilesIdentiques, "Le rack devrait être différent après échange. Si ce test échoue occasionnellement, cela peut être dû à la pioche aléatoire redonnant les mêmes tuiles.");
    }





    @Test
    public void testRemplissageRackAutomatique() {
        Pioche pioche = new Pioche(1);
        Rack rack = new Rack(pioche);
        
        // Vérifier que le rack est vide au début
        assertEquals(0, rack.getTuiles().size(), "Le rack devrait être vide initialement");
        
        // Remplir le rack
        rack.remplir(pioche, 0);
        
        // Vérifier que le rack contient maintenant 5 tuiles
        assertEquals(5, rack.getTuiles().size(), "Le rack devrait contenir 5 tuiles après remplissage");
    }

    @Test
    public void testCalculPointsAvecCaseSoleil() {
        Plateau plateau = new Plateau();
        Arbitre arbitre = new Arbitre(2);
        
        // Vérifier que le score initial est 0
        assertEquals(0, arbitre.getScore(0), "Score initial devrait être 0");
        
        // Placer une tuile sur une case soleil (0,0)
        Tuile tuileSoleil = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        plateau.placerTuile(tuileSoleil, new PositionTuiles(0, 0));
        
        // Placer une tuile adjacente compatible
        Tuile tuileAdjacente = new Tuile(Couleur.ROUGE, Symbole.LEZARD);
        
        // Vérifier le coup et obtenir le nombre de correspondances
        int resultat = arbitre.verifierCoup(0, 1, tuileAdjacente, plateau.getCases(), false);
        
        // Vérifier que le coup est valide et a au moins 1 correspondance
        assertTrue(resultat >= 1, "Le placement devrait avoir au moins 1 correspondance");
        
        // Placer réellement la tuile
        plateau.placerTuile(tuileAdjacente, new PositionTuiles(0, 1));
        
        // Calculer les points en tenant compte du bonus soleil
        // Important: Nous devons spécifier les coordonnées de la case soleil (0,0)
        arbitre.calculerPointsApresCoup(0, 0, resultat, 0);
        
        // Le joueur devrait avoir plus de points qu'un placement normal (avec bonus soleil)
        assertTrue(arbitre.getScore(0) > 0, "Le score devrait être positif avec bonus case soleil");
    }

    @Test
    public void testAchatTourSupplementaire() {
        Arbitre arbitre = new Arbitre(1);
        Pioche pioche = new Pioche(1);
        Rack rack = new Rack(pioche);
        Joueur joueur = new Joueur("TestJoueur", rack, pioche);
        
        // Donner des points au joueur
        arbitre.ajouterPoints(0, 3); // Assez pour acheter un tour supplémentaire
        
        // Tenter d'acheter un tour supplémentaire
        boolean resultat = joueur.jouerActionSpeciale(0, ActionSpeciale.ACTION_SUPPLEMENTAIRE, rack, pioche, arbitre);
        
        assertTrue(resultat, "L'achat du tour supplémentaire devrait réussir");
        assertEquals(1, arbitre.getScore(0), "Le joueur devrait avoir 1 point après l'achat");
    }

    @Test
    public void testDistributionInitialeTuiles() {
        int nbJoueurs = 2;
        Arbitre arbitre = new Arbitre(nbJoueurs);
        Joueur[] joueurs = new Joueur[nbJoueurs];
        Pioche pioche = new Pioche(nbJoueurs);
        
        for (int i = 0; i < nbJoueurs; i++) {
            joueurs[i] = new Joueur("Joueur" + i, new Rack(pioche), pioche);
        }
        
        arbitre.distribuerTuiles(joueurs);
        
        // Vérifier que chaque joueur a 5 tuiles dans son rack
        for (int i = 0; i < nbJoueurs; i++) {
            assertEquals(5, joueurs[i].getRack().getTuiles().size(), 
                    "Chaque joueur devrait avoir 5 tuiles après la distribution initiale");
        }
    }

    @Test
    public void testerViderRack() {
        Pioche pioche = new Pioche(1);
        Rack rack = new Rack(pioche);
        
        // Ajouter des tuiles
        for (int i = 0; i < 3; i++) {
            rack.ajoutTuile(new Tuile(Couleur.ROUGE, Symbole.FLEUR));
        }
        
        // Vérifier que le rack n'est pas vide
        assertFalse(rack.getTuiles().isEmpty(), "Le rack ne devrait pas être vide");
        
        // Vider le rack
        rack.vider();
        
        // Vérifier que le rack est vide
        assertTrue(rack.getTuiles().isEmpty(), "Le rack devrait être vide après l'appel à vider()");
    }
    
    @Test
    public void testCaseLuneValide() {
        PositionTuiles moonCase = new PositionTuiles(4, 4);
        assertTrue(moonCase.estUneCaseLune(moonCase.getX(), moonCase.getY()), 
                "La position (4,4) devrait être une case lune");
    }

    @Test
    public void testPlacementLaticeQuatreCotes() {
        Plateau plateau = new Plateau();
        Arbitre arbitre = new Arbitre(2);
        
        // D'abord placer une tuile sur la case lune pour initialiser le jeu
        Tuile tuileLune = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        plateau.placerTuile(tuileLune, new PositionTuiles(4, 4));
        
        // Utiliser la position (3,3) comme point central pour notre test
        int centerX = 3;
        int centerY = 3;
        
        // Placer des tuiles autour de notre point central
        Tuile tuileHaut = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        Tuile tuileDroite = new Tuile(Couleur.BLEU, Symbole.FLEUR);
        Tuile tuileBas = new Tuile(Couleur.ROUGE, Symbole.LEZARD);
        Tuile tuileGauche = new Tuile(Couleur.BLEU, Symbole.FLEUR);
        
        // Placer des tuiles autour de (3,3)
        plateau.placerTuile(tuileHaut, new PositionTuiles(centerX-1, centerY));  // Au-dessus
        plateau.placerTuile(tuileDroite, new PositionTuiles(centerX, centerY+1)); // À droite
        plateau.placerTuile(tuileBas, new PositionTuiles(centerX+1, centerY));   // En-dessous
        plateau.placerTuile(tuileGauche, new PositionTuiles(centerX, centerY-1)); // À gauche

        Tuile tuileLatice = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        
        // Vérifier le coup
        int resultat = arbitre.verifierCoup(centerX, centerY, tuileLatice, plateau.getCases(), false);
        
        // Vérifier que le coup est valide et a 4 correspondances
        assertEquals(4, resultat, "Le placement devrait avoir exactement 4 correspondances");
    }



    @Test
    public void testComportementPiocheVide() {
        Pioche pioche = new Pioche(1);
        Rack rack = new Rack(pioche);
        
        // Vider la pioche en piochant toutes les tuiles
        while(!pioche.estVide(0)) {
            pioche.piocher(0);
        }
        
        // Vérifier que la pioche est bien vide
        assertTrue(pioche.estVide(0), "La pioche devrait être vide");
        assertEquals(0, pioche.taille(0), "La taille de la pioche devrait être 0");
        
        // Tenter de piocher sur une pioche vide
        Tuile tuilePiochee = pioche.piocher(0);
        assertNull(tuilePiochee, "Piocher sur une pioche vide devrait retourner null");
        
        // Tenter de remplir un rack depuis une pioche vide
        int tailleDepartRack = rack.getTuiles().size();
        rack.remplir(pioche, 0);
        assertEquals(tailleDepartRack, rack.getTuiles().size(), 
                "La taille du rack ne devrait pas changer quand la pioche est vide");
    }

    @Test
    public void testLimiteCapaciteRack() {
        Pioche pioche = new Pioche(1);
        Rack rack = new Rack(pioche);
        
        // Ajouter 8 tuiles au rack (dépassant la limite normale de 5)
        for (int i = 0; i < 8; i++) {
            rack.ajoutTuile(new Tuile(Couleur.ROUGE, Symbole.FLEUR));
        }
        
        // Le rack peut contenir plus de 5 tuiles si on ajoute manuellement
        assertEquals(8, rack.getTuiles().size(), "Le rack peut contenir plus de 5 tuiles si ajoutées manuellement");
        
        // Mais lors du remplissage automatique, il devrait s'arrêter à 5
        rack.vider(); // Vider d'abord le rack
        rack.remplir(pioche, 0); // Remplir automatiquement
        assertEquals(5, rack.getTuiles().size(), 
                "Le remplissage automatique du rack devrait s'arrêter à 5 tuiles");
    }

    @Test
    public void testCalculPointsAvecCaseSoleilCorrige() {
        Plateau plateau = new Plateau();
        Arbitre arbitre = new Arbitre(2);

        // Placer une tuile adjacente à une case soleil
        Tuile tuileAdjacente = new Tuile(Couleur.ROUGE, Symbole.LEZARD);
        plateau.placerTuile(tuileAdjacente, new PositionTuiles(0, 1));

        // Placer une tuile sur la case soleil (0,0)
        Tuile tuileSoleil = new Tuile(Couleur.ROUGE, Symbole.FLEUR);

        // Vérifier le coup et obtenir le nombre de correspondances
        int resultat = arbitre.verifierCoup(0, 0, tuileSoleil, plateau.getCases(), false);

        // Vérifier que le coup est valide et a au moins 1 correspondance
        assertTrue(resultat >= 1, "Le placement devrait avoir au moins 1 correspondance");

        // Placer réellement la tuile
        plateau.placerTuile(tuileSoleil, new PositionTuiles(0, 0));

        // Calculer les points en tenant compte du bonus soleil
        arbitre.calculerPointsApresCoup(0, 0, resultat, 0);

        // La formule de calcul est 0 point pour 1 correspondance + 1 point bonus soleil
        assertEquals(1.0, arbitre.getScore(0), "Le score devrait être de 1.0 point avec une correspondance sur case soleil");
    }


    @Test
    public void testActionSpecialePointsInsuffisants() {
        Arbitre arbitre = new Arbitre(1);
        Pioche pioche = new Pioche(1);
        Rack rack = new Rack(pioche);
        Joueur joueur = new Joueur("TestJoueur", rack, pioche);
        
        // Cas 1: Tour supplémentaire sans assez de points (besoin de 2 points)
        arbitre.ajouterPoints(0, 1); // Seulement 1 point
        boolean resultat = joueur.jouerActionSpeciale(0, ActionSpeciale.ACTION_SUPPLEMENTAIRE, rack, pioche, arbitre);
        assertFalse(resultat, "L'achat du tour supplémentaire devrait échouer avec seulement 1 point");
        assertEquals(1, arbitre.getScore(0), "Le score ne devrait pas changer après un échec d'action");
        
        // Cas 2: Échange de rack sans assez de points (besoin de 1 point)
        // Assurer que le score est 0 pour ce test spécifique
        arbitre.retirerPoints(0, arbitre.getScore(0)); // Met le score à 0
        
        resultat = joueur.jouerActionSpeciale(0, ActionSpeciale.ECHANGER_RACK, rack, pioche, arbitre);
        assertFalse(resultat, "L'échange de rack devrait échouer sans points");
        assertEquals(0, arbitre.getScore(0), "Le score devrait rester à 0");
    }

}


