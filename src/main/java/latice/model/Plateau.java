package latice.model;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;



import java.util.List;

public class Plateau {
	
	private Map<PositionTuiles, Tuile > cases; 
	
	public Plateau() {
		this.cases = new HashMap<>();
	}
	
	public void placerTuile(Tuile tuile, PositionTuiles pos) {
		if (caseLibre(pos)) { 
			cases.put(pos,tuile) ;
        }
	}
        
   
	
	public Tuile getTuile(PositionTuiles pos) {
		return cases.get(pos);
	}
	
	public PositionTuiles getPosition() {
		return new PositionTuiles(0, 0);
		//TODO
	}
	
	public boolean caseIsSunStones(PositionTuiles pos) {
		List<PositionTuiles> listeDeCasesSoleils = Arrays.asList( //TODO Changer le type de List par HashSet pour réduire la compléxité
				new PositionTuiles(0,0),
				new PositionTuiles(0,4),
				new PositionTuiles(0,8),
				new PositionTuiles(1,1),
				new PositionTuiles(1,7),
				new PositionTuiles(2,2),
				new PositionTuiles(2,6),
				new PositionTuiles(4,0),
				new PositionTuiles(4,8),
				new PositionTuiles(6,2),
				new PositionTuiles(6,6),
				new PositionTuiles(7,1),
				new PositionTuiles(7,7),
				new PositionTuiles(8,0),
				new PositionTuiles(8,4),
				new PositionTuiles(8,8)
				); // TODO faire une classe ou mieux une énum qui stocke les postions des cases soleil et lunes
		
		return listeDeCasesSoleils.contains(pos);
		
}
	public boolean caseIsMoon(PositionTuiles pos) {
		PositionTuiles moonCase = new PositionTuiles(4,4);
		
		return pos.equals(moonCase);
		
		
	}
	
	public  boolean caseLibre( PositionTuiles pos) {
		return !cases.containsKey(pos);
       
	}
	
	
}
	
