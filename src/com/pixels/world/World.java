package com.pixels.world;

import java.util.concurrent.ConcurrentHashMap;

import com.pixels.entity.Entity;

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
		for (int i = 0; i < chunks.size(); i++) {
			chunks.get(i).update(this);
		}
	}
	
	public int propogateEntity(Entity entity) {
		int id = entities.size();
		entities.put(id, entity);
		entityPositions.put(getLocationIndex(entity.posX, entity.posY), id);
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
	
	public void moveEntity(int id, int x1, int y1, int x2, int y2) {
		entityPositions.remove(getLocationIndex(x1, y1));
		entityPositions.put(getLocationIndex(x2, y2), id);
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
		// TODO Auto-generated method stub
		return chunks;
	}

	public ConcurrentHashMap<Integer, Entity> getLoadedEntities(Entity e) {
		// TODO Auto-generated method stub
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
