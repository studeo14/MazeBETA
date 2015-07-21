package com.fred.steve.Maze;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

import com.fred.steve.Maze.MakeMap.MakeMap;

@SuppressWarnings("serial")
public class MapPanel extends JPanel{
	/*
	 * The variables
	 * 
	 * Includes: the ball, list to hold the coordinates as they are read,
	 * list to hold the lines, a coordinate array, and the file reader.
	 */
	public Sprite mySprite = new Sprite(this, 5, 5);
	static ArrayList<String> it = new ArrayList<String>();
	
	static int coord[] = new int[4];
	static BufferedReader in;
	public static String[] endCoord;
	
	/*
	 * Constructor.
	 * 
	 * set background color to Black and link to loadFile() and readFile() methods
	 */
	public MapPanel(){
		this.setBackground(Color.BLACK);
		this.addFocusListener(new FocusAdapter(){
			public void focusLost(FocusEvent dv){
				UI.mapContainer.requestFocusInWindow();
			}
		});
		this.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                UI.mapContainer.requestFocus();
                super.mouseClicked(e);
            }
        });
		setKeyBindings();
		loadFile();
	}
	
	public void setKeyBindings() {
		this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "MoveUP");
		this.getActionMap().put("MoveUP", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mySprite.moveBall("up");
				repaint();
			}
		});
		this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "MoveDOWN");
		this.getActionMap().put("MoveDOWN", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mySprite.moveBall("down");
				repaint();
			}
		});
		this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "MoveRIGHT");
		this.getActionMap().put("MoveRIGHT", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mySprite.moveBall("right");
				repaint();
			}
		});
		this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "MoveLEFT");
		this.getActionMap().put("MoveLEFT", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mySprite.moveBall("left");
				repaint();
			}
		});
		
	}

	/*
	 * Attempts to load the Maze file.
	 */
	public void loadFile() {
		if(ProjectVariables.lineList.size()>0){
			//use the current line list
			readFile(true);
		}else if(ProjectVariables.mapPath == null){
			try{
				//print(MapPanel.class.getResource("/res/testMaze.txt").getPath());
				//String s = MapPanel.class.getResource("/res/testMaze.txt").getPath().replaceAll("file:/", "");
				//InputStream p = MapPanel.class.getResourceAsStream("/res/testMaze.txt");
				//print(""+(this.getClass().getResourceAsStream("/res/testMaze.txt").toString()));
				URL u = MapPanel.class.getResource("/res/testMaze.txt");
				in = new BufferedReader(new InputStreamReader(u.openStream()));
			}catch(NullPointerException e){
				print("OOPS");
				print("File not read");
				JOptionPane.showMessageDialog(UI.mapContainer, "File Not Read. \n Now Exiting....\n" + e.toString(),"MazeBETA! - File Error" 
						,JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				System.exit(2);
			}catch(Exception e){
				print("File not read");
				JOptionPane.showMessageDialog(UI.mapContainer, "File Not Read. \n Now Exiting....\n" + e.toString(),"MazeBETA! - File Error" 
						,JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				System.exit(2);
			}
			readFile(false);

		}else{
			try{
				in = new BufferedReader(new FileReader(ProjectVariables.mapPath));
			}catch(Exception e){
				print("File not read");
				JOptionPane.showMessageDialog(UI.mapContainer, "File Not Read. \n Now Exiting....","MazeBETA! - File Error" 
						,JOptionPane.ERROR_MESSAGE);
				System.exit(2);
			}
			readFile(false);
		}
		if (!MapPanel.checkFileValues()){
			ProjectVariables.mapPath = null;
			loadFile();
		}
	}

	/*
	 * loads and splits the Maze file into usable numbers that can be converted into Line objects.
	 */
	private void readFile(Boolean old) {
		if(!old){
			it.clear();it.trimToSize();
			ProjectVariables.lineList.clear();ProjectVariables.lineList.trimToSize();//reset the arrays
			try{
				String str = in.readLine();
				while((str = in.readLine()) != null){
					  it.addAll(Arrays.asList(str.split(";"))); //actually reading the file and adding it to the "it" list
				}
				in.close(); //closing the reader
				System.out.println(it.get(0));
				/*
				 * This for loop does all of the actual splitting of the numbers
				 * 
				 * converts all of the String objects into numbers and creates all of the lines to be used in the
				 * maze.
				 */
				endCoord = it.get(0).split(",");
				ProjectVariables.objectiveCoords[0] = Integer.parseInt(endCoord[0]);ProjectVariables.objectiveCoords[1] = Integer.parseInt(endCoord[1]);
				for(int a = 1; a < it.size();a++){
					String[] ar = it.get(a).split(","); //splitting
					String[] start = ar[0].split(" "); //splitting
					String[] end = ar[1].split(" "); //splitting
					coord[0] = (int)Double.parseDouble(start[0]); //assigning
					coord[1] = (int)Double.parseDouble(start[1]); //assigning
					coord[2] = (int)Double.parseDouble(end[0]); //assigning
					coord[3] = (int)Double.parseDouble(end[1]); //assigning
					ProjectVariables.lineList.add(new Line(UI.mapContainer, coord[0], coord[1], coord[2], coord[3])); //creation of the Line object
				//	UI.mapContainer.repaint(); //refresh the frame.
				}
				System.out.println(ProjectVariables.lineList.size()); //debugging
				System.out.println(it.size()); //debugging
	
			}catch(Exception x){}
		}else{
			endCoord[0] = String.valueOf(ProjectVariables.objectiveCoords[0]);endCoord[1] = String.valueOf(ProjectVariables.objectiveCoords[1]);
			UI.mapContainer.repaint();UI.mapContainer.repaint();			
		}
	}

	public static boolean checkFileValues() {
		int testInt;
		try{
			testInt = Integer.parseInt(endCoord[0]);
			testInt = Integer.parseInt(endCoord[1]);
		}catch(NumberFormatException nfe){
			JOptionPane.showMessageDialog(UI.mapContainer, "Error reading file. Try a new file or edit the existing one.", "File Error -- MAZE beta!", JOptionPane.ERROR_MESSAGE);
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * shortcut for the System.out.println() method
	 */
	public static void print(String txt){
		System.out.println(txt);
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 * 
	 * Is called when the frame refreshes. i.e. when repaint() is used.
	 * paints the frame and then calls each line to draw itself
	 */
	public void paint(Graphics g){
		super.paint(g);
		mySprite.draw(g);
		/*
		 * 
		 * We also need to generate the user objective.
		 * In this case it will be a shape at the end of the maze.
		 * 
		 */
		g.setColor(Color.GREEN);
		g.fillRoundRect(ProjectVariables.objectiveCoords[0], ProjectVariables.objectiveCoords[1], 20, 20, 5, 5);
		print("End Drawn");
		for(Line line: ProjectVariables.lineList){
			line.draw(g); //draw the line
		}
	}

	public Sprite getMySprite() {
		return mySprite;
	}

	public void setMySprite(Sprite mySprite) {
		this.mySprite = mySprite;
	}
	public Rectangle getEndBound(){
		return new Rectangle(ProjectVariables.objectiveCoords[0], ProjectVariables.objectiveCoords[1],20,20);
	}

	public void loadMap() {
		String mapPath = null;
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		chooser.setFileFilter(new FileFilter(){

			@Override
			public boolean accept(File f) {
				return f.getName().toLowerCase().endsWith(".mapx") || 
						f.getName().toLowerCase().endsWith(".txt") ||
						f.isDirectory();
			}

			@Override
			public String getDescription() {
				return "MAP files";
			}
			
		});
		int r = chooser.showOpenDialog(this);
		if(r == JFileChooser.APPROVE_OPTION){
			mapPath = chooser.getSelectedFile().getAbsolutePath();
			System.out.println(mapPath);
		}
		File f = new File(mapPath);
		if(f.exists()){
			if (f.isFile()){
				ProjectVariables.lineList.clear(); ProjectVariables.lineList.trimToSize();
				ProjectVariables.mapPath = mapPath;
				UI.mapContainer.loadFile();
				UI.mapContainer.repaint();
			}else if(f.isDirectory()){
				JOptionPane.showMessageDialog(this, "Sorry, that seems to be a directory. /n Please enter a file name in the path.", "File Error -- MAZE beta!", JOptionPane.ERROR_MESSAGE);
				loadMap();
			}else{
				JOptionPane.showMessageDialog(this, "Woah, that was CRAZY! Please try again.", "File Error -- MAZE beta!", JOptionPane.ERROR_MESSAGE);
				loadMap();
			}
		}else{
			JOptionPane.showMessageDialog(this, "Woah, that was CRAZY! Please try again.", "File Error -- MAZE beta!", JOptionPane.ERROR_MESSAGE);
		}
	}
}
