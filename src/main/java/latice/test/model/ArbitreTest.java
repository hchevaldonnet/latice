package latice.test.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import latice.model.Arbitre;
import latice.model.Plateau;
import latice.model.Tuile;
import latice.model.Couleur;
import latice.model.Symbole;
import latice.model.PositionTuiles;
import latice.model.Rack;
import latice.model.Pioche;
import latice.model.Joueur;

public class ArbitreTest {

    @Test
    public void doitVerifierPlacementTuilePremierCoup() {
        Plateau plateau = new Plateau();
        Arbitre arbitre = new Arbitre(2);
        Tuile tuile = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        
        int resultat = arbitre.verifierCoup(4, 4, tuile, plateau.getCases(), true);
        assertTrue(resultat >= 0, "Le premier coup au centre (4,4) devrait être valide");
        
        resultat = arbitre.verifierCoup(1, 1, tuile, plateau.getCases(), true);
        assertEquals(-1, resultat, "Le premier coup ailleurs qu'au centre devrait être invalide");
    }
    
    @Test
    public void doitVerifierPlacementTuileSelonCompatibilite() {
        Plateau plateau = new Plateau();
        Arbitre arbitre = new Arbitre(2);
        
        Tuile tuileCentre = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        plateau.placerTuile(tuileCentre, new PositionTuiles(4, 4));
        
        Tuile tuileCompatibleCouleur = new Tuile(Couleur.ROUGE, Symbole.LEZARD);
        int resultat = arbitre.verifierCoup(4, 5, tuileCompatibleCouleur, plateau.getCases(), false);
        assertTrue(resultat >= 0, "Une tuile de même couleur devrait être compatible");
        
        Tuile tuileCompatibleSymbole = new Tuile(Couleur.BLEU, Symbole.FLEUR);
        resultat = arbitre.verifierCoup(5, 4, tuileCompatibleSymbole, plateau.getCases(), false);
        assertTrue(resultat >= 0, "Une tuile de même symbole devrait être compatible");
        
        Tuile tuileIncompatible = new Tuile(Couleur.BLEU, Symbole.LEZARD);
        resultat = arbitre.verifierCoup(3, 4, tuileIncompatible, plateau.getCases(), false);
        assertEquals(-1, resultat, "Une tuile incompatible devrait être rejetée");
    }
    
    @Test
    public void doitVerifierPlacementTuileHorsLimites() {
        Plateau plateau = new Plateau();
        Arbitre arbitre = new Arbitre(2);
        Tuile tuile = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        
        int resultat = arbitre.verifierCoup(-1, 4, tuile, plateau.getCases(), false);
        assertEquals(-1, resultat, "Le placement hors limites devrait être invalide");
        
        resultat = arbitre.verifierCoup(9, 4, tuile, plateau.getCases(), false);
        assertEquals(-1, resultat, "Le placement hors limites devrait être invalide");
    }
    
    @Test
    public void doitVerifierPlacementTuileAdjacente() {
        Plateau plateau = new Plateau();
        Arbitre arbitre = new Arbitre(2);
        
        Tuile tuileCentre = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        plateau.placerTuile(tuileCentre, new PositionTuiles(4, 4));
        
        Tuile tuileAdjacente = new Tuile(Couleur.ROUGE, Symbole.LEZARD);
        int resultat = arbitre.verifierCoup(4, 5, tuileAdjacente, plateau.getCases(), false);
        assertTrue(resultat >= 0, "Une tuile adjacente et compatible devrait être acceptée");
        
        Tuile tuileNonAdjacente = new Tuile(Couleur.ROUGE, Symbole.OISEAU);
        resultat = arbitre.verifierCoup(2, 2, tuileNonAdjacente, plateau.getCases(), false);
        assertEquals(-1, resultat, "Une tuile non adjacente devrait être rejetée");
    }
    
    @Test
    public void doitCalculerPointsLatice() {
        Plateau plateau = new Plateau();
        Arbitre arbitre = new Arbitre(2);
        
        Tuile tuileCentre = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        plateau.placerTuile(tuileCentre, new PositionTuiles(4, 4));
        
        plateau.placerTuile(new Tuile(Couleur.ROUGE, Symbole.LEZARD), new PositionTuiles(3, 5));
        plateau.placerTuile(new Tuile(Couleur.BLEU, Symbole.FLEUR), new PositionTuiles(3, 7));
        plateau.placerTuile(new Tuile(Couleur.VERT, Symbole.FLEUR), new PositionTuiles(4, 6));
        
        Tuile tuileLatice = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        int resultat = arbitre.verifierCoup(3, 6, tuileLatice, plateau.getCases(), false);
        assertEquals(3, resultat, "Placer une tuile avec 3 correspondances");
    }

