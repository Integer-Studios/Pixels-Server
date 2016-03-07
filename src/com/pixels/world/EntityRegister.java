package com.pixels.world;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import com.pixels.entity.Entity;
import com.pixels.start.PixelsServer;

public class EntityRegister {
	
	public EntityRegister() {
		nextServerID = 0;
	}
	
	public void update(World w) {
		for (Entity e : entityIDMap.values()) {
			e.update(w);
			updatePosition(e);
		}
	}
	
	public void add(Entity entity) {
		
		// still needs touch up for lots and lots of entities
		int id = nextServerID;
		entity.serverID = id;
		entityIDMap.put(id, entity);
		addEntityToPositionMap(entity);
		nextServerID++;
		
	}
	
	public void remove(int id) {
		removeEntityFromPositionMap(id);
		entityIDMap.remove(id);
	}
	
	public void remove(Entity e) {
		removeEntityFromPositionMap(e);
		entityIDMap.remove(e.serverID);
	}
	
	public Entity get(int i) {
		return entityIDMap.get(i);
	}
	
	public ArrayList<Entity> get(int x, int y) {
		
		ArrayList<Integer> ids = getIDs(x,y);
		ArrayList<Entity> entities = new ArrayList<Entity>();
		for (int id : ids) {
			entities.add(get(id));
		}
		return entities;
	}
	
	public ArrayList<Integer> getIDs(int x, int y) {
		
		return entityPositionMap.get(getLocationIndex(x, y));
		
	}
	
	public ArrayList<Entity> getYGroup(int y) {
		
		//TODO for client
		return null;
		
	}
	
	public ArrayList<Integer> getYGroupIDs(int y) {
		
		//TODO for client
		return null;
		
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
		ArrayList<Integer> entityList = entityPositionMap.get(key);
		if (entityList != null) {
			Object[] ids = entityList.toArray();
			entityList = new ArrayList<Integer>();
			for (int a = 0; a < ids.length; a++) {
				int i = (int) ids[a];
				if (i != e.serverID)
					entityList.add(i);
			}
		}
		entityPositionMap.put(key, entityList);
	}
	
	public void removeEntityFromPositionMap(int id) {
		Entity e = entityIDMap.get(id);
		int key = e.positionKey;
		ArrayList<Integer> entityList = entityPositionMap.get(key);
		if (entityList != null) {
			Object[] ids = entityList.toArray();
			entityList = new ArrayList<Integer>();
			for (int a = 0; a < ids.length; a++) {
				int i = (int) ids[a];
				if (i != e.serverID)
					entityList.add(i);
			}
		}
		entityPositionMap.put(key, entityList);
	}
	
	public void updatePosition(Entity e) {
		if (e.positionKey != getLocationIndex(e)) {
			removeEntityFromPositionMap(e);
			addEntityToPositionMap(e);
		}
	}
	
	public void updatePosition(int i) {
		updatePosition(get(i));
	}
	
	private int getLocationIndex(Entity e) {
		return Math.round(e.posY)*(PixelsServer.world.chunkWidth<<4) + Math.round(e.posX);
	}
	
	private int getLocationIndex(int x, int y) {
		return y*(PixelsServer.world.chunkWidth<<4) + x;
	}
	
	public ConcurrentHashMap<Integer,Entity> entityIDMap = new ConcurrentHashMap<Integer,Entity>();
	public ConcurrentHashMap<Integer,ArrayList<Integer>> entityPositionMap = new ConcurrentHashMap<Integer,ArrayList<Integer>>();
	public int nextServerID;

}
