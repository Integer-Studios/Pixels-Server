package com.pixels.world;

public class WorldThread extends Thread {
	
	public WorldThread(World w) {
		world = w;
	}
	
	public void run() {

		lastUpdateTime = System.nanoTime();

		while (running) {

			double now = System.nanoTime();

			while (now - lastUpdateTime > TIME_BETWEEN_UPDATES)	{
				world.update();
				lastUpdateTime += TIME_BETWEEN_UPDATES;
			}
			
			while (now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
               Thread.yield();
            
               try {Thread.sleep(1);} catch(Exception e) {} 
            
               now = System.nanoTime();
            }

		}

	}
	
	public World world;
	final double GAME_HERTZ = 30.0;
    final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
    double lastUpdateTime;
    public boolean running = true;

}
