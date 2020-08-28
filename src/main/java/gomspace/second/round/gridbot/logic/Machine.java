package gomspace.second.round.gridbot.logic;

public class Machine {
	int h;
    int w;
    int initial_h;
    int initial_w;
    Direction d;
    int height;
    int width;

    public Machine(){}
    
    public void turnLeft(){
        switch(d){
            case DOWN : d = Direction.RIGHT;break;
            case RIGHT : d = Direction.UP;break;
            case UP : d = Direction.LEFT;break;
            case LEFT : d = Direction.DOWN;break;
        }
    }

    public void turnRight(){
        switch(d){
            case DOWN : d = Direction.LEFT;break;
            case RIGHT : d = Direction.DOWN;break;
            case UP : d = Direction.RIGHT;break;
            case LEFT : d = Direction.UP;break;
        }
    }

    public boolean moveForward(char[][]map){
        int current_h = h;
        int current_w = w;
        if(d == Direction.DOWN && h < height-1 && map[h+1][w] != '#') h++;
        else if (d == Direction.UP && h > 0 && map[h-1][w] != '#') h--;
        else if (d == Direction.RIGHT && w < width-1 && map[h][w+1] != '#') w++;
        else if (d == Direction.LEFT && w > 0 && map[h][w-1] != '#') w--;
        else return false;
        map[current_h][current_w]++;
        return true;        
    }

    public void move(char[][] map, Boolean isLeft){
       
    }

    public String toString(){
        return "pika.h = " + h + " , pika.w = "  + w + " , pika.d = " + d.toString();
    }
}
