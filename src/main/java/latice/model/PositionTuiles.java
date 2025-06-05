package latice.model;

import java.util.Objects;

public class PositionTuiles {
	private int x;
	private int y; 
	
	public PositionTuiles(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

    public boolean estUneCaseLune(int row, int col) {
        return (row == 4 && col == 4);
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
