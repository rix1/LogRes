package oving3_rikard;

public class State {

	private Piece[] pieceMap;
	private int centerOfMap;
	private int mapSize;

	// This should be called on startup
	public State(int size){
		mapSize = size;
		centerOfMap = (mapSize/2);
		createMap(mapSize);
	}

	public void updatePositions(int toBeSwapped){
		int centerPos = pieceMap[centerOfMap].getPosition();
		int newPos = pieceMap[toBeSwapped].getPosition();
		
		pieceMap[centerOfMap].setPosition(newPos);
		pieceMap[toBeSwapped].setPosition(centerPos);

	}

	// NEVER MUST ONE CHANGE THE ORDER OF THE ARRAY
	public State(State oldState,int toBeSwapped){
		pieceMap = oldState.getPieceMap();
		updatePositions(toBeSwapped);
	}

	// This should be called on startup
	private void createMap(int size){
		//		System.out.println("Size: " + size + " , CenterOfMAP: "+ centerOfMap);
		if(size % 2 != 0){
			pieceMap = new Piece[size];
			String temp;

			for (int i = 0; i < size; i++) {
				if(i<centerOfMap){
					temp = "B";
					pieceMap[i] = new Piece(i, temp, calculateGoal(i));
				}
				else if(i>centerOfMap){
					temp = "R";
					pieceMap[i] = new Piece(i, temp, calculateGoal(i));
				}
				else if (i == centerOfMap){
					pieceMap[i] = new Piece(i, " ", centerOfMap);
				}
			}
		}
		else System.out.println("The board cannot have a even number of tiles");
	}

	/**
	public String getReverseMap(){
		String temp = "(";
		for (int i = 0; i < mapSize; i++) {
			if(i > 0)
				temp += ",";
			if(i<centerOfMap)
				temp += "R0";
			if(i == centerOfMap)
				temp += " 0";
			if(i>centerOfMap)
				temp += "B0";
		}
		temp += ")";
		return temp;
	}
	 */

	public int calculateGoal(int initialPos){
		if(initialPos < centerOfMap)
			return initialPos + centerOfMap+1;
		else if(initialPos > centerOfMap)
			return initialPos - (centerOfMap+1);
		else return centerOfMap;

	}

	public String toString(){
		String theMap = "(";
		for (int i = 0; i < pieceMap.length; i++) {
			if(i>0)
				theMap += ",";
			theMap += pieceMap[i].toString();
		}
		theMap += ")";
		return theMap;
	}

	// Creates a map in which mimics the goalMAP
	public String getReverseMap(){
		String theMap = "(";
		for (int i = 0; i < pieceMap.length; i++) {
			if(i>0)
				theMap += ",";
			theMap += pieceMap[i].getSpecialString();
		}
		theMap += ")";
		return theMap;
	}


	public Piece getPiece(int pieceToGet){
		return pieceMap[pieceToGet];
	}

	public Piece[] getPieceMap(){
		return pieceMap;
	}

	// NEW, JAAI! Dis is alot shorter =D :
	public int getBlank(){
		return pieceMap[centerOfMap].getPosition();
	}

}
