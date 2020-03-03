import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Exercises{

    public static void main(String[] args) throws IOException{

        //Exercise 1   
        // Files.lines(Path.of("text.txt"))

        // Files.lines(Paths.get("text.txt"))

        //     .filter( (l) -> l.endsWith(".") )
        //     .forEach( System.out::println );

        //Exercise 2

        ArrayList<String> sList = new ArrayList<>();

        sList = Files.lines(Paths.get("text.txt"))
                    .filter( (l) -> l.startsWith("C"))
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        
        // System.out.println(sList);


        //Exercise 3
        long lCount = Files.lines(Path.of("text.txt"))
                    .filter( (l) -> l.contains("L"))
                    .count();

        // System.out.println(lCount);

        //Exercise 4
        // int count = Files.lines(Path.of("text.txt"))
        //             .mapToInt( (l) -> (int) l.chars().filter( (c) -> c == 'C').count())
        //             .sum();

        // System.out.println(count);


        //Exercise 5

        HashMap<String, Integer> cMap = new HashMap<>();

        cMap = Files.lines(Path.of("text.txt"))
                    .map( (l) -> {

                        HashMap<String, Integer> res = new HashMap<>();

                        l.chars().forEach( (c) -> res.merge(String.valueOf((char) c), 1, (k, v) -> k+v));  

                        return res;
                    } )
                    .reduce(new HashMap<String, Integer>(), (prev, cur) -> {

                        cur.forEach( (k, v) -> prev.merge(k, v, (a, b) -> a+b));
                        return prev;
                    });

        System.out.println(cMap);

    }
}