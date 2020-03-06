//Exercise 1
public class Counter {

    private int i = 0;
    private Object lock = new Object();

    public int increment() {
        synchronized (lock) {
            return ++i;
        }
    }

    public int decrement() {
        synchronized (lock) {
            return --i;
        }
    }

}