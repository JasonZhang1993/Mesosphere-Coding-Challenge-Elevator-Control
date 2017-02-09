package elevatorControlSystem;

import java.util.Scanner;

public class Main {
	public static void main (String[] args) throws Exception{
		Scanner in = new Scanner(System.in);
		
		System.out.print("Please type in the number of elevators >> ");
		String str = in.nextLine();
		int num = Integer.parseInt(str);
		if (num <= 0) {
			in.close();
			throw new Exception("Wrong number of elevators");
		}
		
		elevatorControlSystem tst = new elevatorControlSystem(num);
		
		boolean quit = false;
		
		while (!quit){
			System.out.print("Input: ");
			str = in.nextLine();
			
			if (str.equals("quit")) quit = true;
			else if (str.equals("status")){
				System.out.print(tst.status());
			}
			else if (str.equals("")){
				tst.step();
			}
			else if (str.contains("pickup")){
				String[] s = str.split(" ");
				if (s.length != 3) {
					System.out.println("Invalid command, please re-type.");
					continue;
				}
				else if (Integer.parseInt(s[1]) + Integer.parseInt(s[2]) <= 0){
					System.out.println("Invalid floor, please re-type.");
					continue;
				}
				tst.pickup(Integer.parseInt(s[1]), Integer.parseInt(s[2]));
			}
			else System.out.println("Invalid command, please re-type.");
		}
		in.close();
	}
}
