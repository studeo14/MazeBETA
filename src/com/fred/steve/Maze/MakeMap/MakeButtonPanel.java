package com.fred.steve.Maze.MakeMap;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.fred.steve.Maze.UI;
import com.fred.steve.Maze.MakeMap.MakeMapPanel.ACTIONS;

@SuppressWarnings("serial")
public class MakeButtonPanel extends JPanel{
	static JButton newMap, line, objective, save, loadInto, undo;
	
	public MakeButtonPanel(){
		super();
		
		setPreferredSize(new Dimension(550, 35));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		newMap = new JButton(new AbstractAction("New Map"){
			@Override
			public void actionPerformed(ActionEvent e) {
				MakeMapPanel.resetMap();
				MakeMap.myMapPanel.repaint();
			}
		}); 
				
		line = new JButton(new AbstractAction("Add Lines"){
			@Override
			public void actionPerformed(ActionEvent e) {
				MakeMapPanel.setAction(ACTIONS.ADD_LINE);
			}
		});
		objective = new JButton(new AbstractAction("Add Objective"){
			@Override
			public void actionPerformed(ActionEvent e) {
				MakeMapPanel.setAction(ACTIONS.ADD_OBJECTIVE);
			}
		});
		save = new JButton(new AbstractAction("Save"){
			@Override
			public void actionPerformed(ActionEvent e){
				MakeMap.myMapPanel.save();
			}
		});
		loadInto = new JButton(new AbstractAction("Load Into Game"){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MakeMap.myMapPanel.loadInto();
				UI.mapContainer.repaint();
			}
			
		});
		undo  = new JButton(new AbstractAction("Undo"){
			@Override
			public void actionPerformed(ActionEvent e){
				MakeMap.myMapPanel.undoLastLine();
			}
		});
		add(newMap);
		add(line);
		add(objective);
		add(undo);
		add(save);
		add(loadInto);
	}
}
