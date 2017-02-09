package elevatorControlSystem;

public class testcase {
	
	public static void simpleTest(){
		elevatorControlSystem tst = new elevatorControlSystem(2);
		System.out.print(tst.status());
		tst.step();
		tst.pickup(3, 3);
		tst.pickup(3, 3);
		tst.step();
		System.out.print(tst.status());
		tst.step();
		System.out.print(tst.status());
		tst.step();
		System.out.print(tst.status());
		tst.step();
		System.out.print(tst.status());
		tst.step();
		System.out.print(tst.status());		
		tst.step();
		System.out.print(tst.status());
		
	}
	
	public static void simpleTest2(){
		elevatorControlSystem tst = new elevatorControlSystem(1);
		tst.step();
		System.out.print(tst.status());
		tst.pickup(3, 10);
		tst.step();
		System.out.print(tst.status());
		tst.step();
		System.out.print(tst.status());
		tst.pickup(6,2);
		System.out.print(tst.status());
		tst.step();
		System.out.print(tst.status());
		tst.step();
		tst.pickup(5, -2);
		System.out.print(tst.status());
		tst.step();
		System.out.print(tst.status());		
		tst.step();
		System.out.print(tst.status());
		tst.step();
		System.out.print(tst.status());
		tst.step();
		System.out.print(tst.status());
		tst.step();
		System.out.print(tst.status());
		tst.step();
		System.out.print(tst.status());
		tst.step();
		System.out.print(tst.status());
		tst.step();
		System.out.print(tst.status());
		tst.step();
		System.out.print(tst.status());
		tst.step();
		System.out.print(tst.status());
		tst.step();
		System.out.print(tst.status());
		tst.step();
		System.out.print(tst.status());
		tst.step();
		System.out.print(tst.status());
	}
	
	public static void main(String[] args){
		simpleTest();
	}
}
