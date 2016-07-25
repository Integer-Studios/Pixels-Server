package com.pixels.entity;

import java.io.IOException;

import org.newdawn.slick.geom.Rectangle;

import com.pixels.communication.CommunicationServlet;
import com.pixels.item.Item;
import com.pixels.packet.PacketPickupItem;
import com.pixels.player.PlayerManager;

public class EntityItem extends Entity {
	
	public EntityItem(Item i, float x, float y, boolean prop) {
		super(5, x,y,prop);
		item = i;
		collisionBox = new Rectangle(0, 0, 0.25f, 0.25f);
	}
	
	public boolean didCollide(Entity e) {
		if (e instanceof EntityOnlinePlayer) {
			despawn();
			PlayerManager.sendPacketToPlayer(new PacketPickupItem(item), ((EntityOnlinePlayer)e).userID);
			return false;
		}
		return true;
	}
	
	public void writeEntityData(CommunicationServlet servlet) throws IOException {
		servlet.getOutput().writeInt(item.id);
	}
	
	public void readEntityData(CommunicationServlet servlet) throws IOException {
		item = Item.getItemByID(servlet.getInput().readInt());
	}
	
	public Item item;

}
