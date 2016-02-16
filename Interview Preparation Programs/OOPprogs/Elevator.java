package elevatorPackage;

import java.util.LinkedList;
import java.util.Queue;

public class Elevator {
	Queue<Integer> localQ = new LinkedList<Integer>();
	int floorNumber;
	States state = States.stopped;
	
	public void moveUp(int toFloor){
	 System.out.println("Moving UP");
	}
	
	public void moveDown(int toFloor){
	 System.out.println("Moving Down");
	}
	public void addToQueue(int floor){
		//localQ
	}
	
	public void run(){
		while(true){
			if(!localQ.isEmpty()){
				
			} else{
				//Thread.main.sleep();
			}
		}
	}
}
