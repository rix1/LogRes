package oving3_rikard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Astar {

	private Node initialNode;
	private HashMap<String, Node> states = new HashMap<String, Node>();
	private ArrayList<Node> closedNodes = new ArrayList<Node>();
	private int itemsPushedToOpen = 0; // For debugging purposes only
	private String reverseMap; // This is how the solution should look like
	private PriorityQueue<Node> openQueue = new PriorityQueue<Node>(1000);
	private int numberOfMoves = 0;
	
	public static void main(String[] args) {
		Astar temp = new Astar();
		temp.run();
	}
	
	// PICK THE SIZE OF YOUR MAP WITH THE SIZE VARIABLE.
	// NOTE: THIS HAVE TO BE AN ODD NUMBER.
	public void run(){
		
		int size = 9;
		initialNode = new Node(size);
		addState(initialNode);
		reverseMap = initialNode.generateReverseMap();
		
		pushToOpenQue(initialNode);
		agendaLoop();
		
		if(states.containsKey(reverseMap))
			System.out.println("Ok solution found from reverseMap");
		
		
		System.out.println("\n----\n");
		System.out.println("Number of nodes pushed to OPEN: " + itemsPushedToOpen);
		System.out.println("Size of ClosedNodes: " + closedNodes.size());
		System.out.println("Size of OpenQueue: " + openQueue.size());
		System.out.println("Size of States: " + states.size());
		System.out.println("Number of moves: " + numberOfMoves);
		
	}

	
	public void agendaLoop(){
		while(!openQueue.isEmpty()){
			
			// In some cases this workes.
			if(openQueue.peek().getH() == 0){
				System.out.println("Ok solution found from h()");
				break;
			}
			
				findKidz(openQueue.poll());
			if(openQueue.isEmpty()){
				break;
			}
		}
	}
	
	public void pushToClosedQueue(Node node){
		node.setStatus(false);
		closedNodes.add(node);
	}
	
	public void pushToOpenQue(Node node){
		itemsPushedToOpen ++;
		node.setStatus(true);
		openQueue.add(node);
	}
	
	public void findKidz(Node parent){
		int blankSpace = parent.getEmptypos();
		Piece[] state = parent.getState().getPieceMap();
		int distance;
		int currentPos;
		for (int i = 0; i < state.length; i++) {
			currentPos = state[i].getPosition();
			
			distance = Math.abs(currentPos-blankSpace);
			
			if(distance <= 2 && distance >0){
				numberOfMoves ++;
				Node child = new Node(parent, i);
				if(addState(child)){
					pushToOpenQue(child);
				}
			}
		}
		pushToClosedQueue(parent);
	}
	
	public boolean addState(Node tobeAdded){
		String stateMap = "" + tobeAdded.getState();
		if(!states.containsKey(stateMap)){
			states.put(stateMap, tobeAdded);
			return true;
		}
		else {
			Node existingNode = states.get(stateMap);
			if(existingNode.getG() > tobeAdded.getG()){
				states.remove(stateMap);
				
				int difference = existingNode.getG() - tobeAdded.getG();
				tobeAdded.updateKids(existingNode.getKids(), difference, tobeAdded);

				states.put(stateMap, tobeAdded);
				return false;
			}
		} return false;
	}
}
