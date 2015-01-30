package FoxesandRabbits.main;
import static org.junit.Assert.*;

import java.util.List;



import org.junit.Test;

import FoxesandRabbits.model.*;
import FoxesandRabbits.runner.*;

public class SimulatorTest {
    
	/**
	 * Tests to see if the simulation runs 1 step.
	 */
	@Test
	public void testSimulateOneStep() 
	{
		Simulator simulator = new Simulator(100, 200);
		simulator.simulateOneStep();
		assertEquals(1, simulator.getStep());		
	}
	/**
	 * Tests to see if the simulation runs 50 steps
	 */
	@Test
	public void testRunLongSimulation()
	{
		Simulator simulator = new Simulator(100, 200);
		simulator.simulate(50);
		assertEquals(50, simulator.getStep());
	}
	/**
	 * Tests if 26 hunters are created and tests to see if hunters dont die.
	 */
	@Test
	public void testHunterCreation()
	{
		Simulator simulator = new Simulator(100, 200);
		simulator.simulate(100);
		List<Actor> Actor = simulator.getAnimals();
		int hunterCounter = 0;
		for (Actor actor : Actor) {
			if (actor instanceof Hunter) {
				hunterCounter++;
			}				
 		}
		assertEquals(26, hunterCounter);
	}
}
	


