package FoxesandRabbits.runner;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import FoxesandRabbits.controller.Controller;
import FoxesandRabbits.logic.Field;
import FoxesandRabbits.logic.FieldStats;
import FoxesandRabbits.logic.Location;
import FoxesandRabbits.logic.Randomizer;
import FoxesandRabbits.model.Actor;
import FoxesandRabbits.model.Borg;
import FoxesandRabbits.model.Fox;
import FoxesandRabbits.model.Hunter;
import FoxesandRabbits.model.Rabbit;
import FoxesandRabbits.view.AbstractView;
import FoxesandRabbits.view.GridView;
/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class Simulator extends JFrame
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.08;    
    // The probability that a borg will be created in any given grid position.
    private static final double BORG_CREATION_PROBABILITY = 0.04;
    // The probability that a hunter will be created in any given grid position.
    private static final double HUNTER_CREATION_PROBABILITY = 0.003;
    // The maximum amount of hunters
    private static final int MAX_HUNTERS = 26;

    // List of animals in the field.
    private List<Actor> animals;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private AbstractView gridView;
    //
    private FieldStats stats;

    private Controller controller;
    
    

    private final String STEP_PREFIX = "Step: ";
    private final String POPULATION_PREFIX = "Population: ";
    private JLabel stepLabel, population;

	private List<AbstractView> views;
    
    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        views = new ArrayList<AbstractView>();
        controller = new Controller(this);
        setJMenuBar(controller.createMenu());        

        setTitle("Fox and Rabbit Simulation");
        stepLabel = new JLabel(STEP_PREFIX, SwingConstants.CENTER);
        population = new JLabel(POPULATION_PREFIX, SwingConstants.CENTER);
        JPanel leftMenu = controller.createButtons();
        
        setLocation(100, 50);
        
        animals = new ArrayList<Actor>();
        field = new Field(depth, width);
        stats = new FieldStats();

        // Create a view of the state of each location in the field.
        gridView = new GridView(depth, width, this, stats);
        ((GridView)gridView).setColor(Rabbit.class, Color.ORANGE);
        ((GridView)gridView).setColor(Fox.class, Color.BLUE);
        ((GridView)gridView).setColor(Borg.class, Color.BLACK);
        ((GridView)gridView).setColor(Hunter.class, Color.RED);
        
        Container contents = getContentPane();
        contents.add(stepLabel, BorderLayout.NORTH);
        
        contents.add(((GridView)gridView).getFieldView(), BorderLayout.CENTER);
        contents.add(population, BorderLayout.SOUTH);
        contents.add(leftMenu, BorderLayout.WEST);
        
        pack();
        setVisible(true);
        
        // Setup a valid starting point.
        reset();
    }
    
    public int getStep()
    {
    	return step;
    }
    public Field getField()
    {
    	return field;
    }
    public List<Actor> getAnimals()
    {
    	return animals;
    }
    public GridView getGridView()
    {
    	return ((GridView)gridView);   
    }
    public JLabel getPopulation()
    {
    	return population;
    }
    public JLabel getStepLabel()
    {
    	return stepLabel;
    }
    public FieldStats getStats()
    {
    	return stats;
    }
    
    public void addView(AbstractView view) {
		views.add(view);
	}
	
	public void notifyViews() {
		for(AbstractView v: views) v.updateView();
	}
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * (500 steps).
     */
    public void runLongSimulation()
    {
        simulate(500);
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && ((GridView)gridView).isViable(field); step++) {
            simulateOneStep();
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep()
    {
        step++;
        
        List<Actor> newActors = new ArrayList<Actor>();        
        
        for(Iterator<Actor> it = animals.iterator(); it.hasNext(); ) {
            Actor actor = it.next();
            actor.act(newActors);
            if(! actor.isActive()) {
                it.remove();
            }
        }
        
        // Add the newly born foxes and rabbits to the main lists.
        animals.addAll(newActors);
        notifyViews();
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        animals.clear();
        populate();
        
        // Show the starting state in the view.
        gridView.showStatus(step, field, stats);
    }
    
    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        int hunterAmount = 0;
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Fox fox = new Fox(true, field, location);
                    animals.add(fox);
                }
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Rabbit rabbit = new Rabbit(true, field, location);
                    animals.add(rabbit);
                }
                else if(rand.nextDouble() <= BORG_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Borg borg = new Borg(true, field, location);
                    animals.add(borg);
                }
                else if(rand.nextDouble() <= HUNTER_CREATION_PROBABILITY && hunterAmount < MAX_HUNTERS) {
                    Location location = new Location(row, col);
                    Hunter hunter = new Hunter(true, field, location);
                    animals.add(hunter);
                    hunterAmount++;
                }
                // else leave the location empty.
            }
        }
    }
}
