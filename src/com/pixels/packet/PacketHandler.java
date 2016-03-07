package com.pixels.packet;

import java.util.concurrent.ConcurrentHashMap;

import com.pixels.communication.CommunicationServlet;
import com.pixels.entity.Entity;
import com.pixels.entity.EntityOnlinePlayer;
import com.pixels.player.PlayerManager;
import com.pixels.start.PixelsServer;
import com.pixels.world.Chunk;

public class PacketHandler {
	
	public static void handlePacketLogin(PacketLogin packet, CommunicationServlet servlet) {
				
		EntityOnlinePlayer e = new EntityOnlinePlayer(120, 120, true, packet.userID);
		packet.serverID = e.getServerID();
		PlayerManager.addPlayer(packet.userID, packet.serverID, servlet);
						
		servlet.addPacket(packet);
		servlet.addPacket(new PacketSpawn(e));
		
		ConcurrentHashMap<Integer,Chunk> chunks = PixelsServer.world.getLoadedChunks(e);
		ConcurrentHashMap<Integer,Entity> entities = PixelsServer.world.getLoadedEntities(e);
		servlet.addPacket(new PacketWorldData(chunks, entities));
		
	}

	public static void handlePacketPlayerDidSpawn(PacketPlayerDidSpawn packet) {

		System.out.println("User spawned: " + packet.userID);
		//TODO player is loaded
		
	}

	public static void handlePacketUpdateEntity(PacketUpdateEntity packet) {
//		move entity through entity class now, entities update their own position map at the end of update
		PixelsServer.world.getEntity(packet.serverID).setPosition(packet.posX, packet.posY);
		PlayerManager.broadcastPacketExcludingPlayer(new PacketUpdateEntity(PixelsServer.world.getEntity(packet.serverID)), packet.userID);
	}

	public static void handlePacketUpdatePlayer(PacketUpdatePlayer packet) {
//		move entity through entity class now, entities update their own position map at the end of update
		PixelsServer.world.getEntity(packet.serverID).setPosition(packet.posX, packet.posY);
		PlayerManager.broadcastPacketExcludingPlayer(new PacketUpdateEntity(PixelsServer.world.getEntity(packet.serverID)), packet.userID);
		
	}

	public static void handlePacketUpdateWorld(PacketUpdateWorld packet, CommunicationServlet servlet) {
		// TODO Auto-generated method stub
		Entity e = PixelsServer.world.getEntity(PlayerManager.getPlayer(packet.userID));
		packet.chunks = PixelsServer.world.getLoadedChunks(e);
		packet.entities = PixelsServer.world.getLoadedEntities(e);
		servlet.addPacket(packet);
	}

}
