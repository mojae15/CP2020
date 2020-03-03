import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Exercises{

    public static void main(String[] args) throws IOException{
        
        //Exercise 1
        // Files.lines(Path.of("text1.txt")).filter( (l) -> l.endsWith(".")).forEach(System.out::println);

        
        //Exercise 2
        ArrayList<String> sList = new ArrayList<>();

        sList = Files.lines(Path.of("text1.txt"))
                    .filter( (l) -> l.startsWith("C"))
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        // System.out.println(sList);


        //Exercise 3
        long lCount = Files.lines(Path.of("text1.txt"))
                    .filter( (l) -> l.contains("L")).
                    count();

        // System.out.println(lCount);


        //Exercise 4
        int cCount = Files.lines(Path.of("text1.txt"))
                    .mapToInt( (l) -> occurrences(l, 'C'))
                    .sum();

        // System.out.println(cCount);


        //Exercise 5
        HashMap<String, Integer> map = new HashMap<>();
        map = Files.lines(Path.of("text1.txt"))
                    .map( (l) -> mapLine(l))
                    .reduce(new HashMap<String, Integer>(), (res, elem) -> mergeMaps(res, elem));

        System.out.println(map);


    }


     /**
     * Count occurences of a character in a string, using chars()
     */
    static int occurrences(String s, char c) {

        /**
         * Get a stream of "chars" from the String
         * Filter out the charactes we are not looking for
         * Count how many elements are left in the stream
         */

        return (int) s.chars().filter(ch -> ch == c).count();

    }

    /**
     * Map a String to a HashMap<String, Integer> that contains the number
     * of occurences of each character in the string
     */
    private static HashMap<String, Integer> mapLine(String s) {

        // Map that will hold the result
        HashMap<String, Integer> temp = new HashMap<>();

        /**
         * Get a stream of "chars" from the String
         * Since "chars()" returns a IntStream, map each element to a String
         */
        s.chars().mapToObj(Character::toString)
        // For each element in the stream, insert it into the map
        .forEach(ch -> {

            /**
             * compute() takes the key to be used as the first argument, in this
             * case a character from the input string.
             * 
             * The second argument is a "remapping function", which determines how to
             * map the given key.
             * 
             * Internally, HashMap tries to get the old value for the key given in the first argument,
             * which it then uses to call the function in the second argument.
             * 
             * This means that if the key is not in the map, the value is null,
             * which means that the function defined in the lambda expression is called
             * with val = null, and therefore we set the value for that key to be 1, since this
             * is the first occurence of that key (character)
             * 
             * If the key already is in the map, val is not null, and in that case we map
             * the given key to its old value + 1, since we have seen the key one more time
             */

            temp.compute(ch, (key, val) -> {
                return (val == null) ? 1 : val + 1;
            });
        });

        return temp;

    }

    /**
     * Merge two HashMap<String, Integer> together, and return the resulting HashMap<String, Integer>
     */
    private static HashMap<String, Integer> mergeMaps(HashMap<String, Integer> res, HashMap<String, Integer> m2) {

        /**
         * Go through the second HashMap and insert everything from it into the first HashMap
         * In this case, forEach() gives us two values, a (key, val) pair, and we use the previous
         * value to compute the new value for that key in the other HashMap
         */
        m2.forEach((ch, i) -> {
            res.compute(ch, (key, val) -> {
                return (val == null) ? i : i + val;
            });
        });

        return res;

    }

}