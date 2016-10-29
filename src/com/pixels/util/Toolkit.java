package com.pixels.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.util.zip.GZIPInputStream;

public class Toolkit {
	
	public Toolkit() {
		
		if (!this.isWindows()) 			
			separator = "/";
		else
			separator = "\\";
		
		
	}
	
	public String[] parseServerResponse(String response) {
		
		return response.split("!!NEMATTE!!SEPARATOR!!");
		
	}
	
	public void p(Object m) {
		System.out.println(m);
	}
	
	public void e(String m) {
		System.err.println(m);
	}
	
	public String getUsername() {
		return System.getProperty("user.name");
	}
	
	public String getHomeFolder() {
		if (!isMac())
			return null;
		return "/Users/" + getUsername() + "/";
	}
	
	public String getOS() {
		return System.getProperty("os.name") + " " + System.getProperty("os.version");
	}
	
	public String getOSName() {
		return System.getProperty("os.name");
	}
	
	public String getOSVersion() {
		return System.getProperty("os.version");
	}
	
	public boolean isMac() {
		if (getOSName().contains("Mac"))
			return true;
		return false;
	}
	
	public boolean isLinux() {
		if (getOSName().contains("Lin") || getOSName().contains("buntu"))
			return true;
		return false;
	}
	
	public boolean isWindows() {
		if (getOSName().contains("Windows"))
			return true;
		return false;
	}
	
	public String getRunPath() {
		String makepath = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
		makepath = makepath.substring(5);
		makepath = makepath.replaceAll("%20", " ");
		if (makepath.endsWith(".jar")) {
			makepath += "/";
		}
		return makepath;
	}
	
	public String getPath() {
		String path = getRunPath();
		path = shortEndPath(path);

		path = path.substring(0, path.lastIndexOf("/") + 1);

		return path;
	}
	
	public boolean isInJar() {
		String makepath = getRunPath();
		return makepath.lastIndexOf(".jar") == makepath.length() - 4;
	}
	
	
	public Object load(String filename) {
	      try {
	        FileInputStream fis = new FileInputStream(filename);
	        GZIPInputStream gzis = new GZIPInputStream(fis);
	        ObjectInputStream in = new ObjectInputStream(gzis);
	        Object obj = (Object)in.readObject();
	        in.close();
	        return obj;
	      }
	      catch (FileNotFoundException e) {
	    	  return null;
	      }
	      catch (Exception e) {
	          e.printStackTrace();
	      }
	      return null;
	  }
	
	
	public Object[] expandArray(Object[] o) {
	    int length = Array.getLength(o);
	    int newLength = length + 1;
	    Object newArray = Array.newInstance(o.getClass().getComponentType(), newLength);
	    System.arraycopy(o, 0, newArray, 0, length);
	    return (Object[])newArray;
	}
	
	public String[] expandArray(String[] o) {
	    int length = Array.getLength(o);
	    int newLength = length + 1;
	    Object newArray = Array.newInstance(o.getClass().getComponentType(), newLength);
	    System.arraycopy(o, 0, newArray, 0, length);
	    return (String[])newArray;
	}
	
	public int[] expandArray(int[] o) {
	    int length = Array.getLength(o);
	    int newLength = length + 1;
	    Object newArray = Array.newInstance(o.getClass().getComponentType(), newLength);
	    System.arraycopy(o, 0, newArray, 0, length);
	    return (int[])newArray;
	}
	
	public char[] expandArray(char[] o) {
	    int length = Array.getLength(o);
	    int newLength = length + 1;
	    Object newArray = Array.newInstance(o.getClass().getComponentType(), newLength);
	    System.arraycopy(o, 0, newArray, 0, length);
	    return (char[])newArray;
	}
	
	public boolean[] expandArray(boolean[] o) {
	    int length = Array.getLength(o);
	    int newLength = length + 1;
	    Object newArray = Array.newInstance(o.getClass().getComponentType(), newLength);
	    System.arraycopy(o, 0, newArray, 0, length);
	    return (boolean[])newArray;
	}
	
	public Object[] omitFromArray(Object[] obj, int index) {
		for (int i = index; i < obj.length-1; i++) {
			obj[i] = obj[i+1];
		}
		Object newArray = Array.newInstance(obj.getClass().getComponentType(), obj.length-1);
	    System.arraycopy(obj, 0, newArray, 0, obj.length-1);
	    return (Object[])newArray;
	}
	
	public boolean pathIsShortEnd(String path) {
		return path.charAt(path.length()-1) != '/';
	}
	
	public String shortEndPath(String path) {
		if (!pathIsShortEnd(path))
			return path.substring(0, path.length()-1);
		return path;
	}
	
	public boolean pathIsSlashEnd(String path) {
		return path.charAt(path.length()-1) == '/';
	}
	
	public String slashEndPath(String path) {
		if (!pathIsSlashEnd(path))
			return path + separator;
		return path;
	}
	
	public String[] slashEndPaths(String[] paths) {
		String[] newPaths = new String[paths.length];
		for (int i = 0; i < paths.length; i++) {
			newPaths[i] = slashEndPath(paths[i]);
		}
		return newPaths;
	}
	
	public String[] shortEndPaths(String[] paths) {
		String[] newPaths = new String[paths.length];
		for (int i = 0; i < paths.length; i++) {
			newPaths[i] = shortEndPath(paths[i]);
		}
		return newPaths;
	}
	
	public float cutFloat(float f, int range) {
		range ++;
		String s = "" + f;
		int index = s.indexOf(".");
		if (index != -1) {
			s = s.substring(0, index+range);
		}
		return Float.parseFloat(s);
	}
	
	public double cutDouble(double d, int range) {
		range ++;
		String s = "" + d;
		int index = s.indexOf(".");
		if (index != -1) {
			s = s.substring(0, index+range);
		}
		return Double.parseDouble(s);
	}
	public long cutLong(long l, int range) {
		range ++;
		String s = "" + l;
		int index = s.indexOf(".");
		if (index != -1) {
			s = s.substring(0, index+range);
		}
		return Long.parseLong(s);
	}

	
	public String[] reformatSpacesInProgramArguments(String[] args) {
		String s = "";
		for (int i = 0; i < args.length; i++) {
			s+= args[i] + " ";
		}
		s = s.replaceAll("\\\\ ", "!@!");
		args = s.split(" ");
		for (int i = 0; i < args.length; i++) {
			args[i] = args[i].replaceAll("!@!", " ");
		}
		return args;
	}
	
	class PathFinder {
		
		public String find() {
			String path = getRunPath();
			path = shortEndPath(path);
		
			path = path.substring(0, path.lastIndexOf("/") + 1);
		
			return path;
		}
		
		public String getRunPath() {
			String makepath = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
			makepath = makepath.substring(5);
			makepath = makepath.replaceAll("%20", " ");
			if (makepath.endsWith(".jar")) {
				makepath += "/";
			}
			return makepath;
		}

		public boolean pathIsShortEnd(String path) {
			return path.charAt(path.length()-1) != '/';
		}
		
		public String shortEndPath(String path) {
			if (!pathIsShortEnd(path))
				return path.substring(0, path.length()-1);
			return path;
		}
		
	}
	
	public String separator;
	
}