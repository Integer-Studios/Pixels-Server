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
		serverID = e.serverID;
		entityID = e.id;
		posX = e.posX;
		posY = e.posY;
	}

	@Override
	public void writeData(CommunicationServlet servlet) throws IOException {

		servlet.getOutput().writeInt(serverID);
		servlet.getOutput().writeInt(entityID);
		servlet.getOutput().writeInt(posX);
		servlet.getOutput().writeInt(posY);
		
	}

	@Override
	public void readData(CommunicationServlet servlet) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	int serverID, entityID, posX, posY;

}
