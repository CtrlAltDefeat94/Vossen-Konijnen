package FoxesandRabbits.model;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import FoxesandRabbits.logic.*;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael K�lling
 * @version 2011.07.31
 */
public class Rabbit extends Animal implements Actor
{
    // Characteristics shared by all rabbits (class variables).

    // The age at which a rabbit can start to breed.
    private static int BREEDING_AGE = 13;
    // The age to which a rabbit can live.
    private static int MAX_AGE = 40;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.19;
    // The maximum number of births.
    private static int MAX_LITTER_SIZE = 6;
 // number of steps a fox can go before it has to eat again.
    private static int GRASS_FOOD_VALUE = 8;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // Individual characteristics (instance fields).
    
    // The rabbit's age.
    private int age;
    // The rabbit's foodlevel
    private int foodlevel;
    // Rabbit has disease if disease = true
    private boolean disease;    
    // Disease on or off 
    private static boolean diseaseOn = false;
    // How much the rabbits can infect others without dying
    private int diseaseCount;
    // Rabbits who can have the disease
    private boolean sensitiveForDisease;
    // % rabbits that are immume to the disease
    private int percentageDisease = 10;
        
    // generate a random number between min and max 
    int min = 0;
    int max = 100;
    int ranNum = min+(int)(Math.random()*((max-min) + 1));

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Rabbit(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
        foodlevel = (20);
        
        if (ranNum >= percentageDisease)        	
        {this.sensitiveForDisease = true;}        
        else
        {this.sensitiveForDisease = false;} 
        
        this.diseaseCount = 0;
        
        if (ranNum <= 10)
        {
        	this.disease = true;
        }
        else
        {
        	this.disease = false;
        }
    }
    
    /*
     * if a rabbit is infected and disease is turned on the rabbit will spread the disease to other rabbits.
     */
    private void findRabbitDisease()
    {
    	if (diseaseOn == true & disease == true)
    	{
    		Field field = getField();
    		List<Location> adjacent = field.adjacentLocations(getLocation());
    		Iterator<Location> it = adjacent.iterator();
    		    		
    		while(it.hasNext())
    		{
    			Location where = it.next();
    			Object animal = field.getObjectAt(where);
    			if(animal instanceof Rabbit){
    				Rabbit rabbit = (Rabbit) animal;
    				if(rabbit.isActive() && sensitiveForDisease == true)
    				{    					
    					rabbit.setDisease(true);  
    					this.diseaseCount();    					    					
		    		}
    			}
    		}    		
    	}
    }
    public static void DiseaseOn()
    {
    	diseaseOn = true;
    }
    
    public static void DiseaseOff()
    {
    	diseaseOn = false;
    }
    
    private void setDisease(boolean disease)
    {
    	this.disease = disease;
    }
    
    private void diseaseCount(){
    	this.diseaseCount++;
    }
    
    
    public static void setMaxAge(int age)
    {
    	MAX_AGE = age;
    }
    public static int getMaxAge()
    {
    	return MAX_AGE;
    }
    public static int getBreedingAge()
    {
    	return BREEDING_AGE;
    }
    public static void setBreedingAge(int breedingAge)
    {
    	BREEDING_AGE = breedingAge;
    }
    public static int getLitterSize()
    {
    	return MAX_LITTER_SIZE;
    }
    public static void setLitterSize(int litter)
    {
    	MAX_LITTER_SIZE = litter;
    }
    public static int getFoodValue()
    {
    	return GRASS_FOOD_VALUE;
    }
    public static void setFoodValue(int value)
    {
    	GRASS_FOOD_VALUE = value;
    }
  
    
    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to return newly born rabbits.
     */
    @Override
	public void act(List<Actor> newRabbits)
    {
    	foodlevel--;
        incrementAge();
        if(isActive()) {
        	giveBirth(newRabbits);            
			// Try to move into a free location.
			Location newLocation = findFood();
			findRabbitDisease();
			ebola();
			if(newLocation == null) { 
				//No food found - try to move to a free location.
			    newLocation = getField().freeAdjacentLocation(getLocation());
			}
			if(newLocation != null) {
				setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();            	
            }
		    if (this.disease == true && sensitiveForDisease == true)
	   		{
	   			if (this.diseaseCount >=5)
   			    {
	  		     	setDead();
   			    }	   			
	   		}	
        }        
     }
    

    /**
     * Increase the age.
     * This could result in the rabbit's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
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
    	if (foodlevel > (GRASS_FOOD_VALUE / 2) + 2) {
    		breedingProbability = BREEDING_PROBABILITY * 2;
    		maxLitterSize = MAX_LITTER_SIZE + 3;
    	}
    	else if (foodlevel <= ((GRASS_FOOD_VALUE / 2) + 2) && foodlevel > (GRASS_FOOD_VALUE / 2 - 2)){
    		breedingProbability = BREEDING_PROBABILITY;
    		maxLitterSize = MAX_LITTER_SIZE;
    	}    	
    	else {
    		breedingProbability = BREEDING_PROBABILITY / 2;
    		maxLitterSize = MAX_LITTER_SIZE - 3;
    	}
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
     * Look for rabbits adjacent to the current location.
     * Only the first live rabbit is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood()
    {    	
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Grass) {
                Grass grass = (Grass) animal;
                if(grass.isActive()) { 
                    grass.setDead();
                    foodlevel = GRASS_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }
}