package FoxesandRabbits.model;
import java.util.List;
import java.util.Iterator;
import java.util.Random;

import FoxesandRabbits.logic.*;	

/**
 * A simple model of a hunter.
 * hunters move and hunt.
 * 
 * @author Mathijs Lindeboom
 * @version 2015.01.22
 */
public class Hunter extends Animal implements Actor
{
    // Characteristics shared by hunters (class variables).
    
    // The age to which a hunter can live.
    private static final int MAX_AGE = 100;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // Individual characteristics (instance fields).
    // The hunter's age.
    private int age;

    /**
     * Create a Borg. A Borg can be created as a new born (age zero
     * and not hungry) or with a random age and energy level.
     * 
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Hunter(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
        else {
            age = 0;
        }
        infected = true;
    }
    
    /**
     * This is what the Borg does most of the time: it hunts for
     * rabbits. In the process, it might assimilate, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newHunt A list to return newly assimilated Borg.
     */
    @Override
	public void act(List<Actor> newHunt)
    {
        if(isActive()) {
        	Location newLocation = hunt();
        	ebola();
            if(newLocation == null) { 
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            if(newLocation != null) {
                setLocation(newLocation);
            }
        }
    }

    /**
     * Increase the age. This could result in the borg death.
     */
    
    
    /**
     * Look for victim adjacent to the current location.
     * Only the first live Victim is eaten.
     * @return Where a victim was found, or null if it wasn't.
     */
    private Location hunt()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Borg) {
                Borg borg = (Borg) animal;
                if(borg.isActive()) { 
                	if (rand.nextInt(2) == 0)
            		{
                		borg.setDead();
                        return where;
            		}
                	else {
                		return null;
                	}
                    
                }                
            }
            if (animal instanceof Fox) {
            	Fox fox = (Fox) animal;
            	if(fox.isActive()) {
            		if (rand.nextInt(2) == 0)
            		{
            			fox.setDead();
                		return where;              			
            		}
            		else {
            			return null;
            		}
            		          		
            	}
            }
        }
        return null;
    }
}