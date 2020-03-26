import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ThreadsExercise8
{
    
    public static void main(String[] args) {
		Map< String, Integer > occurrences = new HashMap<>();
		
		List< String > filenames = List.of(
			"files/text1.txt",
			"files/text2.txt",
			"files/text3.txt",
			"files/text4.txt",
			"files/text5.txt",
			"files/text6.txt",
			"files/text7.txt",
			"files/text8.txt",
			"files/text9.txt",
			"files/text10.txt"
		);
		
		CountDownLatch latch = new CountDownLatch( filenames.size() );
        
        //Using a AtomicInteger as a global counter and passing that to the "computeOccurrences" method
        final AtomicInteger globalLWordCount = new AtomicInteger(0);
        
        filenames.stream().forEach(filename -> {
            new Thread( () -> {
                computeOccurrences( filename, occurrences, globalLWordCount );
                latch.countDown();
            }).start();
        });

		try {
			latch.await();
		} catch( InterruptedException e ) {
			e.printStackTrace();
		}
        
        System.out.println("Number of words in total starting with the letter L: " + globalLWordCount.get());
        
        
	}
	
	private static void computeOccurrences( String filename, Map< String, Integer > occurrences, AtomicInteger globalLWordCounter ) {
        
        try {
			Files.lines( Paths.get( filename ) )
				.flatMap( Words::extractWords )
                .forEach(s -> {
                    if (s.startsWith("L")) {
                        globalLWordCounter.incrementAndGet();
                    }
                    
                    s = s.toLowerCase();
                    
                    synchronized( occurrences ) {
						occurrences.merge( s, 1, Integer::sum );
					}
                });
		} catch( IOException e ) {
			e.printStackTrace();
		}
	}
}



