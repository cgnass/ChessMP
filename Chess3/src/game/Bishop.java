package game;

import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Bishop extends Piece {
	private PieceManager pM;

	public Bishop(int x, int y, ID id, PieceManager pM, Handler handler, Menu menu) {
		super(x, y, id, pM, handler, menu);
		this.pM = pM;
		try {
			if (id == ID.Player1) {
				image = ImageIO.read(getClass().getResource("/game/images/Chess_blt60.png"));
			} else if (id == ID.Player2) {
				image = ImageIO.read(getClass().getResource("/game/images/Chess_bdt60.png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Integer> possibleMoves() {
		ArrayList<Integer> possibleMoves = new ArrayList<>();
		boolean isBlocked = false;
		int xPos = this.getX();
		int yPos = this.getY();
		int potentialX;
		int potentialY;

		for (int j = -1; j < 2; j = j + 2) {
			int i = 1;
			isBlocked = false;
			while (!isBlocked && xPos + i * j < 8 && yPos + i * j < 8 && yPos + i * j >= 0 && xPos + i * j >= 0) {
				if (pM.getSquare(xPos + i * j, yPos + i * j) == null) {
					if (this.checkCheck(xPos + i * j, yPos + i * j) == false) {
						possibleMoves.add(xPos + i * j);
						possibleMoves.add(yPos + i * j);
					}
				} else if (pM.getSquare(xPos + i * j, yPos + i * j).id != this.id) {
					if (this.checkCheck(xPos + i * j, yPos + i * j) == false) {
						possibleMoves.add(xPos + i * j);
						possibleMoves.add(yPos + i * j);
					}
					isBlocked = true;
				} else {
					isBlocked = true;
				}

				i++;
			}
			i = 1;
			isBlocked = false;
			while (!isBlocked && xPos - i * j >= 0 && yPos + i * j < 8 && yPos + i * j >= 0 && xPos - i * j < 8) {
				if (pM.getSquare(xPos - i * j, yPos + i * j) == null) {
					if (this.checkCheck(xPos - i * j, yPos + i * j) == false) {
						possibleMoves.add(xPos - i * j);
						possibleMoves.add(yPos + i * j);
					}
				} else if (pM.getSquare(xPos - i * j, yPos + i * j).id != this.id) {
					if (this.checkCheck(xPos - i * j, yPos + i * j) == false) {
						possibleMoves.add(xPos - i * j);
						possibleMoves.add(yPos + i * j);
					}
					isBlocked = true;
				} else {
					isBlocked = true;
				}
				i++;
			}

		}
		return possibleMoves;
	}
}
