package gomspace.second.round.gridbot.utils;

public class Position {
	public int x;
	public int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Position(Position p) {
		this.x = p.x;
		this.y = p.y;
	}
	
	@Override
	public int hashCode()
	{
		StringBuilder sb = new StringBuilder(); 
		sb.append(x).append(',').append(y);
	    return sb.toString().hashCode();
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }
}
