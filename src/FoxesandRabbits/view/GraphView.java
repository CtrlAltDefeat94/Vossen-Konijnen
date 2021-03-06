package FoxesandRabbits.view;


import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

import java.util.*;

import FoxesandRabbits.logic.Field;
import FoxesandRabbits.logic.FieldStats;
import FoxesandRabbits.runner.Simulator;

/**
 * creates a graphview of the population in the simulation
 * @author Dani�l Slobben
 *
 */
public class GraphView extends AbstractView {
	
	private static final Color LIGHT_GRAY = new Color(0, 0, 0, 40);
	private static GraphPanel graph;
	
	// The classes being tracked by this view
    private Set<Class> classes;
    // A map for storing colors for participants in the simulator
    private Map<Class, Color> colors;

	public GraphView(int height, int width, int startMax, Simulator simulator) {
		super(simulator);
		classes = new HashSet<Class>();
        colors = new HashMap<Class, Color>();
        graph = new GraphPanel(width, height, startMax);
        graph.newRun();
        add(graph);
	}
	
	/**
     * Define a color to be used for a given class of animal.
     * @param animalClass The animal's Class object.
     * @param color The color to be used for the given class.
     */
    public void setColor(Class animalClass, Color color)
    {
        colors.put(animalClass, color);
        classes = colors.keySet();
    }
    
    /**
     * Show the current status of the field. The status is shown by displaying a line graph for
     * two classes in the field. This view currently does not work for more (or fewer) than exactly
     * two classes. If the field contains more than two different types of animal, only two of the classes
     * will be plotted.
     * 
     * @param step Which iteration step it is.
     * @param field The field whose status is to be displayed.
     */
    public void showStatus(int step, Field field, FieldStats stats)
    {
        if(step == 0){
        	graph.newRun();
        } 
        else {
        	graph.update(step, field, stats);
        }
    }
    
    /**
     * Nested class: a JPanel to display the graph.
     */
    class GraphPanel extends JPanel
    {
        private static final double SCALE_FACTOR = 0.8;

        // An internal image buffer that is used for painting. For
        // actual display, this image buffer is then copied to screen.
        private BufferedImage graphImage;
        private HashMap<Class, Integer> lastVal;
        private int yMax;

        /**
         * Create a new, empty GraphPanel.
         */
        public GraphPanel(int width, int height, int startMax)
        {
            graphImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            clearImage();
            
            lastVal = new HashMap<Class, Integer>();
            Iterator<Class> it = classes.iterator();
            while (it.hasNext()) {
            	lastVal.put(it.next(), 0);
            }
            yMax = startMax;
        }

        /**
         * Indicate a new simulator run on this panel.
         */
        public void newRun()
        {
            int height = graphImage.getHeight();
            int width = graphImage.getWidth();

            Graphics g = graphImage.getGraphics();
            clearImage();
            Iterator<Class> it = classes.iterator();
            while (it.hasNext()) {
            	lastVal.put(it.next(), height);
            }
            repaint();
        }

        /**
         * Display a new point of data.
         */
        public void update(int step, Field field, FieldStats stats)
        {
            if (classes.size() >= 2) {
                stats.reset();
                Graphics g = graphImage.getGraphics();

                int height = graphImage.getHeight();
                int width = graphImage.getWidth();

                // move graph one pixel to left
                g.copyArea(1, 0, width-1, height, -1, 0);

                Iterator<Class> it = classes.iterator();
                while (it.hasNext()) {
                	Class class1 = it.next();
                	int count = stats.getPopulationCount(field, class1);
                	
                	int y = height - ((height * count) / yMax) - 1;
                    while (y < 0) {
                        scaleDown();
                        y = height - ((height * count) / yMax) - 1;
                    }
                    g.setColor(LIGHT_GRAY);
                    g.drawLine(width-2, y, width-2, height);
                    g.setColor(colors.get(class1));
                    g.drawLine(width-3, lastVal.get(class1), width-2, y);
                    lastVal.put(class1, y);
                }
                repaint();
            }
        }

        /**
         * Scale the current graph down vertically to make more room at the top.
         */
        public void scaleDown()
        {
            Graphics g = graphImage.getGraphics();
            int height = graphImage.getHeight();
            int width = graphImage.getWidth();

            BufferedImage tmpImage = new BufferedImage(width, (int)(height*SCALE_FACTOR), 
                                                       BufferedImage.TYPE_INT_RGB);
            Graphics2D gtmp = (Graphics2D) tmpImage.getGraphics();

            gtmp.scale(1.0, SCALE_FACTOR);
            gtmp.drawImage(graphImage, 0, 0, null);

            int oldTop = (int) (height * (1.0-SCALE_FACTOR));

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, oldTop);
            g.drawImage(tmpImage, 0, oldTop, null);

            yMax = (int) (yMax / SCALE_FACTOR);
            Iterator<Class> it = classes.iterator();
            while (it.hasNext()) {
            	Class class1 = it.next();
            	lastVal.put(class1, oldTop + (int) (lastVal.get(class1) * SCALE_FACTOR));
            }
            repaint();
        }

        /**
         * Clear the image on this panel.
         */
        public void clearImage()
        {
            Graphics g = graphImage.getGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, graphImage.getWidth(), graphImage.getHeight());
            repaint();
        }

        /**
         * Tell the layout manager how big we would like to be.
         * (This method gets called by layout managers for placing
         * the components.)
         * 
         * @return The preferred dimension for this component.
         */
        public Dimension getPreferredSize()
        {
            return new Dimension(graphImage.getWidth(), graphImage.getHeight());
        }

        /**
         * This component needs to be redisplayed. Copy the internal image 
         * to screen. (This method gets called by the Swing screen painter 
         * every time it want this component displayed.)
         * 
         * @param g The graphics context that can be used to draw on this component.
         */
        public void paintComponent(Graphics g)
        {
            if(graphImage != null) {
                g.drawImage(graphImage, 0, 0, null);
            }
        }
    }
}
