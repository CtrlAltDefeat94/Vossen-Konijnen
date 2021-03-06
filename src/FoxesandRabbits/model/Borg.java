package FoxesandRabbits.model;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import FoxesandRabbits.logic.Field;
import FoxesandRabbits.logic.Location;
import FoxesandRabbits.logic.Randomizer;

/**
 * A simple model of a borg.
 * borg age, move, assimilate, and die.
 * 
 * @author Dani�l Slobben
 * @version 2015.01.22
 */
public class Borg extends Animal implements Actor
{
    // Characteristics shared by Borg (class variables).
    
    // The age to which a fox can live.
    private static int MAX_AGE = 100;
    // The Energy level the Borg Start with
    private static int ENERGY_LEVEL = 10;
    // The chance the Borg has to assimilate someone in percentage
    private static int ASSIMILATION_CHANCE = 50;
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
            energyLevel = ENERGY_LEVEL;
        }
    }
    public static int getAge()
    {
    	return MAX_AGE;
    }
    public static void setAge(int age)
    {
    	MAX_AGE = age;
    }
    public static int getEnergy()
    {
    	return ENERGY_LEVEL;
    }
    public static void setEnergy(int energy)
    {
    	ENERGY_LEVEL = energy;
    }
    public static int getAssimilationChance()
    {
    	return ASSIMILATION_CHANCE;
    }
    public static void setAssimilationChance(int chance)
    {
    	ASSIMILATION_CHANCE = chance;
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
        	ebola();
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
                }
        	}
        	else
        	{
        		newLocation = getField().freeAdjacentLocation(getLocation());
        		energyLevel++;
        	}
        	if (getField().alone(getLocation()) == true)
        	{
        		if (rand.nextInt(4) == 0)
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
                	if (rand.nextInt(100) < ASSIMILATION_CHANCE)
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
            		if (rand.nextInt(100) == ASSIMILATION_CHANCE)
            		{
            			fox.setDead();
                		return where;              			
            		}
            		else {
            			return null;
            		}
            		          		
            	}
            }
            if (animal instanceof Grass) {
            	Grass grass = (Grass) animal;
            	grass.setDead();
            	return null;
            }
        }
        return null;
    }
}

