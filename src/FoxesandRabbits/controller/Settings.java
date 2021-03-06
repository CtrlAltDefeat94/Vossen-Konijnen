package FoxesandRabbits.controller;

import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import FoxesandRabbits.model.Borg;
import FoxesandRabbits.model.Fox;
import FoxesandRabbits.model.Rabbit;

/**
 * class designed to create and control the settings frame
 * @author Dani�l Slobben *
 */
public class Settings {
	private JFrame frame;
	
	public Settings() {
		frame = new JFrame("Settings");
		frame.setIconImage(new ImageIcon(getClass().getResource("/files/Settings-Icon-4.png")).getImage());
		frame.add(createWindow());
		frame.pack();
		frame.setVisible(false);
	}
	
	/**
	 * the method to create the JTabbedPane
	 * @return the JTabbedPane that was created
	 */
	private JTabbedPane createWindow()
	{
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel panelFox = new JPanel();
	    panelFox.setLayout(new GridLayout(0, 2, 5, 5));
	    
	    JLabel labelAgeFox = new JLabel("Set the Foxes maximum age");
		JSlider sliderAgeFox = new JSlider(JSlider.HORIZONTAL, 0, 500, Fox.getMaxAge());		
		sliderAgeFox.setMajorTickSpacing(100);
		sliderAgeFox.setPaintTicks(true);
		sliderAgeFox.setPaintLabels(true);
		sliderAgeFox.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				Fox.setMaxAge(source.getValue());				
			}
		});
		panelFox.add(labelAgeFox);
		panelFox.add(sliderAgeFox);
		
		JLabel labelFoodValueFox = new JLabel("Set the amount of food a Fox get from a Rabbit");
		JSlider sliderFoodValueFox = new JSlider(JSlider.HORIZONTAL, 0, 100, Fox.getRabbitFoodValue());
		sliderFoodValueFox.setMajorTickSpacing(10);
		sliderFoodValueFox.setPaintTicks(true);
		sliderFoodValueFox.setPaintLabels(true);
		sliderFoodValueFox.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				Fox.setRabbitFoodValue(source.getValue());				
			}
		});
		panelFox.add(labelFoodValueFox);
		panelFox.add(sliderFoodValueFox);
		
		JLabel labelBreedingFox = new JLabel("Set the Foxes breeding age");
		JSlider sliderBreedingFox = new JSlider(JSlider.HORIZONTAL, 0, 100, Fox.getBreedingAge());
		sliderBreedingFox.setMajorTickSpacing(10);
		sliderBreedingFox.setPaintTicks(true);
		sliderBreedingFox.setPaintLabels(true);
		sliderBreedingFox.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				Fox.setBreedingAge(source.getValue());				
			}
		});
		panelFox.add(labelBreedingFox);
		panelFox.add(sliderBreedingFox);
		
		JLabel labelLitterSizeFox = new JLabel("Set the Foxes maximum litter size");
		JSlider sliderLitterSizeFox = new JSlider(JSlider.HORIZONTAL, 0, 20, Fox.getLitterSize());
		sliderLitterSizeFox.setMajorTickSpacing(3);
		sliderLitterSizeFox.setPaintTicks(true);
		sliderLitterSizeFox.setPaintLabels(true);
		sliderLitterSizeFox.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				Fox.setLitterSize(source.getValue());				
			}
		});
		panelFox.add(labelLitterSizeFox);
		panelFox.add(sliderLitterSizeFox);		
		
		tabbedPane.add("Fox", panelFox);
