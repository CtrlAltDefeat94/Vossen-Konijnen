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
		Simulator simulator = new Simulator(300, 300);
		simulator.runLongSimulation();
		assertEquals(500, simulator.getStep());
	}
}
	


