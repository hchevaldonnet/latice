package latice.test.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import latice.model.Rack;
import latice.model.Pioche;
import latice.model.Tuile;
import latice.model.Couleur;
import latice.model.Symbole;

public class RackTest {

    @Test
    public void doitRemplirRackAutomatiquement() {
        Pioche pioche = new Pioche(1);
        Rack rack = new Rack();
        
        assertEquals(0, rack.getTuiles().size(), "Le rack devrait être vide initialement");
        
        rack.remplir(pioche, 0);
        assertEquals(5, rack.getTuiles().size(), "Le rack devrait contenir 5 tuiles après remplissage");
    }

    @Test
    public void doitViderRack() {
        Rack rack = new Rack();
        
        for (int i = 0; i < 3; i++) {
            rack.ajoutTuile(new Tuile(Couleur.ROUGE, Symbole.FLEUR));
        }
        
        assertFalse(rack.getTuiles().isEmpty(), "Le rack ne devrait pas être vide");
        
        rack.vider();
        assertTrue(rack.getTuiles().isEmpty(), "Le rack devrait être vide après l'appel à vider()");
    }
    
    @Test
    public void doitRespecterLimiteCapaciteRack() {
        Pioche pioche = new Pioche(1);
        Rack rack = new Rack();
        
        for (int i = 0; i < 8; i++) {
            rack.ajoutTuile(new Tuile(Couleur.ROUGE, Symbole.FLEUR));
        }
        assertEquals(8, rack.getTuiles().size(), "Le rack peut contenir plus de 5 tuiles si ajoutées manuellement");
        
        rack.vider();
        rack.remplir(pioche, 0);
        assertEquals(5, rack.getTuiles().size(), 
                "Le remplissage automatique du rack devrait s'arrêter à 5 tuiles");
    }
}

