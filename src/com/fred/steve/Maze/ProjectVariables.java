package com.fred.steve.Maze;

import java.util.ArrayList;

public class ProjectVariables {
	public static String mapPath = null;
	public static ArrayList<Line> lineList = new ArrayList<Line>();
	public static Integer[] objectiveCoords = {null, null};
	public final String jarPath = getJarPath();
	
	public String getJarPath(){ 
		String absolutePath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
		absolutePath = absolutePath.replaceAll("%20"," "); // Surely need to do this here
    	return absolutePath;
	}
}
