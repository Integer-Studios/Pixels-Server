package com.pixels.packet;

import java.io.IOException;

import com.pixels.communication.CommunicationServlet;
import com.pixels.entity.Entity;

public class PacketDespawnEntity extends Packet {
	
	public PacketDespawnEntity() {
		this.id = 12;
	}
	
	public PacketDespawnEntity(Entity e) {
		this.id = 12;
		serverID = e.serverID;
	}

	@Override
	public void writeData(CommunicationServlet servlet) throws IOException {

		servlet.getOutput().writeInt(serverID);
		
	}

	@Override
	public void readData(CommunicationServlet servlet) throws IOException {
		// TODO Auto-generated method stub
		
		serverID = servlet.getInput().readInt();
		PacketHandler.handlePacketDespawnEntity(this);
		
	}
	
	int serverID;

}
