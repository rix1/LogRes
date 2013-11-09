package oving5;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Board {
	
	private int[] queenPos;
	private final int size;
	private Random rand;
	
	public Board(int size){
		this.size = size;
		queenPos= new int[size];
		
		
		fillBoard();
	}
	
	public Board(int[] debugMap){
		queenPos = debugMap;
		this.size = queenPos.length;
	}
	
	public String toString(){
		String mapString = "";
		String row = "";
		
		// Fills the array with n strings with n zeroes
		for (int i = 0; i < size; i++) {
			row = "";
		
			for (int j = 0; j < size; j++) {
				if(j == queenPos[i])
					row += "1 ";
				else row += "0 ";
			}
		
			if(i<(size-1))
				mapString += row + "\n";
			else mapString += row;
		}
		return mapString;
	}
	
	
	private void fillBoard(){
		rand = new Random();
		
		for (int i = 0; i < size; i++){
			queenPos[i] = rand.nextInt(size);	
		}
	}
	
	// This works
	public boolean validateAll(){
		int[] setOfViolations = getAllViolations();
		int largestViolation =0;
		
		for (int i = 0; i < setOfViolations.length; i++) {
			if(setOfViolations[i] != 0){
				return false;
			}
		}
		return true;
	}
	
	// We are using pair-checking
	// This method finds violations for all queens and returns an array
	public int[] getAllViolations(){
		int queenPosX = 0, queenPosY = 0, violations = 0; 
		int[] tempDomain;
		int[] returnArray = new int[size];

		for (int i = 0; i < size; i++) {
			violations = 0;
			queenPosX = queenPos[i];

			for (int j = 0; j < size; j++) {
				if(i == j)
					continue;
				queenPosY = queenPos[j];
				
				tempDomain = getDomain(i, j);
				
				if(queenPosY == tempDomain[0] || queenPosY == tempDomain[1] || queenPosY == tempDomain[2]){
					violations++;
				}
			}
			returnArray[i] = violations;
		}
		return returnArray;
	}
	
	
	//Generates a set to test violations
	public int[] getDomain(int xPos, int yPos){
		int[] domain = new int[3];
		
		domain[0] = queenPos[xPos];
		domain[1] = queenPos[xPos] + Math.abs((xPos-yPos));
		domain[2] = queenPos[xPos] - Math.abs((xPos-yPos));
		
//		Debugprint
//		System.out.println(domain[0] + " , " + domain[1] + " , " + domain[2]);
		return domain;
	}
		
	public boolean hasRowViolation(int rowToBeInspected){
		int xPos = rowToBeInspected; 
		int yPos = 0;
		int queenPosY = 0;
		
		for (int i = 0; i < size; i++) {
			yPos = i;
			queenPosY = queenPos[yPos];
			if(i == rowToBeInspected)
				continue;
			int[] tempDomain = getDomain(xPos, yPos);
			
			if(queenPosY == tempDomain[0] || queenPosY == tempDomain[1] || queenPosY == tempDomain[2]){
				return true;
			}
		}
		return false;
	}
	
	
	public void getMinConflicts(int row){
		int currentQueenPos = queenPos[row];
		int smallest = 0;
		int smallestPos = 0;
		int[] sortedArray;
		ArrayList<Integer> positionOfSmallest = new ArrayList<Integer>();
		
		int[] estimates = new int[size];
		for (int i = 0; i < size; i++) {
			estimates[i] = getViolations(row, i);
		}

		
		sortedArray = estimates.clone();
		Arrays.sort(sortedArray);
		smallest = sortedArray[0];
		
		for (int i = 0; i < estimates.length; i++) {
			if(estimates[i] == smallest && i != currentQueenPos){
				positionOfSmallest.add(i);
			}
		}
		// If the list is empty, then the current queen has the best estimate.
		// If the list is not empty, there are more estimates that are less than or equal to the queen. In this case we should not pick the queen.
		if(!positionOfSmallest.isEmpty()){
			moveQueen(row, positionOfSmallest.get(rand.nextInt(positionOfSmallest.size())));
		}
	}
	
	public void moveQueen(int row, int newPos){
		queenPos[row] = newPos;
	}

	
	// THIS WORKES JUST FINE
	// Finds violations for a specific cell - tests this cell with every other queen not in the same cell
	public int getViolations(int row, int cell){
		int violationCounter = 0;
		int xPos = row;
		int queenPosX = cell;
		int yPos = 0;
		int queenPosY= 0;
		
		// Gets the other queens and compares with this cell
		for (int i = 0; i < size; i++) {
			if(i == row)
				continue;
			yPos = i;
			queenPosY = queenPos[yPos];
			
			int[] tempDomain = getDomainSpecial(xPos, queenPosX, yPos);
			if(queenPosY == tempDomain[0] || queenPosY == tempDomain[1] || queenPosY == tempDomain[2]){
				violationCounter++;
			}			
		}
		return violationCounter;
	}
	
	
	//Super method for special kids
	public int[] getDomainSpecial(int xPos, int queenPosX, int yPos){
		int[] domain = new int[3];

		domain[0] = queenPosX;
		domain[1] = queenPosX + Math.abs((xPos-yPos));
		domain[2] = queenPosX - Math.abs((xPos-yPos));

		return domain;
	}
	
		
	public int getQueenPosByRow(int row){
		return queenPos[row];
	}
}
