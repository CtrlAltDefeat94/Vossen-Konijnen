package actors;
import java.util.List;
import java.util.Iterator;
import java.util.Random;

import field.Field;
import field.Location;
import field.Randomizer;

/**
 * A simple model of a borg.
 * borg age, move, assimilate, and die.
 * 
 * @author Daniël Slobben
 * @version 2015.01.22
 */
public class Borg extends Animal implements Actor
{
    // Characteristics shared by Borg (class variables).
    
    // The age to which a fox can live.
    private static final int MAX_AGE = 100;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // Individual characteristics (instance fields).
    // The Borg age.
    private int age;
    // The Borg level, which is increased by standing still.
    private int energyLevel;

    /**
     * Create a Borg. A Borg can be created as a new born (age zero
     * and not hungry) or with a random age and energy level.
     * 
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Borg(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            energyLevel = rand.nextInt(15);
        }
        else {
            age = 0;
            energyLevel = 10;
        }
    }
    
    /**
     * This is what the Borg does most of the time: it hunts for
     * rabbits. In the process, it might assimilate, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newBorg A list to return newly assimilated Borg.
     */
    @Override
	public void act(List<Actor> newBorg)
    {
        incrementAge();
        if(isActive()) {
        	Location newLocation = null;        	
        	if (energyLevel >= 1)
        	{
        	    Location locationVictim = assimilate();
        	    if (locationVictim != null)
        	    {
                    newBorg.add(new Borg(false, field, locationVictim));
                    energyLevel--;
        	    }
                if(locationVictim == null) { 
                    // No victim found -- move
                	newLocation = getField().freeAdjacentLocation(getLocation());
                	energyLevel--;            		
                }
        	}
        	else
        	{
        		newLocation = getField().freeAdjacentLocation(getLocation());
        	}
        	if (getField().alone(getLocation()) == true)
        	{
        		if (rand.nextInt(3) == 0)
        		{
        		    setDead();
        		}
       		}        	
        	else
            {
        		if (newLocation != null) 
        		{
                    setLocation(newLocation);
                    energyLevel += 2;
        		}
            }            
        }
    }

    /**
     * Increase the age. This could result in the borg death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     * Look for victim adjacent to the current location.
     * Only the first live Victim is eaten.
     * @return Where a victim was found, or null if it wasn't.
     */
    private Location assimilate()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isActive()) { 
                	if (rand.nextInt(2) == 0)
            		{
                		rabbit.setDead();
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

