package game;

import java.awt.Canvas; 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JOptionPane;
public class Driver extends Canvas implements Runnable {
	
	private static final long serialVersionUID = -473349850293143017L;
	public static final int WIDTH = 1280, HEIGHT = 720;

	private Thread thread;
	private boolean running = false;

	private Handler handler;
	private Board board;
	private PieceManager pM;
	private Menu menu;
	private int playerID;
	private int otherPlayer;
	
	private ClientSideConnection csc;

	
	public enum STATE {
		Menu, Game
	};

	public STATE gameState = STATE.Menu;

	public Driver() {
		handler = new Handler();
		pM = new PieceManager();
		menu = new Menu(this, handler, pM);
		this.addMouseListener(menu);
		Window window = new Window(WIDTH, HEIGHT, "Game", this);
		board = new Board();
		
	
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
		
	}

	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				
				delta--;
			}
			if (running)
				render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frames = 0;
			}
		}
	
		stop();
	}

	

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		if (gameState == STATE.Game) {
			board.render(g);
		} else if(gameState == STATE.Menu) {
			menu.render(g);
		}
		handler.render(g);
		g.dispose();
		bs.show();
		
	}
	
	public void connectToServer() {
		csc = new ClientSideConnection();
	}
	
	private class ClientSideConnection {
		private Socket socket;
		private DataInputStream dataIn;
		private DataOutputStream dataOut;
		
		public ClientSideConnection() {
			System.out.println("Client");
			try {
			socket = new Socket("localhost", 51734);	
			dataIn = new DataInputStream(socket.getInputStream());
			dataOut = new DataOutputStream(socket.getOutputStream());
			playerID = dataIn.readInt();
			System.out.println("Connected to the server as Player #" + playerID + ".");
			} catch(IOException e) {
				System.out.println("IO Exception from CSC constructor");
			}
		}
	}
	
	
	public static void main(String[] args) {
	Driver player = new Driver();
	player.connectToServer();

	}

}
