package FoxesandRabbits.controller;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import FoxesandRabbits.runner.DynamicThread;
import FoxesandRabbits.runner.Simulator;

 public class Controller
 {
    private Simulator simulator;
    private Views altViews;
    private Settings settingsCreate;
    private int step;
    public Controller(Simulator simulator, Views altViews, Settings settings, int steps)
    {
        this.simulator = simulator;
        this.altViews = altViews;
        this.settingsCreate = settings;
        step = steps;
    }      
     
    public JMenuBar createMenu()
    {
    	JMenuBar menuBar = new JMenuBar();
    	JMenu menu = new JMenu("Options");
    	menuBar.add(menu);
    	JMenuItem quit = new JMenuItem("Quit");
    	quit.addActionListener(new ActionListener() {
    		@Override
			public void actionPerformed(ActionEvent e) { System.exit(0); }
    	});
    	menu.add(quit);
    	menu = new JMenu("Views");
    	menuBar.add(menu);
    	JMenuItem views = new JMenuItem("Views");
    	views.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) { altViews.setVisibilityViews(); }
    	});
    	menu.add(views);
    	
    	menu = new JMenu("Settings");
    	menuBar.add(menu);
    	JMenuItem settings = new JMenuItem("Settings");
    	settings.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) { settingsCreate.setVisibilitySettings(); }
    	});
    	menu.add(settings);
    	return menuBar;
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