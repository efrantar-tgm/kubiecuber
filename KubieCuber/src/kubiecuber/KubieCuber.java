package kubiecuber;

import kubiecuber.control.KubieCuberController;
import kubiecuber.control.KubieCuberControllerImpl;
import kubiecuber.hardware.FlipperMotor;
import kubiecuber.hardware.PlatformMotor;
import kubiecuber.remote.KubieCuberRemoteReceiver;
import lejos.hardware.port.MotorPort;

public class KubieCuber {
	private KubieCuberController kubieCuberController;
	
	private PlatformMotor platform;
	private FlipperMotor flipper;
	
	public KubieCuber() {
		kubieCuberController = new KubieCuberControllerImpl(this);
		
		platform = new PlatformMotor(MotorPort.A);
		flipper = new FlipperMotor(MotorPort.B);
		
		platform.setSpeed(platform.getSpeed() * 2);
		flipper.setFlipSpeed((int)(flipper.getSpeed()  * 1.5));
	}
	
	public void provideRemote() {
		new KubieCuberRemoteReceiver(kubieCuberController);
	}
	
	public void shutdown() {
		/* TODO: not yet implemented */
	}
	
	
	public static void main(String[] args) {
		KubieCuber kubieCuber = new KubieCuber();
		
		kubieCuber.provideRemote();
		System.out.println("Remote provided");
	}
	
	public PlatformMotor getPlatform() { return platform; }
	public FlipperMotor getFlipper() { return flipper; }
}
