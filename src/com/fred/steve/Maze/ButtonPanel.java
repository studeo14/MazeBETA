package com.fred.steve.Maze;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.fred.steve.Maze.MakeMap.MakeMap;

@SuppressWarnings("serial")
public class ButtonPanel extends JPanel{
	static JButton end,makeMap,loadMap;
	static JLabel points;
	
	public ButtonPanel(){
		super();
		
		setPreferredSize(new Dimension(550, 35));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		
		points = new JLabel();
		end = new JButton(new AbstractAction("End"){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(ABORT);
			}
		});
		makeMap = new JButton(new AbstractAction("Make Map"){
			@Override
			public void actionPerformed(ActionEvent e) {
				new MakeMap();
			}
		});
		loadMap = new JButton(new AbstractAction("Load Map"){
			@Override
			public void actionPerformed(ActionEvent e){
				UI.mapContainer.loadMap();
			}
		});
		add(end);
		add(makeMap);
		add(loadMap);
		add(points);
	}
}
