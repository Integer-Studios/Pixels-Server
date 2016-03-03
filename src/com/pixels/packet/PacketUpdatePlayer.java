package com.pixels.packet;

import java.io.IOException;

import com.pixels.communication.CommunicationServlet;

//both are entityonlineplayer now
public class PacketUpdatePlayer extends Packet{
	
	public PacketUpdatePlayer() {
		this.id = 7;
	}

	@Override
	public void writeData(CommunicationServlet servlet) throws IOException {
		
	}

	@Override
	public void readData(CommunicationServlet servlet) throws IOException {

		serverID = servlet.getInput().readInt();
		posX = servlet.getInput().readInt();
		posY = servlet.getInput().readInt();
		
		PacketHandler.handlePacketUpdatePlayer(this);
		
	}
	
	int serverID, posX, posY;

}
