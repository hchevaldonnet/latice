package latice.model;

public enum PositionCaseSoleil {
    POS_0_0(0, 0),
    POS_0_4(0, 4),
    POS_0_8(0, 8),
    POS_1_1(1, 1),
    POS_1_7(1, 7),
    POS_2_2(2, 2),
    POS_2_6(2, 6),
    POS_4_0(4, 0),
    POS_4_8(4, 8),
    POS_6_2(6, 2),
    POS_6_6(6, 6),
    POS_7_1(7, 1),
    POS_7_7(7, 7),
    POS_8_0(8, 0),
    POS_8_4(8, 4),
    POS_8_8(8, 8);

    private final PositionTuiles pos;

    PositionCaseSoleil(int x, int y) {
        this.pos = new PositionTuiles(x, y);
    }

    public int getX() {
        return pos.getX();    
    }

    public int getY() {
        return pos.getY();
    }
    
    public PositionTuiles getPosition() {
        return new PositionTuiles(pos.getX(), pos.getY());
    }
    
    public static boolean estUneCaseSoleil(int x, int y) {
        for (PositionCaseSoleil position : values()) {
            if (position.getX() == x && position.getY() == y) {
                return true;
            }
        }
        return false;
    }
}

