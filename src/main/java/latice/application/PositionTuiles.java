package latice.application;

import java.util.Objects;

public class PositionTuiles {
	private int x; //Lignes
	private int y; //Colonnes
	
	public PositionTuiles(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return -1;
		//TODO
	}
	
	public int getY() {
		return -1;
		//TODO
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PositionTuiles that = (PositionTuiles) obj;
        return x == that.x && y == that.y;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
