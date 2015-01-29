package FoxesandRabbits.model;

import java.util.List;
import java.util.Random;

import FoxesandRabbits.logic.*;

/**
 * A simple model of a grass.
 * Grass age, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class Grass extends Animal implements Actor
{
    // Characteristics shared by all grasss (class variables).

    // The age at which a grass can start to breed.
    private static final int BREEDING_AGE = 3;
    // The age to which a grass can live.
    private static final int MAX_AGE = 12;
    // The likelihood of a grass breeding.
    private static final double BREEDING_PROBABILITY = 0.19;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 6;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // Individual characteristics (instance fields).
    
    // The grass's age.
    private int age;

    /**
     * Create a new grass. A grass may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the grass will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Grass(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
    }
    
    /**
     * This is what the grass does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newGrass A list to return newly born grasss.
     */
    @Override
	public void act(List<Actor> newGrass)
    {
        incrementAge();
        if(isActive()) {
            giveBirth(newGrass);            
           
        }
    }

    /**
     * Increase the age.
     * This could result in the grass's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     * Check whether or not this grass is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newGrass A list to return newly born grasss.
     */
    private void giveBirth(List<Actor> newGrass)
    {
        // New grasss are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Grass young = new Grass(false, field, loc);
            newGrass.add(young);
        }
    }
        
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A grass can breed if it has reached the breeding age.
     * @return true if the grass can breed, false otherwise.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
}
