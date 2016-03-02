package com.pixels.packet;

import java.io.IOException;

import com.pixels.communication.CommunicationServlet;

public class PacketPlayerDidSpawn extends Packet {
	
	public PacketPlayerDidSpawn() {
		this.id = 4;
	}

	@Override
	public void writeData(CommunicationServlet servlet) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readData(CommunicationServlet servlet) throws IOException {
		// TODO Auto-generated method stub
		PacketHandler.handlePacketPlayerDidSpawn();
	}

}
