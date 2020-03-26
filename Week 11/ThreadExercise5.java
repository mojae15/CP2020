
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadExercise5{

    public static void main(String[] args) {

        Point a = new Point(0, 10);
        Point b = new Point(10, 10);
        //Does not work since x is final, prevents that the same Counter can be in two points
        // b.x = a.x;

        Thread t1 = new Thread( () -> {

            while (a.x.increment() < 20){
                System.out.println("Are x and y equal? "+a.areEqual());
            }


        });

        Thread t2 = new Thread( () -> {
            while (a.y.decrement() > 0){

                //Creates a deadlock, so x and y should probably be private, so a client can't write stuff like this
                synchronized (a.y){
                    synchronized (a.x){
                        System.out.println("Are x and y equal? "+a.areEqual());
                    }
                }

            }


        });

        t1.start();
        t2.start();

        try {
			t1.join();
			t2.join();
		} catch( InterruptedException e ) {
			e.printStackTrace();
        }



    }

    public static final class Point {
        
        // Public by request
        public final Counter x;
        public final Counter y;
        
        public Point(int x, int y) {
            this.x = new Counter(x);
            this.y = new Counter(y);
        }

        //Client side locking
        public boolean areEqual() {
            synchronized(x) {
                synchronized(y) {
                    return x.get() == y.get();
                }
            }
        }
        
        public String toString() {
            synchronized(x) {
                synchronized(y) {
                    return "Point("+x.get()+", "+y.get()+")";
                }
            }
        }
        
    }



    public static class Counter {
        private final AtomicInteger i;
        
        public Counter(int i) {
            this.i = new AtomicInteger(i);
        }
        
        public int increment() {
            return i.incrementAndGet();
        }
        
        public int decrement() {
            return i.decrementAndGet();
        }
        
        public int get() {
            return i.get();
        }
    }


}