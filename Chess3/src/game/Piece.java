package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Piece extends GameObject {
	protected Image image;
	protected boolean hasMoved;
	static protected Piece[] selected = new Piece[1];
	private PieceManager pM;
	private Handler handler;
	static protected Piece[] canE;
	private boolean player1;
	private Menu menu;

	public Piece(int x, int y, ID id, PieceManager pM, Handler handler, Menu menu) {
		super(x, y);
		this.id = id;
		this.pM = pM;
		this.handler = handler;
		this.canE = new Piece[1];
		pM.setBoardState(x, y, this);
		this.menu = menu;
	}

	@Override
	public void render(Graphics g) {
		int x, y, possibleX, possibleY;
		if (menu.getSide() == true) {
			x = this.x;
			y = this.y;
		} else {
			x = Math.abs(this.x - 7);
			y = Math.abs(this.y - 7);
		}
		g.drawImage(image, Board.squares[0][x], Board.squares[1][y], null);
		if (Piece.selected[0] == this) {
			g.setColor(Color.RED);
			g.drawRect(Board.squares[0][x], Board.squares[1][y], Board.getSize(), Board.getSize());
			for (int i = 0; i < this.possibleMoves().size(); i = i + 2) {

				ArrayList<Integer> pMoves = this.possibleMoves();
				if (menu.getSide() == true) {
					possibleX = pMoves.get(i);
					possibleY = pMoves.get(i + 1);
				} else {
					possibleX = Math.abs(pMoves.get(i) - 7);
					possibleY = Math.abs(pMoves.get(i + 1) - 7);
				}
				g.setColor(Color.red);
				g.drawOval(Board.squares[0][possibleX], Board.squares[1][possibleY], Board.getSize(), Board.getSize());

			}
		}

	}

	public void move(int newX, int newY) {
		int oY = y;
		pM.setBoardState(x, y, null);
		handler.removeObject(pM.getSquare(newX, newY));
		this.setX(newX);
		this.setY(newY);
		pM.setBoardState(newX, newY, Piece.selected[0]);
		if (Math.abs(newY - oY) == 2 && this instanceof Pawn && hasMoved == false) {
			canE[0] = this;

		} else {
			canE[0] = null;
		}
		Piece.selected[0] = null;
		hasMoved = true;
	}

	public ArrayList<Integer> possibleMoves() {
		return null;
	}

	public boolean checkCheck(int possibleX, int possibleY) {
		if (this.pawnThreat(possibleX, possibleY)) {
			return true;
		}
		if (this.kingThreat(possibleX, possibleY)) {
			return true;
		}
		if (this.rookThreat(possibleX, possibleY)) {
			return true;
		}
		if (this.bishopThreat(possibleX, possibleY)) {
			return true;
		}
		if (this.knightThreat(possibleX, possibleY)) {
			return true;
		}
		return false;
	}

	private boolean pawnThreat(int possibleX, int possibleY) {
		int oX = this.getX();
		int oY = this.getY();
		King king;
		int kingX, kingY;
		Piece eaten = this.checkHelper(possibleX, possibleY);
		if (id == ID.Player1) {
			king = pM.getKing(0);
			kingX = king.getX();
			kingY = king.getY();
			for (int i = -1; i < 2; i = i + 2) {
				if (kingY - 1 > -1 && kingX + i > -1 && kingX + i < 8 && pM.getSquare(kingX + i, kingY - 1) != null
						&& pM.getSquare(kingX + i, kingY - 1) instanceof Pawn
						&& pM.getSquare(kingX + i, kingY - 1).id != king.id) {
					this.checkFinisher(oX, oY, eaten);
					return true;
				}
			}
		} else {
			king = pM.getKing(1);

			kingX = king.getX();
			kingY = king.getY();
			for (int i = -1; i < 2; i = i + 2) {
				if (kingY + 1 < 8 && kingX + i > -1 && kingX + i < 8 && pM.getSquare(kingX + i, kingY + 1) != null
						&& pM.getSquare(kingX + i, kingY + 1) instanceof Pawn
						&& pM.getSquare(kingX + i, kingY + 1).id != king.id) {
					this.checkFinisher(oX, oY, eaten);
					return true;
				}
			}
		}

		this.checkFinisher(oX, oY, eaten);
		return false;
	}

	private boolean kingThreat(int possibleX, int possibleY) {
		if (!(this instanceof King)) {
			return false;
		}

		int oX = this.getX();
		int oY = this.getY();
		Piece eaten = this.checkHelper(possibleX, possibleY);
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (possibleX + i > -1 && possibleX + i < 8 && possibleY + j > -1 && possibleY + j < 8) {
					if (pM.getSquare(possibleX + i, possibleY + j) != null
							&& pM.getSquare(possibleX + i, possibleY + j) instanceof King
							&& pM.getSquare(possibleX + i, possibleY + j).id != this.id) {
						this.checkFinisher(oX, oY, eaten);
						return true;
					}
				}
			}
		}
		this.checkFinisher(oX, oY, eaten);
		return false;

	}

	private boolean rookThreat(int possibleX, int possibleY) {
		King king = null;
		if (this.id == ID.Player1) {
			king = pM.getKing(0);
		} else {
			king = pM.getKing(1);
		}
		int oX = this.getX();
		int oY = this.getY();
		boolean isBlocked = false;
		Piece eaten = this.checkHelper(possibleX, possibleY);
		int kingX = king.getX();
		int kingY = king.getY();
		for (int i = -1; i < 2; i = i + 2) {
			isBlocked = false;
			int j = 1;
			while (!isBlocked && kingX + i * j > -1 && kingX + i * j < 8 && kingY > -1 && kingY < 8) {
				if (pM.getSquare(kingX + i * j, kingY) != null) {
					isBlocked = true;
					if (pM.getSquare(kingX + i * j, kingY).id != king.id
							&& (pM.getSquare(kingX + i * j, kingY) instanceof Rook
									|| pM.getSquare(kingX + i * j, kingY) instanceof Queen)) {
						this.checkFinisher(oX, oY, eaten);
						return true;
					}
				}
				j++;
			}
		}
		for (int i = -1; i < 2; i = i + 2) {
			isBlocked = false;
			int j = 1;
			while (!isBlocked && kingX > -1 && kingX < 8 && kingY + i * j > -1 && kingY + i * j < 8) {

				if (pM.getSquare(kingX, kingY + i * j) != null) {
					isBlocked = true;
					if (pM.getSquare(kingX, kingY + i * j).id != king.id
							&& (pM.getSquare(kingX, kingY + i * j) instanceof Rook
									|| pM.getSquare(kingX, kingY + i * j) instanceof Queen)) {
						this.checkFinisher(oX, oY, eaten);
						return true;
					}
				}
				j++;
			}

		}
		this.checkFinisher(oX, oY, eaten);
		return false;
	}

	private boolean bishopThreat(int possibleX, int possibleY) {
		int oX = this.getX();
		int oY = this.getY();
		Piece eaten = this.checkHelper(possibleX, possibleY);
		King king = null;
		if (this.id == ID.Player1) {
			king = pM.getKing(0);
		} else {
			king = pM.getKing(1);
		}

		boolean isBlocked = false;
		int kingX = king.getX();
		int kingY = king.getY();
		int j;

		for (int i = -1; i < 2; i = i + 2) {
			isBlocked = false;
			j = 1;
			while (!isBlocked && kingX + i * j > -1 && kingX + i * j < 8 && kingY + i * j > -1 && kingY + i * j < 8) {

				if (pM.getSquare(kingX + i * j, kingY + i * j) != null) {
					isBlocked = true;

					if (pM.getSquare(kingX + i * j, kingY + i * j).id != king.id
							&& (pM.getSquare(kingX + i * j, kingY + i * j) instanceof Bishop
									|| pM.getSquare(kingX + i * j, kingY + i * j) instanceof Queen)) {
						this.checkFinisher(oX, oY, eaten);
						return true;
					}
				}
				j++;
			}

		}
		for (int i = -1; i < 2; i = i + 2) {
			isBlocked = false;
			j = 1;
			while (!isBlocked && kingX - i * j > -1 && kingX - i * j < 8 && kingY + i * j > -1 && kingY + i * j < 8) {
				if (pM.getSquare(kingX - i * j, kingY + i * j) != null) {
					isBlocked = true;

					if (pM.getSquare(kingX - i * j, kingY + i * j).id != king.id
							&& (pM.getSquare(kingX - i * j, kingY + i * j) instanceof Bishop
									|| pM.getSquare(kingX - i * j, kingY + i * j) instanceof Queen)) {
						this.checkFinisher(oX, oY, eaten);
						return true;
					}
				}
				j++;
			}

		}

		this.checkFinisher(oX, oY, eaten);
		return false;
	}

	private boolean knightThreat(int possibleX, int possibleY) {
		int oX = this.getX();
		int oY = this.getY();
		Piece eaten = this.checkHelper(possibleX, possibleY);
		King king = null;
		if (this.id == ID.Player1) {
			king = pM.getKing(0);
		} else {
			king = pM.getKing(1);
		}
		int kingX = king.getX();
		int kingY = king.getY();
		for (int i = -1; i < 2; i = i + 2) {
			for (int j = -2; j < 5; j = j + 4) {
				if (kingY + j < 8 && kingY + j >= 0 && kingX + i >= 0 && kingX + i < 8
						&& pM.getSquare(kingX + i, kingY + j) != null
						&& pM.getSquare(kingX + i, kingY + j).id != this.id
						&& pM.getSquare(kingX + i, kingY + j) instanceof Knight) {
					this.checkFinisher(oX, oY, eaten);
					return true;
				}
				if (kingY + i < 8 && kingY + i >= 0 && kingX + j >= 0 && kingX + j < 8
						&& pM.getSquare(kingX + j, kingY + i) != null
						&& pM.getSquare(kingX + j, kingY + i).id != this.id
						&& pM.getSquare(kingX + j, kingY + i) instanceof Knight) {
					this.checkFinisher(oX, oY, eaten);
					return true;
				}
			}
		}

		this.checkFinisher(oX, oY, eaten);
		return false;
	}

	private Piece checkHelper(int possibleX, int possibleY) {
		Piece eaten = null;
		pM.setBoardState(x, y, null);
		if (pM.getSquare(possibleX, possibleY) != null) {
			eaten = pM.getSquare(possibleX, possibleY);
		}
		this.setX(possibleX);
		this.setY(possibleY);
		pM.setBoardState(possibleX, possibleY, this);
		return eaten;
	}

	private void checkFinisher(int oX, int oY, Piece eaten) {
		pM.setBoardState(x, y, eaten);
		this.setX(oX);
		this.setY(oY);
		pM.setBoardState(oX, oY, this);
	}

}
