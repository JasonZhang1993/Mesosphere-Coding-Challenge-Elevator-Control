# Mesosphere Coding Challenge: Elevator Control
## Candidate: Jiayi (Jason) Zhang
      
### Implementation and Architecture

The program works for almost every test case by myself, although a few  of them may not so efficient. But generally most of the testcases are following my algorithm to pick up and drop passengers.

The Architecture is quite simple. Besides the elevator object and elevatorControlSystem class that define the main interface, I also claim passangers for the consideration to connect elevator to passengers. Therefore my codes can also observe behavior of certain passengers.

### Control Algorithm

I do not use too complicated data structure to handle the algorithm, for there's is hardly an effective data structure that can schedule the pickup and space-saving. Generally my algorithm compares the 3 conditions for each pickup request: 1) elevator can pickup the passenger on the same way (passed-by and pickup), 2) idle elevator pickup directly, 3) a busy elevator finishs its work and then pickup. The algorithm would estimate the fastest one of each elevator, then choose the one with smallest time to take the request. Therefore, my algorithm is NOT dynamic - every passenger is guaranteed to be served in a given time as the request is submitted to the system.

### System Interface

1. status() : {int, int, int[], int}. It returns 4 elements: the Elevator ID, Current Floor Number, ALL Goal Floor Numbers, Number of Passengers.
2. update(int, int, int[]). Update the elevator by 3 variables: Elevator ID, Current Floor Number, Additional Goal Floor Numbers.
3. pickup(int, int). Same definition in the statement, the core of the algorithm is deducted in this funciton.
4. step(). Same definition and functionality.

### Build & Instruction

I write the codes on eclipse. Unfortunately I am not familiar to run Java built by eclipse on terminal, so I provide the .jar file named as Main.jar. Please run the code by
```
$: java -jar Main.jar
```
Once it's runned, user is required to input the number of elevators. Then the valid commands are:

```
- "status": call status(), system will output the status of elevators.
- "pick x y": call pickup(x,y), to request a pickup from x floor with direction y.
- "\n" (only enterkey press): call step(), system will simulate behavior of next second.
- "quit": quit the program.
```
The testcase.java is for me to test and debug, if you want to give request and simulate by yourself, please ignore it.
