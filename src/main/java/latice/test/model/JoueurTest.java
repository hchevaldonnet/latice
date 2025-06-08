package latice.test.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import latice.model.Joueur;
import latice.model.Rack;
import latice.model.Pioche;
import latice.model.Arbitre;
import latice.model.Tuile;
import latice.model.ActionSpeciale;


public class JoueurTest {

    @Test
    public void doitEchangerRackJoueur() {
        Pioche pioche = new Pioche(1);
        Rack rack = new Rack(pioche);
        Joueur joueur = new Joueur("TestJoueur", rack, pioche);
        Arbitre arbitre = new Arbitre(1);
        
        arbitre.ajouterPoints(0, 2); 
        
        rack.remplir(pioche, 0);
        assertEquals(5, rack.getTuiles().size(), "Le rack devrait contenir 5 tuiles avant l'action d'échange.");
        
        List<Tuile> tuilesDAvant = new ArrayList<>(rack.getTuiles());
        
        boolean resultat = joueur.jouerActionSpeciale(0, ActionSpeciale.ECHANGER_RACK, rack, pioche, arbitre);
        assertTrue(resultat, "L'échange de rack devrait réussir.");
        
        assertEquals(5, rack.getTuiles().size(), "Le rack devrait contenir 5 tuiles après l'échange.");
        assertEquals(1, arbitre.getScore(0), "Le joueur devrait avoir 1 point après l'échange (-1 point).");

        assertNotEquals(0, tuilesDAvant.size(), "Le rack initial (après remplissage) ne devrait pas être vide.");
        
        boolean tuilesIdentiques = true;
        if (tuilesDAvant.size() == rack.getTuiles().size()) { // Vérifier si les tailles sont égales pour éviter OutOfBounds
            for (int i = 0; i < tuilesDAvant.size(); i++) {
                Tuile avant = tuilesDAvant.get(i);
                Tuile apres = rack.getTuiles().get(i);
                if (!avant.couleur.equals(apres.couleur) || !avant.symbole.equals(apres.symbole)) {
                    tuilesIdentiques = false;
                    break; 
                }
            }
        } else {
            tuilesIdentiques = false; // Si les tailles sont différentes, les racks sont différents
        }
        assertFalse(tuilesIdentiques, "Le rack devrait être différent après échange. Si ce test échoue occasionnellement, cela peut être dû à la pioche aléatoire redonnant les mêmes tuiles.");
    }

    @Test
    public void doitAcheterTourSupplementaire() {
        Arbitre arbitre = new Arbitre(1);
        Pioche pioche = new Pioche(1);
        Rack rack = new Rack(pioche);
        Joueur joueur = new Joueur("TestJoueur", rack, pioche);
        
        arbitre.ajouterPoints(0, 3);
        
        boolean resultat = joueur.jouerActionSpeciale(0, ActionSpeciale.ACTION_SUPPLEMENTAIRE, rack, pioche, arbitre);
        
        assertTrue(resultat, "L'achat du tour supplémentaire devrait réussir");
        assertEquals(1, arbitre.getScore(0), "Le joueur devrait avoir 1 point après l'achat"); // 3 - 2 = 1
    }

    @Test
    public void doitTesterActionSpecialePointsInsuffisants() {
        Arbitre arbitre = new Arbitre(1);
        Pioche pioche = new Pioche(1);
        Rack rack = new Rack(pioche);
        Joueur joueur = new Joueur("TestJoueur", rack, pioche);
        
        // Cas 1: Tour supplémentaire sans assez de points (besoin de 2 points)
        arbitre.ajouterPoints(0, 1);
        boolean resultat = joueur.jouerActionSpeciale(0, ActionSpeciale.ACTION_SUPPLEMENTAIRE, rack, pioche, arbitre);
        assertFalse(resultat, "L'achat du tour supplémentaire devrait échouer avec seulement 1 point");
        assertEquals(1, arbitre.getScore(0), "Le score ne devrait pas changer après un échec d'action");
        
        // Cas 2: Échange de rack sans assez de points (besoin de 1 point)
        arbitre.retirerPoints(0, arbitre.getScore(0)); // Met le score à 0
        
        resultat = joueur.jouerActionSpeciale(0, ActionSpeciale.ECHANGER_RACK, rack, pioche, arbitre);
        assertFalse(resultat, "L'échange de rack devrait échouer sans points");
        assertEquals(0, arbitre.getScore(0), "Le score devrait rester à 0");
    }
}

