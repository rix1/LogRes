package oving5;

public class Queen {

	private int position;
	private int violations;
	
	
	public Queen(int pos){
		position = pos;
		violations = 0;
	}
	
	public int getViolations(){
		return violations;
	}
	
	public int getPosition(){
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public void setViolations(int violations) {
		this.violations = violations;
	}
}