//---------------------------------------------------------------------------------------------------//
		
		JPanel panelRabbit = new JPanel();
	    panelRabbit.setLayout(new GridLayout(0, 2, 5, 5));
	    
	    JLabel labelAgeRabbit = new JLabel("Set the Rabbits maximum age");
		JSlider sliderAgeRabbit = new JSlider(JSlider.HORIZONTAL, 0, 500, Rabbit.getMaxAge());		
		sliderAgeRabbit.setMajorTickSpacing(100);
		sliderAgeRabbit.setPaintTicks(true);
		sliderAgeRabbit.setPaintLabels(true);
		sliderAgeRabbit.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				Rabbit.setMaxAge(source.getValue());				
			}
		});
		panelRabbit.add(labelAgeRabbit);
		panelRabbit.add(sliderAgeRabbit);
		
		JLabel labelBreedingRabbit = new JLabel("Set the Rabbits breeding age");
		JSlider sliderBreedingRabbit = new JSlider(JSlider.HORIZONTAL, 0, 100, Rabbit.getBreedingAge());
		sliderBreedingRabbit.setMajorTickSpacing(10);
		sliderBreedingRabbit.setPaintTicks(true);
		sliderBreedingRabbit.setPaintLabels(true);
		sliderBreedingRabbit.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				Rabbit.setBreedingAge(source.getValue());				
			}
		});
		panelRabbit.add(labelBreedingRabbit);
		panelRabbit.add(sliderBreedingRabbit);
		
		JLabel labelLitterSizeRabbit = new JLabel("Set the Rabbits maximum litter size");
		JSlider sliderLitterSizeRabbit = new JSlider(JSlider.HORIZONTAL, 0, 21, Rabbit.getLitterSize());
		sliderLitterSizeRabbit.setMajorTickSpacing(3);
		sliderLitterSizeRabbit.setPaintTicks(true);
		sliderLitterSizeRabbit.setPaintLabels(true);
		sliderLitterSizeRabbit.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				Rabbit.setLitterSize(source.getValue());				
			}
		});
		panelRabbit.add(labelLitterSizeRabbit);
		panelRabbit.add(sliderLitterSizeRabbit);		
		
		JLabel labelFoodValueRabbit = new JLabel("Set the amount of food a Rabbit gets from Grass");
		JSlider sliderFoodValueRabbit = new JSlider(JSlider.HORIZONTAL, 0, 21, Rabbit.getFoodValue());		
		sliderFoodValueRabbit.setMajorTickSpacing(3);
		sliderFoodValueRabbit.setPaintTicks(true);
		sliderFoodValueRabbit.setPaintLabels(true);
		sliderFoodValueRabbit.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				Rabbit.setFoodValue(source.getValue());				
			}
		});
		panelRabbit.add(labelFoodValueRabbit);
		panelRabbit.add(sliderFoodValueRabbit);
		
		tabbedPane.add("Rabbit", panelRabbit);
		
//--------------------------------------------------------------------------------------------------------
		
		JPanel panelBorg = new JPanel();
	    panelBorg.setLayout(new GridLayout(0, 2, 5, 5));
	    
	    JLabel labelAgeBorg = new JLabel("Set the Borg maximum age");
		JSlider sliderAgeBorg = new JSlider(JSlider.HORIZONTAL, 0, 1000, Borg.getAge());		
		sliderAgeBorg.setMajorTickSpacing(200);
		sliderAgeBorg.setPaintTicks(true);
		sliderAgeBorg.setPaintLabels(true);
		sliderAgeBorg.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				Borg.setAge(source.getValue());				
			}
		});
		panelBorg.add(labelAgeBorg);
		panelBorg.add(sliderAgeBorg);
		
		JLabel labelBreedingBorg = new JLabel("Set the Borg Energy Level");
		JSlider sliderBreedingBorg = new JSlider(JSlider.HORIZONTAL, 0, 60, Borg.getEnergy());
		sliderBreedingBorg.setMajorTickSpacing(6);
		sliderBreedingBorg.setPaintTicks(true);
		sliderBreedingBorg.setPaintLabels(true);
		sliderBreedingBorg.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				Borg.setEnergy(source.getValue());				
			}
		});
		panelBorg.add(labelBreedingBorg);
		panelBorg.add(sliderBreedingBorg);
		
		JLabel labelChanceBorg = new JLabel("Set the chance that a borg kills a Rabbit or Fox");
		JSlider sliderChanceBorg = new JSlider(JSlider.HORIZONTAL, 0, 100, Borg.getAssimilationChance());
		sliderChanceBorg.setMajorTickSpacing(10);
		sliderChanceBorg.setPaintTicks(true);
		sliderChanceBorg.setPaintLabels(true);
		sliderChanceBorg.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				Borg.setAssimilationChance(source.getValue());				
			}
		});
		panelBorg.add(labelChanceBorg);
		panelBorg.add(sliderChanceBorg);
		
		tabbedPane.add("Borg", panelBorg);		
//----------------------------------------------------------------------------------------------		
		return tabbedPane;
	}
    /**
     * method to set the visibility of the frame
     */
	public void setVisibilitySettings() {
		frame.setVisible(true);	
		
	}
	

}
