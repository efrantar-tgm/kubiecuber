package kubiecuber.control;

import kubiecuber.KubieCuber;

import static kubiecuber.hardware.FlipperMotor.FlipperState.*;

public class KubieCuberControllerImpl implements KubieCuberController {
	private KubieCuber kubieCuber;
	
	public KubieCuberControllerImpl(KubieCuber kubieCuber) {
		this.kubieCuber = kubieCuber;
	}
	
	@Override
	public void perform(String sequence) {
		String[] moves = sequence.split(" ");
		for(String move : moves) {
			switch(move) {
			case "T":
				T(false);
				break;
			case "T'":
				T(true);
				break;
			case "T2":
				T2();
				break;
			case "O":
				O();
				break;
			case "C":
				C();
				break;
			case "F":
				F();
				break;
			}
		}
	}

	@Override
	public void T(boolean inverted) {
		kubieCuber.getPlatform().turn90(inverted, (kubieCuber.getFlipper().getState() == CLOSE));
	}

	@Override
	public void T2() {
		kubieCuber.getPlatform().turn180(false, (kubieCuber.getFlipper().getState() == CLOSE));
	}

	@Override
	public void F() {
		kubieCuber.getFlipper().flip();
	}

	@Override
	public void O() {
		kubieCuber.getFlipper().open();
	}

	@Override
	public void C() {
		kubieCuber.getFlipper().close();
	}

	@Override
	public void ready() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void test() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
}
