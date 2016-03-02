package com.pixels.packet;

import java.io.IOException;

import com.pixels.communication.CommunicationServlet;

public class PacketLogin extends Packet {
	
	public PacketLogin() {
		this.id = 1;
	}

	@Override
	public void writeData(CommunicationServlet servlet) throws IOException {
		servlet.getOutput().writeInt(serverID);
	}

	@Override
	public void readData(CommunicationServlet servlet) throws IOException {
		// user ID sent in all packets
		PacketHandler.handlePacketLogin(this, servlet);
	}
	
	public int serverID;

}
