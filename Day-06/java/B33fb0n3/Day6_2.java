import java.io.*;
import java.util.*;

public class Day6_2 {
    public static void main(String args[]) {
        Scanner s = new Scanner(System.in);
        List<String> letters = new LinkedList<String>(Arrays.asList(s.nextLine().split("")));
        for(int i = 0; i < letters.size(); i++) {
            int start = 14;
            if(i < start)
                continue;

            List tempCheck = letters.subList(0 + (i - start), start + (i-start));
            Set<String> set = new HashSet<String>(tempCheck);

            if(set.size() < tempCheck.size()){
            } else {
                System.out.println("Result: "+i);
                break;
            }
        }
    }
}
