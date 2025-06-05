package latice.ihm;

import latice.model.Plateau;
import latice.model.PositionTuiles;
import latice.model.Tuile;

public class PlateauVueConsole implements PlateauVue {
    private static final int GRID_SIZE = 9;
    
    @Override
    public void afficherPlateau(Plateau plateau) {
        afficherPlateau(plateau, false);
    }
    
    public void afficherPlateau(Plateau plateau, boolean avecIndices) {
        // Afficher les indices de colonnes si demandé
    	//TODO: résoudre le probème de léger décalage des cases quand on insére des tuiles
        if (avecIndices) {
            System.out.print("    "); // Espace pour l'alignement
            for (int i = 1; i <= GRID_SIZE; i++) {
                System.out.print(i + "   ");
            }
            System.out.println();
        }
        
        for (int x = 0; x < GRID_SIZE; x++) {
            // Afficher l'indice de ligne si demandé
            if (avecIndices) {
                System.out.print((x + 1) + "  ");
            }
            
            for (int y = 0; y < GRID_SIZE; y++) {
                System.out.print("[");
                if (plateau.caseLibre(new PositionTuiles(x, y))) {
                    System.out.print("  ");
                } else {
                    Tuile tuile = plateau.getTuile(new PositionTuiles(x, y));
                    System.out.printf("%-2s", tuile.toString()); 
                }
                if (y != GRID_SIZE - 1) {
                    System.out.print("]");
                } else {
                    System.out.println("]");
                }
            }

        }
    }
}



