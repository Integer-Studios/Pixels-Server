package com.pixels.packet;

import java.io.IOException;

import com.pixels.communication.CommunicationServlet;
import com.pixels.entity.Entity;

public class PacketMoveEntity extends Packet {
	
	public PacketMoveEntity() {
		this.id = 10;
	}
	
	public PacketMoveEntity(Entity e) {
		this.id = 10;
		serverID = e.serverID;
		posX = e.posX;
		posY = e.posY;
		velocityX = e.velocityX;
		velocityY = e.velocityY;
	}

	@Override
	public void writeData(CommunicationServlet servlet) throws IOException {

		servlet.getOutput().writeInt(serverID);
		servlet.getOutput().writeFloat(posX);
		servlet.getOutput().writeFloat(posY);
		servlet.getOutput().writeFloat(velocityX);
		servlet.getOutput().writeFloat(velocityY);

	}

	@Override
	public void readData(CommunicationServlet servlet) throws IOException {

		serverID = servlet.getInput().readInt();
		posX = servlet.getInput().readFloat();
		posY = servlet.getInput().readFloat();
		velocityX = servlet.getInput().readFloat();
		velocityY = servlet.getInput().readFloat();

		PacketHandler.handlePacketMoveEntity(this);
		
	}
	
	int serverID;
	public float posX, posY, velocityX, velocityY;

}
