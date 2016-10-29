package com.pixels.world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

import com.pixels.entity.Entity;
import com.pixels.entity.EntityOnlinePlayer;
import com.pixels.packet.PacketDespawnEntity;
import com.pixels.packet.PacketSpawnEntity;
import com.pixels.packet.PacketUpdatePiece;
import com.pixels.piece.Piece;
import com.pixels.player.PlayerManager;
import com.pixels.start.PixelsServer;
import com.pixels.tile.Tile;
import com.pixels.util.CollisionManager;

public class World {
	
	public World(int w, int h) {
		
		chunkWidth = w;
		chunkHeight = h;
		
		entities = new EntityRegister();
		
		generateWorld();
		
	}
	
	public World(String t) {
		generateWorldFromTemplate("resources" + PixelsServer.t.separator + "templates" + PixelsServer.t.separator + t);
		entities = new EntityRegister();
	}
	
	public void generateWorldFromTemplate(String template) {
		try {
			File tilesFile = new File(template + PixelsServer.t.separator + "tiles.png");
			BufferedImage tileMap = ImageIO.read(tilesFile);
			File elevationFile = new File(template + PixelsServer.t.separator + "elevation.png");
			BufferedImage elevationMap = ImageIO.read(elevationFile);
			File humidityFile = new File(template + PixelsServer.t.separator + "humidity.png");
			BufferedImage humidityMap = ImageIO.read(humidityFile);
			File tempFile = new File(template + PixelsServer.t.separator + "tempurature.png");
			BufferedImage tempMap = ImageIO.read(tempFile);
			chunkWidth = tileMap.getWidth() >> 4;
			chunkHeight = tileMap.getHeight() >> 4;
			
			for (int y = 0; y < tileMap.getHeight(); y++) {
				for (int x = 0; x < tileMap.getHeight(); x++) {
					int tileColor = tileMap.getRGB(x, y);
					int eColor = elevationMap.getRGB(x, y);
					int hColor = humidityMap.getRGB(x, y);
					int tColor = tempMap.getRGB(x, y);
					int id = Tile.getIDForTemplateColor(tileColor);
					int elevation = ((eColor & 0x00ff0000) >> 16)-10;
					int humidity = ((hColor & 0x00ff0000) >> 16);
					int tempurature = ((tColor & 0x00ff0000) >> 16)/2;
					
					getOrCreateChunk(x,y).setTile(x, y, new Tile(x, y, id, elevation, humidity, tempurature));
					
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		generatePieces();
	}
	
	public void generatePieces() {
		for (int y = 0; y < chunkHeight<<4; y++) {
			for (int x = 0; x < chunkWidth<<4; x++) {
				
				int id = getTileID(x, y);
				if (id != 1) {
				
				int elevation = getElevation(x, y);
				int down = getElevation(x, y+1);
				if (down - elevation >= 0) {
				
				Random r = new Random();
				//grass
				if (r.nextInt(50) == 0)
					setPiece(x, y, new Piece(x, y, 1));
				else if (r.nextInt(50) == 0)
					setPiece(x, y, new Piece(x, y, 2));
				
				//trees
				else if (r.nextInt(20) == 0)
					setPiece(x, y, new Piece(x, y, 5));
				else if (r.nextInt(20) == 0)
					setPiece(x, y, new Piece(x, y, 6));
				else if (r.nextInt(25) == 0)
					setPiece(x, y, new Piece(x, y, 14));
				else if (r.nextInt(50) == 0)
					setPiece(x, y, new Piece(x, y, 7));
				
				//rocks
				else if (r.nextInt(70) == 0)
					setPiece(x, y, new Piece(x, y, 3));
				else if (r.nextInt(70) == 0)
					setPiece(x, y, new Piece(x, y, 4));
				else if (r.nextInt(70) == 0)
					setPiece(x, y, new Piece(x, y, 15));
				
				//flowers
				else if (r.nextInt(100) == 0)
					setPiece(x, y, new Piece(x, y, 18));
				else if (r.nextInt(100) == 0)
					setPiece(x, y, new Piece(x, y, 8));
				else if (r.nextInt(100) == 0)
					setPiece(x, y, new Piece(x, y, 13));
				
				// bushes
				else if (r.nextInt(100) == 0)
					setPiece(x, y, new Piece(x, y, 11));
				else if (r.nextInt(100) == 0)
					setPiece(x, y, new Piece(x, y, 12));
				
				}
				}
			}
		}
	}
	
	private Chunk getOrCreateChunk(int x, int y) {
		Chunk c =  chunks.get(getChunkIndex(x>>4, y>>4));
		if (c == null) {
			c = new Chunk(x>>4, y>>4);
			chunks.put(getChunkIndex(x>>4, y>>4), c);
		}
		return c;
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
	
	public void despawnEntity(Entity e, boolean broadcast) {
		if (broadcast)
			PlayerManager.broadcastPacket(new PacketDespawnEntity(e));
		
		entities.remove(e.serverID);
	}
	
	public void despawnEntity(int id, boolean broadcast) {
		if (broadcast)
			PlayerManager.broadcastPacket(new PacketDespawnEntity(entities.get(id)));
		
		entities.remove(id);
	}

	public Entity getEntity(int entityID) {
		return entities.get(entityID);
	}
	
	public void checkEntityCollisions(Entity e) {
		int radius = 1;
		
		for (int y = ((int)e.posY - radius); y <= ((int)e.posY + radius); y++) {
			for (int x = ((int)e.posX - radius);x <= ((int)e.posX + radius); x++) {
				//check collision for piece here
				Piece p = getPiece(x, y);
				if (p != null)
					CollisionManager.testPieceCollision(e, p);
				//check collision for all entities here
				ArrayList<Entity> localEntities = entities.get(x, y);
				if (localEntities != null) {
					for (Entity entity : localEntities) {
						if (entity.serverID != e.serverID)
							CollisionManager.testEntityCollision(e, entity);
					}
				}
			}
		}

	}
	
	public void setElevation(int x, int y, int e) {
		getChunk(x, y).getTile(x, y).elevation = e;
	}
	
	public int getElevation(int x, int y) {
		Chunk c = getChunk(x, y);
		if (c == null)
			return 0;
		else
			return c.getTile(x, y).elevation;
	}
	
	public void setPieceID(int x, int y, int id) {
		getChunk(x, y).setPieceID(x, y, id);
		PlayerManager.broadcastPacket(new PacketUpdatePiece(getPiece(x, y)));
	}
	
	public void setPieceIDAndMetadata(int x, int y, int id, int metadata) {
		getChunk(x, y).setPieceIDAndMetadata(x, y, id, metadata);		
	}

	public int getPieceID(int x, int y) {
		return getChunk(x, y).getPieceID(x, y);
	}
	
	public void setPiece(int x, int y, Piece p) {
		getChunk(x, y).setPiece(x, y, p);
	}
	
	public Piece getPiece(int x, int y) {
		return getChunk(x, y).getPiece(x, y);
	}
	
	public int getTileID(int x, int y) {
		return getChunk(x, y).getTile(x, y).id;
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
		
		
		ConcurrentHashMap<Integer, Entity> loadedEntities = new ConcurrentHashMap<Integer, Entity>();

		int chunkX = (int)e.posX >> 4;
		int chunkY = (int)e.posY >> 4;
		int x1 = (chunkX-1) << 4;
		int y1 = (chunkY-1) << 4;
		int x2 = (chunkX+2) << 4;
		int y2 = (chunkY+2) << 4;
		for (int y = y1; y < y2; y++) {
			for (int x = x1; x < x2; x++) {
				if (x >= 0 && x < (chunkWidth<<4) && y >= 0 && y < (chunkHeight<<4)) {
					ArrayList<Entity> entityLocationGroup = entities.get(x, y);
					if (entityLocationGroup != null) {
						for (Entity entity : entityLocationGroup) {
							loadedEntities.put(entity.serverID, entity);
						}
					}
				}
			}
		}
				
		return loadedEntities;
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
