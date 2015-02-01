package FoxesandRabbits.runner;

public class DynamicThread extends Thread 
{
	private static int steps;	
	private static boolean busy = true;
	private static Simulator simulator;
	/**
	 * Constructor with name for thread.
	 * @param threadName The name for the thread.
	 * @param simulator The simulator to use.
	 */
	public DynamicThread(String threadName, Simulator simulator)
	{
		super(threadName);
		this.simulator = simulator;
		start();
	}
	
	/**
	 * will run until the pause() method is called.
	 */
	public void run() 
	{
		busy = true;
		while(busy) {
		simulator.simulateOneStep();		
		}
	}
		
	/**
	 * pauses the simulation
	 */
	public static void pause()
	{
		busy = false;
	}
}