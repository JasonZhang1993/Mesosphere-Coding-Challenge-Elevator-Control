package elevatorControlSystem;

public interface elevatorControlInterface {
//	String
	public String status();
	
//	integer (floor) or list (floors)
	public void update(int elevatorID, int currFloor, int[] goalFloor);
	
	public void pickup(int pickupFloor, int direction);
	
	public void step();
}
