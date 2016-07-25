package com.pixels.packet;

import java.io.IOException;

import com.pixels.communication.CommunicationServlet;
import com.pixels.entity.Entity;

public class PacketSpawnEntity extends Packet {
	
	public PacketSpawnEntity() {
		this.id = 6;
	}
	
	public PacketSpawnEntity(Entity e) {
		this.id = 6;
		this.e = e;
	}

	@Override
	public void writeData(CommunicationServlet servlet) throws IOException {

		servlet.getOutput().writeInt(e.serverID);
		servlet.getOutput().writeInt(e.id);
		servlet.getOutput().writeInt(e.positionKey);
		servlet.getOutput().writeFloat(e.posX);
		servlet.getOutput().writeFloat(e.posY);
		e.writeEntityData(servlet);
		
	}

	@Override
	public void readData(CommunicationServlet servlet) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	public Entity e;

}
