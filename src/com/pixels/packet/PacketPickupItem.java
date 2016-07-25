package com.pixels.packet;

import java.io.IOException;

import com.pixels.communication.CommunicationServlet;
import com.pixels.item.Item;

public class PacketPickupItem extends Packet{
	
	public PacketPickupItem(Item i) {
		this.id = 13;
		itemID = i.id;
	}

	
	int itemID;


	@Override
	public void writeData(CommunicationServlet servlet) throws IOException {
		servlet.getOutput().writeInt(itemID);
	}


	@Override
	public void readData(CommunicationServlet servlet) throws IOException {
		
	}

}
