package latice.model;

import java.util.Objects;

public class PositionTuiles {
	private int x; //Lignes
	private int y; //Colonnes
	
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
	
    public boolean isSunTile(int row, int col) {
        return (row == 0 && col == 0) || (row == 0 && col == 8) || (row == 0 && col == 4) ||
               (row == 1 && col == 1) || (row == 1 && col == 7) || (row == 2 && col == 2) ||
               (row == 2 && col == 6) || (row == 4 && col == 0) || (row == 4 && col == 8) ||
               (row == 6 && col == 2) || (row == 6 && col == 6) || (row == 7 && col == 1) || 
               (row == 7 && col == 7) || (row == 8 && col == 0) || (row == 8 && col == 4) || 
               (row == 8 && col == 8);
    }

    public boolean isMoonTile(int row, int col) {
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
