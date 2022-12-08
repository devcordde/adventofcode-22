import java.io.*;
import java.util.*;

public class Day7_2 {
    public static void main(String args[]) {
        final InputStream input = System.in;
        Scanner s = new Scanner(input);
        s.useDelimiter("\n");
        HashMap<String, Integer> dirs = new HashMap<>();
        String currentDir = "";
        int result = 0;
        ArrayList<String> path = new ArrayList<String>();
        while(s.hasNext()) {
            String line = s.next();
            String pathString = String.join("/", path);
            if((line.startsWith("$ cd") && line.length() > 3)) {
                currentDir = line.split(" ")[2];
                if(currentDir.equals("..")) {
                    final int addableSize = dirs.get(pathString);
                    path.remove(path.size()-1);
                    String addTo = String.join("/", path);
                    dirs.put(addTo, (dirs.get(addTo) + addableSize));
                } else {
                    path.add(currentDir);
                    pathString = String.join("/", path);
                    dirs.put(pathString, 0);
                }
            }
            if(!line.startsWith("$") && !line.startsWith("dir")) {
                int size = dirs.getOrDefault(pathString, 0);
                size += Integer.parseInt(line.split(" ")[0].trim());
                dirs.put(pathString, size);
            }
        }

        int maxSpace = 70000000;
        int usedSpace = dirs.get("/");
        int freeSpace = maxSpace - usedSpace;
        int updateSize = 30000000;
        int needToDeleteSize = updateSize - freeSpace;

        int nearest = 999999999;
        for(int size : dirs.values()) {
            if(size < needToDeleteSize)
                continue;
            if(nearest > size)
                nearest = size;
        }
        System.out.println("Result: "+nearest);
    }
}
