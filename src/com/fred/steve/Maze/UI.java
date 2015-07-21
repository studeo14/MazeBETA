package com.fred.steve.Maze;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class UI extends JFrame{
	
	public static final int TIMER = 20;
	public static ButtonPanel buttonContainer = new ButtonPanel();
	public static MapPanel mapContainer = new MapPanel();
	public static final int X_SIZE = 550, Y_SIZE = 450;
	
	public UI(){
		super("MazeBETA!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		getContentPane().add(buttonContainer);
		getContentPane().add(mapContainer);

		
		pack();
		setSize(X_SIZE,Y_SIZE);
		setVisible(true);
		
		setLocationRelativeTo(null);
		setResizable(false);
		
		JOptionPane.showMessageDialog(mapContainer, "In order to move the ball you must click the "
				+ " map area.","MazeBETA!" 
				,JOptionPane.INFORMATION_MESSAGE);
	}

	public static void runGUI(){
		new UI();
		JFrame.setDefaultLookAndFeelDecorated(false);
	}
	
	public static void main(String[] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				runGUI();
			}
		});
	}
	
}
