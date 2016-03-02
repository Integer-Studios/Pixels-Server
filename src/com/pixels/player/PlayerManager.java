package com.pixels.player;

import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {
	
	public static void addPlayer(int userID, int serverID) {
		players.put(userID, serverID);
	}
	
	public static void getPlayer(int userID) {
		players.get(userID);
	}
	
	public static ConcurrentHashMap<Integer, Integer> players = new ConcurrentHashMap<Integer, Integer>();

}
