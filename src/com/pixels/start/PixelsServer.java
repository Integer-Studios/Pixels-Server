package com.pixels.start;



import java.util.Random;

import com.pixels.communication.CommunicationServer;
import com.pixels.entity.EntityBear;
import com.pixels.entity.EntityGob;
import com.pixels.util.Log;
import com.pixels.util.ThreadName;
import com.pixels.util.Toolkit;
import com.pixels.world.World;
import com.pixels.world.WorldThread;

public class PixelsServer extends Thread {
	
	public static void main(String[] args) {
		new PixelsServer().start();
	}
	
	public void run () {
		
		Log.print(ThreadName.MAIN, "Server Launched");
				
		// Start Client Listening Thread
		server = new CommunicationServer(port);
		new Thread(server).start();

		// Start World Thread
		world = new World("test");
		Random r = new Random();
		for (int i = 0; i < 3; i++) {
			new EntityBear(50f+r.nextInt(5), 50f+r.nextInt(5), true);
		}
		for (int i = 0; i < 3; i++) {
			new EntityGob(65f+r.nextInt(5), 70f+r.nextInt(5), true);
		}

		worldThread = new WorldThread(world);
		worldThread.start();
		
	}
	
	public static int port = 25565;
	public static World world;
	public static WorldThread worldThread;
	public static CommunicationServer server;
	public static Toolkit t = new Toolkit();
}
