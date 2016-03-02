package com.pixels.packet;

import java.io.IOException;

import com.pixels.communication.CommunicationServlet;
import com.pixels.entity.Entity;
import com.pixels.player.PlayerManager;
import com.pixels.start.PixelsServer;

public class PacketSpawn extends Packet {
	
	public PacketSpawn() {
		this.id = 2;
		worldWidth = PixelsServer.world.chunkWidth;
		worldWidth = PixelsServer.world.chunkHeight;
		Entity player = (PixelsServer.world.getEntity(PlayerManager.players.get(userID)));
		playerPosX = player.posX;
		playerPosY = player.posY;
	}

	@Override
	public void writeData(CommunicationServlet servlet) throws IOException {
		// TODO Auto-generated method stub
		servlet.getOutput().writeInt(worldWidth);
		servlet.getOutput().writeInt(worldHeight);
		servlet.getOutput().writeInt(playerPosX);
		servlet.getOutput().writeInt(playerPosY);
	}

	@Override
	public void readData(CommunicationServlet servlet) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	public int worldWidth, worldHeight, playerPosX, playerPosY;


}
