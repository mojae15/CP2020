import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class ThreadsExercise7
{

	public static void main(String[] args) {
		Map<String, Integer> occurrences = new HashMap<>();
		
		List<String> filenames = List.of(
			"text1.txt",
			"text2.txt",
			"text3.txt",
			"text4.txt",
			"text5.txt",
			"text6.txt",
			"text7.txt",
			"text8.txt",
			"text9.txt",
			"text10.txt"
		);
		
		CountDownLatch latch = new CountDownLatch( filenames.size() );
		
		//Array that will store the amounts of "L"'s in each file
        final int[] lWordCounts = new int[filenames.size()];
        
        for (int i = 0; i < filenames.size(); ++i) {
            
			final String filename = filenames.get(i);
			
			//Use the counter to put the result into the array at a safe position
			final int threadIndex = i;
			//These variables are final so they can be used inside the lambda function
            
            new Thread( () -> {
				lWordCounts[threadIndex] = computeOccurrences( filename, occurrences );
				latch.countDown();
			}).start();
        }

		try {
			latch.await();
		} catch( InterruptedException e ) {
			e.printStackTrace();
		}
        
        System.out.println("Number of words in total starting with the letter L: " + IntStream.of(lWordCounts).sum());
		
	}
	
	private static int computeOccurrences( String filename, Map< String, Integer > occurrences ) {
		
		//Counter for the amount of "L"'s we see
        final int lWordCount[] = new int[1];
        
        try {
			Files.lines( Paths.get( filename ) )
				.flatMap( Words::extractWords )
                .forEach(s -> {
                    if (s.startsWith("L")) {
                        ++lWordCount[0];
                    }
                    
                    s = s.toLowerCase();
                    
                    synchronized( occurrences ) {
						occurrences.merge( s, 1, Integer::sum );
					}
                });
		} catch( IOException e ) {
			e.printStackTrace();
		}
        
        return lWordCount[0];
	}
	
}
