package kubiecuber;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class KubieCuber {
	private final int REGISTRY_PORT = 1098;
	private final String REMOTE_ADDRESS = "KubieCuberRemote";
	
	private KubieCuberRemote kubieCuberRemote;
	private Registry registry;
	
	public KubieCuber() {
		kubieCuberRemote = new KubieCuberRemoteImpl();
	}
	
	public void provideRemote() throws Exception  {
		/* create the RMI-registry on the mindstorm */
        System.setProperty("java.rmi.server.hostname", "10.0.1.1");
        try {
            registry = LocateRegistry.createRegistry(REGISTRY_PORT); 
        } 
        catch (RemoteException e) {  // registry already exists, so look it up
        	registry = LocateRegistry.getRegistry(REGISTRY_PORT);
        }
        
        KubieCuberRemote stub = (KubieCuberRemote)UnicastRemoteObject.exportObject(kubieCuberRemote, REGISTRY_PORT);
        registry.rebind(REMOTE_ADDRESS, stub); // bind the remote in the registry
	}
	
	public void shutdown() throws Exception {
		registry.unbind(REMOTE_ADDRESS); // unbind the remote
		UnicastRemoteObject.unexportObject(registry, true); // unbind the registry itself to stop it
	}
	
	
	public static void main(String[] args) {
		KubieCuber kubieCuber = new KubieCuber();
		
		try {
			kubieCuber.provideRemote();
			System.out.println("Remote provided");
		}
		catch(Exception e) {  // the program ends if providing the remote fails
			e.printStackTrace();
		}
	}
}
