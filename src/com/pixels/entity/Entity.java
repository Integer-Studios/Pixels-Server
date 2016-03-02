package com.pixels.entity;

import java.io.IOException;
import java.util.HashMap;

import com.pixels.communication.CommunicationServlet;
import com.pixels.start.PixelsServer;
import com.pixels.world.World;

public class Entity {
	
	public Entity() { }
	
	public Entity(int x, int y, boolean prop) {
		posX = x;
		posY = y;
		if (prop)
			serverID = PixelsServer.world.propogateEntity(this);
	}
	
	public void initializePosition(int x, int y) {
		//used only for pre propogation
		posX = x;
		posY = y;
	}
	
	public void setPosition(int x, int y, World w) {
		//Must get called EVERY time entity moves
		w.moveEntity(serverID, posX, posY, x, y);
		posX = x;
		posY = y;
	}

	public void update(World w) {

	}

	public int getServerID() {
		return serverID;
	}
	
	@SuppressWarnings("rawtypes")
	public static Entity getEntity(int id) {
        try
        {
            Class entityClass = (Class)entityMap.get(id);
            return entityClass == null ? null : (Entity)entityClass.newInstance();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    } 
	
	public void writeEntityData(CommunicationServlet servlet) throws IOException {
		
	}
	
	public void readEntityData(CommunicationServlet servlet) throws IOException {
		
	}
	
	public int id, posX, posY, serverID;
	
	@SuppressWarnings("rawtypes")
	private static HashMap<Integer, Class> entityMap = new HashMap<Integer, Class>();
	
	static {
		
		entityMap.put(0, EntityBlank.class);
		entityMap.put(1, EntityPlayer.class);
//		entityMap.put(2, EntityOnlinePlayer.class); client only

	}

}
