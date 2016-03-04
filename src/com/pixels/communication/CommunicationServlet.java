package com.pixels.communication;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import com.pixels.packet.Packet;
import com.pixels.packet.PacketLogout;
import com.pixels.player.PlayerManager;
import com.pixels.communication.CommunicationServletReaderThread;
import com.pixels.communication.CommunicationServletWriterThread;

public class CommunicationServlet {
	
	public CommunicationServlet(Socket s) {
		
		socket = s;
		
		try {
			this.input = new DataInputStream(socket.getInputStream());
			this.output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream(), 5120));
			this.reader = new CommunicationServletReaderThread(this);
			this.writer = new CommunicationServletWriterThread(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void start() {
		
		this.running = true;
		this.reader.start();
		this.writer.start();
		
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public DataInputStream getInput() {
		return input;
	}
	
	public DataOutputStream getOutput() {
		return output;
	}
	
	public void addPacket(Packet packet) {
		
		packetQue.add(packet);
		
	}
	
	public boolean readPacket() {
		
		Packet packet = Packet.readPacket(this);
		if (packet != null)
			return true;
		else		
			return false;
		
	}
	
	public boolean writePacket() {
		
		if (packetQue.size() > 0) {
			
			Packet packet = packetQue.get(0);
			if (packet != null) {
				try {
					Packet.writePacket(packet, this);
					output.flush();
				} catch (IOException e) {
					if (isRunning()) {
						disconnect(false);

					}
				}
				packetQue.remove(0);
				return true;
				
			}

		}
		
		return false;
	}
	
	public void disconnect(boolean packet) {
		int userID = getKeyByValue(PlayerManager.connections, this);
		int serverID = PlayerManager.players.get(userID);
		running = false;
		if (!packet) {
		
			PlayerManager.broadcastPacket(new PacketLogout(userID, serverID));

		} else {
			
			PlayerManager.broadcastPacketExcludingPlayer(new PacketLogout(userID, serverID), userID);
			
		}
		
		PlayerManager.logout(userID);
		
	}
	
	public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
	    for (Entry<T, E> entry : map.entrySet()) {
	        if (Objects.equals(value, entry.getValue())) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}
	
	public Socket socket;
	private volatile Thread reader;
	private volatile Thread writer;
	private volatile DataInputStream input;
	private volatile DataOutputStream output;
	private boolean running = false;
	private ArrayList<Packet> packetQue = new ArrayList<Packet>();

}
