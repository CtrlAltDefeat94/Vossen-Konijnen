import static org.junit.Assert.*;

import org.junit.Test;


public class SimulatorTest {

	@Test
	public void testSimulateOneStep() 
	{
		Simulator simulator = new Simulator();
		simulator.simulateOneStep();
		assertEquals(1, simulator.getStep());		
	}
	@Test
	public void testRunLongSimulation()
	{
		Simulator simulator = new Simulator();
		simulator.runLongSimulation();
		assertEquals(510, simulator.getStep());
	}
}
	


