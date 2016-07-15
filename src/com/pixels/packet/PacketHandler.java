package com.pixels.packet;

import java.util.concurrent.ConcurrentHashMap;

import com.pixels.communication.CommunicationServlet;
import com.pixels.entity.Entity;
import com.pixels.entity.EntityOnlinePlayer;
import com.pixels.player.PlayerManager;
import com.pixels.start.PixelsServer;
import com.pixels.util.Log;
import com.pixels.util.ThreadName;
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

		Log.print(ThreadName.SERVLET, "User spawned: " + packet.userID);
		//TODO player is loaded
		
	}

	public static void handlePacketUpdateEntity(PacketUpdateEntity packet) {
//		move entity through entity class now, entities update their own position map at the end of update
		PixelsServer.world.getEntity(packet.serverID).setPosition(packet.posX, packet.posY);
		PlayerManager.broadcastPacketExcludingPlayer(new PacketUpdateEntity(PixelsServer.world.getEntity(packet.serverID)), packet.userID);
	}
	
	public static void handlePacketMoveEntity(PacketMoveEntity packet) {
//		move entity through entity class now, entities update their own position map at the end of update
		float xDiff = Math.abs(PixelsServer.world.getEntity(packet.serverID).posX - packet.posX);
		float yDiff = Math.abs(PixelsServer.world.getEntity(packet.serverID).posY - packet.posY);
		PixelsServer.world.getEntity(packet.serverID).setVelocity(packet.velocityX, packet.velocityY);
		PlayerManager.broadcastPacketExcludingPlayer(packet, packet.userID);

		if (packet.velocityX == 0 && packet.velocityY == 0) {
			
			if (xDiff < 1 && yDiff < 1) {
				
				PixelsServer.world.getEntity(packet.serverID).setPosition(packet.posX, packet.posY);
				
			}
			
			PlayerManager.broadcastPacketExcludingPlayer(new PacketUpdateEntity(PixelsServer.world.getEntity(packet.serverID)), packet.userID);
			
		}
		
	}

	public static void handlePacketUpdatePlayer(PacketUpdatePlayer packet) {
//		move entity through entity class now, entities update their own position map at the end of update
		PixelsServer.world.getEntity(packet.serverID).setVelocity(packet.velocityX, packet.velocityY);
		PixelsServer.world.getEntity(packet.serverID).setPosition(packet.posX, packet.posY);
		PlayerManager.broadcastPacketExcludingPlayer(new PacketUpdateEntity(PixelsServer.world.getEntity(packet.serverID)), packet.userID);
		
	}

	public static void handlePacketUpdateWorld(PacketUpdateWorld packet, CommunicationServlet servlet) {
		// TODO Auto-generated method stub
		Entity e = PixelsServer.world.getEntity(PlayerManager.getPlayer(packet.userID));
		e.setPosition(packet.percievedPosX, packet.percievedPosY);
		packet.chunks = PixelsServer.world.getLoadedChunks(e);
		packet.entities = PixelsServer.world.getLoadedEntities(e);
		servlet.addPacket(packet);
	}

	public static void handlePacketUpdatePiece(PacketUpdatePiece packet) {
		PixelsServer.world.setPieceIDAndMetadata(packet.posX, packet.posY, packet.pieceID, packet.metadata);
	}

}