    @Test
    public void doitVerifierPlacementTuileDiagonale() {
        Plateau plateau = new Plateau();
        Arbitre arbitre = new Arbitre(2);
        
        Tuile tuileCentre = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        plateau.placerTuile(tuileCentre, new PositionTuiles(4, 4));
        
        Tuile tuileDiagonale = new Tuile(Couleur.ROUGE, Symbole.LEZARD);
        int resultat = arbitre.verifierCoup(3, 5, tuileDiagonale, plateau.getCases(), false);
        assertEquals(-1, resultat, "Une tuile diagonale ne doit pas être acceptée");
    }
    
    @Test
    public void doitVerifierFinDePartie() {
        int nbJoueurs = 2;
        int maxTours = 10;
        Arbitre arbitre = new Arbitre(nbJoueurs);
        Rack[] racks = new Rack[nbJoueurs];
        Pioche pioche = new Pioche(nbJoueurs);
        
        for (int i = 0; i < nbJoueurs; i++) {
            racks[i] = new Rack(pioche);
            racks[i].ajoutTuile(new Tuile(Couleur.ROUGE, Symbole.FLEUR));
        }
        
        assertFalse(arbitre.finDePartie(racks, 5, maxTours), "Le jeu ne doit pas être terminé en 5 tours");
        assertTrue(arbitre.finDePartie(racks, 10, maxTours), "Le jeu devrait être terminé à 10 tours");
        assertTrue(arbitre.finDePartie(racks, 11, maxTours), "Le jeu devrait être terminé si le nombre de tours est supérieur au maximum");
    }


    @Test
    public void doitCalculerPointsAvecCaseSoleil() {
        Plateau plateau = new Plateau();
        Arbitre arbitre = new Arbitre(2);

        Tuile tuileAdjacente = new Tuile(Couleur.ROUGE, Symbole.LEZARD);
        plateau.placerTuile(tuileAdjacente, new PositionTuiles(0, 1));

        Tuile tuileSoleil = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        int resultat = arbitre.verifierCoup(0, 0, tuileSoleil, plateau.getCases(), false);
        assertTrue(resultat >= 1, "Le placement devrait avoir au moins 1 correspondance");

        plateau.placerTuile(tuileSoleil, new PositionTuiles(0, 0));
        arbitre.calculerPointsApresCoup(0, 0, resultat, 0); // La tuile est placée en (0,0) qui est une case soleil
        
        // Si resultat = 1 (1 correspondance), points normaux = 0. Bonus soleil = +1. Total = 1.
        assertEquals(1.0, arbitre.getScore(0), "Le score devrait être de 1.0 point avec une correspondance sur case soleil");
    }

    @Test
    public void doitDistribuerTuilesInitialement() {
        int nbJoueurs = 2;
        Arbitre arbitre = new Arbitre(nbJoueurs);
        Joueur[] joueurs = new Joueur[nbJoueurs];
        Pioche pioche = new Pioche(nbJoueurs);
        
        for (int i = 0; i < nbJoueurs; i++) {
            joueurs[i] = new Joueur("Joueur" + i, new Rack(pioche), pioche);
        }
        
        arbitre.distribuerTuiles(joueurs);
        
        for (int i = 0; i < nbJoueurs; i++) {
            assertEquals(5, joueurs[i].getRack().getTuiles().size(), 
                    "Chaque joueur devrait avoir 5 tuiles après la distribution initiale");
        }
    }

    @Test
    public void doitVerifierPlacementLaticeQuatreCotes() {
        Plateau plateau = new Plateau();
        Arbitre arbitre = new Arbitre(2);
        
        Tuile tuileLune = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        plateau.placerTuile(tuileLune, new PositionTuiles(4, 4)); // Premier coup
        
        int centerX = 3;
        int centerY = 3;
        
        plateau.placerTuile(new Tuile(Couleur.ROUGE, Symbole.FLEUR), new PositionTuiles(centerX-1, centerY));
        plateau.placerTuile(new Tuile(Couleur.BLEU, Symbole.FLEUR), new PositionTuiles(centerX, centerY+1));
        plateau.placerTuile(new Tuile(Couleur.ROUGE, Symbole.LEZARD), new PositionTuiles(centerX+1, centerY));
        plateau.placerTuile(new Tuile(Couleur.BLEU, Symbole.FLEUR), new PositionTuiles(centerX, centerY-1));

        Tuile tuileLatice = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        int resultat = arbitre.verifierCoup(centerX, centerY, tuileLatice, plateau.getCases(), false);
        assertEquals(4, resultat, "Le placement devrait avoir exactement 4 correspondances");
    }
    
    @Test
    public void doitVerifierPlacementTuileSurPositionOccupee() {
        Plateau plateau = new Plateau();
        Tuile tuile1 = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        Tuile tuile2 = new Tuile(Couleur.VERT, Symbole.FLEUR);
        PositionTuiles pos = new PositionTuiles(2, 2);

        plateau.placerTuile(tuile1, pos);
        plateau.placerTuile(tuile2, pos);

        // Vérifier que la tuile1 est toujours à cet emplacement et pas tuile2
        assertSame(tuile1, plateau.getTuile(pos), "La position est déjà occupée, la deuxième tuile ne doit pas être placée.");
    }
}

