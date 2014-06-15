package kubiecuber.remote;

import java.io.Serializable;

public class Packet implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public enum PacketType {
		PERFORM,
		T,
		T2,
		F,
		O,
		C,
		READY,
		TEST,
		SHUTDOWN,
		
		SUCCESS,
		FAIL
	}
	
	private PacketType packetType; // the method to call at the robot
 	private Object[] payload; // the parameters of the method to call
	
	public Packet(PacketType requestType, Object[] payload) {
		setPacketType(requestType);
		setPayload(payload);
	}
	
	/* Getters and Setters */
	public PacketType getPacketType() { return packetType; }
	public Object[] getPayload() { return payload; }
	public void setPacketType(PacketType packetType) { this.packetType = packetType; }
	public void setPayload(Object[] payload) { this.payload = payload; }
}