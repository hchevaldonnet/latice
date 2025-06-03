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
	
    public boolean estUneCaseSoleil(int ligne, int col) {
        return (ligne == 0 && col == 0) || (ligne == 0 && col == 8) || (ligne == 0 && col == 4) ||
               (ligne == 1 && col == 1) || (ligne == 1 && col == 7) || (ligne == 2 && col == 2) ||
               (ligne == 2 && col == 6) || (ligne == 4 && col == 0) || (ligne == 4 && col == 8) ||
               (ligne == 6 && col == 2) || (ligne == 6 && col == 6) || (ligne == 7 && col == 1) || 
               (ligne == 7 && col == 7) || (ligne == 8 && col == 0) || (ligne == 8 && col == 4) || 
               (ligne == 8 && col == 8);
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
