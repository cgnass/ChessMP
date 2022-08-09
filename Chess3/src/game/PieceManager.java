package game;

public class PieceManager {
	private Piece[][] boardState = new Piece[8][8];
	private King[] kings = new King[2];
	public void addKing(King king) {
		if(king.id==ID.Player1) {
			kings[0] = king;
		} else {
			kings[1] = king;
		}
	}
	
	public Piece[][] getBoardState() {
		return boardState;
	}
	
	public Piece getSquare(int x, int y) {
		return boardState[x][y];
	}
	
	public void setBoardState(int x, int y, Piece piece) {
		boardState[x][y] = piece;
	}
	
	public King getKing(int i) {
		return kings[i];
	}
	
}
