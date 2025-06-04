package latice.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import latice.ihm.TexteConsole;
import latice.model.Plateau;
import latice.model.PositionTuiles;
import latice.model.Tuile;


public class LaticeJeuxEssais {
	
	
	@Test
	void TestcaseisMoonTrue() {
		Plateau plateau = new Plateau();
		PositionTuiles moonCase = new PositionTuiles(4,4);
		AssertTrue(plateau.caseIsMoon(pos));
	}
	
	@Test
	void TestcaseisMoonFalse() {
		Plateau plateau = new Plateau();
		PositionTuiles moonCase = new PositionTuiles(2,4);
		AssertFalse(plateau.caseIsMoon(pos));
	
	@Test
	void TestcaseisSunStone() {
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
	        PlateauJeu plateau = new PlateauJeu();
	        Tuile tuile = new Tuile("T1");
	        PositionTuiles pos = new PositionTuiles(1, 1);

	        boolean result = plateau.placerTuile(tuile, pos);

	        assertTrue(result, "La tuile devrait être placée avec succès.");
	    }

	    @Test
	    public void testPlacerTuilePositionDéjàOccupée() {
	        Plateau plateau = new Plateau();
	        Tuile tuile1 = new Tuile("rouge","🌸");
	        Tuile tuile2 = new Tuile("vert","🌸");
	        PositionTuiles pos = new PositionTuiles(2, 2);

	        plateau.placerTuile(tuile1, pos);
	        boolean resultat = plateau.placerTuile(tuile2, pos);

	        assertFalse(resultat, "La position est déjà occupée, la deuxième tuile ne doit pas être placée.");
	    
	}
		   @Test
	   public void testOrdrejoueurVrai() {
		   
	   }
	   
	   
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
	
	
}
