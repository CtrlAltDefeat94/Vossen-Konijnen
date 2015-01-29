package FoxesandRabbits.controller;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import FoxesandRabbits.runner.Simulator;

 public class Controller
 {
    private Simulator simulator;
    private Views altViews;
    private Settings settingsCreate;
    public Controller(Simulator simulator, Views altViews, Settings settings)
    {
        this.simulator = simulator;
        this.altViews = altViews;
        this.settingsCreate = settings;
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
        
        JButton oneStepButton = new JButton("One Step");
        oneStepButton.addActionListener(new ActionListener() {
                           @Override
						public void actionPerformed(ActionEvent e) { simulator.simulate(1); }
                       });
        leftMenu.add(oneStepButton);
        
        JButton hundredStepButton = new JButton("Hundred Step");
        hundredStepButton.addActionListener(new ActionListener() {
                           @Override
						public void actionPerformed(ActionEvent e) {
                        	    
                        	    disableButton(hundredStepButton);
                        	    Thread thread = new Thread(new Runnable(){

                        	     @Override
                        	     public void run() {
                        	      simulator.simulate(100);
                                  enableButton(hundredStepButton);
                        	     }
                        	     
                        	    });
                        	    thread.start();}
                       });
        leftMenu.add(hundredStepButton);
        
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