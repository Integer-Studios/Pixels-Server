package com.pixels.packet;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import com.pixels.communication.CommunicationServlet;
import com.pixels.entity.Entity;
import com.pixels.entity.EntityPlayer;
import com.pixels.player.PlayerManager;
import com.pixels.start.PixelsServer;
import com.pixels.world.Chunk;

public class PacketHandler {
	
	public static void handlePacketLogin(PacketLogin packet, CommunicationServlet servlet) {
		
		System.out.println("entities: " + PixelsServer.world.entities.size());
		
		Random r = new Random();
		EntityPlayer e = new EntityPlayer(30 + r.nextInt(5), 30 + r.nextInt(5), true, packet.userID);
		packet.serverID = e.getServerID();
		PlayerManager.addPlayer(packet.userID, packet.serverID, servlet);
		
		System.out.println("entities: " + PixelsServer.world.entities.size());
				
		servlet.addPacket(packet);
		servlet.addPacket(new PacketSpawn(e));
		
		ConcurrentHashMap<Integer,Chunk> chunks = PixelsServer.world.getLoadedChunks(e);
		ConcurrentHashMap<Integer,Entity> entities = PixelsServer.world.getLoadedEntities(e);
		System.out.println("loaded entities: " + entities.size());
		servlet.addPacket(new PacketWorldData(chunks, entities));
		
		
	}

	public static void handlePacketPlayerDidSpawn(PacketPlayerDidSpawn packet) {

		System.out.println("did spawn");
		//TODO player is loaded
		
	}

	public static void handlePacketUpdateEntity(PacketUpdateEntity packet) {
		
		PixelsServer.world.moveEntityFromPacket(packet.serverID, packet.posX, packet.posY);
		
	}

}
