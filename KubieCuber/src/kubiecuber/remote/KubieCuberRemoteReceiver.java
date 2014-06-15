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
	
	private Packet processPacket(Packet packet) throws Exception {
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
			return new Packet(FAIL, null);
		}
		
		return new Packet(SUCCESS, null);
	}
	
	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(PORT);
		} 
		catch (Exception e) {}
		
		while(true)  {
			System.out.println("Waiting for connection ...");
			
			try {
				/* wait for connection with remote */
				socketFromRemote = serverSocket.accept();
				System.out.println("Connected ...");
				
				/* initialize write and read streams */
				objectOutputStream = new ObjectOutputStream(socketFromRemote.getOutputStream());
				objectInputStream = new ObjectInputStream(socketFromRemote.getInputStream());
				
				Packet request;
				while((request = (Packet)objectInputStream.readObject()) != null) {
					Packet response = processPacket(request);
					
					/* answer the request with either a fail- or a success-response */
					objectOutputStream.writeObject(response);
					objectOutputStream.flush();
				}
			} 
			catch (Exception e) {
			}
			
			System.out.println("Disconnected ...");
			try { sleep(1000); } catch (InterruptedException e) {} // we need to wait a bit before accepting a new connection
		}
	}
	
	public void shutdown() {
		/* close the sockets; Exceptions will occur on client- and on server-side! */
		try {
			socketFromRemote.close();
		}
		catch(Exception e) {}
		try {
			serverSocket.close();
		}
		catch(Exception e) {}
		
		System.out.println("Remote shutdown ...");
	}
}
