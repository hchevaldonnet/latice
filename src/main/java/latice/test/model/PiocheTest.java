package latice.test.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import latice.model.Pioche;
import latice.model.Tuile;

public class PiocheTest {

    @Test
    public void doitRetournerTaillePioches() {
        Pioche pioche = new Pioche(2);
        int tailleJoueur1 = pioche.taille(0);
        int tailleJoueur2 = pioche.taille(1);
        assertEquals(36, tailleJoueur1, "Joueur 1 doit avoir 36 tuiles");
        assertEquals(36, tailleJoueur2, "Joueur 2 doit avoir 36 tuiles");
        assertEquals(tailleJoueur1, tailleJoueur2, "Les deux joueurs doivent avoir le même nombre de tuiles");
    }

    @Test
    public void doitGererComportementPiocheVide() {
        Pioche pioche = new Pioche(1);
        
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
    }
}

