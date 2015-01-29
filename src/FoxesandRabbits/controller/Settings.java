package FoxesandRabbits.controller;

import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import FoxesandRabbits.model.Fox;

public class Settings {
	private JFrame frame;
	
	public Settings() {
		frame = new JFrame("Settings");
		frame.add(createWindow());
		frame.pack();
		frame.setVisible(true);
	}
	
	private JTabbedPane createWindow()
	{
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel panelFox = new JPanel();
	    panelFox.setLayout(new GridLayout(0, 2, 5, 5));
	    
	    JLabel labelAge = new JLabel("Set the Fox maximum age");
		JSlider sliderAge = new JSlider(JSlider.HORIZONTAL, 10, 1000, Fox.getMaxAge());
		sliderAge.setMinorTickSpacing(1);
		sliderAge.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				Fox.setMaxAge(source.getValue());				
			}
		});
		panelFox.add(labelAge);
		panelFox.add(sliderAge);
		
		JLabel labelFoodValue = new JLabel("Set the Foxes Rabbit_Food_Value");
		JSlider sliderFoodValue = new JSlider(JSlider.HORIZONTAL, 1, 100, Fox.getRabbitFoodValue());
		sliderFoodValue.setMinorTickSpacing(1);
		sliderFoodValue.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				Fox.setRabbitFoodValue(source.getValue());				
			}
		});
		panelFox.add(labelFoodValue);
		panelFox.add(sliderFoodValue);
		
		tabbedPane.add(panelFox);
		return tabbedPane;
	}

	public static void setVisibilitySettings() {
		// TODO Auto-generated method stub
		
	}
	public JFrame returnFrame()
	{
		return frame;
	}
	

}
