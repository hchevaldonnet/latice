package latice.test.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import latice.model.PositionTuiles;
import latice.model.Symbole;
import latice.model.Tuile;
import latice.model.Couleur;
import latice.model.Plateau;
import latice.model.PositionCaseSoleil; // Ajout de l'import

public class PositionTuilesTest {

	@Test
    public void doitValiderCaseLune() {
        PositionTuiles moonCase = new PositionTuiles(4, 4);
        assertTrue(moonCase.estUneCaseLune(moonCase.getX(), moonCase.getY()), 
                "La position (4,4) devrait être une case lune");
    }
	
	@Test
    public void doitInvaliderCaseLuneNonValide() {
        PositionTuiles notMoonCase1 = new PositionTuiles(0, 0);
        assertFalse(notMoonCase1.estUneCaseLune(notMoonCase1.getX(), notMoonCase1.getY()), 
                "La position (0,0) ne devrait pas être une case lune");

        PositionTuiles notMoonCase2 = new PositionTuiles(4, 3);
        assertFalse(notMoonCase2.estUneCaseLune(notMoonCase2.getX(), notMoonCase2.getY()), 
                "La position (4,3) ne devrait pas être une case lune");
    }

    @Test
    public void doitValiderCaseSoleil() {
        for (PositionCaseSoleil position : PositionCaseSoleil.values()) {
            assertTrue(PositionCaseSoleil.estUneCaseSoleil(position.getX(), position.getY()),
                    "La position (" + position.getX() + "," + position.getY() + ") devrait être une case soleil");
        }
    }
    
    @Test
    public void doitInvaliderCaseSoleilNonValide() {
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
    public void doitReussirPlacementTuile() {
        Plateau plateau = new Plateau();
        Tuile tuile = new Tuile(Couleur.ROUGE, Symbole.FLEUR);
        PositionTuiles pos = new PositionTuiles(1, 1);

        plateau.placerTuile(tuile, pos);
        
        assertSame(tuile, plateau.getTuile(pos), "La tuile devrait être placée avec succès.");
    }

    
    @Test
    public void doitTesterPlacementTuileCasesSpeciales() { 
        PositionTuiles posLune = new PositionTuiles(4, 4);
        assertTrue(posLune.estUneCaseLune(posLune.getX(), posLune.getY()), 
                "La position (4,4) devrait être une case lune");
        
        PositionTuiles posSoleil = new PositionTuiles(0, 0);
        assertTrue(PositionCaseSoleil.estUneCaseSoleil(posSoleil.getX(), posSoleil.getY()), 
                "La position (0,0) devrait être une case soleil");
        
        PositionTuiles posNonSoleil = new PositionTuiles(1, 2);
        assertFalse(PositionCaseSoleil.estUneCaseSoleil(posNonSoleil.getX(), posNonSoleil.getY()), 
                "La position (1,2) ne devrait pas être une case soleil");
    }
}

