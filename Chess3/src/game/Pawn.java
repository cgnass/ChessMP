package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Pawn extends Piece {
	private PieceManager pM;
	private boolean canE;
	private Handler handler;
	protected boolean promotion;
	private Menu menu;
	public Pawn(int x, int y, ID id, PieceManager pM, Handler handler, Menu menu) {
		super(x, y, id, pM, handler, menu);
		this.handler = handler;
		this.pM = pM;
		canE = false;
		promotion = false;
		this.menu = menu;
		try {
			if (id == ID.Player1) {
				image = ImageIO.read(getClass().getResource("/game/images/Chess_plt60.png"));
			} else if (id == ID.Player2) {
				image = ImageIO.read(getClass().getResource("/game/images/Chess_pdt60.png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void move(int newX, int newY) {
		int oY = y;
		int eY = 0;
		pM.setBoardState(x, y, null);
		handler.removeObject(pM.getSquare(newX, newY));
		this.setX(newX);
		this.setY(newY);
		pM.setBoardState(newX, newY, Piece.selected[0]);
		if (Math.abs(newY - oY) == 2 && this instanceof Pawn && hasMoved == false) {
			Piece.canE[0] = this;

		} else {
			Piece.canE[0] = null;
		}
		if (this.id == ID.Player1) {
			eY = 1;
		} else {
			eY = -1;
		}
		if (this.getY() == 0||this.getY() ==7) {
			promotion = true;
		}
		if (this instanceof Pawn && pM.getSquare(x, this.getY() + eY) != null
				&& pM.getSquare(x, this.getY() + eY) instanceof Pawn
				&& pM.getSquare(x, this.getY() + eY).id != this.id) {

			handler.removeObject(pM.getSquare(x, this.getY() + eY));
			pM.setBoardState(x, this.getY() + eY, null);

		}
		if(promotion==false) {
		Piece.selected[0] = null;
		}
		hasMoved = true;
	}

	@Override
	public ArrayList<Integer> possibleMoves() {
		ArrayList<Integer> moves = new ArrayList<>();
		int possibleY = 0;
		int possibleY2 = 0;
		if (this.id == ID.Player1) {
			possibleY = y - 1;
		} else {
			possibleY = y + 1;
		}
		if (possibleY < 8 && possibleY > -1 && pM.getSquare(x, possibleY) == null) {
			if (this.checkCheck(x, possibleY) == false) {
				moves.add(x);
				moves.add(possibleY);
			}
		}
		for (int i = -1; i < 2; i = i + 2) {
			if (this.getX() + i > -1 && this.getX() + i < 8 && possibleY < 8 && possibleY > -1
					&& pM.getSquare(this.getX() + i, possibleY) != null
					&& pM.getSquare(this.getX() + i, possibleY).id != this.id) {
				if (this.checkCheck(x, possibleY) == false) {
					moves.add(this.getX() + i);
					moves.add(possibleY);
				}
			}
		}
		for (int i = -1; i < 8; i++) {
			if (this.getX() + i < 8 && this.getX() + i > -1 && pM.getSquare(this.getX() + i, y) != null
					&& pM.getSquare(this.getX() + i, y) == Piece.canE[0]) {
				if (this.checkCheck(this.getX() + i, possibleY) == false) {
					moves.add(this.getX() + i);
					moves.add(possibleY);
				}
			}
		}
		if (hasMoved == false) {
			if (this.id == ID.Player1) {
				possibleY2 = y - 2;
			} else {
				possibleY2 = y + 2;
			}
			if (pM.getSquare(x, possibleY)==null&&this.checkCheck(x, possibleY2) == false) {
				moves.add(x);
				moves.add(possibleY2);
			}
		}

		return moves;
	}

	public void promotion(int x) {
		if (x == 1) {
			Queen queen = new Queen(Piece.selected[0].getX(), Piece.selected[0].getY(), this.id, pM, handler, menu);
			pM.setBoardState(queen.getX(), queen.getY(), queen);
			handler.addObject(queen);
			handler.removeObject(Piece.selected[0]);
		} else if (x == 2) {
			Rook rook = new Rook(Piece.selected[0].getX(), Piece.selected[0].getY(), this.id, pM, handler, menu);
			pM.setBoardState(rook.getX(), rook.getY(), rook);
			handler.addObject(rook);
			handler.removeObject(Piece.selected[0]);
			
		} else if (x == 3) {
			Bishop bishop = new Bishop(Piece.selected[0].getX(), Piece.selected[0].getY(), this.id, pM, handler, menu);
			pM.setBoardState(bishop.getX(), bishop.getY(), bishop);
			handler.addObject(bishop);
			handler.removeObject(Piece.selected[0]);
			
		} else if (x == 4) {
			Knight knight = new Knight(Piece.selected[0].getX(), Piece.selected[0].getY(), this.id, pM, handler, menu);
			pM.setBoardState(knight.getX(), knight.getY(), knight);
			handler.addObject(knight);
			handler.removeObject(Piece.selected[0]);
		}
		Piece.selected[0] = null;
		this.promotion = false;
	}
	@Override
	public void render(Graphics g) {
		int x, y, possibleX, possibleY;
		if(menu.getSide()==true) {
			x = this.x;
			y = this.y;
		} else {
			x = Math.abs(this.x-7);
			y = Math.abs(this.y-7);
		}
		g.drawImage(image, Board.squares[0][x], Board.squares[1][y], null);
		Image image1 = null, image2 = null, image3 = null, image4 = null, image5 = null, image6 = null, image7 = null, image8 = null;
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
if(ID.Player1==this.id&&y==0) {
			
			try {
				image1 = ImageIO.read(getClass().getResource("/game/images/Chess_qlt60.png"));
				image2 = ImageIO.read(getClass().getResource("/game/images/Chess_rlt60.png"));
				image3 = ImageIO.read(getClass().getResource("/game/images/Chess_blt60.png"));
				image4 = ImageIO.read(getClass().getResource("/game/images/Chess_nlt60.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.setColor(Color.GRAY);
			g.fillRect(Board.squares[0][x], Board.squares[1][y]- Board.getSize(), 4*Board.getSize(), Board.getSize());
			g.drawImage(image1, Board.squares[0][x], Board.squares[1][y] - Board.getSize(), null);
			g.drawImage(image2, Board.squares[0][x]+Board.getSize(), Board.squares[1][y] - Board.getSize(), null);
			g.drawImage(image3, Board.squares[0][x]+2*Board.getSize(), Board.squares[1][y] - Board.getSize(), null);
			g.drawImage(image4, Board.squares[0][x]+3*Board.getSize(), Board.squares[1][y] - Board.getSize(), null);
		} else if(ID.Player2==this.id&&y==7) {
			try {
				image5 = ImageIO.read(getClass().getResource("/game/images/Chess_qdt60.png"));
				image6 = ImageIO.read(getClass().getResource("/game/images/Chess_rdt60.png"));
				image7 = ImageIO.read(getClass().getResource("/game/images/Chess_bdt60.png"));
				image8 = ImageIO.read(getClass().getResource("/game/images/Chess_ndt60.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.setColor(Color.GRAY);
			g.fillRect(Board.squares[0][x], Board.squares[1][y]+Board.getSize(), 4*Board.getSize(), Driver.WIDTH);
			g.drawImage(image5, Board.squares[0][x], Board.squares[1][y] + Board.getSize(), null);
			g.drawImage(image6, Board.squares[0][x+1], Board.squares[1][y] + Board.getSize(), null);
			g.drawImage(image7, Board.squares[0][x+2], Board.squares[1][y] + Board.getSize(), null);
			g.drawImage(image8, Board.squares[0][x+3], Board.squares[1][y] + Board.getSize(), null);
		}

	}

}
