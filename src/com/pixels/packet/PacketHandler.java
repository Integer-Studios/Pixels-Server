package com.pixels.packet;

import java.util.concurrent.ConcurrentHashMap;

import com.pixels.communication.CommunicationServlet;
import com.pixels.entity.Entity;
import com.pixels.entity.EntityBlank;
import com.pixels.entity.EntityPlayer;
import com.pixels.player.PlayerManager;
import com.pixels.start.PixelsServer;
import com.pixels.world.Chunk;

public class PacketHandler {
	
	public static void handlePacketLogin(PacketLogin packet, CommunicationServlet servlet) {
		
		EntityPlayer e = new EntityPlayer(40, 40, true);
		packet.serverID = e.getServerID();
		PlayerManager.addPlayer(packet.userID, packet.serverID);
		
		new EntityBlank(16, 16, true);
		
		servlet.addPacket(packet);
		servlet.addPacket(new PacketSpawn());
		
		ConcurrentHashMap<Integer,Chunk> chunks = PixelsServer.world.getLoadedChunks(e);
		ConcurrentHashMap<Integer,Entity> entities = PixelsServer.world.getLoadedEntities(e);
		servlet.addPacket(new PacketWorldData(chunks, entities));
		
	}

	public static void handlePacketPlayerDidSpawn() {

		System.out.println("did spawn");
		//TODO player is loaded
		
	}

}
