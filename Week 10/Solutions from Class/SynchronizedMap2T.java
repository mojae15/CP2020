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
        
        Map< String, Integer > finalRes = new HashMap<>();

        finalRes.putAll(res1);

        res2.forEach( (key, val) -> finalRes.merge(key, val, Integer::sum));

        System.out.println(finalRes);
	}
}