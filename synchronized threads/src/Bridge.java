import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Bridge {
    private int count = 0;
    private Direction direction = null;
    
    private final ReentrantLock lockcounter = new ReentrantLock();
    private final ReentrantLock lockcondN = new ReentrantLock();
    private final Condition conditionN = lockcondN.newCondition();
    private final ReentrantLock lockcondS = new ReentrantLock();
    private final Condition conditionS = lockcondS.newCondition();

    public void cross(Th thread) throws InterruptedException {
        double t = getTime(thread);
        System.out.println(t+ " " + thread.getName()+thread.getFlag()+" intenta cruzar el puente");
        
        if(direction==null) {
        	direction = thread.getDir();
        } else {
        	if(direction!=thread.getDir()) {
        		if(direction==Direction.NORTH) {
        			lockcondS.lock();
        			try {
        				while(direction==Direction.NORTH) {
        					System.out.println(t+ " " + thread.getName()+thread.getFlag()+" espera");
        					conditionS.await();
            			}
        			} finally {
        				lockcondS.unlock();
        			}
        		} else {
        			lockcondN.lock();
        			try {
        				while(direction==Direction.SOUTH) {
        					System.out.println(t+ " " + thread.getName()+thread.getFlag()+" espera");
        					conditionN.await();
            			}
        			} finally {
        				lockcondN.unlock();
        			}
        		}
        	}
        }
        t = getTime(thread);
        System.out.println(t + " " + thread.getName()+thread.getFlag() +" empieza a cruzar el puente.");
        countUp();
		
		cross();
    	t = getTime(thread);
    	System.out.println(t+ " " + thread.getName()+thread.getFlag() +" ha cruzado el puente.");
    	countDown();
    	
    	if(freeBridge()) {
    	    if(thread.getDir()==Direction.NORTH) {
    	        direction = Direction.SOUTH;
    	        lockcondS.lock();
    	        try {
    	            synchronized (conditionS) {
    	                conditionS.signalAll();
    	            }
    	        } finally {
    	            lockcondS.unlock();
    	        }
    	    } else {
    	        direction = Direction.NORTH;
    	        lockcondN.lock();
    	        try {
    	            synchronized (conditionN) {
    	                conditionN.signalAll();
    	            }
    	        } finally {
    	            lockcondN.unlock();
    	        }
    	    }
    	}
    }
    
    private boolean freeBridge() {
    	boolean free = false;
    	lockcounter.lock();
        try {
        	if(count==0) {free=true;}
        } finally {
        	lockcounter.unlock();
        }
        return free;
    }
    
    private void countUp() {
    	lockcounter.lock();
        try {
        	count++;
        	System.out.println("hilos cruzando: " +  count);
        } finally {
        	lockcounter.unlock();
        }
    }
    
    private void countDown() {
    	lockcounter.lock();
    	try {
        	count--;
        	System.out.println("hilos cruzando: " +  count);
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
