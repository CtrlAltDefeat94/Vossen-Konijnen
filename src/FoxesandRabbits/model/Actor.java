package FoxesandRabbits.model;
import java.util.*;
/**
 * Write a description of interface Actor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface Actor
{
    /**
     * Do the usual activity of the Actor
     * @param newActors A list with the newly created actors
     */
    void act(List<Actor> newActors);
    
    /**
     * is the Actor still active?
     */
    boolean isActive();
}
