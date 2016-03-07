package com.pixels.packet;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import com.pixels.communication.CommunicationServlet;
import com.pixels.entity.Entity;
import com.pixels.piece.Piece;
import com.pixels.tile.Tile;
import com.pixels.world.Chunk;

public class PacketUpdateWorld extends Packet {
	
	public PacketUpdateWorld() {
		this.id = 8;
	}

	@Override
	public void writeData(CommunicationServlet servlet) throws IOException {

		boolean isFirstChunk = true;
		
		int x1 = minChunkXLoaded;
		int y1 = minChunkYLoaded;
		int x2 = maxChunkXLoaded;
		int y2 = maxChunkYLoaded;
		
		for (Integer index : chunks.keySet()) {
			Chunk c = chunks.get(index);
			if (isFirstChunk) {
				minChunkXLoaded = c.chunkX;
				maxChunkXLoaded = c.chunkX;
				minChunkYLoaded = c.chunkY;
				maxChunkYLoaded = c.chunkY;
				isFirstChunk = false;
			} else {
				if (c.chunkX < minChunkXLoaded)
					minChunkXLoaded = c.chunkX;
				if (c.chunkX > maxChunkXLoaded)
					maxChunkXLoaded = c.chunkX;
				if (c.chunkY < minChunkYLoaded)
					minChunkYLoaded = c.chunkY;
				if (c.chunkY > maxChunkYLoaded)
					maxChunkYLoaded = c.chunkY;
			}
			
			if (c.chunkX >= x1 && c.chunkX <= x2) {
				if (c.chunkY >= y1 && c.chunkY <= y2) {
					chunks.remove(index);
				}
			}
		}
		
		for (Integer index : entities.keySet()) {
			Entity e = entities.get(index);
			if (e.posX >= (x1>>4) && e.posX <= ((x2+1)>>4)) {
				if (e.posY >= (y1>>4) && e.posY <= ((y2+1)>>4)) {
					entities.remove(index);
				}
			}
		}

		
		writeChunks(servlet);
		writeEntities(servlet);
		
		servlet.getOutput().writeInt(minChunkXLoaded);
		servlet.getOutput().writeInt(minChunkYLoaded);
		servlet.getOutput().writeInt(maxChunkXLoaded);
		servlet.getOutput().writeInt(maxChunkYLoaded);
		
	}

	@Override
	public void readData(CommunicationServlet servlet) throws IOException {

		minChunkXLoaded = servlet.getInput().readInt();	
		minChunkYLoaded = servlet.getInput().readInt();		
		maxChunkXLoaded = servlet.getInput().readInt();		
		maxChunkYLoaded = servlet.getInput().readInt();		
		
		
		PacketHandler.handlePacketUpdateWorld(this, servlet);

	}
	
	public void writeChunks(CommunicationServlet servlet) throws IOException {
		
		servlet.getOutput().writeInt(chunks.size());
		
		for (int key : chunks.keySet()) {
			writeChunk(chunks.get(key), servlet);
		}
		
	}
	
	public void writeEntities(CommunicationServlet servlet) throws IOException {
		
		servlet.getOutput().writeInt(entities.size());
		
		for (int key : entities.keySet()) {
			writeEntity(entities.get(key), servlet);
		}
	}
	
	public void writeChunk(Chunk chunk, CommunicationServlet servlet) throws IOException {
		
		servlet.getOutput().writeInt(chunk.chunkX);
		servlet.getOutput().writeInt(chunk.chunkY);
		
		for (int i = 0; i < 256; i++) {
			writeTile(chunk.tiles.get(i), servlet);
			writePiece(chunk.pieces.get(i), servlet);
		}
	}
	
	public void writeTile(Tile tile, CommunicationServlet servlet) throws IOException {
		servlet.getOutput().writeInt(tile.id);
	}
	
	public void writePiece(Piece piece, CommunicationServlet servlet) throws IOException {
		if (piece != null)
			servlet.getOutput().writeInt(piece.id);
		else
			servlet.getOutput().writeInt(0);
	}
	
	public void writeEntity(Entity e, CommunicationServlet servlet) throws IOException {
		servlet.getOutput().writeInt(e.serverID);
		servlet.getOutput().writeInt(e.id);
		servlet.getOutput().writeInt(e.positionKey);
		servlet.getOutput().writeFloat(e.posX);
		servlet.getOutput().writeFloat(e.posY);
		e.writeEntityData(servlet);
	}
	
	public int minChunkXLoaded, minChunkYLoaded, maxChunkXLoaded, maxChunkYLoaded;
	public ConcurrentHashMap<Integer,Chunk> chunks = new ConcurrentHashMap<Integer,Chunk>();
	public ConcurrentHashMap<Integer,Entity> entities = new ConcurrentHashMap<Integer,Entity>();

}
