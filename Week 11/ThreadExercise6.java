import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ThreadExercise6{

    public static void main(String[] args) {

        String[] files = {"text1.txt", "text2.txt",  "text3.txt", "text4.txt"};

        Stream<String> fileStream = Stream.of(files);

        //Sequential Version
        // Map<String, Integer> seqMap = computeOccurrencesSequential(fileStream);
        // System.out.println(seqMap);
        
        //Concurrent Version
        Map<String, Integer> conMap = computeOccurrencesConcurrent(fileStream);
        System.out.println(conMap);
        
    }

    
    public static Map< String, Integer > computeOccurrencesSequential( Stream< String > filenames ){

        final Map< String, Integer > results = new HashMap<>();
        
        filenames.map(s -> Path.of(s))
                .forEach((path) -> {
                    try {

                        Files.lines(path)
                                .flatMap(line -> Stream.of(line.split(" ")))
                                .forEach( word -> {
                                    results.merge( word, 1, Integer::sum );
                                });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        
        return results;

    }

    public static Map< String, Integer > computeOccurrencesConcurrent ( Stream< String > filenames ){

        //First we map every String to a Path
        Map<String, Integer> res = filenames.map(Path::of)
                                            //Then we map to a pair, containing the hashmap and the thread using it
                                            .map(ThreadExercise6::mapToThread)
                                            //Start all the threads
                                            .peek( (pair) -> pair.getValue().start())
                                            //Let the threads finish
                                            .peek( (pair) -> {
                                                try {
                                                    pair.getValue().join();
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            })
                                            //Map from pairs to hashmaps
                                            .map( (pair) -> pair.getKey())
                                            //Merge all the maps
                                            .reduce(new HashMap<String, Integer>(), (prev, cur) -> {

                                                cur.forEach( (k, v) -> prev.merge(k, v, (a, b) -> a+b));
                                                return prev;
                                            });
        

        return res;
    }

    private static Pair<Map<String, Integer>, Thread> mapToThread(Path path){
        Map<String, Integer> threadMap = new HashMap<>();
        Thread t = new Thread( () -> {
            
            try {
                
                Files.lines(path)
                    .flatMap(line -> Stream.of(line.split(" ")))
                    .forEach( word -> {
                        threadMap.merge( word, 1, Integer::sum );
                    });

            } catch (IOException e) {
                e.printStackTrace();
            }

            });

        return new Pair<>(threadMap, t);

    }

    private static class Pair<K, V>{

        private K key;
        private V value;

        public Pair(K key, V value){
            this.key = key;
            this.value = value;
        }

        public K getKey(){
            return this.key;
        }

        public V getValue(){
            return this.value;
        }

    }


}