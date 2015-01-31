package FoxesandRabbits.controller;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import FoxesandRabbits.runner.DynamicThread;
import FoxesandRabbits.runner.Simulator;


public class LeftMenu {
    private Simulator simulator;
    private int step;

	public LeftMenu(Simulator simulator, int steps) {
        this.simulator = simulator;
        step = steps;
	}
	
    public JPanel createButtons()
    {
    	JPanel leftMenu = new JPanel();
		leftMenu.setLayout(new GridLayout(0, 1));
        
        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(new ActionListener() {
                           @Override
						public void actionPerformed(ActionEvent e) { DynamicThread.pause(); }
                       });
        leftMenu.add(pauseButton);
        
        JButton oneStepButton = new JButton("One Step");
        oneStepButton.addActionListener(new ActionListener() {
                           @Override
						public void actionPerformed(ActionEvent e) { simulator.simulate(1); }
                       });
        leftMenu.add(oneStepButton);
        
        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
                           @Override
						public void actionPerformed(ActionEvent e) {
                        	   DynamicThread thread1 = new DynamicThread("thread1", simulator);
                       }});
        leftMenu.add(startButton);
        
        JButton hundredButton = new JButton("Hundred Step");
        hundredButton.addActionListener(new ActionListener() {
        	@Override
        	   public void actionPerformed(ActionEvent arg0) {
        	    
        	    //disableButton(hundredButton);
        	    
        	    Thread thread = new Thread(new Runnable(){

        	     @Override
        	     public void run() {
        	      simulator.simulate(100);
        	      //enableButton(hundredButton);
        	     }
        	     
        	    });
        	    thread.start();
        	}});
                           
        leftMenu.add(hundredButton);
        
        JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				simulator.reset();
			}
		});
		leftMenu.add(resetButton);
		
		JButton alpacalypseButton = new JButton("ALPACAlypse");
		alpacalypseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				simulator.alpacalypse();
			}
		});
		leftMenu.add(alpacalypseButton);
				
		JButton DiseaseButton = new JButton("Myxomatosis");
		DiseaseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Simulator.DiseaseButton();
			}
		});
		
		leftMenu.add(DiseaseButton);
		
		JButton EbolaButton = new JButton("Ebola!!!");
		EbolaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Simulator.EbolaButton();
			}
		});
		
		leftMenu.add(EbolaButton);
		leftMenu.add(resetButton);
		
		
		
        return leftMenu;
    }
    
    private void disableButton(JButton button)
    {
        button.setEnabled(false);
    }
    private void enableButton(JButton button)
    {
        button.setEnabled(true);
    }
}
