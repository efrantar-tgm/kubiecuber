package kubiecuber.remote;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import kubiecuber.control.KubieCuberController;
import static kubiecuber.remote.Packet.PacketType.*;

public class KubieCuberRemoteReceiver extends Thread {
	public static final int PORT = 2107;
	
	private KubieCuberController kubieCuberController;
	
	private ServerSocket serverSocket;
	private Socket socketFromRemote;
	
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;
	
	public KubieCuberRemoteReceiver(KubieCuberController kubieCuberController) {
		this.kubieCuberController = kubieCuberController;
		start();
	}
	
	private void processPacket(Packet packet) throws Exception {
		switch(packet.getPacketType()) {
		case PERFORM:
			kubieCuberController.perform((String)packet.getPayload()[0]);
			break;
		case T:
			kubieCuberController.T((boolean)packet.getPayload()[0]);
			break;
		case T2:
			kubieCuberController.T2();
			break;
		case O:
			kubieCuberController.O();
			break;
		case C:
			kubieCuberController.C();
			break;
		case F:
			kubieCuberController.F();
			break;
		case TEST:
			kubieCuberController.test();
			break;
		default:
			objectOutputStream.writeObject(new Packet(FAIL, null));
			return;
		}
		
		objectOutputStream.writeObject(new Packet(SUCCESS, null));
	}
	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(PORT);
		} 
		catch (Exception e) {}
		
		while(true)  {// TODO: create watchdog for save termination 
			try {
				/* wait for connection with remote */
				System.out.println("Waiting for connection ...");
				socketFromRemote = serverSocket.accept();
				System.out.println("Connected ...");
				
				/* initialize write and read streams */
				objectOutputStream = new ObjectOutputStream(socketFromRemote.getOutputStream());
				objectInputStream = new ObjectInputStream(socketFromRemote.getInputStream());
				
				Packet packet;
				while((packet = (Packet)objectInputStream.readObject()) != null) {
					processPacket(packet);
					objectOutputStream.flush();
				}
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			try { sleep(1000); } catch (InterruptedException e) {}
			System.out.println("Disconnected ...");
		}
	}
}
