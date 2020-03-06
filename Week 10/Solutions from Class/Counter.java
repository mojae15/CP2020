public class Counter{

    private Integer i;
    private final Object lock = new Object();

    public Counter(){
        this.i = 0;
    }

    public void increment(){
        synchronized (lock){
            i++;
            // System.out.println(i);
        }
    }

    public void decrement(){
        synchronized (lock){
            i--;
            // System.out.println(i);
        }
    }

    public int getI(){
        return i;
    }


}