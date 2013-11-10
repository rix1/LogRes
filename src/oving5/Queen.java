package oving5;

public class Queen {

	private int position;
	private int violations;
	
	public Queen(int pos){
		position = pos;
		violations = 0;
	}

	public void setPosition(int newPos){
		position = newPos;
	}
	
	public int getViolations() {
		return violations;
	}

	public void setViolations(int violations) {
		this.violations = violations;
	}

	public int getPosition() {
		return position;
	}

	public void addVoilation(){
		violations ++;
	}
}
