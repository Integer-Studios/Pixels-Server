package com.pixels.packet;

import java.io.IOException;

import com.pixels.communication.CommunicationServlet;
import com.pixels.piece.Piece;

public class PacketUpdatePiece extends Packet {
	
	public PacketUpdatePiece() {
		this.id = 11;
	}
	
	public PacketUpdatePiece(Piece p) {
		this.id = 11;
		posX = p.posX;
		posY = p.posY;
		pieceID = p.id;
		metadata = p.metadata;
	}

	@Override
	public void writeData(CommunicationServlet servlet) throws IOException {
		servlet.getOutput().writeInt(posX);
		servlet.getOutput().writeInt(posY);
		servlet.getOutput().writeInt(pieceID);
		servlet.getOutput().writeInt(metadata);
	}

	@Override
	public void readData(CommunicationServlet servlet) throws IOException {
		posX = servlet.getInput().readInt();
		posY = servlet.getInput().readInt();
		pieceID = servlet.getInput().readInt();
		metadata = servlet.getInput().readInt();
		PacketHandler.handlePacketUpdatePiece(this);
		
	}
	
	public int posX, posY, pieceID, metadata;

}
