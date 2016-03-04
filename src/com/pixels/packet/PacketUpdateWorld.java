package com.pixels.packet;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import com.pixels.communication.CommunicationServlet;
import com.pixels.entity.Entity;
import com.pixels.world.Chunk;

public class PacketUpdateWorld extends Packet {
	
	public PacketUpdateWorld() {
		this.id = 8;
	}

	@Override
	public void writeData(CommunicationServlet servlet) throws IOException {

		//send back update world, only chunks that aren't already loaded
		for (Object o : chunks.keySet().toArray()) {
			int index = (int)o;
			Chunk c = chunks.get(index);
			if (c.chunkX >= minChunkXLoaded && c.chunkX <= maxChunkXLoaded) {
				if (c.chunkY >= minChunkYLoaded && c.chunkY <= maxChunkYLoaded) {
					chunks.remove(index);
				}
			}
		}
		
	}

	@Override
	public void readData(CommunicationServlet servlet) throws IOException {

		minChunkXLoaded = servlet.getInput().readInt();	
		minChunkYLoaded = servlet.getInput().readInt();		
		maxChunkXLoaded = servlet.getInput().readInt();		
		maxChunkYLoaded = servlet.getInput().readInt();		
		
		PacketHandler.handlePacketUpdateWorld(this, servlet);

	}
	
	public int minChunkXLoaded, minChunkYLoaded, maxChunkXLoaded, maxChunkYLoaded;
	public ConcurrentHashMap<Integer,Chunk> chunks = new ConcurrentHashMap<Integer,Chunk>();
	public ConcurrentHashMap<Integer,Entity> entities = new ConcurrentHashMap<Integer,Entity>();


}
