
public class Th extends Thread {
    private String name;
    private Direction direction;
    private Bridge bridge;
    private long start;

    public Th(String name, Bridge bridge, Direction direction, long start) {
        this.name = name;
        this.bridge = bridge;
        this.direction = direction;
        this.start = start;
    }
    
    public long getStart() {
    	return start;
    }
    
    public Direction getDir() {
    	return this.direction;
    }

    @Override
    public void run() {
    	try {
			bridge.cross(this);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    public String getFlag() {
    	String flag = null;
    	switch (this.direction) {
		case NORTH:
			flag = "*N*";
			break;
		case SOUTH:
			flag = "*S*";
			break;
		default:
			break;
		}
    	return flag;
    }
}
