package gomspace.second.round.gridbot.logic;

import gomspace.second.round.gridbot.utils.Direction;
import gomspace.second.round.gridbot.utils.Position;

public class Machine {
	private Position p;
    public Direction d;

    public Machine(Position p){
    	this.p = p;
    }
    
    public void setPositionAt(Position p) {
    	this.p.x = p.x;
    	this.p.y = p.y;
    }
    
    
    private void turnLeft(){
        switch(d){
            case DOWN : d = Direction.RIGHT;break;
            case RIGHT : d = Direction.UP;break;
            case UP : d = Direction.LEFT;break;
            case LEFT : d = Direction.DOWN;break;
        }
    }

    private void turnRight(){
        switch(d){
            case DOWN : d = Direction.LEFT;break;
            case RIGHT : d = Direction.DOWN;break;
            case UP : d = Direction.RIGHT;break;
            case LEFT : d = Direction.UP;break;
        }
    }

    public Position moveForward(){
        // and move forward 1 unit;
        if(d == Direction.DOWN) p.y++;
        else if (d == Direction.UP) p.y--;
        else if (d == Direction.RIGHT) p.x++;
        else if (d == Direction.LEFT) p.x--; 
        return this.p;
    }
    
    public Position getPosition() {
    	return this.p;
    }

    public void turn(boolean isCurrentCellBlack){
       if(isCurrentCellBlack) {
    	   // If the machine is in a black square, turn 90° counter-clockwise 
    	   turnLeft();
       }else {
    	   // If the machine is in a white square, turn 90° clockwise
    	   turnRight();
       }
    }
    

    public String toString(){
        return "machine.y = " + p.y + " , machine.x = "  + p.x + " , machine.d = " + d;
    }
}
