package com.pixels.communication;


public class CommunicationServletWriterThread extends Thread {

	CommunicationServlet servlet;
	
	public CommunicationServletWriterThread (CommunicationServlet servlet) {
		
		this.servlet = servlet;
		
	}
	
	public void run() {
		
		while (servlet.isRunning()) {
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (servlet.isRunning()) 
				servlet.writePacket();
			else 
				break;
				
		}
		
		if (servlet.isRunning()) {
			servlet.disconnect();
		}
		
	}
	
}
