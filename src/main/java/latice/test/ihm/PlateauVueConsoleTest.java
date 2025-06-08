package latice.test.ihm;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import latice.ihm.PlateauVueConsole;
import latice.model.Plateau;
import latice.model.Tuile;
import latice.model.Couleur;
import latice.model.Symbole;
import latice.model.PositionTuiles;

public class PlateauVueConsoleTest {

    @Test
    public void doitAfficherLePlateauEnConsoleSimplement() {
        // Rediriger la sortie standard
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        PrintStream sortieOriginale = System.out;
        System.setOut(new PrintStream(contenuSortie));
        
        try {
            // Créer et configurer le plateau
            Plateau plateau = new Plateau();
            
            // Placer quelques tuiles pour le test
            plateau.placerTuile(new Tuile(Couleur.ROUGE, Symbole.FLEUR), new PositionTuiles(4, 4));
            plateau.placerTuile(new Tuile(Couleur.BLEU, Symbole.LEZARD), new PositionTuiles(4, 5));
            
            // Afficher le plateau avec la vue console
            PlateauVueConsole plateauVue = new PlateauVueConsole();
            plateauVue.afficherPlateau(plateau);
            
            // Vérifier que la sortie contient des caractères attendus
            String sortie = contenuSortie.toString();
            assertTrue(sortie.contains("["), "L'affichage devrait contenir des crochets");
            assertTrue(sortie.length() > 100, "L'affichage devrait être assez long pour un plateau 9x9");
            
        } finally {
            // Restaurer la sortie standard
            System.setOut(sortieOriginale);
        }
    }

    @Test
    public void doitAfficherLePlateauEnConsoleAvecLesIndices() {
        // Rediriger la sortie standard
        ByteArrayOutputStream contenuSortie = new ByteArrayOutputStream();
        PrintStream sortieOriginale = System.out;
        System.setOut(new PrintStream(contenuSortie));
        
        try {
            Plateau plateau = new Plateau();
            PlateauVueConsole plateauVue = new PlateauVueConsole();
            
            // Afficher avec indices
            plateauVue.afficherPlateau(plateau, true);
            
            String sortie = contenuSortie.toString();
            assertTrue(sortie.contains("1"), "Les indices numériques devraient être affichés");
            assertTrue(sortie.contains("9"), "L'indice 9 devrait être présent pour une grille 9x9");
            
        } finally {
            System.setOut(sortieOriginale);
        }
    }
}

