package oving4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;


public class SA {

	private EggCarton initialCarton;
	private PriorityQueue<EggCarton> neighborList = new PriorityQueue<EggCarton>();
	
	private final int numberOfNeighbors = 5; // This is the number of neighbors created each iteration
	private final int bitsToBeFlipped = 1; // This is the number of bits to be flipped when generating new neighbors
	private int iterations = 3; // Number of iterations the algorithm should do
	private final double tmax = 5; //Initial temperature
	private final double dt = 0.01;
	private double t;
	private EggCarton initialCart;
	private EggCarton currentCart;
	
	
	public static void main(String[] args) {
		SA temp = new SA();
		temp.run();
//		temp.debugRun();
	}

	public SA(){
		t = tmax;
		initialCart = new EggCarton(10, 10, 3); // Change these values to change map size
		currentCart = initialCart;

	}
	
	public void run(){
		currentCart.eval();
		agendaLoop(currentCart);
		System.out.println("Iterations: 10");
		
		//TODO: PRINT FACTS
		System.out.println(currentCart.toString());
		System.out.println("Value of F: " + currentCart.getF());
		System.out.println("Number of eggs: " + currentCart.getNumberOfEggs());
		System.out.println("Value of T: " + t);
		
	}

	public void agendaLoop(EggCarton parent){
		while(parent.getF() <= 1){
			if(parent.getF() == 1)
				break;
			if(iterations == 0){
				currentCart = neighborList.peek();
				break;
			}
				
			if(parent.getF() == 1){
				// I have no idea of why this prints more than one time
				break;
			}
			
			generateNeighbors(parent, numberOfNeighbors, bitsToBeFlipped);

			double q = ((neighborList.peek().getF()-parent.getF())/parent.getF());
			double p = 0;

			double eulerEquation = Math.exp((-q)/t); 

			if(eulerEquation < 1){
				p = eulerEquation;
			}else p = 1;

			Random random = new Random();
			double x = random.nextDouble();
			
			if(x>p){
				currentCart = neighborList.poll(); //Exploiting!
			}else{
				EggCarton[] array = neighborList.toArray(new EggCarton[neighborList.size()]);
				currentCart = array[random.nextInt(array.length)]; //Exploring!
			}
			
			t -= dt;
			iterations --;
			agendaLoop(currentCart);
		}
	}
	
	public void generateNeighbors(EggCarton parent, int numberOfNeighbors, int bitsToBeFlipped){
		for (int i = 0; i < numberOfNeighbors; i++) {
			EggCarton newNeighbor = parent;
			newNeighbor.flipBits(bitsToBeFlipped); // Improve this
			newNeighbor.eval();
			neighborList.add(newNeighbor);
		}
	}
	

	
	
/**	
	
	public void debugRun(){
		//An optimal solution for 6x6:
		String debug = "101000001001000011110000010100000110";
		
		//Debugstring to check different things..
		String testColumn = "111000110000110000110000110000111100";
		
		initialCart.setString(testColumn);
		
//		initialCart.flipBits(bitsToBeFlipped);
		initialCart.objectiveFunction();
		initialCart.eval();
		System.out.println("\n\n---------\nValue of f: " + initialCart.getF());
		
		System.out.println(initialCart.getF());
		System.out.println(initialCart.toString());
		System.out.println();
		initialCart.splitToList();
	}
*/
	
}
