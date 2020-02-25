import java.util.*;

public class Test{

    public static void main(String[] args) {

        //Exercise 2
        Box<String> b = new Box<>("Hi");
        
        int l = b.apply( (s) -> {
            return s.length();
        });

        // System.out.println(l);

        //Exercise 3
        ArrayList<String> sList = new ArrayList<>();

        sList.add("D");
        sList.add("DDDDD");
        sList.add("AA");
        sList.add("A");
        sList.add("B");

        Box<ArrayList<String>> sBox = new Box<>(sList);

        ArrayList<String> res = sBox.apply( (list) -> {
            ArrayList<String> cpy = (ArrayList<String>) list.clone();

            cpy.sort( (s1, s2) -> {
                return s1.compareTo(s2);
            } );

            return cpy;
        });

        // System.out.println(res);

        Box<ArrayList<String>> sBox2 = new Box<>(sList);

        int sums = sBox2.apply( (list) -> {
            int sum = 0;

            for (int i = 0; i < list.size(); i++){
                sum = sum + list.get(i).length();
            }

            return sum;
        
        });
        
        
        System.out.println(sums);

    }


}