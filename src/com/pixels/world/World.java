package com.pixels.world;

import java.util.concurrent.ConcurrentHashMap;

import com.pixels.entity.Entity;
import com.pixels.entity.EntityPlayer;
import com.pixels.packet.PacketSpawnEntity;
import com.pixels.packet.PacketUpdateEntity;
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
	}
	
	public int propogatePlayer(EntityPlayer entity) {
		int id = entities.size();
		entity.serverID = id;
		entities.put(id, entity);
		entityPositions.put(getLocationIndex(entity.posX, entity.posY), id);
		PlayerManager.broadcastPacketExcludingPlayer(new PacketSpawnEntity(entity), entity.userID);
		return id;
	}
	
	public int propogateEntity(Entity entity) {
		int id = entities.size();
		entity.serverID = id;
		entities.put(id, entity);
		entityPositions.put(getLocationIndex(entity.posX, entity.posY), id);
		PlayerManager.broadcastPacket(new PacketSpawnEntity(entity));
		return id;
	}
	
	public Entity getEntity(int x, int y) {		
		Integer serverID = entityPositions.get(getLocationIndex(x, y));
		if (serverID != null)
			return entities.get(serverID);
		else
			return null;
	}
	
	public Entity getEntity(int entityID) {
		return entities.get(entityID);
	}
	
	public void moveEntity(int id, int x, int y) {
		
		Entity e = getEntity(id);
				
		entityPositions.remove(getLocationIndex(e.posX, e.posY));
		
		e.posX = x;
		e.posY = y;
		
		entityPositions.put(getLocationIndex(e.posX, e.posY), id);
		
		//need to specify to only people who have entity loaded
		PlayerManager.broadcastPacket(new PacketUpdateEntity(e));
		
	}
	
	public void moveEntityFromPacket(int id, int x, int y) {
		
		Entity e = getEntity(id);
				
		entityPositions.remove(getLocationIndex(e.posX, e.posY));
		
		e.posX = x;
		e.posY = y;
		
		entityPositions.put(getLocationIndex(e.posX, e.posY), id);
		
		if (e instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)e;
			PlayerManager.broadcastPacketExcludingPlayer(new PacketUpdateEntity(e), player.userID);
		} else {
			//need to specify to only people who have entity loaded
			PlayerManager.broadcastPacket(new PacketUpdateEntity(e));
		}
		
	}
	
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
		
		int chunkX = e.posX >> 4;
		int chunkY = e.posY >> 4;
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
	
	private int getLocationIndex(int x, int y) {
		return y*(chunkWidth<<4) + x;
	}
	
	public int chunkWidth, chunkHeight;
	public ConcurrentHashMap<Integer,Chunk> chunks = new ConcurrentHashMap<Integer,Chunk>();
	public ConcurrentHashMap<Integer,Entity> entities = new ConcurrentHashMap<Integer,Entity>();
	public ConcurrentHashMap<Integer,Integer> entityPositions = new ConcurrentHashMap<Integer,Integer>();


}
