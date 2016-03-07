package com.pixels.start;

import java.util.Random;

import com.pixels.communication.CommunicationServer;
import com.pixels.entity.EntityBunny;
import com.pixels.world.World;
import com.pixels.world.WorldThread;

public class PixelsServer extends Thread {
	
	public static void main(String[] args) {
		new PixelsServer().start();
	}
	
	public void run () {
				
		// Start Client Listening Thread
		server = new CommunicationServer(port);
		new Thread(server).start();

		// Start World Thread
		world = new World(20, 20);
		Random r = new Random();
		for (int i = 0; i < r.nextInt(10); i++) {
			new EntityBunny(125 + r.nextInt(5), 125 + r.nextInt(5), true);
		}
		worldThread = new WorldThread(world);
		worldThread.start();

		
	}
	
	public static int port = 25565;
	public static World world;
	public static WorldThread worldThread;
	public static CommunicationServer server;
}
