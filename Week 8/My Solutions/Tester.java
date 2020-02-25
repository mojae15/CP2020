import java.util.ArrayList;
import java.util.List;

public class Tester {

    public static void main(String[] args){

        //Exercise 3
        ArrayList<String> sList = new ArrayList<>();

        sList.add("DCBA");
        sList.add("DCABA");
        sList.add("DBCABB");
        sList.add("ABCD1232131");
        sList.add("BCD");
        sList.add("QB");

        Box<ArrayList<String>> sBox = new Box<>(sList);

        ArrayList<String> sortedList = sBox.apply( (s) -> {
            s.sort( (s1, s2) -> {
                return s1.compareTo(s2);
            } );
            return s;
        });

        // System.out.println(sortedList);

        //Exercise 4

        int sumLengths = sBox.apply( (s) -> {
            int sum = 0;
            for (String str : s){
                sum = sum + str.length();
            }
            return sum;
        });

        // System.out.println(sumLengths);

        //Excersice 5

        Box<String> b1 = new Box<>("a");
        Box<String> b2 = new Box<>("aa");
        Box<String> b3 = new Box<>("aaa");
        Box<String> b4 = new Box<>("aaaa");

        ArrayList<Box<String>> bList = new ArrayList<>();
        bList.add(b1);
        bList.add(b2);
        bList.add(b3);
        bList.add(b4);

        List<Integer> resList = Box.applyToAll(bList, s -> {
            return s.length();
        });

        System.out.println(resList);

    }



    
}