package com.fred.steve.Maze;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class Line {
	//variables
	int startX, startY;
	int endX, endY;
	JPanel pane;
	public enum ALIGNMENT{VERTICAL,HORIZONTAL};
	public ALIGNMENT align;
	
	/*
	 * constructor
	 * 
	 * Uses the coordinates from the text file and the JPanel to assign to
	 * the Line class' local variables
	 */
	public Line(JPanel pane, int startX, int startY, int endX, int endY){
		this.pane = pane;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		getAlignment();
	}
	
	
	private void getAlignment() {
		if(startX == endX){
			align = ALIGNMENT.VERTICAL;
		}else if(startY == endY){
			align = ALIGNMENT.HORIZONTAL;
		}else{
			align = null;
		}
	}
	public Rectangle getBound(){
		int height=1, width=1;
		
		switch(align){
		case VERTICAL:
			height = Math.abs(startY-endY);
			break;
		case HORIZONTAL:
			width = Math.abs(startX-endX);
			break;
		}
		return new Rectangle(startX, startY, width, height);
	}


	/*
	 * Draw the line using the defined coordinates
	 */
	public void draw(Graphics g){
		System.out.println("Successful Line!!"); //why not?
		g.setColor(Color.WHITE); //set color to white
		g.drawLine(startX, startY, endX, endY);
	}
}
