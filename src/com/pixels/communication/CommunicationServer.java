package com.pixels.communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class CommunicationServer implements Runnable {
	
	public CommunicationServer(int p) {
		port = p;
	}
	
	@Override
	public void run() {
		
		// Create Server Socket
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(port);
		} 
		catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Socket Loop
		while (listen) {
			
			Socket clientSocket;

			try {

				clientSocket = serverSocket.accept();
				clientSocket.setTcpNoDelay(true);
				CommunicationServlet servlet = new CommunicationServlet(clientSocket);
				servlet.start();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}

		}
		
		// if listen is turned off, close socket
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public int port;
	public boolean listen = true;

}
