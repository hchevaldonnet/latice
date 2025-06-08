package latice.test.model.setup;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import latice.model.setup.PreparerJeu;

public class PreparerJeuTest {

	//TODO: changer le nom de tous les test par "doit" + verbe à l'infinitif pour respecter la convention de nommage des tests unitaires	
    @Test
    public void doitPermettreNom1SoitPremierePosition() {
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
    public void doitPermettreNom2SoitPremierePosition() {
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
}

