public class Main {
    public static void main(String[] args) {
        Bridge bridge = new Bridge();
        String name;
        int i = 1;
        long start = System.currentTimeMillis();
        long now;

        // Create and start threads every 2 seconds
        while (true) {
        	try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	Direction direction = Math.random() < 0.5 ? Direction.NORTH : Direction.SOUTH;
        	name = "Thread "+i;
            Thread thread = new Th(name, bridge, direction, start);
            now = System.currentTimeMillis();
            double t = (now - start)/1000;
            System.out.println(t+ ". new thread. " + thread.getName());
            thread.start();
            i++;
        }
    }
}