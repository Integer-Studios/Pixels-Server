package com.pixels.packet;

import java.io.IOException;

import com.pixels.communication.CommunicationServlet;

public class PacketLogout extends Packet {

	public int userID;
	public int serverID;

	public PacketLogout() {
		
		this.id = 9;
		
	}
	
	
	public PacketLogout(int userID, int serverID) {
		
		this.id = 9;
		this.userID = userID;
		this.serverID = serverID;
		
	}
	
	@Override
	public void writeData(CommunicationServlet servlet) throws IOException {

		servlet.getOutput().writeInt(userID);
		servlet.getOutput().writeInt(serverID);

	}

	@Override
	public void readData(CommunicationServlet servlet) throws IOException {

		this.userID = servlet.getInput().readInt();
		servlet.disconnect(true);
		
	}

}
