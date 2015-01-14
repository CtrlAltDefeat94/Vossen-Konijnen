import java.util.*;
/**
 * Write a description of class Animal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Animal implements Actor
{
     // Whether the rabbit is alive or not.
    private boolean alive;
    // The rabbit's position.
    private Location location;
    // The field occupied.
    private Field field;

    /**
     * Constructor for objects of class Animal
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
     * get field
     */
    public Field getField()
    {
        return field;
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    public Location getLocation()
    {
        return location;
    }
    
    /**
     * Check whether the fox is alive or not.
     * @return True if the fox is still alive.
     */
    public boolean isActive()
    {
        return alive;
    }    
    
    /**
     * Indicate that the fox is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
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
    
}
