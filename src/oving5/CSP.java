package oving5;

import java.sql.Time;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;

import javax.sql.RowSet;

public class CSP {
	
	private Random rand;
	
	public CSP(){
		rand = new Random();
	}
	
	
	public static void main(String[] args) {
		CSP start = new CSP();
		int n = 1000; // This is the size of the board
		start.theAlgorithm(n);
	}
	
	
	// Debugging only. See theAlgorithm for the right algorithm
	public void run(){
		int boardSize = 4;
		int[] debugBardet = new int[]{2,4,1,3};
		
		//
//		Board debug = new Board(debugBardet);
		Board test = new Board(boardSize);
//		System.out.println(test.toString());
		
//		int[] lol = debug.getAllViolations();
//		printArray(lol);
//		System.out.println(debug.toString());
		System.out.println(test.validateAll());
/* 
		violationCheckSpecific()
		estimates = getMinConflicts(queenPos[i], )

		compareAndMove(estimates);
		
		validateAll()
*/
	}
	
	
	public void theAlgorithm(int s){
		long startTime = System.currentTimeMillis();
		long tempTime = startTime;
		long currentTime = 0;
		
		int size = s; // Size of n 
		int iterations = 0;
		int rowToCheck = 0;
		
		Board brett = new Board(size);
		
		boolean solutionFound = brett.validateAll();
		
//		System.out.println("Initial Board of " + size +"x" + size +" pieces :\n" + brett.toString());
		
		printArray(brett.getQueens());
		
		System.out.println("\n--------------------\n");
		
		long test = 0;
		
		while(!solutionFound){
			currentTime = System.currentTimeMillis();


			if((currentTime-tempTime) > 2*(Math.pow(10, 3))){
				test = currentTime - startTime;
				test = (long)(test/(Math.pow(10, 3)));
				System.out.println("Time elapsed: " + (test) +"s");
				
				printArray(brett.getQueens());
				System.out.println("Number of iterations: " + iterations+"\n...");
				tempTime = System.currentTimeMillis();
			}
			solutionFound = brett.validateAll();
			
			
			rowToCheck = brett.getMostViolations();
				brett.getMinConflicts(rowToCheck);
			iterations++;
		}

		System.out.println("Number of iterations: " + iterations);
		test = currentTime - startTime;
		test = (long)(test/(Math.pow(10, 3)));
		System.out.println("Time elapsed: " + test +"ms");
//		System.out.print(size+","+test+";");
		System.out.println(brett.toString());
		printArray(brett.getQueens());
	}
	
	
	
	public void printArray(Queen[] queens){
		String printString = "";
		int totalViolations = 0;
		
		for (int i = 0; i < queens.length; i++) {
			totalViolations += queens[i].getViolations();
			printString += "Q" + (i+1) + " : " + queens[i].getViolations() + "\n";
		}
		System.out.println("Total Violations: " + totalViolations);
	}
}