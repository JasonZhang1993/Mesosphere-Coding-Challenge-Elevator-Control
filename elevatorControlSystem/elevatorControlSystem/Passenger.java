package elevatorControlSystem;

public class Passenger {
	
	public static enum state_P{
		WAITING, SERVED, ARRIVE
	}
	
	private int pickupFloor;
	private int direction;
	private	state_P state;
	private int pickElv;
	
	public Passenger(int pickupFloor, int direction){
		this.pickupFloor = pickupFloor;
		this.direction = direction;
		this.state = state_P.WAITING;
		this.pickElv = -1;
	}
	
//	pickup floor
	
	public int getPickupFloor(){
		return this.pickupFloor;
	}
	
//	direction
	public int getDirection(){
		return this.direction;
	}
	
//	drop off floor
	public int getDropFloor(){
		return (this.pickupFloor + this.direction);
	}

//	state
	public void setState(state_P s){
		this.state = s;
	}
	
	public state_P getState(){
		return this.state;
	}
	
//	pick elevator
	public void setElv(int elv){
		this.pickElv = elv;
	}
	
	public int getElv(){
		return this.pickElv;
	}
}
