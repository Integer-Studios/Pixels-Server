package com.pixels.packet;

import java.io.IOException;

import com.pixels.communication.CommunicationServlet;

public class PacketHarvestPiece extends Packet {
	
	public PacketHarvestPiece() {
		this.id = 14;
	}

	@Override
	public void writeData(CommunicationServlet servlet) throws IOException {
		
	}

	@Override
	public void readData(CommunicationServlet servlet) throws IOException {
		x = servlet.getInput().readInt();
		y = servlet.getInput().readInt();
		PacketHandler.handlePacketHarvestPiece(this);
	}
	
	int x, y;

}
