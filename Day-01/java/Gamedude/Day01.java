import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day01 {

    public static void main(String[] args) throws Exception {

        File inputFile = new File("./Day-01/java/Gamedude/input.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        List<Integer> caloriesElfes = new ArrayList<>();
        int currentCalorieCount = 0;
        String line;
        while((line = bufferedReader.readLine()) != null) {
            if(!line.isEmpty()) {
                currentCalorieCount+=Integer.parseInt(line);
            }
            if(line.isEmpty()) {
                caloriesElfes.add(currentCalorieCount);
                currentCalorieCount = 0;
            }
        }
        caloriesElfes.sort(Collections.reverseOrder());

        System.out.println("Part 1: " + caloriesElfes.get(0));
        System.out.println("Part 2: " + caloriesElfes.stream().limit(3).mapToInt(Integer::intValue).sum());

    }
}