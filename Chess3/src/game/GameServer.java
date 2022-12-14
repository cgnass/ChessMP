package game;

import java.io.*;
import java.net.*;

public class GameServer {

	private ServerSocket ss;
	private int numPlayers;
	private ServerSideConnection player1, player2;
	
	public GameServer() {
		System.out.println("Game Server");
		numPlayers = 0;
		try {
			ss = new ServerSocket(51734);
		} catch (IOException e) {
			System.out.println("IOException from GameServer Constructor");
		}
	}
//bruh
	public void acceptConnections() {
		try {
			System.out.println("Waiting for connections...");
			while (numPlayers < 2) {
				Socket s = ss.accept();
				numPlayers++;
				System.out.println("Player #" + numPlayers + " has connected.");
				
				ServerSideConnection ssc = new ServerSideConnection(s, numPlayers);
				if(numPlayers == 1) {
					player1 = ssc;
				} else {
					player2 = ssc;
				}
				Thread t = new Thread(ssc);
				t.start();
			}
			System.out.println("We now have 2 players. No longer accepting connections.");
		} catch (IOException e) {
			System.out.println("IOException from acceptConnections()");

		}
	}
	
	private class ServerSideConnection implements Runnable {
		private Socket socket;
		private DataInputStream dataIn;
		private DataOutputStream dataOut;
		private int playerID;
		
		public ServerSideConnection(Socket s, int id) {
			socket = s;
			playerID = id;
			try {
				dataIn = new DataInputStream(socket.getInputStream());
				dataOut = new DataOutputStream(socket.getOutputStream());
			} catch(IOException e) {
				System.out.println("IOException from run() SSC");
			}
		}
		public void run() {
			try {
				dataOut.writeInt(playerID);
				dataOut.flush();
				while(true) {
					
				}
			} catch(IOException e) {
				System.out.println("IOException from run() SSC");
			}
		}
	}
	
	
	
	public static void main(String[] args) {
		GameServer gs = new GameServer();
		gs.acceptConnections();
	}
	
}
