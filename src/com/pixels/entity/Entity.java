package com.pixels.entity;

import java.io.IOException;
import java.util.HashMap;

import com.pixels.communication.CommunicationServlet;
import com.pixels.packet.PacketUpdateEntity;
import com.pixels.player.PlayerManager;
import com.pixels.start.PixelsServer;
import com.pixels.world.World;

public class Entity {
	
	public Entity() { }
	
	public Entity(float x, float y, boolean prop) {
		posX = x;
		posY = y;
		if (prop)
			PixelsServer.world.propogateEntity(this);
	}
	
	public void setPosition(float x, float y) {
		
		posX = x;
		posY = y;
		
		if (!(this instanceof EntityOnlinePlayer))
			PlayerManager.broadcastPacket(new PacketUpdateEntity(this));
	
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
		
	public int id, serverID, positionKey;
	public float posX, posY;
	
	@SuppressWarnings("rawtypes")
	private static HashMap<Integer, Class> entityMap = new HashMap<Integer, Class>();
	
	static {
		
		entityMap.put(0, EntityBlank.class);
//		entityMap.put(1, EntityPlayer.class); client only
		entityMap.put(2, EntityOnlinePlayer.class);

	}

}
