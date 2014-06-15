package kubiecuber.hardware;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;

/**
 * This motor is the base motor of all robot-motor components.
 * The motor tries to balance inaccuracies by keeping track of the last turn's offset and adjusting the next one appropriately.
 * 
 * @author Elias Frantar
 * @version 14.6.2014
 */
public class ErrorCorrectingMotor extends EV3LargeRegulatedMotor {
	private int offset; // the inaccuracy of the last turn
	private int threshold; // degrees; when to adjust
	
	/**
	 * Creates a new ErrorCorrectingMotor on the specified robot port.
	 * @param port the port the hardware-motor is connected to
	 * @param threshold the amount of degrees which are considered as exact
	 */
	public ErrorCorrectingMotor(Port port, int threshold) {
		super(port);
		
		offset = 0; // we assume the motor to be in an accurate starting position
		this.threshold = threshold;
	}
	
	/**
	 * Turns n degrees including the inaccuracy of the last move.
	 * If the turn was not very exact, it also adjusts the final position.
	 * @param degrees the degrees to turn
	 */
	public void turn(int degrees) {
		turn(degrees, true);
	}
	/**
	 * Turns n degrees including the inaccuracy of the last move.
	 * If the turn was not very exact, it also adjusts the final position when told to.
	 * @param degrees the degrees to turn
	 * @param adjust true to adjust the position when not very exact; false to never adjust
	 */
	public void turn(int degrees, boolean adjust) {
		int posTo = getPosition() + degrees + offset;
		
		rotateTo(posTo);
		
		offset = getPosition() - posTo;
		
		if(adjust && Math.abs(offset) > threshold)
			adjust();
	}
	
	/**
	 * Adjusts the current position be turning offset backwards.
	 */
	public void adjust() {
		turn(-offset, false); // we should only adjust once per turn to prevent possible (but very unlikely) infinite loop
	}
	
	/* Getters and Setters */
	public int getOffset() { return offset; }
	public void setOffset(int offset) { this.offset = offset; }
	public int getThreshold() { return threshold; }
	public void setThreshold(int threshold) { this.threshold = threshold; }
}
