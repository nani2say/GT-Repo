package elevatorPackage;

import java.util.LinkedList;
import java.util.Queue;

enum States {
	up, down, stopped;
}

public class ElevatorSystem {

	Queue<Integer> mainQ = new LinkedList<Integer>();
	Elevator[] elevators;
	final int numberOfFloors = 6;
	int numberOfElevators;

	public ElevatorSystem(int n) {
		elevators = new Elevator[n];
		numberOfElevators = n;
		for (int i = 0; i < n; i++) {
			elevators[i] = new Elevator();
		}
	}

	public int getRandomFloor() {
		int var = (int)( Math.random() * 100 % numberOfFloors);
		System.out.println("R: " + var);
		return var;
	}

	public void assignElevators() {
		int flag = 0;
		while (!mainQ.isEmpty()) {
			Integer top = mainQ.remove();
			flag = 0;
			// case1
			for (int i = 0; i < numberOfElevators; i++) {
				// elevator stopped at that floor.
				if (elevators[i].state == States.stopped
						&& elevators[i].floorNumber == top) {
					// elevators[i].addToQueue(top);
					flag = 1;
					break;
				}
			}

			// case 2
			if (flag == 0) {
				for (int i = 0; i < numberOfElevators; i++) {
					// elevator going towards the floor
					if (elevators[i].state == States.up
							&& elevators[i].floorNumber < top) {
						elevators[i].addToQueue(top);
						flag = 1;
						break;
					} else if (elevators[i].state == States.down
							&& elevators[i].floorNumber > top) {
						elevators[i].addToQueue(top);
						flag = 1;
						break;
					}
				}
			}

			// case 3
			if (flag == 0) {
				for (int i = 0; i < numberOfElevators; i++) {
					// elevator stopped at any other floor
					if (elevators[i].state == States.stopped) {
						// elevators[i].addToQueue(top);
						flag = 1;
						break;
					}
				}
			}
			// case 4
			// all elevators moving away from the floor
		}
	}

	public static void main(String[] args) {
		ElevatorSystem es = new ElevatorSystem(3);
		es.mainQ.add(es.getRandomFloor());
		es.mainQ.add(es.getRandomFloor());
		es.mainQ.add(es.getRandomFloor());
		es.mainQ.add(es.getRandomFloor());
		es.mainQ.add(es.getRandomFloor());

	}

}
