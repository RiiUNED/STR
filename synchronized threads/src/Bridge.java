import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Bridge {
    private int count = 0;
    private Direction direction = null;
    
    private final ReentrantLock lockcounter = new ReentrantLock();
    private final ReentrantLock lockcond = new ReentrantLock();
    private final Condition condition = lockcond.newCondition();

    public void cross(Th thread) throws InterruptedException {
        double t = getTime(thread);
        System.out.println(t+ " " + thread.getName()+" try to cross the bridge");
        lockcond.lock(); // Adquirir el bloqueo
    	try {
    	    while (count != 0) {
    	        condition.await(); // Esperar a que se cumpla la condici�n
    	    }
    	    // Acci�n a realizar cuando se cumpla la condici�n
    	} finally {
    	    lockcond.unlock(); // Liberar el bloqueo
    	}
        countUp();
        t = getTime(thread);
		System.out.println(t + " " + thread.getName() +" begin to cross the bridge");
		System.out.println("count: " +  count);
		cross();
    	t = getTime(thread);
    	System.out.println(t+ " " + thread.getName() +" has crossed the bridge");
    	countDown();
    	System.out.println("count: " +  count);
    	lockcond.lock();
    	try {
    	    condition.signal(); // Hace el signal en el primer hilo en espera en la cola
    	} finally {
    	    lockcond.unlock(); // Libera el bloqueo asociado al objeto de bloqueo
    	}
    	
    }
    
    private void countUp() {
    	lockcounter.lock();
        try {
        	count++;
        } finally {
        	lockcounter.unlock();
        }
    }
    
    private void countDown() {
    	lockcounter.lock();
    	try {
        	count--;
        } finally {
        	lockcounter.unlock();
        }
    }
    
    private double getTime(Th thread) {
    	long now = System.currentTimeMillis();
        double t = (now - thread.getStart())/1000;
        return t;
    }
    
    private static void cross() throws InterruptedException {
    	Th.sleep(5000);
    }
}
