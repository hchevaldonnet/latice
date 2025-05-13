package latice.ihm;

import latice.model.Plateau;
import latice.model.PositionTuiles;
import latice.model.Tuile;

public class PlateauViewConsole implements PlateauView {
    @Override
    public void afficherPlateau(Plateau plateau) {
        for (int x=0; x<8; x++) {
        	for (int y=0; y < 8; y++) {
        		System.out.print("[");
        		if (plateau.caseLibre(new PositionTuiles(x,y))){
        			System.out.print("  "); 
        		}
        		else {
        			Tuile tuile = plateau.getTuile(new PositionTuiles(x,y));
        			System.out.print(tuile.toString()); 	
        			}
        		if (y != 7) {
        			System.out.print("]");	
        		}
        		else {
        			System.out.println("]");	
        		}
        		
        		
        		
        	}
        }
 }
}
	

