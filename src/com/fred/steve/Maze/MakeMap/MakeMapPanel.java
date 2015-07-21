package com.fred.steve.Maze.MakeMap;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import com.fred.steve.Maze.Line;
import com.fred.steve.Maze.MapPanel;
import com.fred.steve.Maze.ProjectVariables;
import com.fred.steve.Maze.UI;

public class MakeMapPanel extends JPanel implements MouseListener{

	
	private static final long serialVersionUID = -1654843619961842510L;
	public static enum ACTIONS{ADD_LINE, ADD_OBJECTIVE};
	public static ACTIONS currentAction  = ACTIONS.ADD_LINE;
	static ArrayList<String> coords = new ArrayList<String>();
	static ArrayList<Line> lines = new ArrayList<Line>();
	static Integer[] objectiveCoords = {0, 0}; //Default Value
	private static int startX, startY, endX, endY;
	private static JPanel myPane = MakeMap.myMapPanel;
	private static Line currentLine = null;
	private static int currMouseX, currMouseY;
	
	public MakeMapPanel(){
		this.setBackground(Color.BLACK);
		this.resetMap();
		this.setFocusable(true);
		this.addMouseListener(this);
		this.addMouseMotionListener(new LineUIThread());
	}

	public static void setAction(ACTIONS addLine) {
		currentAction = addLine;
		System.out.println("Make map action changed");
	}
	/**
	 * Here we will save the map to an appropriate file at a user specified location. will use  a buffered writer.
	 */
	public void save() {
		JOptionPane.showMessageDialog(this,
				"Remember, we can only load .txt and .mapx files! so save accordingly please. :)");
		JFileChooser chooser = new JFileChooser();
		String pathToFile = null;
		chooser.setCurrentDirectory(new File("."));
		chooser.setFileFilter(new FileFilter(){
			@Override
			public boolean accept(File arg0) {
				return arg0.getName().toLowerCase().endsWith(".mapx")||
						arg0.getName().toLowerCase().endsWith(".txt")||
						arg0.isDirectory();
			}
			@Override
			public String getDescription() {
				return "MAP files";
			}
		});
		int r = chooser.showSaveDialog(this);
		if(r == JFileChooser.APPROVE_OPTION){
			pathToFile = chooser.getSelectedFile().getAbsolutePath();
		}
		Writer out = null;
		//pathToFile = JOptionPane.showInputDialog(this, "Where would you like to save your map??");
	//	String fileName = JOptionPane.showInputDialog(this, "What's the name of your map?");
		//pathToFile += (fileName +".mapx");
		try{
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(pathToFile), "utf-8"));
		}catch(IOException e){
			e.printStackTrace();
		}
		try{
			out.write(System.lineSeparator());
			out.write(String.valueOf(this.objectiveCoords[0]) + "," + String.valueOf(this.objectiveCoords[1]) + ";");
			out.write(System.lineSeparator());
			for(String i: coords){
				out.write(i);
				out.write(System.lineSeparator());
			}
			out.write(System.lineSeparator());
		}catch (IOException e){
			e.printStackTrace();
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void loadInto(){
		ProjectVariables.lineList.clear();ProjectVariables.lineList.trimToSize();// just to make sure we have no data overlap.
		ProjectVariables.lineList.addAll(lines); //bug found here if you set theProjectVar list == to the map list then they become linked. fixed with this workaround.
		ProjectVariables.objectiveCoords[0] = this.objectiveCoords[0]; ProjectVariables.objectiveCoords[1] = this.objectiveCoords[1];// the objective
		MapPanel.endCoord[0] = Integer.toString(this.objectiveCoords[0]); MapPanel.endCoord[1] = Integer.toString(this.objectiveCoords[1]);// the objective
		UI.mapContainer.loadFile(); //the actual loading into the game.
//		UI.mapContainer.requestFocus();
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Mouse Clicked!!");
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch(currentAction){
		case ADD_LINE:
			startX = e.getX(); startY = e.getY();
			break;
		case ADD_OBJECTIVE:
			objectiveCoords[0] = e.getX();
			objectiveCoords[1] = e.getY();
			System.out.println("Make Map: Objective Created");
			repaint();
		default:
			break;
		}
				
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch(currentAction){
		case ADD_LINE:
			endX = e.getX();endY = e.getY();
			//make the line either vertical or horizontal
			/*
			 * we do this because making lines that were diagonal weren't supported in out current collision engine. 8-11-14
			 */
			if(Math.abs(endX-startX)>Math.abs(endY-startY)){
				endY = startY;
			}else{
				endX = startX;
			}
			//set x's correctly for collision detection
			if(endX<startX){
				int temp = endX;
				endX = startX;
				startX = temp;
			}
			//set y's correctly for collision detection
			if(endY<startY){
				int temp = endY;
				endY = startY;
				startY = temp;
			}
			lines.add(new Line(this, startX, startY, endX, endY));
			coords.add(String.valueOf(startX) + " "+ String.valueOf(startY)+","+String.valueOf(endX)+" "+String.valueOf(endY)+";");
			startX = 0; startY = 0; endX = 0; endY = 0;
			System.out.println("Make Map: Line Created");
			break;
		default:
			break;
		}
		currentLine = null;
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void paint(Graphics g){
		super.paint(g);
		/*
		 * 
		 * We also need to generate the user objective.
		 * In this case it will be a shape at the end of the maze.
		 * 
		 */
		g.setColor(Color.GREEN);
		if((objectiveCoords[0]!=null) && (objectiveCoords[1]!=null)){
			g.fillRoundRect(objectiveCoords[0], objectiveCoords[1], 20, 20, 5, 5);
		}
		for(Line line: lines){
			line.draw(g); //draw the line
		}
		if(currentLine!=null){
			currentLine.draw(g);
		}	
		Graphics2D g2d = (Graphics2D)g;
		drawMouseGrid(g2d);
	}
	public void drawMouseGrid(Graphics2D g2d){
		g2d.setColor(Color.BLUE);
		final float dash1[] = {5.0f};
		g2d.setStroke(new BasicStroke(1.0f,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER,
                10.0f, dash1, 0.0f));//set to  dashed line
		g2d.drawLine(currMouseX, 0, currMouseX, this.getHeight());
		g2d.drawLine(0, currMouseY, this.getWidth(), currMouseY);
	}
	class LineUIThread implements MouseMotionListener{
		int startX = MakeMapPanel.startX, startY = MakeMapPanel.startY, currX, currY;
		Line currLine;
		
		@Override
		public void mouseDragged(MouseEvent arg0) {
			switch(currentAction){
			case ADD_LINE:
				startX = MakeMapPanel.startX; startY = MakeMapPanel.startY;
				currX = arg0.getX();currY = arg0.getY();
				currLine = new Line(MakeMap.myMapPanel, startX, startY, currX, currY);
				currentLine = currLine;
				break;
			case ADD_OBJECTIVE:
				objectiveCoords[0] = arg0.getX();objectiveCoords[1] = arg0.getY();
				
				break;
			}
			currMouseX = arg0.getX(); currMouseY = arg0.getY();
			repaint();
		}
		@Override
		public void mouseMoved(MouseEvent arg0) {
			currMouseX = arg0.getX(); currMouseY = arg0.getY();
			repaint();		}
	}
	public static void resetMap() {
		coords.clear();coords.trimToSize();
		objectiveCoords[0] = 0;objectiveCoords[1] = 0;
		lines.clear();lines.trimToSize();
	}

	public void undoLastLine() {
		try{
			coords.remove(coords.size() - 1);coords.trimToSize();
			lines.remove(lines.size() - 1);lines.trimToSize();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		MakeMap.myMapPanel.repaint();
		}
	}

}
