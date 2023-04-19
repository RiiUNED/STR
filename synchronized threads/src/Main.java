public class Main {
    public static void main(String[] args) {
        Bridge bridge = new Bridge();
        String name;
        int i = 1;
        long start = System.currentTimeMillis();
        long now;

        // Crea un hilo cada 2 segundos
        while (true) {
        	try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        	Direction direction = Math.random() < 0.5 ? Direction.NORTH : Direction.SOUTH;
        	name = "Thread "+i;
            Th thread = new Th(name, bridge, direction, start);
            String flag = thread.getFlag();
            now = System.currentTimeMillis();
            double t = (now - start)/1000;
            System.out.println(t+ ". nuevo hilo. " + thread.getName() + " " + flag);
            thread.start();
            i++;
        }
    }
}