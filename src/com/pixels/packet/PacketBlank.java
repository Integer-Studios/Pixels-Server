package com.pixels.packet;

import java.io.IOException;

import com.pixels.communication.CommunicationServlet;

public class PacketBlank extends Packet {

	public PacketBlank() {
		
		this.id = 0;
		
	}

	@Override
	public void writeData(CommunicationServlet servlet) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readData(CommunicationServlet servlet) throws IOException {
		// TODO Auto-generated method stub
		
	}

	
}
