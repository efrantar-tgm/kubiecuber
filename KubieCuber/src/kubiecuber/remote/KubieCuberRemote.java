package kubiecuber.remote;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import kubiecuber.control.KubieCuberController;
import static kubiecuber.remote.Packet.PacketType.*;

public class KubieCuberRemote implements KubieCuberController {
	private Socket socketToRobot;
	
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;
	
	public KubieCuberRemote(String host, int port) throws Exception {
		socketToRobot = new Socket(host, port);
		
		objectOutputStream = new ObjectOutputStream(socketToRobot.getOutputStream());
		objectInputStream = new ObjectInputStream(socketToRobot.getInputStream());
	}
	
	@Override
	public void perform(String sequence) {
		try {
			objectOutputStream.writeObject(new Packet(PERFORM, new Object[]{sequence}));
			objectOutputStream.flush();
			
			objectInputStream.readObject(); // just wait for the response
		}
		catch(Exception e) {}
	}

	@Override
	public void T(boolean inverted) {
		try {
			objectOutputStream.writeObject(new Packet(T, new Object[]{inverted}));
			objectOutputStream.flush();
			
			objectInputStream.readObject();
		}
		catch(Exception e) {}
	}
	@Override
	public void T2() {
		try {
			objectOutputStream.writeObject(new Packet(T2, null));
			objectOutputStream.flush();
			
			objectInputStream.readObject();
		}
		catch(Exception e) {}
	}
	@Override
	public void F() {
		try {
			objectOutputStream.writeObject(new Packet(F, null));
			objectOutputStream.flush();
			
			objectInputStream.readObject();
		}
		catch(Exception e) {}
	}
	@Override
	public void O() {
		try {
			objectOutputStream.writeObject(new Packet(O, null));
			objectOutputStream.flush();
			
			objectInputStream.readObject();
		}
		catch(Exception e) {}
	}
	@Override
	public void C() {
		try {
			objectOutputStream.writeObject(new Packet(C, null));
			objectOutputStream.flush();
			
			objectInputStream.readObject();
		}
		catch(Exception e) {}
	}

	@Override
	public void ready() {
		try {
			objectOutputStream.writeObject(new Packet(READY, null));
			objectOutputStream.flush();
			
			objectInputStream.readObject();
		}
		catch(Exception e) {}
	}
	@Override
	public void test() {
		try {
			objectOutputStream.writeObject(new Packet(TEST, null));
			objectOutputStream.flush();
			
			objectInputStream.readObject();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void shutdown() {
		try {
			objectOutputStream.writeObject(new Packet(SHUTDOWN, null));
			objectOutputStream.flush();
			
			objectInputStream.readObject();
		}
		catch(Exception e) {}
	}
}
