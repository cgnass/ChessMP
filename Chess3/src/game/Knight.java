package game;

import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Knight extends Piece {
	private PieceManager pM;

	public Knight(int x, int y, ID id, PieceManager pM, Handler handler, Menu menu) {
		super(x, y, id, pM, handler, menu);
		this.pM = pM;
		try {
			if (id == ID.Player1) {
				image = ImageIO.read(getClass().getResource("/game/images/Chess_nlt60.png"));
			} else if (id == ID.Player2) {
				image = ImageIO.read(getClass().getResource("/game/images/Chess_ndt60.png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Integer> possibleMoves() {
		ArrayList<Integer> moves = new ArrayList<>();
		for (int i = -1; i < 2; i = i + 2) {
			for (int j = -2; j < 5; j = j + 4) {
				if (this.getY() + j < 8 && this.getY() + j >= 0 && this.getX() + i >= 0 && this.getX() + i < 8
						&& (pM.getSquare(this.getX() + i, this.getY() + j) == null
						|| pM.getSquare(this.getX() + i, this.getY() + j).id != this.id)) {
					if (this.checkCheck(this.getX() + i, this.getY() + j) == false) {
						moves.add(this.getX() + i);
						moves.add(this.getY() + j);
					}
				}
				if (this.getY() + i < 8 && this.getY() + i >= 0 && this.getX() + j >= 0 && this.getX() + j < 8
						&& (pM.getSquare(this.getX() + j, this.getY() + i) == null
								|| pM.getSquare(this.getX() + j, this.getY() + i).id != this.id)) {
					if (this.checkCheck(this.getX() + j, this.getY() + i) == false) {
						moves.add(this.getX() + j);
						moves.add(this.getY() + i);
					}
				}
			}
		}
		return moves;
	}

}
