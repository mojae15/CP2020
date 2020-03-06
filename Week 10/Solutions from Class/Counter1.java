import java.util.concurrent.atomic.*;

public class Counter{

    private AtomicInteger i;

    public Counter() {
        i = new AtomicInteger();
    }

    // private int i;

    public int increment(){
        return i.incrementAndGet();
    }

    public int decrement(){
        return i.decrementAndGet();
    }


}