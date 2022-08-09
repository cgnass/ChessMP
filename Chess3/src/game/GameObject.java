package game;

import java.awt.Graphics;

public abstract class GameObject {
	//king 1, queen 2, rook 3, bishop 4, knight 5, pawn 6
	protected int x, y, piece;
	protected ID id;
	
	public GameObject(int x, int y) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.piece = piece;
	}
	
	
	public abstract void render(Graphics g);
	
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setId(ID id) {
		this.id = id;
	}
	public ID getId() {
		return id;
	}
}
