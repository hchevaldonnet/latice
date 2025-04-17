package latice.application;

import java.util.Map;

import latice.application.PositionTuiles;
import latice.application.Tuile;

public class Plateau {
	private Map<Tuile, PositionTuiles> cases;
	
	public Plateau(Tuile tuile, PositionTuiles position) {
		//TODO
	}
	
	public boolean placerTuile(Tuile tuile, PositionTuiles pos) {
		return true;
		//TODO
	}
	
	public Tuile getTuile() {
		return new Tuile(null, null);
		//TODO
	}
	
	public PositionTuiles getPosition() {
		return new PositionTuiles(0, 0);
		//TODO
	}
}
