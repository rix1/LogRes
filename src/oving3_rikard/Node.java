package oving3_rikard;

import java.util.ArrayList;

public class Node implements Comparable<Node>{

	private State state;
	private int f=0, g=0, h=0;
	private boolean status; //true = OPEN, false = CLOSED
	private ArrayList<Node> children;
	private Node parent;
	
	
	 public Node(int size){
		children = new ArrayList<Node>();
		state = new State(size);
		setG(-1);
		generateH();
	 }
	 
	 public Node(Node parent, int currentPos){
		 children = new ArrayList<Node>();
		 this.parent = parent;
		 this.state = new State(parent.getState(), currentPos);
		 setG(parent.getG());
		 generateH();
		 parent.addKids(this);
	 }
	 
	 public void printChildren(){
		 for (Node child : children) {
			 //TODO
		}
	 }

	public void setStatus(boolean status){
		this.status = status;
	}
	
	public String generateReverseMap(){
		return state.getReverseMap();
	}
	
	
	public ArrayList<Node> getKids(){
		return children;
	}
	
	
	// Copies children, update parent of immediate succsessors and updates g()
	
	public void updateKids(ArrayList<Node> newKids, int difference, Node parent){
		this.children = newKids;
		
		for (Node node : children) {
			node.g -= difference;
			node.updateF();
			if((node.getG() - parent.getG()) == 1){
				node.setParent(parent);
			}
		}
	}
	private void setParent(Node newParent){
		this.parent = newParent;
	}
	
	private void setG(int preVal){
		g = preVal+1;
		updateF();
	}
	
	
	private void generateH(){
		Piece[] temp = state.getPieceMap();
		h = 0;
		for (Piece piece : temp) {
//			System.out.println("MyPos: " + piece.getPosition() + " - My Goal is @: " + piece.getGoal() + " - which I am " + piece.getDistanceToGoal() + " steps away from");
			h += piece.getDistanceToGoal();
		}
		updateF();
	}
	
	public int getH(){
		return h;
	}
	
	public Node getParent(){
		return this.parent;
	}

	public boolean solutionFound(){
		if(h == 0)
			return true;
		return false;	
	}
	
	public void updateF(){
		f = g + h;
	}
	public int getF(){
		return f;
	}
	public int getEmptypos(){
		return state.getBlank();
	}
	
	public void addKids(Node kid){
		children.add(kid);
	}
	
	public int getG(){
		return g;
	}
	
	public State getState(){
		return this.state;
	}
	
	public String toString(){
//		String print = "State of Node:\n-----";
		String print = "\nf(): " + f + "\ng(): " + g + "\nh(): " + h + "\n";
//		print += "state of this Node: " + this.state.toString();
//		print += "\nNumber of children: " + this.children.size();
		return print;
	}

	@Override
	public int compareTo(Node other) {
		return this.getF()-other.getF();
	}
}