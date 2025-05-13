package latice.model;

public enum PositionCaseSoleilLune {
	Pos0_0(0,0),
	Pos0_4(0,4),
	Pos0_8(0,8),
	Pos1_1(1,1),
	Pos1_7(1,7),
	Pos2_2(2,2),
	Pos2_6(2,6),
	Pos4_0(4,0),
	Pos4_8(4,8),
	Pos6_2(6,2),
	Pos6_6(6,6),
	Pos7_1(7,1),
	Pos7_7(7,7),
	Pos8_0(8,0),
	Pos8_4(8,4),
	Pos8_8(8,8),
	Pos4_4(4,4);

private final PositionTuiles pos;

PositionCaseSoleilLune(int x, int y) {
    this.pos = new PositionTuiles(x, y);
	}
}
