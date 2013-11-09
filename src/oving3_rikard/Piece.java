package oving3_rikard;

public class Piece {
	
	private String name;
	private final int goalPos;
	private final int initialPos;
	private int currentPos;
	private int distanceToGoal;
	
	public Piece(int initialPos, String name, int goalPos){
		this.initialPos = initialPos;
		this.name = name;
		this.goalPos = goalPos;
		
		setPosition(initialPos);
	}
	
	public int getPosition(){
		return currentPos;
	}
	
	public void setPosition(int newPos){
		distanceToGoal = Math.abs(goalPos-newPos);
		currentPos = newPos;
	}
	
	public int getGoal(){
		return goalPos;
	}
	
	public int getDistanceToGoal(){
		return distanceToGoal;
	}
	
	// Creates a string with name of char and goal position
	public String getSpecialString(){
		return name + goalPos;
	}
	
	public String toString(){
		return name + currentPos;
	}
	public int getInitialPosition(){
		return initialPos;
	}
}
