package game;

import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Queen extends Piece {
	private PieceManager pM;

	public Queen(int x, int y, ID id, PieceManager pM, Handler handler, Menu menu) {
		super(x, y, id, pM, handler, menu);
		this.pM = pM;
		try {
			if (id == ID.Player1) {
				image = ImageIO.read(getClass().getResource("/game/images/Chess_qlt60.png"));
			} else if (id == ID.Player2) {
				image = ImageIO.read(getClass().getResource("/game/images/Chess_qdt60.png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Integer> possibleMoves() {
		ArrayList<Integer> moves = new ArrayList<>();
		boolean isBlocked = false;
		int xSquare = this.getX();
		int ySquare = this.getY();

		for (int j = -1; j < 2; j = j + 2) {
			int i = 1;
			isBlocked = false;
			while (!isBlocked && xSquare + i * j < 8 && ySquare + i * j < 8 && ySquare + i * j >= 0
					&& xSquare + i * j >= 0) {
				if (pM.getSquare(xSquare + i * j, ySquare + i * j) == null) {
					if (this.checkCheck(xSquare + i * j, ySquare + i * j) == false) {
						moves.add(xSquare + i * j);
						moves.add(ySquare + i * j);
					}
				} else if (pM.getSquare(xSquare + i * j, ySquare + i * j).id != this.id) {
					if (this.checkCheck(xSquare + i * j, ySquare + i * j) == false) {
						moves.add(xSquare + i * j);
						moves.add(ySquare + i * j);
					}
					isBlocked = true;
				} else {
					isBlocked = true;
				}

				i++;
			}
			i = 1;
			isBlocked = false;
			while (!isBlocked && xSquare - i * j >= 0 && ySquare + i * j < 8 && ySquare + i * j >= 0
					&& xSquare - i * j < 8) {
				if (pM.getSquare(xSquare - i * j, ySquare + i * j) == null) {
					if (this.checkCheck(xSquare - i * j, ySquare + i * j) == false) {
						moves.add(xSquare - i * j);
						moves.add(ySquare + i * j);
					}
				} else if (pM.getSquare(xSquare - i * j, ySquare + i * j).id != this.id) {
					if (this.checkCheck(xSquare - i * j, ySquare + i * j) == false) {
						moves.add(xSquare - i * j);
						moves.add(ySquare + i * j);
					}
					isBlocked = true;
				} else {
					isBlocked = true;
				}
				i++;
			}
		}
		for (int j = -1; j < 2; j = j + 2) {
			isBlocked = false;
			int i = 1;
			while (!isBlocked && ySquare + i * j < 8 && ySquare + i * j >= 0) {
				if (pM.getSquare(xSquare, ySquare + i * j) == null) {
					if (this.checkCheck(xSquare, ySquare + i * j) == false) {
						moves.add(xSquare);
						moves.add(ySquare + i * j);
					}
				} else if (pM.getSquare(xSquare, ySquare + i * j).id != this.id) {
					if (this.checkCheck(xSquare, ySquare + i * j) == false) {
						moves.add(xSquare);
						moves.add(ySquare + i * j);
					}
					isBlocked = true;
				} else {
					isBlocked = true;
				}
				i++;
			}
			i = 1;
			isBlocked = false;
			while (!isBlocked && xSquare + i * j < 8 && xSquare + i * j >= 0) {
				if (pM.getSquare(xSquare + i * j, ySquare) == null) {
					if (this.checkCheck(xSquare + i * j, ySquare) == false) {
						moves.add(xSquare + i * j);
						moves.add(ySquare);
					}

				} else if (pM.getSquare(xSquare + i * j, ySquare).id != this.id) {
					if (this.checkCheck(xSquare + i * j, ySquare) == false) {
						moves.add(xSquare + i * j);
						moves.add(ySquare);
					}

					isBlocked = true;
				} else {
					isBlocked = true;
				}
				i++;
			}
		}
		return moves;
	}
}
