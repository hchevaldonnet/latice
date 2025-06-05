package latice.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import latice.ihm.PlateauViewConsole;
import latice.ihm.PlateauViewJavaFX;
import latice.ihm.RackViewConsole;
import latice.model.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LaticeJeuxEssais {
	
	
	@Test
	void TestcaseisMoonTrue() {
		Plateau plateau = new Plateau();
		PositionTuiles pos = new PositionTuiles(4,4);
		AssertTrue(plateau.caseIsMoon(pos));
	}
	
	@Test
	void TestcaseisMoonFalse() {
		Plateau plateau = new Plateau();
		PositionTuiles pos = new PositionTuiles(2,4);
		AssertFalse(plateau.caseIsMoon(pos));
	
	@Test
	void TestcaseisSunStone() {
        Plateau plateau = new Plateau();
        PositionTuiles moonCase = new PositionTuiles(4, 4);
        assertTrue(plateau.caseIsMoon(moonCase), "La position (4,4) devrait être une case lune");
    }
    
    @Test
    public void testCaseIsMoonFalse() {
        Plateau plateau = new Plateau();
        PositionTuiles notMoonCase = new PositionTuiles(2, 4);
        assertFalse(plateau.caseIsMoon(notMoonCase), "La position (2,4) ne devrait pas être une case lune");
    }
    
    @Test
    public void testCaseIsSunStone() {
        Plateau plateau = new Plateau();

        assertTrue(plateau.caseIsSunStones(new PositionTuiles(0, 0)));
        assertTrue(plateau.caseIsSunStones(new PositionTuiles(0, 4)));
        assertTrue(plateau.caseIsSunStones(new PositionTuiles(0, 8)));
        assertTrue(plateau.caseIsSunStones(new PositionTuiles(1, 1)));
        assertTrue(plateau.caseIsSunStones(new PositionTuiles(1, 7)));
        assertTrue(plateau.caseIsSunStones(new PositionTuiles(2, 2)));
        assertTrue(plateau.caseIsSunStones(new PositionTuiles(2, 6)));
        assertTrue(plateau.caseIsSunStones(new PositionTuiles(4, 0)));
        assertTrue(plateau.caseIsSunStones(new PositionTuiles(4, 8)));
        assertTrue(plateau.caseIsSunStones(new PositionTuiles(6, 2)));
        assertTrue(plateau.caseIsSunStones(new PositionTuiles(6, 6)));
        assertTrue(plateau.caseIsSunStones(new PositionTuiles(7, 1)));
        assertTrue(plateau.caseIsSunStones(new PositionTuiles(7, 7)));
        assertTrue(plateau.caseIsSunStones(new PositionTuiles(8, 0)));
        assertTrue(plateau.caseIsSunStones(new PositionTuiles(8, 4)));
        assertTrue(plateau.caseIsSunStones(new PositionTuiles(8, 8)));
    }
    
    @Test
    public void testCaseIsSunStonesFalse() {
        Plateau plateau = new Plateau();

        assertFalse(plateau.caseIsSunStones(new PositionTuiles(3, 3)));
        assertFalse(plateau.caseIsSunStones(new PositionTuiles(1, 2)));
        assertFalse(plateau.caseIsSunStones(new PositionTuiles(4, 4)));
        assertFalse(plateau.caseIsSunStones(new PositionTuiles(2, 5)));
    }
    
    @Test
    public void testPlacerTuileSuccess() {
        Plateau plateau = new Plateau();
        Tuile tuile = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        PositionTuiles pos = new PositionTuiles(1, 1);

        plateau.placerTuile(tuile, pos);
        
        // Vérifier que la tuile a bien été placée
        assertSame(tuile, plateau.getTuile(pos), "La tuile devrait être placée avec succès.");
    }

    @Test
    public void testPlacerTuilePositionDejaOccupee() {
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
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        
        try {
            // Créer et configurer le plateau
            Plateau plateau = new Plateau();
            
            // Placer quelques tuiles pour le test
            plateau.placerTuile(new Tuile(Couleur.ROUGE, Symbole.FLEUR), new PositionTuiles(4, 4));
            plateau.placerTuile(new Tuile(Couleur.BLEU, Symbole.LEZARD), new PositionTuiles(4, 5));
            
            // Afficher le plateau avec la vue console
            PlateauViewConsole plateauView = new PlateauViewConsole();
            plateauView.afficherPlateau(plateau);
            
            // Vérifier que la sortie contient des caractères attendus
            String output = outContent.toString();
            assertTrue(output.contains("["), "L'affichage devrait contenir des crochets");
            assertTrue(output.length() > 100, "L'affichage devrait être assez long pour un plateau 9x9");
            
        } finally {
            // Restaurer la sortie standard
            System.setOut(originalOut);
        }
    }
    
    @Test
    public void testAffichagePlateauConsoleAvecIndices() {
        // Rediriger la sortie standard
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        
        try {
            Plateau plateau = new Plateau();
            PlateauViewConsole plateauView = new PlateauViewConsole();
            
            // Afficher avec indices
            plateauView.afficherPlateau(plateau, true);
            
            String output = outContent.toString();
            assertTrue(output.contains("1"), "Les indices numériques devraient être affichés");
            assertTrue(output.contains("9"), "L'indice 9 devrait être présent pour une grille 9x9");
            
        } finally {
            System.setOut(originalOut);
        }
    }
    
    @Test
    public void testAffichageRackConsole() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        
        try {
            // Créer une pioche et un rack
            Pioche pioche = new Pioche(1);
            Rack rack = new Rack(pioche);
            
            // Ajouter des tuiles au rack
            rack.ajoutTuile(new Tuile(Couleur.ROUGE, Symbole.FLEUR));
            rack.ajoutTuile(new Tuile(Couleur.BLEU, Symbole.OISEAU));
            
            // Afficher le rack
            RackViewConsole rackView = new RackViewConsole();
            rackView.afficherRack(rack);
            
            String output = outContent.toString();
            assertTrue(output.contains("Votre rack contient"), "L'affichage devrait indiquer le contenu du rack");
            
        } finally {
            System.setOut(originalOut);
        }
    }
    
    // V3 - Interface graphique pour le plateau et les racks
    // Nécessite JavaFX - nous utilisons JFXPanel pour initialiser l'environnement
    @Test
    public void testCreationPlateauViewJavaFX() { //TODO test non fonctionnel à corriger
        // Initialiser JavaFX avant de créer des composants graphiques
        new JFXPanel();
        
        // Tester la création de la vue graphique du plateau
        PlateauViewJavaFX plateauView = new PlateauViewJavaFX();
        assertNotNull(plateauView.getGridPane(), "Le GridPane ne devrait pas être null");
    }
    
    // V4 - Initialisation de la partie dans l'interface graphique
    @Test
    public void testInitialisationPartie() throws InterruptedException { //TODO test non fonctionnel à corriger
        // Initialiser JavaFX
        new JFXPanel();
        
        // Tester l'initialisation d'une partie complète
        CountDownLatch latch = new CountDownLatch(1);
        
        Platform.runLater(() -> {
            try {
                // Créer les composants nécessaires au jeu
                Pioche pioche = new Pioche(2);
                Arbitre arbitre = new Arbitre(2);
                
                // Créer les racks pour 2 joueurs
                Rack rackJoueur1 = new Rack(pioche);
                rackJoueur1.remplir(pioche, 0);
                Rack rackJoueur2 = new Rack(pioche);
                rackJoueur2.remplir(pioche, 1);
                
                // Créer les joueurs (modifié pour correspondre à la nouvelle implémentation)
                Joueur joueur1 = new Joueur("Joueur1", rackJoueur1, pioche);
                Joueur joueur2 = new Joueur("Joueur2", rackJoueur2, pioche);
                
                // Vérifier que les racks ont bien été remplis
                assertEquals(5, rackJoueur1.getTuiles().size(), "Le rack du joueur 1 devrait contenir 5 tuiles");
                assertEquals(5, rackJoueur2.getTuiles().size(), "Le rack du joueur 2 devrait contenir 5 tuiles");
                
                // Vérifier que la pioche a bien été diminuée
                assertEquals(31, pioche.taille(0), "La pioche du joueur 1 devrait avoir 31 tuiles restantes");
                assertEquals(31, pioche.taille(1), "La pioche du joueur 2 devrait avoir 31 tuiles restantes");
                
                // Créer une petite interface graphique pour tester
                BorderPane root = new BorderPane();
                PlateauViewJavaFX plateauView = new PlateauViewJavaFX();
                
                Plateau plateau = new Plateau();
                plateauView.afficherPlateau(plateau);
                
                root.setCenter(plateauView.getGridPane());
                
                Scene scene = new Scene(root, 600, 600);
                Stage stage = new Stage();
                stage.setScene(scene);
                
                // Ne pas afficher réellement la fenêtre pour le test
                // stage.show();
                
                // Vérifier que tout s'est bien passé
                assertNotNull(scene, "La scène ne devrait pas être null");
                
            } finally {
                latch.countDown();
            }
        });
        
        // Attendre que le test sur le thread JavaFX soit terminé
        assertTrue(latch.await(5, TimeUnit.SECONDS), "Le test n'a pas pu s'exécuter dans le délai imparti");
    }

	        assertFalse(resultat, "La position est déjà occupée, la deuxième tuile ne doit pas être placée.");
	    
	}
	   @Test
	   public void testOrdrejoueurVrai() {
		//TODO   
	   }
		@Test
		public void CreerGererPioche() {
			//TODO
		}
		@Test
		public void Pioches
	   
	    @Test
	    public void testcaselune() {
	    Plateau plateau = new Plateau();
	    PositionTuiles pos = new PositionTuiles(4,4);
		if (plateau.caseIsMoon(pos)) {
			TexteConsole.caseMoonStone();
		}
		else {
			TexteConsole.notCaseMoonStone();
		boolean resultat = plateau.caseIsMoon(pos);
		assertTrue(resultat,"C'est une case lune");
		}
		}
		@Test
		public void testcasesoleil() {
		Plateau plateau = new Plateau();
		PositionTuiles pos = new PositionTuiles(4,0);
		if (plateau.caseIsSunStones(pos)){
			TexteConsole.caseSunStone();
		}
		else {
			TexteConsole.notCaseSunStone();
		boolean resultat = plateau.caseIsSunStones(pos);
		assertTrue(resultat);
		}
		
		
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
        int resultat = arbitre.verifierCoup(4, 4, tuile, plateau.getCases(), true, 0);
        assertTrue(resultat >= 0, "Le premier coup au centre (4,4) devrait être valide");
        
        // Premier coup invalide (pas au centre)
        resultat = arbitre.verifierCoup(1, 1, tuile, plateau.getCases(), true, 0);
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
        int resultat = arbitre.verifierCoup(4, 5, tuileCompatibleCouleur, plateau.getCases(), false, 0);
        assertTrue(resultat >= 0, "Une tuile de même couleur devrait être compatible");
        
        // Tuile compatible (même symbole)
        Tuile tuileCompatibleSymbole = new Tuile(Couleur.BLEU, Symbole.FLEUR);
        resultat = arbitre.verifierCoup(5, 4, tuileCompatibleSymbole, plateau.getCases(), false, 0);
        assertTrue(resultat >= 0, "Une tuile de même symbole devrait être compatible");
        
        // Tuile incompatible (ni même couleur, ni même symbole)
        Tuile tuileIncompatible = new Tuile(Couleur.BLEU, Symbole.LEZARD);
        resultat = arbitre.verifierCoup(3, 4, tuileIncompatible, plateau.getCases(), false, 0);
        assertEquals(-1, resultat, "Une tuile incompatible devrait être rejetée");
    }
    
    @Test
    public void testPlacementTuileHorsLimites() {
        // Test de placement hors des limites du plateau
        Plateau plateau = new Plateau();
        Arbitre arbitre = new Arbitre(2);
        Tuile tuile = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        
        // Placer une tuile hors des limites
        int resultat = arbitre.verifierCoup(-1, 4, tuile, plateau.getCases(), false, 0);
        assertEquals(-1, resultat, "Le placement hors limites devrait être invalide");
        
        resultat = arbitre.verifierCoup(9, 4, tuile, plateau.getCases(), false, 0);
        assertEquals(-1, resultat, "Le placement hors limites devrait être invalide");
    }
    
    @Test
    public void testPlacementTuileCasesSpeciales() {
        // Test de placement sur cases spéciales (lune, soleil)
        Plateau plateau = new Plateau();
        
        // Vérifier que les cases spéciales sont bien détectées
        PositionTuiles posLune = new PositionTuiles(4, 4);
        assertTrue(plateau.caseIsMoon(posLune), "La position (4,4) devrait être une case lune");
        
        PositionTuiles posSoleil = new PositionTuiles(0, 0);
        assertTrue(plateau.caseIsSunStones(posSoleil), "La position (0,0) devrait être une case soleil");
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
        int resultat = arbitre.verifierCoup(4, 5, tuileAdjacente, plateau.getCases(), false, 0);
        assertTrue(resultat >= 0, "Une tuile adjacente et compatible devrait être acceptée");
        
        // Tester une tuile compatible mais non adjacente
        Tuile tuileNonAdjacente = new Tuile(Couleur.ROUGE, Symbole.OISEAU);
        resultat = arbitre.verifierCoup(2, 2, tuileNonAdjacente, plateau.getCases(), false, 0);
        assertEquals(-1, resultat, "Une tuile non adjacente devrait être rejetée");
    }
    
    @Test
    public void testCalculPointsLatice() { //TODO test non fonctionnel à corriger
        // Test du calcul des points (cas Latice - 4 correspondances)
        Plateau plateau = new Plateau();
        Arbitre arbitre = new Arbitre(2);
        
        // Créer une configuration pour tester un Latice (4 correspondances)
        // Placer des tuiles en forme de croix autour d'une position centrale
        Tuile tuileCentre = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        Tuile tuileHaut = new Tuile(Couleur.BLEU, Symbole.FLEUR);
        Tuile tuileBas = new Tuile(Couleur.VERT, Symbole.FLEUR);
        Tuile tuileGauche = new Tuile(Couleur.ROUGE, Symbole.LEZARD);
        
        plateau.placerTuile(tuileCentre, new PositionTuiles(4, 4));
        plateau.placerTuile(tuileHaut, new PositionTuiles(3, 4));
        plateau.placerTuile(tuileBas, new PositionTuiles(5, 4));
        plateau.placerTuile(tuileGauche, new PositionTuiles(4, 3));
        
        // Placer une tuile à droite qui correspond à 4 tuiles adjacentes
        Tuile tuileDroite = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        int resultat = arbitre.verifierCoup(4, 5, tuileDroite, plateau.getCases(), false, 0);
        
        // Vérifier le nombre de correspondances (devrait être 4 pour un Latice)
        assertEquals(4, resultat, "Placer une tuile avec 4 correspondances devrait être un Latice");
    }
}
