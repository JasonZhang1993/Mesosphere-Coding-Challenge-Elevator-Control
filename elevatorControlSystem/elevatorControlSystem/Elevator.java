package elevatorControlSystem;

import java.util.*;

public class Elevator {
	
	public static enum state_E{
		IDLE, PICKING, SERVING
	}
	
	private int elevatorID;
	private int currFloor;
	private ArrayList<Integer> goalFloor;
	
//	direction: 0 for idle, 1 for up, -1 for down
	private int direction;
//	passengers in this elevator
	private ArrayList<Passenger> passengers;
	private state_E state;
	
	public Elevator(int id){
		this.elevatorID = id;
		this.currFloor = 1;
		this.goalFloor = new ArrayList<Integer>();
		this.direction = 0;
		this.state = state_E.IDLE;
		this.passengers = new ArrayList<Passenger>();
	}
	
//	elevator ID	
	public int getElevatorID(){
		return this.elevatorID;
	}
	
//	current floor
	public void setCurrFloor(int floor){
		this.currFloor = floor;
	}
	
	public int getCurrFloor(){
		return this.currFloor;
	}
	
//	goal floors: add at end or by index
	public void addGoalFloor(int floor){
		this.goalFloor.add(floor);
	}
	
	public void addGoalFloor(int index, int floor){
		if (this.goalFloor.size() < index){
			System.out.println("Out of bound.");
			return;
		}
		else if (this.goalFloor.size() == index && this.goalFloor.get(index-1) != floor)
			this.goalFloor.add(index, floor);
		else if (this.goalFloor.size() > index && this.goalFloor.get(index) != floor)
			this.goalFloor.add(index, floor);
	}
	
	public void dropGoalFloor(int index){
		this.goalFloor.remove(index);
	}
	
	public ArrayList<Integer> getGoalFloors(){
		return this.goalFloor;
	}
	
//	directions
	// may need to change
	public void setDirection(){
		if (this.goalFloor.isEmpty()) this.direction = 0;
		else if (this.goalFloor.get(0) > this.currFloor) this.direction = 1;
		else if (this.goalFloor.get(0) < this.currFloor) this.direction = -1;
		else this.direction = 0;
	}
	//
	
	public int getDirection(){
		return this.direction;
	}
	
//	passengers: add or drop passengers
	public void addPassenger(Passenger p){
		this.passengers.add(p);
	}
	// 
	public void dropPassenger(Passenger p){
		if (this.passengers.contains(p))
			this.passengers.remove(p);
	}
	//
	
	public ArrayList<Passenger> getPassengers(){
		return this.passengers;
	}
	
//	state
	// may not be necessary
	public void setState(){
		if (this.passengers.isEmpty()) {
			if (this.goalFloor.isEmpty()) this.state = state_E.IDLE;
			else this.state = state_E.PICKING;
		}
		else this.state = state_E.SERVING;
	}
	//
	
	public state_E getState(){
		return this.state;
	}
	
//	convert to string for status()
	public String toString(){
		String str = "Elevator ID: " + this.elevatorID + "\t current floor: " + this.currFloor + "\t goal floors:";
		for (int floor : goalFloor){
			str += " " + floor + ",";
		}
//		str += "\n";
		return str;
	}
	
}
