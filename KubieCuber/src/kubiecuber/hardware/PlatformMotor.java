package kubiecuber.hardware;

import lejos.hardware.port.Port;

public class PlatformMotor extends ErrorCorrectingMotor {
	private final int SCALE = 3;
	private static final int THRESHOLD = 5;

	public PlatformMotor(Port port) {
		super(port, THRESHOLD);
	}
	
	public void turn90(boolean inverted, boolean flipperClosed) {
		turn(inverted, flipperClosed, 90);
	}	
	public void turn180(boolean inverted, boolean flipperClosed) {
		turn(inverted, flipperClosed, 180);
	}
	
	private void turn(boolean inverted, boolean flipperClosed, int toTurn) {
		int direction = (inverted)?-1:1;
		
		if(flipperClosed) {
			int turnOver = 15;
			turn((toTurn + turnOver) * SCALE * direction);
			turn(-turnOver * SCALE * direction);
		}
		else
			turn(toTurn * SCALE * direction);
	}
}
