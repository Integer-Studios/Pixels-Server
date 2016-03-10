package com.pixels.player;

import java.util.concurrent.ConcurrentHashMap;

import com.pixels.communication.CommunicationServlet;
import com.pixels.packet.Packet;
import com.pixels.start.PixelsServer;
import com.pixels.util.Log;
import com.pixels.util.ThreadName;

public class PlayerManager {
	
	public static void addPlayer(int userID, int serverID, CommunicationServlet servlet) {
		players.put(userID, serverID);
		connections.put(userID, servlet);
	}
	
	public static int getPlayer(int userID) {
		return players.get(userID);
	}
	
	public static void broadcastPacket(Packet p) {
		for (CommunicationServlet servlet : connections.values()) {
			servlet.addPacket(p);
		}
	}
	
	public static void broadcastPacketExcludingPlayer(Packet p, int userID) {
		for (Integer i : connections.keySet()) {
			if (i != userID)
				connections.get(i).addPacket(p);
		}
	}
	
	public static void logout(int userID) {

		PixelsServer.world.entities.remove(players.get(userID));
		players.remove(userID);
		connections.remove(userID);
		Log.print(ThreadName.SERVLET, "User Logged Out: " + userID);
		
	}
	
	public static ConcurrentHashMap<Integer, Integer> players = new ConcurrentHashMap<Integer, Integer>();
	public static ConcurrentHashMap<Integer, CommunicationServlet> connections = new ConcurrentHashMap<Integer, CommunicationServlet>();
	


}
