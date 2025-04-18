package latice.application;

import java.util.HashMap;
import java.util.Map;

import latice.application.PositionTuiles;
import latice.application.Tuile;

public class Plateau {
	
	private Map<Tuile, PositionTuiles> cases;
	private static final Integer nbSunStonesTotal = 16;
	
	public Plateau(Tuile tuile, PositionTuiles position) {
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
	
	public boolean caseIsSunStones(Map<Tuile, PositionTuiles> cases) {
		if (cases.get(getTuile()).getX() == 1 ) //TODO
	}
}
