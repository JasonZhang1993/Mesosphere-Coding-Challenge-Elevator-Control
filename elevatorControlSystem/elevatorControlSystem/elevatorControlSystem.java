package elevatorControlSystem;

import java.util.*;
import elevatorControlSystem.Passenger.state_P;
import elevatorControlSystem.Elevator.state_E;

public class elevatorControlSystem implements elevatorControlInterface{
	
	private List<Elevator> elevators;
	private Map<Integer, ArrayList<Passenger>> passengers; // passengers that are waiting
	private int num_elv;
	private int time; // simulation time
	
	public elevatorControlSystem(int num){
		this.num_elv = num;
		this.time = 0;
		this.elevators = new ArrayList<Elevator>(num);
		this.passengers = new HashMap<Integer, ArrayList<Passenger>>();
		for (int i = 0;i < this.num_elv; i++){
			this.elevators.add(new Elevator(i));
		}
	}

//	return a String that give the status of elevators
	@Override
	public String status() {
		String str = "Simulation time: " + this.time + "\n";
		for (Elevator elv : elevators){
			str += elv.toString() + "\t number of passengers: " + elv.getPassengers().size() + "\n";
		}
		return str;
	}

//	update status of a elevator - change goal floors, direction, state
	@Override
	public void update(int elevatorID, int currFloor, int[] goalFloor) {
		Elevator elv = elevators.get(elevatorID);
//		update current floor
		elv.setCurrFloor(currFloor);
//		add goal floor to elevator
		for (int floor : goalFloor){
			if (!elv.getGoalFloors().contains(floor)){
				elv.addGoalFloor(floor);
			}
		}
//		direction and state will be updated in step()
	}

	@Override
	public void pickup(int pickupFloor, int direction) {
		if (direction == 0){
			System.out.println("Serve yourself.");
			return;
		}
		
		// generate passenger
		Passenger psg = new Passenger(pickupFloor, direction);
		if (!this.passengers.containsKey(pickupFloor)){
			ArrayList<Passenger> psgs = new ArrayList<>();
			psgs.add(psg);
			this.passengers.put(pickupFloor, psgs);
		}
		else{
			ArrayList<Passenger> psgs = this.passengers.get(pickupFloor);
			psgs.add(psg);
			this.passengers.put(pickupFloor, psgs);
		}
		
		// 1. check elevators that are on the same way
		// 2. idle elevators
		// 3. elevators that will finish serving
		// compare the time to pickup 
		List<Integer> pickTime = new ArrayList<>(this.num_elv);
		int[] cond = new int[this.num_elv];
		for (Elevator e : this.elevators){
			int t = 0;
			if (e.getState() == state_E.IDLE){ // 2.
				t = Math.abs(e.getCurrFloor() - pickupFloor);
				cond[this.elevators.indexOf(e)] = 2;
			}
			else{ // check 1.
				List<Integer> floors = e.getGoalFloors();
				
				if (direction > 0 && e.getDirection() > 0 && pickupFloor > e.getCurrFloor() && pickupFloor <= Collections.max(floors)){ // up
						
					t = pickupFloor - e.getCurrFloor();
					cond[this.elevators.indexOf(e)] = 1;
				}
				else if (direction < 0 && e.getDirection() < 0 && pickupFloor < e.getCurrFloor() && pickupFloor >= Collections.min(floors)){ // down
						
					t = e.getCurrFloor() - pickupFloor;
					cond[this.elevators.indexOf(e)] = 1;
				}
				else{ // 3.
					
					for (int i = 0;i < floors.size();i++){
						if (i == 0) t += Math.abs(e.getCurrFloor() - floors.get(i));
						else t += Math.abs(floors.get(i) - floors.get(i - 1));
						if (i == floors.size() - 1) t += Math.abs(floors.get(i) - pickupFloor);
					}
					cond[this.elevators.indexOf(e)] = 3;
				}	
			}
			pickTime.add(t);
		}
		
		//  choose min time
		int id = pickTime.indexOf(Collections.min(pickTime));
		Elevator e = this.elevators.get(id);
		psg.setElv(id);
		System.out.println("picked up by elv " + id + ", condition " + cond[id]);
		if (cond[id] == 1){// find goal floors location
			List<Integer> floors = e.getGoalFloors();
			int pickLoc = -1, dropLoc = -1, i = 0;
			if (direction > 0){
				for (i = 0; i < floors.size(); i++){
					if (i > 0 && floors.get(i) < floors.get(i-1)) break;
					if (i == 0){
						if (pickupFloor <= floors.get(i)) pickLoc = 0;
						if (psg.getDropFloor() <= floors.get(i)) dropLoc = 0;
					}
					else{
						if (pickupFloor > floors.get(i - 1) && pickupFloor <= floors.get(i)) pickLoc = i;
						if (psg.getDropFloor() > floors.get(i-1) && psg.getDropFloor() <= floors.get(i)) dropLoc = i;
					}
				}
				if (pickLoc == -1) pickLoc = i;
				if (dropLoc == -1) dropLoc = i;
			}
			else{
				for (i = 0; i < floors.size(); i++){
					if (i > 0 && floors.get(i) > floors.get(i-1)) break;
					if (i == 0){
						if (pickupFloor >= floors.get(i)) pickLoc = 0;
						if (psg.getDropFloor() >= floors.get(i)) dropLoc = 0;
					}
					else{
						if (pickupFloor < floors.get(i - 1) && pickupFloor >= floors.get(i)) pickLoc = i;
						if (psg.getDropFloor() < floors.get(i-1) && psg.getDropFloor() >= floors.get(i)) dropLoc = i;
					}
				}
				if (pickLoc == -1) pickLoc = i;
				if (dropLoc == -1) dropLoc = i;
			}
			e.addGoalFloor(dropLoc, psg.getDropFloor());
			e.addGoalFloor(pickLoc, pickupFloor);
		}
		// condition 2, 3, just put goal floors in sequence
		else if (cond[id] == 2) this.update(id, e.getCurrFloor(), new int[] {pickupFloor, pickupFloor + direction});
		else{
			this.update(id, e.getCurrFloor(), new int[] {pickupFloor});
			e.addGoalFloor(e.getGoalFloors().size(), pickupFloor + direction);
		}
		
		e.setDirection();
		e.setState();
	}

