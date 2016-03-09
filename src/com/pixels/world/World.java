package com.pixels.world;

import java.util.concurrent.ConcurrentHashMap;

import com.pixels.entity.Entity;
import com.pixels.entity.EntityOnlinePlayer;
import com.pixels.packet.PacketSpawnEntity;
import com.pixels.player.PlayerManager;

public class World {
	
	public World(int w, int h) {
		
		chunkWidth = w;
		chunkHeight = h;
		
		entities = new EntityRegister();
		
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
		
		entities.update(this);
	}
	
	public void propogatePlayer(EntityOnlinePlayer entity) {
		
		entities.add(entity);
		PlayerManager.broadcastPacketExcludingPlayer(new PacketSpawnEntity(entity), entity.userID);
		
	}
	
	public void propogateEntity(Entity entity) {
		
		entities.add(entity);
		PlayerManager.broadcastPacket(new PacketSpawnEntity(entity));
		
	}

	public Entity getEntity(int entityID) {
		return entities.get(entityID);
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
		
		int chunkX = ((int)e.posX) >> 4;
		int chunkY = ((int)e.posY) >> 4;
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
		
		return entities.entityIDMap;
	}
	
	private int getChunkIndex(int chunkX, int chunkY) {
		return chunkY*chunkWidth + chunkX;
	}
	
	public int chunkWidth, chunkHeight;
	public ConcurrentHashMap<Integer,Chunk> chunks = new ConcurrentHashMap<Integer,Chunk>();
//	public ConcurrentHashMap<Integer,Entity> entities = new ConcurrentHashMap<Integer,Entity>();
//	public ConcurrentHashMap<Integer,ArrayList<Integer>> entityPositionMap = new ConcurrentHashMap<Integer,ArrayList<Integer>>();
	public EntityRegister entities;

}
