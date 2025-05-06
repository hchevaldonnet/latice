package latice.application;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import latice.application.PositionTuiles;
import latice.application.Tuile;

public class Plateau {
	
	private Map<Tuile, PositionTuiles> cases;
	private static final Integer nbSunStonesTotal = 16; //A peut-être supprimer
	
	public Plateau() {
		this.cases = new HashMap<>();
	}
	
	public boolean placerTuile(Tuile tuile, PositionTuiles pos) {
		if (cases.containsValue(pos)) {
            return false;
        }
        cases.put(tuile, pos);
        return true;
       }
	
	public Tuile getTuile() {
		return new Tuile(null, null);
		//TODO
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
				);
		
		return listeDeCasesSoleils.contains(pos);
	
}
	
}
