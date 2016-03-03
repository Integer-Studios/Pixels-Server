package com.pixels.packet;

import java.io.IOException;

import com.pixels.communication.CommunicationServlet;
import com.pixels.entity.Entity;

public class PacketUpdateEntity extends Packet {
	
	public PacketUpdateEntity() {
		this.id = 5;
	}
	
	public PacketUpdateEntity(Entity e) {
		this.id = 5;
		serverID = e.serverID;
		posX = e.posX;
		posY = e.posY;
	}

	@Override
	public void writeData(CommunicationServlet servlet) throws IOException {
		
		servlet.getOutput().writeInt(serverID);
		servlet.getOutput().writeInt(posX);
		servlet.getOutput().writeInt(posY);
		
	}

	@Override
	public void readData(CommunicationServlet servlet) throws IOException {

		serverID = servlet.getInput().readInt();
		posX = servlet.getInput().readInt();
		posY = servlet.getInput().readInt();
		
		PacketHandler.handlePacketUpdateEntity(this);
		
	}
	
	int serverID, posX, posY;

}
