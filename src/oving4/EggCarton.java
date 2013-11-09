package oving4;

import java.util.ArrayList;
import java.util.Random;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import oving3_rikard.Node;

public class EggCarton implements Comparable<EggCarton> {

	private Random random;
	private double f;
	private String state;
	private final int m, n, k, size;
	private ArrayList<String> liste;
	

	public EggCarton(int m, int n, int k){
		this.m = m;
		this.n = n;
		this.k = k;
		f = 0;
		this.size = m*n;

		random = new Random();
		state = generateSolution();
	}

	public int getSize(){
		return size;
	}

	public String toString(){
		splitToList();
		String returnString = "The EggCarton:\n";
		for (String i : liste) {
			returnString += i + "\n";
		}
		return returnString;
	}

	// Creates a empty egg carton
	public String generateSolution(){
		String temp = "";
		for (int i = 0; i < size; i++) {
			temp += "0";
		}
		return temp;
	}


	public void flipBits(int n){ // n = bits to be flipped

		StringBuilder charFlipper = new StringBuilder(state);
		for (int i = 0; i < n; i++) {
			int rand = random.nextInt(size); //position of bit to be flipped 

			if(charFlipper.charAt(rand) == '0'){
				charFlipper.setCharAt(rand, '1');
			}
			else{ 
				charFlipper.setCharAt(rand, '0');
			}
		}
		state = charFlipper.toString();
	}

	public int getNumberOfEggs(){
		int counter = 0;
		for (int i = 0; i < state.length(); i++) {
			if(state.charAt(i) == '1')
				counter++;
		}
		return counter;
	}


	public double getF(){
		return f;
	}

	public boolean evalHorizontal(){
		int counter;
		for (int i = 0; i < m; i++) {
			counter = 0;
			String row = liste.get(i);
			for (int j = 0; j < n; j++) {
				if(row.charAt(j) == '1'){
					counter++; // Counts how many ones there are in the string.
				}
			}
			if(counter>k){
				objectiveFunction(counter-k); //Punishment
			}
		}
		return true;
	}
	
	public boolean evalVertical(){
		int counter;
		for (int i = 0; i < m; i++) {
			counter = 0;
			for (int j = 0; j < n; j++) {
				if(liste.get(j).charAt(i) == '1')
					counter ++;
			}
			if(counter>k){
				objectiveFunction(counter-k); //Punishment
			}
		}
		return true;
	}
	
	// Borrowed this method from a friend. Modified to handle our datatypes.
	// Note, this is not working properly
	public boolean evalDiagonal(){
		int counter;

		for (int i = 0; i < this.m; i++) { 
			counter = 0;

			//down-right
			for (int j = 0; j < this.n && i+j < this.m; j++){
				try {
					if (liste.get(j+i).charAt(j) == '1')
						counter++;
				} catch (IndexOutOfBoundsException e) {
					break;
				}
			}
		
			if(counter > k){
				objectiveFunction(counter-k);
			}
			
			//up-right
			counter = 0;
			for (int j = 0; j < this.n && i-j > -1; j++){
				if(liste.get(i-j).charAt(j) == '1')
					counter++;
			}
			if(counter > k){
				objectiveFunction(counter-k);
			}
			
			//down-left
			counter = 0;
			for (int j = (this.n - 1), a = 0; j > -1 && i+a < this.m; j--, a++){
				if(liste.get(i+a).charAt(j) == '1')
					counter++;
			}
			if(counter > k){
				objectiveFunction(counter-k);
			} 
					
			
			//up-left
			counter = 0;
			for (int j = (this.n - 1), a = 0; j > -1 && i-a > -1; j--, a++){
				if(liste.get(i-a).charAt(j) == '1')
					counter++;
			}
			if(counter > k){
				objectiveFunction(counter-k);
			}
		}
		return true;
	}			

	
	public boolean eval(){
		this.objectiveFunction(); // Sets the base
		this.splitToList();
		
		if(evalHorizontal()){
			if(evalVertical()){
				if(evalDiagonal())
					return true;
			}
		}
		return false;
	}

	//For debugging only
	public void setString(String debugString){
		state = debugString;
	}
	
	public void splitToList(){
		liste = new ArrayList<String>();
		for (int i = 0; i < m; i++) {
			if(i<1)
				liste.add(state.substring(0, m));
			else
				liste.add(state.substring(i*m, m*(i+1)));
		}
	}
	
	private void objectiveFunction(int punishment){
		f -= punishment*0.01; //The punishment for number of times the k-rule is violated.
		if(f<0)
			f = 0;
	}
	
	public void objectiveFunction(){
		int eggMax = m*k; // Max number of eggs in the carton (for a optimal solution)
		int numOfeggs = getNumberOfEggs();

		int distance = Math.abs(eggMax-numOfeggs);	
		double base = 0;

		// The base 
		if(numOfeggs > 0 && numOfeggs <= eggMax){
			base = numOfeggs*(1/(double)eggMax); // Sets a base level based on the number of eggs on the board.
		}else if(numOfeggs > eggMax){
			if(distance > 2)
				base = 0.3;
			else base = 0.5;
		}
		f = base; // Sets f
	}

	@Override //We think the program fail to compare the 
	public int compareTo(EggCarton other) {
		if(this.getF() < other.getF())
			return -1;
		if(this.getF() == other.getF())
			return 0;
		if(this.getF() > other.getF())
			return 1;
		return 0;
	}	
}