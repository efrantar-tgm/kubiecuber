package kubiecuber;

import kubiecuber.control.KubieCuberController;
import kubiecuber.control.KubieCuberControllerImpl;
import kubiecuber.hardware.FlipperMotor;
import kubiecuber.hardware.PlatformMotor;
import kubiecuber.remote.KubieCuberRemoteReceiver;
import lejos.hardware.Button;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;

/**
 * This is the main class of the KubieCuber. It has instances to all objects, which are required for running the robot.
 * It also has a main-entry-point.
 * 
 * @author Elias Frantar
 * @version 15.6.2014
 */
public class KubieCuber {
	private final Port PLATFORM_PORT = MotorPort.A;
	private final Port FLIPPER_PORT = MotorPort.B;
	
	private KubieCuberController kubieCuberController; // the robot controller handling the moves
	
	/* instances to the hardware-controllers (motors) */
	private PlatformMotor platform;
	private FlipperMotor flipper;
	
	private KubieCuberRemoteReceiver kubieCuberRemoteReceiver; // remote receiver for remote calls from external devices
	
	/**
	 * Creates a new KubieCuber.
	 * Initializes and sets up all required hardware-parts (motors)
	 */
	public KubieCuber() {
		kubieCuberController = new KubieCuberControllerImpl(this);
		
		platform = new PlatformMotor(PLATFORM_PORT);
		flipper = new FlipperMotor(FLIPPER_PORT);
		
		/* TODO: make speed setting accessible from outside / remote */
		/* set the motor speeds */
		platform.setSpeed(platform.getSpeed() * 2);
		flipper.setFlipSpeed((int)(flipper.getSpeed()  * 1.5));
	}
	
	/**
	 * Provides a network remote for externally controlling the KubieCuber.
	 */
	public void provideRemote() {
		kubieCuberRemoteReceiver = new KubieCuberRemoteReceiver(kubieCuberController);
		System.out.println("Remote provided");
	}
	
	/**
	 * Allows the user to manually shut down the robot by pressing any button. (canceling the remote service)
	 */
	public void allowManualShutdown() {
		new Thread() {
			@Override
			public void run() {
				Button.waitForAnyPress();
				shutdown();
			}
		}.start();
		System.out.println("Manual shutdown activated");
	}
	
	/**
	 * Shuts down the robot and cancels the remote service.
	 */
	public void shutdown() {
		/* stop all motors */
		platform.stop();
		flipper.stop();
		
		/* stop remote service if activated */
		if(kubieCuberRemoteReceiver != null)
			kubieCuberRemoteReceiver.shutdown();
	}
	
	
	public static void main(String[] args) {
		KubieCuber kubieCuber = new KubieCuber();
		
		kubieCuber.allowManualShutdown();
		kubieCuber.provideRemote();
	}
	
	
	/* just Getters because the motors must never be set from outside */
	public PlatformMotor getPlatform() { return platform; }
	public FlipperMotor getFlipper() { return flipper; }
}
