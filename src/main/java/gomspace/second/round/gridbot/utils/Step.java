package gomspace.second.round.gridbot.utils;

public enum Step {
	 DO_NOTHING("TURN_MACHINE"), 
	 TURN_MACHINE("MOVE_FORWARD"),
	 MOVE_FORWARD("FLIP_BASE_CELL"),
	 FLIP_BASE_CELL("TURN_MACHINE");
	
	
	private String nextStep;

    Step(String next) {
        this.nextStep = next;
    }
    
    public Step next() {
    	return Step.valueOf(nextStep);
    }
	
}
