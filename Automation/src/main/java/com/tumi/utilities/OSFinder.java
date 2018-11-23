package com.tumi.utilities;

public class OSFinder {

	private static String os = System.getProperty("os.name").toLowerCase();
	
	public static boolean isWindows() {
		
		return (os.indexOf("win") >= 0);
	}
	
	public static boolean isMac() {
		
		return (os.indexOf("mac") >= 0);
	}
	
}
