package com.fred.steve.Maze;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Sprite{
	int x, y;
	final int HEIGHT = 10, WIDTH = 10, SPEED = 7;
	
	JPanel myPane;
	
	public Sprite(JPanel pane, int x, int y){
		this.x = x;
		this.y = y;
		this.myPane = pane;
	}
	
	public void draw(Graphics g){
		g.setColor(Color.RED);
		g.fillOval(this.x, this.y, this.WIDTH, this.HEIGHT);
	}

	public void setX(int i) {
		this.x = i;
	}
	public void setY(int i){
		this.y = i;
	}
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}

	public void moveBall(String act){
		 switch (act){
		 case "up":
			 System.out.println("up");
			 if ((this.y - SPEED) <= 0){
				 break;
			 }
			 setY(this.y - SPEED);
			 if(Collision()){
				 setY(this.y + SPEED);
			 }
			 break;
		 case "down":
			 System.out.println("right");
			 if ((this.y + SPEED) >= (UI.mapContainer.getHeight() - 15)){
				 break;
			 }
			 setY(this.y + SPEED);
			 if(Collision()){
				 setY(this.y - SPEED);
			 }
			 break;
		 case "right":
			 System.out.println("right");
			 if ((this.x + SPEED) >= (UI.mapContainer.getWidth() - 15)){
				 break;
			 }
			 setX(this.x + SPEED);
			 if(Collision()){
				 setX(this.x - SPEED);
			 }
			 break;
		 case "left":
			 System.out.println("left");
			 if ((this.x - SPEED) <= 0){
				 break;
			 }
			 setX(this.x - SPEED);
			 if(Collision()){
				 setX(this.x + SPEED);
			 }
			 break;
		 default:
			 break;
		 }
	}
	public Rectangle getBounds(){
		return new Rectangle(this.x, this.y, HEIGHT, WIDTH);
	}
	public boolean Collision(){
		for(Line line: ProjectVariables.lineList){
			if(line.getBound().intersects(getBounds())){
				return true;
			}
		}
		if(UI.mapContainer.getEndBound().intersects(getBounds())){
			JOptionPane.showMessageDialog(null, "YOU WIN!!!", "WINNER" 
					,JOptionPane.INFORMATION_MESSAGE);
			setX(5);setY(5);
			return true;
		}
		return false;
	}
	
}
