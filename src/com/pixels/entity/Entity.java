package com.pixels.entity;

import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.geom.Rectangle;

import com.pixels.communication.CommunicationServlet;
import com.pixels.packet.PacketMoveEntity;
import com.pixels.piece.Piece;
import com.pixels.player.PlayerManager;
import com.pixels.start.PixelsServer;
import com.pixels.world.World;

public class Entity {
	
	public Entity() { 
		collisionBox = new Rectangle(0, 0, 0, 0);
	}
	
	public Entity(int i, float x, float y, boolean prop) {
		id = i;
		posX = x;
		posY = y;
		collisionBox = new Rectangle(0, 0, 0, 0);
		if (prop)
			PixelsServer.world.propogateEntity(this);
	}
	
	public void setPosition(float x, float y) {
		
		posX = x;
		posY = y;
		
	}

	public void update(World w) {
		
		collisionBox.setLocation(posX - (collisionBox.getWidth()/2), posY - collisionBox.getHeight());
		
		if(!(this instanceof EntityOnlinePlayer))
			w.checkEntityCollisions(this);
		
		setPosition(posX+velocityX, posY+velocityY);
		
		if (!(this instanceof EntityOnlinePlayer)) {
			if (velocityX != prevVelocityX || velocityY != prevVelocityY) {

				PlayerManager.broadcastPacket(new PacketMoveEntity(this));
				
			} 
	
		}
		
		prevVelocityX = velocityX;
		prevVelocityY = velocityY;
		prevPosX = posX;
		prevPosY = posY;

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
		
	public void setVelocity(float x, float y) {
		
		this.velocityX = x;
		this.velocityY = y;
		
	}
	
	public void setCollisionBoxSize(float width, float height) {
		collisionBox.setSize(width, height);
	}
	
	public float getCollisionWidth() {
		return collisionBox.getWidth();
	}
	
	public float getCollisionHeight() {
		return collisionBox.getHeight();
	}
	
	public boolean didCollide(Entity e) {
		return true;
	}
	
	public boolean didCollide(Piece p) {
		return true;
	}
	
	public void despawn() {
		PixelsServer.world.despawnEntity(this, true);
	}
	
	public int id, serverID, positionKey;
	public float posX, posY, prevPosX, prevPosY;
	public float velocityX, velocityY;
	public float prevVelocityX, prevVelocityY;
	public Rectangle collisionBox;

	
	@SuppressWarnings("rawtypes")
	private static HashMap<Integer, Class> entityMap = new HashMap<Integer, Class>();
	
	static {
		
		entityMap.put(0, EntityBlank.class);
//		entityMap.put(1, EntityPlayer.class); client only
		entityMap.put(2, EntityOnlinePlayer.class);
		entityMap.put(3, EntityGob.class);
		entityMap.put(4, EntityBear.class);
		entityMap.put(5, EntityItem.class);
		
	}

	

}
