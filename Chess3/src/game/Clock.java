package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

public class Clock extends GameObject{
	private int timeRemaining;
	ID id;
	private boolean gameOver;
	public Clock(int x, int y, int timeRemaining, ID id) {
		super(x, y);
		this.timeRemaining = timeRemaining;
		this.id = id;
		gameOver = false;
	}
	
	
	Timer timer = new Timer();
	TimerTask task = new TimerTask() {
		

		public void run() {
			if(timeRemaining==0) {
				gameOver = true;
			} else if(MouseInput.hasMoved()==true&&Clock.this.id==MouseInput.getTurn()) {
			timeRemaining--;
			}
			
		}
	};
	public void start() {
		timer.scheduleAtFixedRate(task, 1000,1000);
	}

	

	@Override
	public void render(Graphics g) {
		String time = "";
		if(timeRemaining%60>=10) {
		 time = timeRemaining/60 +" : " + timeRemaining%60;
		} else {
			time = timeRemaining/60 +" : 0" + timeRemaining%60;
		}
		
		g.setFont(new Font("Arial Black", Font.BOLD, 50));
		g.setColor(Color.black);
		g.drawString(time, x, y);
		
	}
	
	public boolean isGameOver() {
		return gameOver;
	}
	

}
