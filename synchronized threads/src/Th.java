
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

    public void run() {
    	try {
			bridge.cross(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
