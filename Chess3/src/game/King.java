package game;

import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class King extends Piece {
	private PieceManager pM;
	private boolean isChecked;
	private Handler handler;

	public King(int x, int y, ID id, PieceManager pM, Handler handler, Menu menu) {
		super(x, y, id, pM, handler, menu);
		this.pM = pM;
		this.isChecked = false;
		this.handler = handler;
		try {
			if (id == ID.Player1) {
				image = ImageIO.read(getClass().getResource("/game/images/Chess_klt60.png"));

			} else if (id == ID.Player2) {
				image = ImageIO.read(getClass().getResource("/game/images/Chess_kdt60.png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		pM.addKing(this);
	}

	@Override
	public void move(int newX, int newY) {
		int oX = x;
		pM.setBoardState(x, y, null);
		handler.removeObject(pM.getSquare(newX, newY));
		this.setX(newX);
		this.setY(newY);
		pM.setBoardState(newX, newY, Piece.selected[0]);
		if (Math.abs(newX - oX) >= 2) {
			if (newX == 6) {
				Piece rook = pM.getSquare(7, newY);
				pM.getSquare(7, newY).setX(5);
				pM.getSquare(7, newY).setY(newY);
				pM.setBoardState(7, newY, null);
				pM.setBoardState(5, newY, rook);
			} else if (newX == 2) {
				Piece rook = pM.getSquare(0, newY);
				pM.getSquare(0, newY).setX(3);
				pM.getSquare(0, newY).setY(newY);
				pM.setBoardState(0, newY, null);
				pM.setBoardState(3, newY, rook);
			}
		}
		Piece.selected[0] = null;
		hasMoved = true;
	}

	@Override
	public ArrayList<Integer> possibleMoves() {
		ArrayList<Integer> moves = new ArrayList<>();

		for (int possibleX = -1 + x; possibleX < 2 + x; possibleX++) {
			for (int possibleY = -1 + y; possibleY < 2 + y; possibleY++) {
				if (possibleX < 8 && possibleX > -1 && possibleY < 8 && possibleY > -1
						&& !(possibleX - x == 0 && possibleY - y == 0) && (pM.getSquare(possibleX, possibleY) == null
								|| pM.getSquare(possibleX, possibleY).id != this.id)) {
					if (this.checkCheck(possibleX, possibleY) == false) {
						moves.add(possibleX);
						moves.add(possibleY);
					}
				}
			}
		}
		if (hasMoved == false && pM.getSquare(7, this.getY()) != null && pM.getSquare(7, this.getY()).hasMoved == false
				&& pM.getSquare(6, this.getY()) == null && pM.getSquare(5, this.getY()) == null) {
			if (this.checkCheck(6, this.getY()) == false) {
				moves.add(6);
				moves.add(this.getY());
			}
		}
		if (hasMoved == false && pM.getSquare(0, this.getY()) != null && pM.getSquare(0, this.getY()).hasMoved == false
				&& pM.getSquare(1, this.getY()) == null && pM.getSquare(2, this.getY()) == null
				&& pM.getSquare(3, this.getY()) == null) {
			if (this.checkCheck(2, this.getY()) == false) {
				moves.add(2);
				moves.add(this.getY());
			}
		}

		return moves;
	}

}
