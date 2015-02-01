package FoxesandRabbits.controller;

import java.awt.Color;

import javax.swing.*;

import FoxesandRabbits.model.Alpaca;
import FoxesandRabbits.model.Borg;
import FoxesandRabbits.model.Fox;
import FoxesandRabbits.model.Hunter;
import FoxesandRabbits.model.Rabbit;
import FoxesandRabbits.runner.Simulator;
import FoxesandRabbits.view.GraphView;
import FoxesandRabbits.view.PieChart;

/**
 * the class to create the views frame
 * @author Daniël Slobben
 *
 */
public class Views {
	JFrame frame;
	Simulator simulator;
	
	public Views(Simulator simulator) {
		this.simulator = simulator;
		frame = new JFrame("Alternative Views");
		frame.setIconImage(new ImageIcon(getClass().getResource("/files/graph-icon.png")).getImage());
		frame.add(createAltButton());
		frame.pack();
		frame.setVisible(false);
		
	}
	public JPanel createAltButton()
	{
		JPanel panel = new JPanel();
		JTabbedPane tabbedPane = new JTabbedPane();
		
		GraphView subPanel1 = new GraphView(200, 280, 200, simulator);
		subPanel1.setColor(Rabbit.class, Color.ORANGE);
		subPanel1.setColor(Fox.class, Color.BLUE);
		subPanel1.setColor(Borg.class, Color.BLACK);
		subPanel1.setColor(Hunter.class, Color.RED);
        subPanel1.setColor(Alpaca.class, Color.PINK);
		
		PieChart subPanel2 = new PieChart(200, 280, simulator);
		subPanel2.setColor(Rabbit.class, Color.ORANGE);
		subPanel2.setColor(Fox.class, Color.BLUE);
		subPanel2.setColor(Borg.class, Color.BLACK);
		subPanel2.setColor(Hunter.class, Color.RED);
        subPanel2.setColor(Alpaca.class, Color.PINK);
		
		tabbedPane.add("Graphview", subPanel1);
		tabbedPane.add("PieChart", subPanel2);
		
		panel.add(tabbedPane);
		
		return panel;
	}
	/**
	 * method to set the visibility of the Frame
	 */
	public void setVisibilityViews()
	{
		frame.setVisible(true);
	}
	

}
