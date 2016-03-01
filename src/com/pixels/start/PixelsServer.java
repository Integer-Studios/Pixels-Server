package com.pixels.start;

import com.pixels.communication.CommunicationServer;

public class PixelsServer extends Thread {
	
	public static void main(String[] args) {
		new PixelsServer().start();
	}
	
	public void run () {
				
		CommunicationServer server = new CommunicationServer(port);
		new Thread(server).start();

//		world = new WorldServer();
//		
//		game = new GameServer(world);
//		game.start();
		
	}
	
	public static int port = 25565;

}
