package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import game.Driver.STATE;

public class Menu extends MouseAdapter {
	private boolean isSideSelected;
	private boolean isWhite;
	private int time;
	private boolean isTimeSelected;
	private Driver game;
	private Handler handler;
	private PieceManager pM;
	private Clock clock1, clock2;

	public Menu(Driver game, Handler handler, PieceManager pM) {
		this.game = game;
		this.handler = handler;
		this.pM = pM;
	}

	public void mousePressed(MouseEvent e) {
		if (game.gameState == STATE.Menu) {
			int x = e.getX();
			int y = e.getY();
			// black button
			if (x < 617 && x > 373 && y > 253 && y < 497) {
				isSideSelected = true;
				isWhite = false;
			}
			// white button
			if (x < 347 && x > 103 && y > 253 && y < 497) {
				isSideSelected = true;
				isWhite = true;

			}

			// 1:00 button
			if (x < 848 && x > 702 && y > 253 && y < 346) {
				time = 60;
				isTimeSelected = true;
			} else if (x < 1018 && x > 872 && y > 253 && y < 346) {
				time = 180;
				isTimeSelected = true;
			} else if (x < 1188 && x > 1042 && y > 253 && y < 346) {
				time = 300;
				isTimeSelected = true;
			} else if (x < 848 && x > 702 && y > 374 && y < 467) {
				time = 600;
				isTimeSelected = true;
			} else if (x < 1018 && x > 872 && y > 374 && y < 467) {
				time = 1800;
				isTimeSelected = true;
			} else if (x < 1188 && x > 1042 && y > 374 && y < 467) {
				time = -1;
				isTimeSelected = true;
			}

			if (x > 977 && 1192 > x && y > 502 && y < 598) {
				if (isTimeSelected == true && isSideSelected == true) {
					pM = new PieceManager();
					clock1 = null;
					clock2 = null;
					if (time != -1) {
						clock1 = new Clock(10, 50, time, ID.Player1);
						clock2 = new Clock(1000, 50, time, ID.Player2);
						handler.addObject(clock1);
						handler.addObject(clock2);
						clock1.start();
						clock2.start();
					}
					game.addMouseListener(new MouseInput(pM, this));
					game.addKeyListener(new KeyInput(handler));

					handler.addObject(new King(4, 7, ID.Player1, pM, handler, this));
					handler.addObject(new King(4, 0, ID.Player2, pM, handler, this));
					handler.addObject(new Queen(3, 7, ID.Player1, pM, handler, this));
					handler.addObject(new Queen(3, 0, ID.Player2, pM, handler, this));
					handler.addObject(new Rook(7, 0, ID.Player2, pM, handler, this));
					handler.addObject(new Rook(0, 0, ID.Player2, pM, handler, this));
					handler.addObject(new Rook(0, 7, ID.Player1, pM, handler, this));
					handler.addObject(new Rook(7, 7, ID.Player1, pM, handler, this));
					handler.addObject(new Bishop(2, 7, ID.Player1, pM, handler, this));
					handler.addObject(new Bishop(5, 7, ID.Player1, pM, handler, this));
					handler.addObject(new Bishop(2, 0, ID.Player2, pM, handler, this));
					handler.addObject(new Bishop(5, 0, ID.Player2, pM, handler, this));
					handler.addObject(new Knight(1, 0, ID.Player2, pM, handler, this));
					handler.addObject(new Knight(1, 7, ID.Player1, pM, handler, this));
					handler.addObject(new Knight(6, 7, ID.Player1, pM, handler, this));
					handler.addObject(new Knight(6, 0, ID.Player2, pM, handler, this));
					for (int i = 0; i < 8; i++) {
						handler.addObject(new Pawn(i, 6, ID.Player1, pM, handler, this));
					}
					for (int i = 0; i < 8; i++) {
						handler.addObject(new Pawn(i, 1, ID.Player2, pM, handler, this));
					}
					game.gameState = STATE.Game;
				}
			}
		}
	}

	public void render(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		g.setFont(new Font("Arial Black", Font.CENTER_BASELINE, 70));
		g.setColor(Color.black);
		g.drawString("Chess", 500, 120);

		// Side selection
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 20));
		g.drawString("Choose Side", 300, 200);
		g.fillRect(370, 250, 250, 250);

		g2.setStroke(new BasicStroke(7));
		if (isSideSelected == false) {
			g.setColor(Color.DARK_GRAY);
			g.drawRect(100, 250, 250, 250);
			g.drawRect(370, 250, 250, 250);
		} else if (isWhite == true) {
			g.setColor(Color.blue);
			g.drawRect(100, 250, 250, 250);
			g.setColor(Color.DARK_GRAY);
			g.drawRect(370, 250, 250, 250);
		} else {
			g.setColor(Color.blue);
			g.drawRect(370, 250, 250, 250);

			g.setColor(Color.DARK_GRAY);
			g.drawRect(100, 250, 250, 250);

		}

		g.setColor(Color.LIGHT_GRAY);
		g.drawString("Black", 470, 310);
		g.setColor(Color.DARK_GRAY);
		g.drawString("White", 200, 310);

		// Time selection
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 20));
		g.drawString("Choose Time Limit", 850, 200);

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(700, 250, 150, 100);
		g.fillRect(870, 250, 150, 100);
		g.fillRect(1040, 250, 150, 100);
		g.fillRect(700, 370, 150, 100);
		g.fillRect(870, 370, 150, 100);
		g.fillRect(1040, 370, 150, 100);

		g.setColor(Color.DARK_GRAY);
		g.drawRect(700, 250, 150, 100);
		g.drawRect(870, 250, 150, 100);
		g.drawRect(1040, 250, 150, 100);
		g.drawRect(700, 370, 150, 100);
		g.drawRect(870, 370, 150, 100);
		g.drawRect(1040, 370, 150, 100);
		if (time != 0) {
			g.setColor(Color.blue);
			if (time == 60) {
				g.drawRect(700, 250, 150, 100);
			} else if (time == 180) {
				g.drawRect(870, 250, 150, 100);
			} else if (time == 300) {
				g.drawRect(1040, 250, 150, 100);
			} else if (time == 600) {
				g.drawRect(700, 370, 150, 100);
			} else if (time == 1800) {
				g.drawRect(870, 370, 150, 100);
			} else {
				g.drawRect(1040, 370, 150, 100);
			}
		}

		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 30));
		g.drawString("1:00", 745, 310);
		g.drawString("3:00", 915, 310);
		g.drawString("5:00", 1085, 310);
		g.drawString("10:00", 740, 430);
		g.drawString("30:00", 910, 430);
		g.drawString("No Time", 1057, 430);

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(975, 500, 220, 100);
		g.setColor(Color.DARK_GRAY);
		g.drawRect(975, 500, 220, 100);
		g.setColor(Color.black);
		g.drawString("Start/Ready", 1002, 560);

	}

	public int getTime() {
		return time;
	}

	public boolean getSide() {
		return isWhite;
	}

	public Clock getClock1() {
		return clock1;
	}

	public Clock getClock2() {
		return clock2;
	}

}
