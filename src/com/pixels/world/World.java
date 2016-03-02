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
		entities.put(entities.size(), entity);
		return id;
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
	
	public int chunkWidth, chunkHeight;
	public ConcurrentHashMap<Integer,Chunk> chunks = new ConcurrentHashMap<Integer,Chunk>();
	public ConcurrentHashMap<Integer,Entity> entities = new ConcurrentHashMap<Integer,Entity>();

}
