import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class SynchronizedMap2T
{
	public static void main(String[] args)
	{
		Map< String, Integer > res1 = new HashMap<>();
		Map< String, Integer > res2 = new HashMap<>();
		
		Thread t1 = new Thread( () -> {
			try {
				Files.lines( Paths.get( "text1.txt" ) )
					.flatMap( s -> Stream.of( s.split( " " ) ) )
					.forEach( word -> {
                        res1.merge( word, 1, Integer::sum );
						
					} );
			} catch( IOException e ) {
				e.printStackTrace();
			}
		});
		
		Thread t2 = new Thread( () -> {
			try {
				Files.lines( Paths.get( "text2.txt" ) )
					.flatMap( s -> Stream.of( s.split( " " ) ) )
					.forEach( word -> {
						
                        res2.merge( word, 1, Integer::sum );
						
					} );
			} catch( IOException e ) {
				e.printStackTrace();
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

        Map<String, Integer> result = new HashMap<>();

        result = mergeMaps(res1, res2);

        System.out.println(result);
        

    }

    /**
     * Merge two HashMap<String, Integer> together, and return the resulting HashMap<String, Integer>
     */
    private static Map<String, Integer> mergeMaps(Map<String, Integer> res, Map<String, Integer> m2) {

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