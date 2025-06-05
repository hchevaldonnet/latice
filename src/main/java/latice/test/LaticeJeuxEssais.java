package latice.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import latice.ihm.PlateauViewConsole;
import latice.ihm.RackViewConsole;
import latice.model.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class LaticeJeuxEssais {
	
		
    @Test
    public void testCaseIsMoonFalse() {
        PositionTuiles notMoonCase = new PositionTuiles(2, 4);
        assertFalse(notMoonCase.estUneCaseLune(notMoonCase.getX(), notMoonCase.getY()), 
                "La position (2,4) ne devrait pas être une case lune");
    }
    
    @Test
    public void testCaseIsSunStone() {
        for (PositionCaseSoleil position : PositionCaseSoleil.values()) {
            assertTrue(PositionCaseSoleil.estUneCaseSoleil(position.getX(), position.getY()),
                    "La position (" + position.getX() + "," + position.getY() + ") devrait être une case soleil");
        }
    }

    
    @Test
    public void testCaseIsSunStonesFalse() {
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
   
    
    // V4 - Initialisation de la partie dans l'interface graphique

	   @Test
	   public void testOrdrejoueurVrai() {
		   //TODO
	   }
	   @Test 
	   public void testOrdrejoueurFaux() {
		   //TODO
	   }
		@Test
		public void CreerGererPioche() {
			//TODO
		}
		
		@Test
		public void PiochesTailleTuile() {
			Pioche pioche = new Pioche(2);
	        int tailleJoueur1 = pioche.taille(0);
	        int tailleJoueur2 = pioche.taille(1);
	        assertEquals(36, tailleJoueur1, "Joueur 1 doit avoir 36 tuiles");
	        assertEquals(36, tailleJoueur2, "Joueur doit avoir 36 tuiles");
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
        int resultat = arbitre.verifierCoup(4, 5, tuileAdjacente, plateau.getCases(), false, 0);
        assertTrue(resultat >= 0, "Une tuile adjacente et compatible devrait être acceptée");
        
        // Tester une tuile compatible mais non adjacente
        Tuile tuileNonAdjacente = new Tuile(Couleur.ROUGE, Symbole.OISEAU);
        resultat = arbitre.verifierCoup(2, 2, tuileNonAdjacente, plateau.getCases(), false, 0);
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
        int resultat = arbitre.verifierCoup(3, 6, tuileLatice, plateau.getCases(), false, 0);
        
        // Vérifier le nombre de correspondances
        assertEquals(3, resultat, "Placer une tuile avec 3 correspondances");
    }


    @Test
    public void testPlacementTuileDiagonale(){
        Plateau plateau = new Plateau();
        Arbitre arbitre = new Arbitre(2);
        
        Tuile tuileCentre = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        plateau.placerTuile(tuileCentre, new PositionTuiles(4, 4));
        
        
        Tuile tuileDiagonale = new Tuile(Couleur.ROUGE, Symbole.LEZARD);
        int resultat = arbitre.verifierCoup(3, 5, tuileDiagonale, plateau.getCases(), false, 0);
        assertEquals(-1, resultat, "Une tuile diagonale ne doit pas être acceptée");
    }
    
}
