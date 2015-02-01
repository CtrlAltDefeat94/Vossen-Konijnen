package FoxesandRabbits.controller;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
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
		leftMenu.setLayout(new GridLayout(8, 1));		

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
                           @Override
						public void actionPerformed(ActionEvent e) {
                        	   DynamicThread thread1 = new DynamicThread("thread1", simulator);
                       }});
        leftMenu.add(startButton);
        
        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(new ActionListener() {
                           @Override
						public void actionPerformed(ActionEvent e) { DynamicThread.pause(); }
                       });
        leftMenu.add(pauseButton);
        
        JButton oneStepButton = new JButton("One Step");
        oneStepButton.addActionListener(new ActionListener() {
                           @Override
						public void actionPerformed(ActionEvent e) { DynamicThread.pause(); simulator.simulate(1); }
                       });
        leftMenu.add(oneStepButton);
                
        JButton hundredButton = new JButton("Hundred Step");
        hundredButton.addActionListener(new ActionListener() {
        	@Override
        	   public void actionPerformed(ActionEvent arg0) {
        	    
        	    //disableButton(hundredButton);

   	    	     DynamicThread.pause();
        	    
        	    Thread thread = new Thread(new Runnable(){

        	     @Override
        	     public void run() {
        	      simulator.simulate(100);
        	     }
        	     
        	    });
        	    thread.start();
        	}});
                           
        leftMenu.add(hundredButton);
		
		JButton alpacalypseButton = new JButton(new ImageIcon(getClass().getResource("/files/alpacalypse-6.png")));
		alpacalypseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound(); simulator.alpacalypse();
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

        
        JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				simulator.reset();
			}
		});
		leftMenu.add(resetButton);
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
    /**
     * Plays a sound 
     * @param the sound to play
     */
    public void playSound()
    {
    	try {
    		 URL is= getClass().getResource("/files/Psycho_Scream.wav");
    	   	AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(is);
    	     Clip clip = AudioSystem.getClip();
    	     clip.open(audioInputStream);
    	     clip.start( );
    	} catch (Exception ex) {
    	     ex.printStackTrace();
    	}
    }

}
