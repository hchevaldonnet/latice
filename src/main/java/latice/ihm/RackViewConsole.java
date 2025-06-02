package latice.ihm;

import latice.model.Rack;
import latice.model.Tuile;

public class RackViewConsole implements RackView {
    
    @Override
    public void afficherRack(Rack rack) {
        System.out.println("Votre rack contient :");
        
        if (rack.getTuiles().isEmpty()) {
            System.out.println("Votre rack est vide.");
            return;
        }
        
        for (int i = 0; i < rack.getTuiles().size(); i++) {
            Tuile tuile = rack.getTuiles().get(i);
            System.out.println((i+1) + ". " + tuile.toString());
        }
        System.out.println();
    }
}
