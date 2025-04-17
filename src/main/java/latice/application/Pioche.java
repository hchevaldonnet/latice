package latice.application;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Pioche {
    private Stack<Tuile> tuiles;

    public Pioche(List<Tuile> listeTuiles) {
        Collections.shuffle(listeTuiles);
        this.tuiles = new Stack<>();
        this.tuiles.addAll(listeTuiles);
    }

    public Tuile piocher() {
    	if (tuiles.isEmpty()) {
    		return null;
    	} else {
    		return tuiles.pop();
    	}
    }

    public boolean estVide() {
    	if (tuiles.isEmpty()) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public int taille() {
    	return tuiles.size();
    }
}
