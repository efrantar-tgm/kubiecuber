package kubiecuber.hardware;

import lejos.hardware.port.Port;

import static kubiecuber.hardware.FlipperMotor.FlipperState.*;

public class FlipperMotor extends ErrorCorrectingMotor {
	private static final int THRESHOLD = 5;
	
	public enum FlipperState {
		OPEN,
		CLOSE
	}
	
	private FlipperState state;
	private int flipSpeed;
	
	public FlipperMotor(Port port) {
		super(port, THRESHOLD);
		
		state = OPEN;
	}
	
	public void open() {
		if(state == CLOSE) {
			int toTurn = -90;
			turn(toTurn);
			
			state = OPEN;
			flipSpeed = getSpeed();
		}
	}	

	public void close() {
		if(state == OPEN) {
			int toTurn = 90;
			turn(toTurn);
			
			state = CLOSE;
		}
	}
	
	public void flip() {
		if(state == CLOSE) {
			int backup = getSpeed();
			setSpeed(flipSpeed);
			
			int toTurn = 90;
			int push = 15;
			
			turn(toTurn);
			turn(-toTurn - push);
			turn(push);
			
			setSpeed(backup);
		}
	}
	
	/* Getters and Setters */
	public FlipperState getState() { return state; }
	public void setState(FlipperState state) { this.state = state; }
	public int getFlipSpeed() { return flipSpeed; }
	public void setFlipSpeed(int flipSpeed) { this.flipSpeed = flipSpeed; }
}
