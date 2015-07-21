package com.fred.steve.Maze.MakeMap;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.fred.steve.Maze.UI;

public class MakeMap extends JFrame{
	
	static MakeButtonPanel myButtonPanel = new MakeButtonPanel();
	static MakeMapPanel myMapPanel = new MakeMapPanel();
	
	
	public MakeMap(){
		super("Make Map");
		JFrame.setDefaultLookAndFeelDecorated(false);
		

		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		getContentPane().add(myButtonPanel);
		getContentPane().add(myMapPanel);
		
		pack();
		setSize(550,450);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(UI.mapContainer);
		setLocation(this.getX()+ UI.X_SIZE, this.getY());
		JOptionPane.showMessageDialog(myMapPanel, "Here is where you make your very own custom-map. All you have to do is click and drag for the lines"
				+ "\n"
				+ "and when you want to put the objective in, click the objective button and click anywhere. \nKeep in mind that you can make lines overlap."
				+ "\nHave fun!" 
				);
	}

}
