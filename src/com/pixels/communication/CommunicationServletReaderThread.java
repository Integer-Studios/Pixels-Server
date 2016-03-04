package com.pixels.communication;


public class CommunicationServletReaderThread extends Thread {

	CommunicationServlet servlet;
	
	public CommunicationServletReaderThread (CommunicationServlet servlet) {
		
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
				servlet.readPacket();
			else
				break;
			
		}
		
		if (servlet.isRunning()) {
			servlet.disconnect(false);
		}
		
	}
	
}
