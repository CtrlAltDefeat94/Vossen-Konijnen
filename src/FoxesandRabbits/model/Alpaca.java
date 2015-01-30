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
public class Alpaca extends Animal implements Actor
{
    // Characteristics shared by hunters (class variables).
    
	// The age at which a rabbit can start to breed.
    private static int BREEDING_AGE = 13;
    // The age to which a hunter can live.
    private static final int MAX_AGE = 100;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.19;
    // The maximum number of births.
    private static int MAX_LITTER_SIZE = 6;
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
    public Alpaca(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
        else {
            age = 0;
        }
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
        	Location newLocation = null;
            if(newLocation == null) { 
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            if(newLocation != null) {
                setLocation(newLocation);
            }
        }
    }

    private void giveBirth(List<Actor> newRabbits)
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Rabbit young = new Rabbit(false, field, loc);
            newRabbits.add(young);
        }
    }
        
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
    	double breedingProbability;
    	int maxLitterSize;
    	breedingProbability = BREEDING_PROBABILITY / 2;
    	maxLitterSize = MAX_LITTER_SIZE - 3;
    	
        int births = 0;
        if(canBreed() && rand.nextDouble() <= breedingProbability) {
            births = rand.nextInt(maxLitterSize) + 1;
        }
        return births;
    }

    /**
     * A rabbit can breed if it has reached the breeding age.
     * @return true if the rabbit can breed, false otherwise.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }

    
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
                	if (rand.nextInt(1) == 0)
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
            		if (rand.nextInt(1) == 0)
            		{
            			fox.setDead();
                		return where;              			
            		}
            		else {
            			return null;
            		}
            		          		
            	}
            }
            if (animal instanceof Rabbit) {
            	Rabbit rabbit = (Rabbit) animal;
            	if(rabbit.isActive()) {
            		if (rand.nextInt(1) == 0)
            		{
            			rabbit.setDead();
                		return where;              			
            		}
            		else {
            			return null;
            		}
            		          		
            	}
            }
            if (animal instanceof Hunter) {
            	Hunter hunter = (Hunter) animal;
            	if(hunter.isActive()) {
            		if (rand.nextInt(1) == 0)
            		{
            			hunter.setDead();
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