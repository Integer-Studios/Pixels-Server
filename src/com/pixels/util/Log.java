package com.pixels.util;

public class Log {
	
	public static void print(ThreadName t, Object o) {
		if (t == ThreadName.MAIN && main) {
			System.out.println("[Pixel Realms Server] (main) " + o);
		}
		if (t == ThreadName.SERVER && server) {
			System.out.println("[Pixel Realms Server] (server) " + o);
		}
		if (t == ThreadName.SERVLET && servlet) {
			System.out.println("[Pixel Realms Server] (servlet) " + o);
		}
	}
	
	public static void error(ThreadName t, Object o) {
		if (t == ThreadName.MAIN && main) {
			System.out.println("[ERROR] (main)" + o);
		}
		if (t == ThreadName.SERVER && server) {
			System.out.println("[ERROR] (server)" + o);
		}
		if (t == ThreadName.SERVLET && servlet) {
			System.out.println("[ERROR] (servlet) " + o);
		}
	}
	
	public static boolean main = true;
	public static boolean server = true;
	public static boolean servlet = true;

}