	@Override
	public void step() {
		for (Elevator elv : elevators){
			this.ElevatorStep(elv);
		}
		this.time++;
	}
	
//	elevator move, pick and/or drop passenger, set next direction and state
	private void ElevatorStep(Elevator e){
		// move
		int currFloor = e.getCurrFloor() + e.getDirection();
		e.setCurrFloor(currFloor);
		
		// check passenger event
		if (e.getGoalFloors().contains(currFloor)){
			if (e.getGoalFloors().get(0) == currFloor){
	
				// find pickup
				if (!this.passengers.isEmpty()){
					this.findPickup(e, currFloor);
				}
	
				// find drop off
				if (!e.getPassengers().isEmpty())
					this.findDropoff(e, currFloor);
				
				// drop current floor from goals
				e.dropGoalFloor(0);
			}
			else{
				if (!this.passengers.isEmpty()){
					this.findPickup(e, currFloor);
				}
			}
		}
		
		// update direction, state
		e.setDirection();
		e.setState();
		// check duplicate goal floor
		if (e.getState() == state_E.PICKING && e.getGoalFloors().size() == 1){
			e.dropGoalFloor(0);
			e.setDirection();
			e.setState();
		}
	}
	
	private void findPickup(Elevator e, int currFloor){
		boolean flag = false;
		if (this.passengers.containsKey(currFloor)){
			for (Passenger p : this.passengers.get(currFloor)){
				if (p.getElv() == e.getElevatorID() && e.getGoalFloors().get(0) == currFloor){
					e.addPassenger(p);
					p.setState(state_P.SERVED);
				}
				else if (p.getElv() == e.getElevatorID() && e.getDirection() * p.getDirection() > 0){
					e.addPassenger(p);
					p.setState(state_P.SERVED);
					flag = true;
				}
			}
			ArrayList<Passenger> leftPass = new ArrayList<>();
			for (Passenger p : this.passengers.get(currFloor)){
				if (p.getState() == state_P.WAITING) leftPass.add(p);
			}
			if (leftPass.isEmpty()) this.passengers.remove(currFloor);
			else this.passengers.put(currFloor, leftPass);
			
			if (flag) e.dropGoalFloor(e.getGoalFloors().indexOf(currFloor));
		}
	}
	
	private void findDropoff(Elevator e, int currFloor){
		for (int i = 0;i < e.getPassengers().size();i++){
			if (e.getPassengers().get(i).getDropFloor() == currFloor){
				e.getPassengers().get(i).setState(state_P.ARRIVE);
				e.getPassengers().remove(i);
				i--;
			}
		}
	}

}
