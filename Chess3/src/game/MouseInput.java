package game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MouseInput extends MouseAdapter {
	static private ID turn;
	private int size;
	private PieceManager pM;
	private boolean isPromoting;
	private static boolean hasMoved;
	private Menu menu;
	public MouseInput(PieceManager pM, Menu menu) {
		turn = ID.Player1;
		size = Board.getSize();
		this.pM = pM;
		hasMoved = false;
		this.menu = menu;
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(menu.getClock1()!=null&&menu.getClock2()!=null&&(menu.getClock1().isGameOver()||menu.getClock2().isGameOver())) {
			turn=null;
			return;
		}
		int x, y;
		if(menu.getSide()==true) {
			x = mouseOnBoardX(e.getX());
			y = mouseOnBoardY(e.getY());
		} else {
			x = Math.abs(mouseOnBoardX(e.getX())-7);
			y = Math.abs(mouseOnBoardY(e.getY())-7);
		}
		
		if(isPromoting == true) {
			Pawn pawn = (Pawn)Piece.selected[0];
			if(x>0&&x<5) {
			pawn.promotion(x);
			isPromoting = false;
			this.switchTurns();
			}
			return;
		}
		ArrayList<Integer> possibleMoves = null;
		if(x>-1&&x<8&&y>-1&&y<8) {
			
			if(pM.getSquare(x, y)!=null&&pM.getSquare(x, y).id==turn) {	
				
				Piece.selected[0] = pM.getSquare(x, y);
				return;
				
			}
			//Piece is in hand and player is clicking a move
			if(Piece.selected[0]!=null) {
				possibleMoves = Piece.selected[0].possibleMoves();
				for(int i = 0; i < possibleMoves.size(); i=i+2) {
					if(possibleMoves.get(i)==x&&possibleMoves.get(i+1)==y) {
						Piece.selected[0].move(x, y);
						hasMoved = true;
						if((y==0||y==7)&&Piece.selected[0] instanceof Pawn) {
							Pawn pawn = (Pawn)Piece.selected[0];
							if(pawn.promotion==true) {
								this.isPromoting = true;
								return;
							}
						}
						this.switchTurns();
						
					}
				}
			}
		}
		
	
		
	}

	private int mouseOnBoardX(int mouseX) {
		if (isPromoting) {
			if (mouseX < Board.squares[0][Piece.selected[0].getX()]
					|| mouseX > Board.squares[0][Piece.selected[0].getX()] + size * 4) {
				return -1;
			}
			for (int i = 1; i <= 4; i++) {
				if (mouseX < Board.squares[0][Piece.selected[0].getX()] + size * i) {
					return i;
				}
			}
		}
		for (int i = 0; i < Board.squares[0].length; i++) {
			if (mouseX < Board.squares[0][i] + size && mouseX > Board.squares[0][0]) {
				return i;
			}
		}
		return -1;
	}

	private int mouseOnBoardY(int mouseY) {
		if (isPromoting) {
			if (turn == ID.Player1 && Board.squares[1][0] - Board.getSize() < mouseY && Board.squares[1][0] > mouseY) {
				return 1;
			} else if (turn == ID.Player2 && Board.squares[1][7] + Board.getSize() < mouseY
					&& Board.squares[1][7] + Board.getSize() * 2 > mouseY) {
				return 1;
			}
			return -1;
		}
		for (int i = 0; i < Board.squares[1].length; i++) {
			if (mouseY < Board.squares[1][i] + size && mouseY > Board.squares[1][0]) {
				return i;
			}
		}
		return -1;
	}

	private void switchTurns() {
		if (turn == ID.Player1) {
			turn = ID.Player2;
		} else {
			turn = ID.Player1;
		}
	}
	
	static public ID getTurn() {
		return turn;
	}
	
	static public boolean hasMoved() {
		return hasMoved;
	}
		
	}

	
	


