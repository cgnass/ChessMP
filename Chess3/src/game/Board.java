package game;

import java.awt.Color; 
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

public class Board {
	private final int WIDTH;
	private final int HEIGHT;
	private static int size;
	private int start, tileX, tileY;
	protected static int[][] squares = new int[2][8];
	
	public Board() {
		WIDTH = Driver.WIDTH;
		HEIGHT = Driver.HEIGHT;
		size = WIDTH/20;
		start = WIDTH/2 - size*4;
		
		tileX = WIDTH/2 - size*4;
		tileY= HEIGHT/2 - size*4;
		int tX = tileX;
		int tY = tileY;
		for(int i = 0; i < 8; i++) {
			squares[0][i] = tX;
			tX += size;
			squares[1][i] = tY;
			tY += size;
		}
		
	}

	public void render(Graphics g) {
		int tX = tileX;
		int tY = tileY;
		int tileColor = 1;
		int xIndex = 0;
		int yIndex = 0;
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(tileColor%2 == 0) {
				g.setColor(Color.DARK_GRAY);
				} else {
				g.setColor(Color.LIGHT_GRAY);
				}
				tileColor++;
				g.fillRect(tX, tY, size, size);
				tX += size;
				
			
			}
			tileColor++;
			tY += size;
			tX = start;
		}	
		
	}
	
	
	static public int getSize() {
		return size;
	}
	
	static String hash(int x, int y) {
		return x+""+y;
	}
	
}
