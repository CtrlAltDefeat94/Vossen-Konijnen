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
     * Voer het gebruikelijke gedrag van de deelnemer uit.
     * @param newActors Een lijst waarin zojuist gemaakte 
     * deelnemers worden opgeslagen
     */
    void act(List<Actor> newActors);
    
    /**
     * Is de deelnemer nog steeds actief?
     */
    boolean isActive();
}
