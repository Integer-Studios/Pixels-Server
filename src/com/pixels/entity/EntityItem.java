package com.pixels.entity;

import java.io.IOException;

import org.newdawn.slick.geom.Rectangle;

import com.pixels.communication.CommunicationServlet;
import com.pixels.item.Item;

public class EntityItem extends Entity {
	
	public EntityItem(Item i, float x, float y, boolean prop) {
		super(x,y,prop);
		this.id = 5;
		item = i;
		collisionBox = new Rectangle(0, 0, 0.25f, 0.25f);
	}
	
	public boolean didCollide(Entity e) {
		System.out.println("o");
		if (e instanceof EntityOnlinePlayer) {
			System.out.println("yo");
			despawn();
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
