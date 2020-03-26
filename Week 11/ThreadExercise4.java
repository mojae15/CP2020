import java.util.Set;
import java.util.HashSet;

public class ThreadExercise4 {

    public static void main(String[] args) {

        PersonSet pSet = new PersonSet();


        //Unsafe access to set
        Thread t1 = new Thread( () -> {
            Set<Person> t1Set = pSet.getSet();
            for (int i = 0; i < 10000; i++){
                t1Set.add(new Person());
            }
        }); 


        Thread t2 = new Thread( () -> {
            Set<Person> t2Set = pSet.getSet();
            for (int i = 0; i < 10000; i++){
                t2Set.add(new Person());
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

        System.out.println("Set size: "+pSet.getSet().size());



    }

    private static class PersonSet {
        // @GuardedBy("this")
        private final Set<Person> mySet = new HashSet<Person>();

        public synchronized void addPerson(Person p) {
            mySet.add(p);
        }

        public synchronized boolean containsPerson(Person p) {
            return mySet.contains(p);
        }

        public Set<Person> getSet() {
            return mySet;
        }
    }

    private static class Person {

    }

}
