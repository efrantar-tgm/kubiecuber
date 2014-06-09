package kubiecuber;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This is the interface for the RMI-remote-Controller of the KubieCuber.
 * It defines all methods necessary for running and controlling the robot.
 * 
 * @author Elias Frantar
 * @version 9.6.2014
 */
public interface KubieCuberRemote extends Remote {
	
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
	 * @throws RemoteException thrown if a RemoteException occurred
	 */
	public void perform(String sequence) throws RemoteException;
	
	
	/* the methods for the individual moves */
	
	/**
	 * Rotates the center platform 90 degrees. (aka T)
	 * @param inverted defines whether the rotation should be clockwise or not
	 * @throws RemoteException thrown if a RemoteException occurred
	 */
	public void T(boolean inverted)  throws RemoteException;
	/**
	 * Rotates the center platform 180 degrees. (aka T2)
	 * @throws RemoteException thrown if a RemoteException occurred
	 */
	public void T2() throws RemoteException;
	/**
	 * Flips the cube once. (aka F)
	 * @throws RemoteException thrown if a RemoteException occurred
	 */
	public void F() throws RemoteException;
	/**
	 * Opens the flipper. (aka O)
	 * @throws RemoteException thrown if a RemoteException occurred
	 */
	public void O() throws RemoteException;
	/**
	 * Closes the flipper (aka C)
	 * @throws RemoteException
	 */
	public void C() throws RemoteException;
	
	/**
	 * Drives the robot into starting position.
	 * @throws RemoteException thrown if a RemoteException occurred
	 */
	public void ready() throws RemoteException;
	/**
	 * Tests the mechanics of the robot and then drive sback into starting position.
	 * @throws RemoteException thrown if a RemoteException occurred
	 */
	public void test() throws RemoteException;
	
	
	/**
	 * Shuts down the robot and the it's remote service.
	 * This remote should not be used anymore after calling this method!
	 * @throws RemoteException thrown if a RemoteException occurred
	 */
	public void shutdown() throws RemoteException;
}
