package oving5;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Board {
	
	private Queen[] queens;
	private final int size;
	private Random rand;
	
	public Board(int size){
		this.size = size;
		queens = new Queen[size];
		fillBoard();
	}
	
	
	private void fillBoard(){
		rand = new Random();
		for (int i = 0; i < size; i++){
			queens[i] = new Queen(rand.nextInt(size));
		}
	}
	
	
/*	
	public Board(int[] debugMap){
		queenPos = debugMap;
		this.size = queenPos.length;
	}
*/

	public String toString(){
		String mapString = "";
		String row = "";
		
		// Fills the array with n strings with n zeroes
		for (int i = 0; i < size; i++) {
			row = "";

			for (int j = 0; j < size; j++) {
				if(j == queens[i].getPosition())
					row += "1 ";
				else row += "0 ";
			}
		
			if(i<(size-1))
				mapString += row + "\n";
			else mapString += row;
		}
		return mapString;
	}
	
	public int getMostViolations(){
		int mostViolations = queens[0].getViolations();
		int mostVioRow = 0;
		
		for (int i = 0; i < queens.length; i++) {
			if(queens[i].getViolations() >= mostViolations){
				mostViolations = queens[i].getViolations();
				mostVioRow = i;
			}
		}
		return mostVioRow;
	}
	
	public Queen[] getQueens(){
		return queens;
	}
	
	// This works
	public boolean validateAll(){
		updateViolations();
		
		for (int i = 0; i < queens.length; i++) {
			if(queens[i].getViolations() != 0){
				return false;
			}
		}
		return true;
	}
	
	public void updateViolations(){
		int queenPosX = 0, queenPosY = 0; 
		int[] tempDomain;

//		Checks a row. 
		for (int i = 0; i < size; i++) {
			queens[i].setViolations(0);
			queenPosX = queens[i].getPosition();
			
//			Checks all the other rows to the row above
			for (int j = 0; j < size; j++) {
				if(i == j)
					continue;
				queenPosY = queens[j].getPosition();
				
				tempDomain = getDomain(i, j);
				
				if(queenPosY == tempDomain[0] || queenPosY == tempDomain[1] || queenPosY == tempDomain[2]){
					queens[i].addVoilation();
				}
			}
		}
	}
	
	
	//Generates a set to test violations
	public int[] getDomain(int xPos, int yPos){
		int[] domain = new int[3];
		
		domain[0] = queens[xPos].getPosition();
		domain[1] = queens[xPos].getPosition() + Math.abs((xPos-yPos));
		domain[2] = queens[xPos].getPosition() - Math.abs((xPos-yPos));
		
		return domain;
	}
	
	
//	==================================================================================================
		
	
	public boolean hasRowViolation(int rowToBeInspected){
		if(queens[rowToBeInspected].getViolations() == 0)
			return false;
		return true;
	}
	
	
	public void getMinConflicts(int row){
		
		int currentQueenPos = queens[row].getPosition();
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
			moveQueen(row, positionOfSmallest.get(rand.nextInt(positionOfSmallest.size())), smallest);
		}
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
			queenPosY = queens[yPos].getPosition();
			
			int[] tempDomain = getDomainSpecial(xPos, queenPosX, yPos);
			if(queenPosY == tempDomain[0] || queenPosY == tempDomain[1] || queenPosY == tempDomain[2]){
				violationCounter++;
			}			
		}
		return violationCounter;
	}
	
	
	public void moveQueen(int row, int newPos, int violations){
		queens[row].setPosition(newPos);
		queens[row].setViolations(violations);
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
		return queens[row].getPosition();
	}
}
