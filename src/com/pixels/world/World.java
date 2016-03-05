package com.pixels.world;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import com.pixels.entity.Entity;
import com.pixels.entity.EntityOnlinePlayer;
import com.pixels.packet.PacketSpawnEntity;
import com.pixels.player.PlayerManager;

public class World {
	
	public World(int w, int h) {
		
		chunkWidth = w;
		chunkHeight = h;
		generateWorld();
		
	}
	
	public void generateWorld() {
		
		for (int chunkY = 0; chunkY < chunkHeight; chunkY++) {
			for (int chunkX = 0; chunkX < chunkWidth; chunkX++) {
				chunks.put(getChunkIndex(chunkX, chunkY), new Chunk(chunkX, chunkY));
			}
		}

	}
	
	public void update() {

		for (Chunk chunk : chunks.values()) {
			chunk.update(this);
		}
		
		for (Entity entity : entities.values()) {
			entity.update(this);
		}
	}
	
	public int propogatePlayer(EntityOnlinePlayer entity) {
		// this wont work after entities despawn...entities.size will be wrong for next new index
		int id = entities.size();
		entity.serverID = id;
		entities.put(id, entity);
		addEntityToPositionMap(entity);
		PlayerManager.broadcastPacketExcludingPlayer(new PacketSpawnEntity(entity), entity.userID);
		return id;
	}
	
	public int propogateEntity(Entity entity) {
		int id = entities.size();
		entity.serverID = id;
		entities.put(id, entity);
		addEntityToPositionMap(entity);
		PlayerManager.broadcastPacket(new PacketSpawnEntity(entity));
		return id;
	}
	
	public void addEntityToPositionMap(Entity e) {
		int key = getLocationIndex(e);
		e.positionKey = key;
		ArrayList<Integer> entities = entityPositionMap.get(key);
		if (entities == null) {
			entities = new ArrayList<Integer>();
		}
		entities.add(e.serverID);
		entityPositionMap.put(key, entities);
	}
	
	public void removeEntityFromPositionMap(Entity e) {
		int key = e.positionKey;
		ArrayList<Integer> entityMap = entityPositionMap.get(key);
		if (entityMap != null) {
			int i = entityMap.lastIndexOf(e.serverID);
			entityMap.remove(i);
		}
		entityPositionMap.put(key, entityMap);
	}
	
	public void updateEntityPositionMap(Entity e) {
		if (e.positionKey != getLocationIndex(e)) {
			removeEntityFromPositionMap(e);
			addEntityToPositionMap(e);
		}
	}

	public Entity getEntity(int entityID) {
		return entities.get(entityID);
	}
	
//	Shit at end of entity tick to update their position map
	
//	public void moveEntity(int id, float x, float y) {
//		
//		Entity e = getEntity(id);
//				
//		entityPositions.remove(getEntityLocationIndex(e.posX, e.posY));
//		
//		e.posX = x;
//		e.posY = y;
//		
//		entityPositions.put(getEntityLocationIndex(e.posX, e.posY), id);
//		
//		//need to specify to only people who have entity loaded
//		PlayerManager.broadcastPacket(new PacketUpdateEntity(e));
//		
//	}
//	
//	public void moveEntityFromPacket(int id, float x, float y) {
//				
//		Entity e = getEntity(id);
//				
//		entityPositions.remove(getEntityLocationIndex(e.posX, e.posY));
//		
//		e.posX = x;
//		e.posY = y;
//		
//		entityPositions.put(getEntityLocationIndex(e.posX, e.posY), id);
//		
//		if (e instanceof EntityOnlinePlayer) {
//			EntityOnlinePlayer player = (EntityOnlinePlayer)e;
//			PlayerManager.broadcastPacketExcludingPlayer(new PacketUpdateEntity(e), player.userID);
//		} else {
//			//need to specify to only people who have entity loaded
//			PlayerManager.broadcastPacket(new PacketUpdateEntity(e));
//		}
//		
//	}
	
	public void setPieceID(int x, int y, int id) {
		getChunk(x, y).setPieceID(x, y, id);
	}

	public int getPieceID(int x, int y) {
		return getChunk(x, y).getPieceID(x, y);
	}
	
	public Chunk getChunk(int x, int y) {
		return chunks.get(getChunkIndex(x>>4, y>>4));
	}
	
	public ConcurrentHashMap<Integer, Chunk> getLoadedChunks(Entity e) {
		
		ConcurrentHashMap<Integer, Chunk> loadedChunks = new ConcurrentHashMap<Integer, Chunk>();
		
		int chunkX = Math.round(e.posX) >> 4;
		int chunkY = Math.round(e.posY) >> 4;
		for (int y = chunkY-1; y <= chunkY+1; y++) {
			for (int x = chunkX-1; x <= chunkX+1; x++) {
				if (x >= 0 && x < (chunkWidth<<4) && y >= 0 && y < (chunkHeight<<4)) {
					Chunk c = chunks.get(getChunkIndex(x, y));
					if (c != null) {
						loadedChunks.put(getChunkIndex(x, y), c);
					}
				}
			}
		}
		return loadedChunks;
	}

	public ConcurrentHashMap<Integer, Entity> getLoadedEntities(Entity e) {
		
//		OLD code, probaly have to redo for floating point LITERALLY ONLY REASN FOR POSITION MAP ON SERVER
		
//		ConcurrentHashMap<Integer, Entity> loadedEntities = new ConcurrentHashMap<Integer, Entity>();
//		
//		int chunkX = e.posX >> 4;
//		int chunkY = e.posY >> 4;
//		int x1 = (chunkX-2) << 4;
//		int y1 = (chunkY-2) << 4;
//		int x2 = (chunkX+1) << 4;
//		int y2 = (chunkY+1) << 4;
//		for (int y = y1; y < y2; y++) {
//			for (int x = x1; x < x2; x++) {
//				if (x >= 0 && x < (chunkWidth<<4) && y >= 0 && y < (chunkHeight<<4)) {
//					Integer i = entityPositions.get(getLocationIndex(x, y));
//					if (i != null)
//						loadedEntities.put(i, entities.get(i));
//				}
//			}
//		}
//		return loadedEntities;
		
		return entities;
	}
	
	private int getChunkIndex(int chunkX, int chunkY) {
		return chunkY*chunkWidth + chunkX;
	}
	
	private int getLocationIndex(Entity e) {
		return Math.round(e.posY)*(chunkWidth<<4) + Math.round(e.posX);
	}
	
	public int chunkWidth, chunkHeight;
	public ConcurrentHashMap<Integer,Chunk> chunks = new ConcurrentHashMap<Integer,Chunk>();
	public ConcurrentHashMap<Integer,Entity> entities = new ConcurrentHashMap<Integer,Entity>();
	
//	map of int location hash to id array
	public ConcurrentHashMap<Integer,ArrayList<Integer>> entityPositionMap = new ConcurrentHashMap<Integer,ArrayList<Integer>>();


}
