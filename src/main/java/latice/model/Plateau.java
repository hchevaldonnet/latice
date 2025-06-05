package latice.model;

import java.util.HashMap;
import java.util.Map;

public class Plateau {
	
	private Map<PositionTuiles, Tuile > cases; 
	
	public Plateau(Map<PositionTuiles, Tuile> plateau) {
		this.setCases(new HashMap<>());
	}
	
	public void placerTuile(Tuile tuile, PositionTuiles pos) {
		if (caseLibre(pos)) { 
			getCases().put(pos,tuile) ;
        }
	}
        
   
	
	public Tuile getTuile(PositionTuiles pos) {
		return getCases().get(pos);
	}
	
	
	public  boolean caseLibre( PositionTuiles pos) {
		return !getCases().containsKey(pos);
       
	}

	public Map<PositionTuiles, Tuile > getCases() {
		return cases;
	}

	public void setCases(Map<PositionTuiles, Tuile > cases) {
		this.cases = cases;
	}
	
	
}
	
