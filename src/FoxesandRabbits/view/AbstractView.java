package FoxesandRabbits.view;

import javax.swing.JPanel;

import FoxesandRabbits.logic.Field;
import FoxesandRabbits.logic.FieldStats;
import FoxesandRabbits.runner.Simulator;

public abstract class AbstractView extends JPanel {
	
	protected Simulator simulator;
	public AbstractView(Simulator simulator) 
	{
	    this.simulator = simulator;
	    simulator.addView(this);
	}
		
	public Simulator getSimulator() {
       	return simulator;
	}
		
	public void updateView() {
		showStatus(getSimulator().getStep(), getSimulator().getField(), getSimulator().getStats());
	}
		
	public abstract void showStatus(int step, Field field, FieldStats stats);
	
}
