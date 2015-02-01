package FoxesandRabbits.model;
import java.util.List;
import java.util.Iterator;
import java.util.Random;

import FoxesandRabbits.logic.*;	

/**
 * A simple model of a Alpaca.
 * Alpacas move and hunt.
 * 
 * @author Mathijs Lindeboom
 * @version 2015.01.22
 */
public class Alpaca extends Animal implements Actor
{
    // Characteristics shared by Alpacas (class variables).
    
	// The age at which a rabbit can start to breed.
    private static int BREEDING_AGE = 6;
    // The age to which a Alpaca can live.
    private static final int MAX_AGE = 300;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 1;
    // The maximum number of births.
    private static int MAX_LITTER_SIZE = 590;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // Individual characteristics (instance fields).
    // The Alpaca's age.
    private int age;

    /**
     * Create a Alpaca. A Alpaca can be created as a new born (age zero
     * and not hungry) or with a random age and energy level.
     * 
     * @param randomAge If true, the Alpaca will have random age and hunger level.
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
     * This is what the Alpaca does most of the time: hunt for everything.
     * @param field The field currently occupied.
     * @param newHunt A list to return newly bred Alpacas
     */
    @Override
	public void act(List<Actor> newAlpaca)
    {
        if(isActive()) {
        	giveBirth(newAlpaca); 
        	Location newLocation = hunt();
            if(newLocation == null) { 
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            if(newLocation != null) {
                setLocation(newLocation);
            }
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
    	breedingProbability = BREEDING_PROBABILITY;
    	maxLitterSize = MAX_LITTER_SIZE;
    	
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
    private void giveBirth(List<Actor> newAlpaca)
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Alpaca young = new Alpaca(false, field, loc);
            newAlpaca.add(young);
        }
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
            // If the next location is grass, it will be crushed
            if (animal instanceof Grass) {
            	Grass grass = (Grass) animal;
            	if (rand.nextInt(1) == 0)
        		{
        			grass.setDead();
            		return where;              			
        		}
        		else {
        			return null;
        		}
            		
            		          		
            }
        }
        return null;
    }
}