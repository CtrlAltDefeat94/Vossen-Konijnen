package FoxesandRabbits.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class MenuBar {

    private Views altViews;
    private Settings settingsCreate;
    
	public MenuBar(Views altViews, Settings settings) {
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
    
    private void disableButton(JButton button)
    {
        button.setEnabled(false);
    }
    private void enableButton(JButton button)
    {
        button.setEnabled(true);
    }
}
