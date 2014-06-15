package kubiecuber.control;

/**
 * This is the interface for the controller of the KubieCuber.
 * It defines all methods necessary for running and controlling the robot.
 * 
 * @author Elias Frantar
 * @version 9.6.2014
 */
public interface KubieCuberController {
	
	/**
	 * Performs the given sequence of moves.
	 * sequence may only consist of the following moves separated by spaces (' '):
	 * 	- T
	 * 	- T'
	 * 	- T2
	 * 	- F
	 * 	- O
	 * 	- C
	 * Calling this method has the exact same effect as calling the individual methods for the moves manually in succession.
	 * 
	 * @param sequence the sequence of moves to perform
	 */
	public void perform(String sequence);
	
	
	/* the methods for the individual moves */
	
	/**
	 * Rotates the center platform 90 degrees. (aka T)
	 * @param inverted defines whether the rotation should be clockwise or not
	 */
	public void T(boolean inverted);
	/**
	 * Rotates the center platform 180 degrees. (aka T2)
	 */
	public void T2();
	/**
	 * Flips the cube once. (aka F)
	 */
	public void F();
	/**
	 * Opens the flipper. (aka O)
	 */
	public void O();
	/**
	 * Closes the flipper (aka C)
	 */
	public void C();
	
	/**
	 * Drives the robot into starting position.
	 */
	public void ready();
	/**
	 * Tests the mechanics of the robot and then drive sback into starting position.
	 */
	public void test();
	
	
	/**
	 * Shuts down the robot and the it's remote service.
	 * This remote should not be used anymore after calling this method!
	 */
	public void shutdown();
}
