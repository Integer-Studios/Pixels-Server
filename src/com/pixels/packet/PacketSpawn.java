package com.pixels.packet;

import java.io.IOException;

import com.pixels.communication.CommunicationServlet;
import com.pixels.entity.EntityOnlinePlayer;
import com.pixels.start.PixelsServer;

public class PacketSpawn extends Packet {
	
	//doesn't technically need to send position, client never uses it
	
	public PacketSpawn() {
		this.id = 2;
	}
	
	public PacketSpawn(EntityOnlinePlayer player) {
		this.id = 2;
		worldWidth = PixelsServer.world.chunkWidth;
		worldWidth = PixelsServer.world.chunkHeight;
		playerPosX = player.posX;
		playerPosY = player.posY;
	}

	@Override
	public void writeData(CommunicationServlet servlet) throws IOException {
		// TODO Auto-generated method stub
		servlet.getOutput().writeInt(worldWidth);
		servlet.getOutput().writeInt(worldHeight);
		servlet.getOutput().writeFloat(playerPosX);
		servlet.getOutput().writeFloat(playerPosY);
	}

	@Override
	public void readData(CommunicationServlet servlet) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	public int worldWidth, worldHeight;
	public float playerPosX, playerPosY;


}
