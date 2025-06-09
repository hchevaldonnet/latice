package latice.test.ihm;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import latice.ihm.RackVueConsole;
import latice.model.Rack;
import latice.model.Tuile;
import latice.model.Couleur;
import latice.model.Symbole;

public class RackVueConsoleTest {
	
    @Test
    public void doitAfficherRackConsole() {
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        PrintStream sortieOriginale = System.out;
        System.setOut(new PrintStream(contenuSortie));
        
        try {
            // Créer un rack
            Rack rack = new Rack();
            
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
}

