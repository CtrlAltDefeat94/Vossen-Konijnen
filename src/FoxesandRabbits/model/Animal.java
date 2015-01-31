package FoxesandRabbits.model;

import java.util.Iterator;
import java.util.List;

import FoxesandRabbits.logic.*;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public abstract class Animal
{
    // Whether the animal is alive or not.
    protected boolean alive;
    // The animal's field.
    protected Field field;
    // The animal's position in the field.
    protected Location location;
    
    // set Ebola to false (off)
    protected static boolean ebola = false;
    // Set ebolacount to 0
    protected int ebolaCount = 0;
    // Animal is infected or not.
    protected boolean infected;
    
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Actor> newAnimals);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    public boolean isActive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    public void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }
    
    /**
     * Methode to set infected to true
     */
    protected void setInfected()
    {
    	infected = true;
    }
    
    /**
     * Count how many the actor has infected. 
     * @return true if actor has infected 10 or more other actors
     * @return false if actor not infected 10 or more other actors
     */
    protected boolean ebolaCount()
    {
    	if (ebolaCount >= 10)
    	{return true;}
    	else
    	{ebolaCount++;
    	return false;
    	}
    }
    
    /**
     * Methode to set ebola to true.
     */
    public static void EbolaOn()
    {
    	ebola = true;
    }
    
    /**
     * Methode to set ebola to false 
     */
    public static void EbolaOff()
    {
    	ebola = false;
    }
    
    /**
     * Methode to infect other actors.
     * If ebola = true & infected = true
     * than ~ Actors who come in contact with other actors will infect them.
     * and kills them if ebolaCount = true
     */
    protected void ebola()
    {
    	if (ebola == true && infected == true)
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
    				if(rabbit.isActive())
    				{   
    					rabbit.setInfected();
    					
    					if (ebolaCount() == true)
    					{rabbit.setDead();
    					 }  			
    					rabbit.ebolaCount();
		    		}
    			}
    			if (animal instanceof Borg){
    				Borg borg = (Borg) animal;
    				if(borg.isActive())
    				{
    					borg.setInfected();
    					if (ebolaCount() == true)
    					{borg.setDead();
    					}  	
    					borg.ebolaCount();
    				}
    			}
    			if (animal instanceof Fox){
    				Fox fox = (Fox) animal;
    				if(fox.isActive())
    				{
    					fox.setInfected();
    					if (ebolaCount() == true)
    					{fox.setDead();
    					}  	
    					fox.ebolaCount();
    				}
    			}
    			if (animal instanceof Hunter){
    				Hunter hunter = (Hunter) animal;
    				if(hunter.isActive())
    				{
    					hunter.setInfected();
    					if (ebolaCount() == true)
    					{hunter.setDead();
    					}  
    					hunter.ebolaCount();
    				}
    			}    			
    		}    		
    	}
    }

}
