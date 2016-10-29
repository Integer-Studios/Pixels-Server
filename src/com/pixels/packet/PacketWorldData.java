package com.pixels.packet;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import com.pixels.communication.CommunicationServlet;
import com.pixels.entity.Entity;
import com.pixels.piece.Piece;
import com.pixels.tile.Tile;
import com.pixels.world.Chunk;

public class PacketWorldData extends Packet {
	
	public PacketWorldData() {
		this(null, null);
	}
	
	public PacketWorldData(ConcurrentHashMap<Integer,Chunk> chunks, ConcurrentHashMap<Integer,Entity> entities) {
		this.id = 3;
		this.chunks = chunks;
		this.entities = entities;
	}

	@Override
	public void writeData(CommunicationServlet servlet) throws IOException {
		// TODO Auto-generated method stub
		writeChunks(servlet);
		writeEntities(servlet);
		
	}

	@Override
	public void readData(CommunicationServlet servlet) throws IOException {
		// TODO Auto-generated method stub
		
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
		servlet.getOutput().writeInt(tile.elevation);
		servlet.getOutput().writeInt(tile.humidity);
		servlet.getOutput().writeInt(tile.tempurature);
	}
	
	public void writePiece(Piece piece, CommunicationServlet servlet) throws IOException {
		if (piece != null) {
			servlet.getOutput().writeInt(piece.id);
			servlet.getOutput().writeInt(piece.metadata);
		} else
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
	
	public ConcurrentHashMap<Integer,Chunk> chunks = new ConcurrentHashMap<Integer,Chunk>();
	public ConcurrentHashMap<Integer,Entity> entities = new ConcurrentHashMap<Integer,Entity>();

}
