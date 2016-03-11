package com.pixels.util;

public class Log {
	
	public static void print(ThreadName t, Object o) {
		if (t == ThreadName.MAIN && main) {
			System.out.println("[Pixel Realms Server] "+count+" (main) " + o);
		}
		if (t == ThreadName.SERVER && server) {
			System.out.println("[Pixel Realms Server] "+count+" (server) " + o);
		}
		if (t == ThreadName.SERVLET && servlet) {
			System.out.println("[Pixel Realms Server] "+count+" (servlet) " + o);
		}
		count++;
	}
	
	public static void error(ThreadName t, Object o) {
		if (t == ThreadName.MAIN && main) {
			System.out.println("[ERROR] "+count+" (main)" + o);
		}
		if (t == ThreadName.SERVER && server) {
			System.out.println("[ERROR] "+count+" (server)" + o);
		}
		if (t == ThreadName.SERVLET && servlet) {
			System.out.println("[ERROR] "+count+" (servlet) " + o);
		}
		count++;
	}
	
	public static boolean main = true;
	public static boolean server = true;
	public static boolean servlet = true;
	public static int count = 0;

}
