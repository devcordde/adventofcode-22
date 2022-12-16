import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day16 {
    private static HashMap<String, Valve> valves;
    private static final Map<String, Integer> maxReleaseForTime = new HashMap<>();

    public static void main(String args[]) {
       final InputStream inputStream = System.in;

       Scanner scanner = new Scanner(inputStream);

       Pattern pattern = Pattern.compile("^Valve ([A-Z]+) has flow rate=([0-9]+); tunnels? leads? to valves? ([A-Z, ]+)$");
       Matcher matcher;
       valves = new HashMap<>();
       while (scanner.hasNextLine()) {
           String line = scanner.nextLine();
           matcher = pattern.matcher(line);
           if (matcher.find()) {
               valves.put(matcher.group(1), new Valve(matcher.group(1), Integer.parseInt(matcher.group(2)), matcher.group(3).split(", ")));
           } else {
               throw new RuntimeException("no match found! (" + line + ")");
           }
       }

       int maxRelease = calcMaxRelease("AA", new ArrayList<>(), 30);
       System.out.println("Solution 1: " + maxRelease);

       List<String> openable = valves.values().stream().filter(r -> r.getFlowRate() > 0).map(r->r.name).toList();

       State entry = new State("AA", new ArrayList<>(), 0);
       Set<State> queue = new HashSet<>();
       queue.add(entry);

       for (int i=0; i<26; i++) {
           Set<State> nextQueue = new HashSet<>();
           for (State state : queue) {
               int release = state.getRelease();
               if (state.open.size() == openable.size()) {
                   nextQueue.add(new State(state.me, state.open, release));
                   continue;
               }
               if(valves.get(state.me).flowRate > 0 && !state.open.contains(state.me)) {
                   List<String> newOpen = new ArrayList<>(state.open);
                   newOpen.add(state.me);
                   nextQueue.add(new State(state.me, newOpen, release));
               }
               for (String connection : valves.get(state.me).connections) {
                   nextQueue.add(new State(connection, state.open, release));
               }
           }
           queue = nextQueue;
       }


       List<State> resultList = new ArrayList<>(queue.stream().toList());
       resultList.sort(State::compareTo);

       int solution2 = 0;
       otherloop:
       for (int me = 0; me < resultList.size() - 1; me ++) {
           for (int elephant = me + 1; elephant < resultList.size(); elephant ++) {
               if (resultList.get(me).hasNotOpened(resultList.get(elephant).open)) {
                   solution2 = resultList.get(me).pressureReleased + resultList.get(elephant).pressureReleased;
                   break otherloop;
               }
           }
       }

       System.out.println("Solution 2: " + solution2);
   }

    private static int calcMaxRelease(String pos, List<String> open, int timeLeft) {
        if (timeLeft <= 0) {
            return 0;
        }
        String key = pos + "-" + String.join("|", open) + "-" + timeLeft;
        if (maxReleaseForTime.containsKey(key)) {
            return maxReleaseForTime.get(key);
        }
        Valve posObj = valves.get(pos);
        int maxRelease = 0;
        int release = (timeLeft - 1) * posObj.getFlowRate();
        List<String> newOpen = new ArrayList<>(open);
        newOpen.add(pos);
        for (String connection : posObj.getConnections()) {
            if (release > 0 && !open.contains(pos)) {
                maxRelease = Math.max(maxRelease, release + calcMaxRelease(connection, newOpen, timeLeft-2));
            }
            maxRelease = Math.max(maxRelease, calcMaxRelease(connection, open, timeLeft-1));
        }
        maxReleaseForTime.put(key, maxRelease);
        return maxRelease;
    }

    private static class Valve {
        private final String name;
        private final int flowRate;
        private final String[] connections;

        public Valve(String name, int flowRate, String[] connections) {
            this.name = name;
            this.flowRate = flowRate;
            this.connections = connections;
        }

        public int getFlowRate() {
            return flowRate;
        }

        public String[] getConnections() {
            return connections;
        }
    }

    private static class State {
        private final String me;
        private final List<String> open;
        private final int pressureReleased;

        public State(String me, List<String> open, int pressureReleased) {
            this.me = me;
            this.open = open;
            this.pressureReleased = pressureReleased;
        }

        public int getRelease() {
            return getRate() + pressureReleased;
        }

        private int getRate() {
            int retVal = 0;
            for (String valve : open) {
                retVal += valves.get(valve).flowRate;
            }
            return retVal;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return pressureReleased == state.pressureReleased && me.equals(state.me) && open.equals(state.open);
        }

        @Override
        public int hashCode() {
            return Objects.hash(me, open, pressureReleased);
        }

        public int compareTo(State state) {
            if (pressureReleased > state.pressureReleased) {
                return -1;
            }
            if (pressureReleased < state.pressureReleased) {
                return 1;
            }
            return 0;
        }

        public boolean hasNotOpened(List<String> open) {
            for (String o : this.open) {
                if (open.contains(o)) {
                    return false;
                }
            }
            return true;
        }
    }
}
