package FoxesandRabbits.view;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

import FoxesandRabbits.logic.Field;
import FoxesandRabbits.logic.FieldStats;
import FoxesandRabbits.runner.Simulator;

/**
 * Creates a pie chart view of the population
 * @author Daniël Slobben
 *
 */
public class PieChart extends AbstractView {

	private static PieChartPanel pie;
	
	// The classes being tracked by this view
    private Set<Class> classes;
    // A map for storing colors for participants in the simulator
    private Map<Class, Color> colors;
	
	public PieChart(int width, int height, Simulator simulator) {
		super(simulator);
		classes = new HashSet<Class>();
		colors = new HashMap<Class, Color>();
        pie = new PieChartPanel(width, height);
        pie.newRun();
        add(pie);
	}
    
	/**
	 * updates the pie chart
	 */
	@Override
	public void showStatus(int step, Field field, FieldStats stats) {
        pie.update(step, field, stats);	
	}
    
	/**
	 * Method to set a color for a specific animal
	 * @param animalClass The animal to set a color for
	 * @param color The color for the animal
	 */
	public void setColor(Class animalClass, Color color) {
		colors.put(animalClass, color);
        classes = colors.keySet();		
	}
	
	class PieChartPanel extends JPanel
    {
        // An internal image buffer that is used for painting. For
        // actual display, this image buffer is then copied to screen.
        private BufferedImage pieImage;
		
		/**
         * Create a new, empty GraphPanel.
         */
        public PieChartPanel(int width, int height)
        {
            pieImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            clearImage();
        }
        
        /**
         * Display a new point of data.
         */
        public void update(int step, Field field, FieldStats stats)
        {
            if (classes.size() >= 2) {
                stats.reset();
                
                Graphics g = pieImage.getGraphics();

                int height = pieImage.getHeight();
                int width = pieImage.getWidth();

                g.setColor(this.getBackground());
    			g.fillRect(0, 0, width, height);
    			
    			int total = 0;    			
    			Iterator<Class> it = classes.iterator();
                while (it.hasNext()) {
                	Class class1 = it.next();
                	total += stats.getPopulationCount(field, class1);
                }
    			
                int previous = 0;
                it = classes.iterator();
                while (it.hasNext()) {
                	Class class1 = it.next();
                	int count = stats.getPopulationCount(field, class1);
                	g.setColor(colors.get(class1));
                	double arc = ((double)count / (double)total) * (double)360;
                	Long l = Math.round(arc);
                	int newArc = Integer.valueOf(l.intValue());
                	g.fillArc(10, 10, width - 20, height - 20, previous, newArc);
                	previous += newArc;
                }
                
                repaint();
            }
        }
        /**
         * Indicate a new simulator run on this panel.
         */
        public void newRun()
        {
            clearImage();
            repaint();
        }

        /**
         * Clear the image on this panel.
         */
        public void clearImage()
        {
            Graphics g = pieImage.getGraphics();
            g.setColor(this.getBackground());
            g.fillRect(0, 0, pieImage.getWidth(), pieImage.getHeight());
            repaint();
        }
        
        /**
        * @return The preferred dimension for this component.
        */
        public Dimension getPreferredSize()
        {
            return new Dimension(pieImage.getWidth(), pieImage.getHeight());
        }
		
        /**
         * This component needs to be redisplayed. Copy the internal image 
         * to screen. (This method gets called by the Swing screen painter 
         * every time it want this component displayed.)
         * 
         * @param g The graphics context that can be used to draw on this component.
         */
		public void paintComponent(Graphics g) {
            if(pieImage != null) {
                g.drawImage(pieImage, 0, 0, null);
            }
		}
    }
}
