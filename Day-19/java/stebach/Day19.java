import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19 {
    public record Blueprint(
            int id,
            int oreRobotOreCost,
            int clayRobotOreCost,
            int obsidianRobotOreCost,
            int obsidianRobotClayCost,
            int geodeRobotOreCost,
            int geodeRobotObsidianCost
    ) {}

   public static void main(String args[]) {
       final InputStream inputStream = System.in;
       Scanner scanner = new Scanner(inputStream);

       Pattern pattern = Pattern.compile("^Blueprint ([0-9]+): Each ore robot costs ([0-9]+) ore\\. Each clay robot costs ([0-9]+) ore\\. Each obsidian robot costs ([0-9]+) ore and ([0-9]+) clay\\. Each geode robot costs ([0-9]+) ore and ([0-9]+) obsidian\\.$");
       Matcher matcher;
       List<Blueprint> blueprints = new ArrayList<>();
       while (scanner.hasNextLine()) {
           String line = scanner.nextLine();
           matcher = pattern.matcher(line);
           if (matcher.find()) {
               blueprints.add(new Blueprint(
                       Integer.parseInt(matcher.group(1)),
                       Integer.parseInt(matcher.group(2)),
                       Integer.parseInt(matcher.group(3)),
                       Integer.parseInt(matcher.group(4)),
                       Integer.parseInt(matcher.group(5)),
                       Integer.parseInt(matcher.group(6)),
                       Integer.parseInt(matcher.group(7))
               ));
           } else {
               throw new RuntimeException("no match!");
           }
       }

       int solution1 = 0;
       for (Blueprint blueprint : blueprints) {

           Set<State> states = getBestStates(blueprint, 24);

           int geodes = states.stream().filter(r -> r.blueprint.id == blueprint.id).sorted((o1, o2) -> Integer.compare(o2.geode, o1.geode)).toList().get(0).geode;
           solution1 += blueprint.id * geodes;
       }

       int solution2 = 1;
       for (int i=0; i<Math.min(3, blueprints.size()); i++) {
           Blueprint blueprint = blueprints.get(i);
           Set<State> states = getBestStates(blueprint, 32);
           int maxGeodes = states.stream().filter(r -> r.blueprint.id == blueprint.id).sorted((o1, o2) -> Integer.compare(o2.geode, o1.geode)).toList().get(0).geode;
           solution2 *= maxGeodes;
       }

       System.out.println("Solution 1: " + solution1);
       System.out.println("Solution 2: " + solution2);
   }

    private static Set<State> getBestStates(Blueprint blueprint, int minutes) {
        Set<State> states = new HashSet<>();
        states.add(new State(blueprint));
        Set<State> newStates = new HashSet<>();

        for (int minute = minutes - 1; minute >= 0; minute --) {
            for (State state : states) {
                newStates.addAll(state.constructAndProduce());
            }

            states.clear();


            int maxNewGeodes = (minute * (minute + 1)) / 2;
            int minutesLeft = minute;

            int maxGeodes = newStates.stream().filter(r->r.blueprint.id == blueprint.id).mapToInt(r -> r.geode).max().getAsInt();
            List<State> sorted = newStates.stream().filter(r -> r.geode + r.geodeRobots * minutesLeft + maxNewGeodes >= maxGeodes  && r.blueprint.id == blueprint.id).sorted(State::compareTo).toList();

            states.addAll(sorted.subList(0,Math.min(sorted.size(), 100_000)));

            newStates.clear();
        }

        return states;
    }

    private static class State {
        private final Blueprint blueprint;
        private int oreRobots = 1;
        private int ore;
        private int clayRobots;
        private int clay;
        private int obsidianRobots;
        private int obsidian;
        private int geodeRobots;
        private int geode;

        public State(Blueprint blueprint) {
            this.blueprint = blueprint;
        }

        public State(Blueprint blueprint, int oreRobots, int ore, int clayRobots, int clay, int obsidianRobots, int obsidian, int geodeRobot, int geode) {
            this.blueprint = blueprint;
            this.oreRobots = oreRobots;
            this.ore = ore;
            this.clayRobots = clayRobots;
            this.clay = clay;
            this.obsidianRobots = obsidianRobots;
            this.obsidian = obsidian;
            this.geodeRobots = geodeRobot;
            this.geode = geode;
        }

        public void produce() {
            ore += oreRobots;
            clay += clayRobots;
            obsidian += obsidianRobots;
            geode += geodeRobots;
        }

        public List<State> constructAndProduce() {
            List<State> retVal = new ArrayList<>();
            State copy;
            if (obsidian >= blueprint.geodeRobotObsidianCost && ore >= blueprint.geodeRobotOreCost) {
                copy = copy();
                copy.ore -= blueprint.geodeRobotOreCost;
                copy.obsidian -= blueprint.geodeRobotObsidianCost;
                copy.produce();
                copy.geodeRobots += 1;
                retVal.add(copy);
                return retVal;
            }
            if (clay >= blueprint.obsidianRobotClayCost && ore >= blueprint.obsidianRobotOreCost) {
                copy = copy();
                copy.ore -= blueprint.obsidianRobotOreCost;
                copy.clay -= blueprint.obsidianRobotClayCost;
                copy.produce();
                copy.obsidianRobots += 1;
                retVal.add(copy);
            }
            if (ore >= blueprint.clayRobotOreCost) {
                copy = copy();
                copy.ore -= blueprint.clayRobotOreCost;
                copy.produce();
                copy.clayRobots += 1;
                retVal.add(copy);
            }
            if (ore >= blueprint.oreRobotOreCost) {
                copy = copy();
                copy.ore -= blueprint.oreRobotOreCost;
                copy.produce();
                copy.oreRobots += 1;
                retVal.add(copy);
            }
            produce();
            retVal.add(this);
            return retVal;
        }


        private State copy() {
            return new State(blueprint, oreRobots, ore, clayRobots, clay, obsidianRobots, obsidian, geodeRobots, geode);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return oreRobots == state.oreRobots && ore == state.ore && clayRobots == state.clayRobots && clay == state.clay && obsidianRobots == state.obsidianRobots && obsidian == state.obsidian && geodeRobots == state.geodeRobots && geode == state.geode && blueprint.equals(state.blueprint);
        }

        @Override
        public int hashCode() {
            return Objects.hash(blueprint, oreRobots, ore, clayRobots, clay, obsidianRobots, obsidian, geodeRobots, geode);
        }

        @Override
        public String toString() {
            return "[State: blueprint id: " + blueprint.id + ", geodes: " + geode + ", robots: " + oreRobots + "/" + clayRobots + "/" + obsidianRobots + "/" + geodeRobots + " " +
                    "resources: " + ore + "/" + clay + "/" + obsidian + "/" + geode + "]";
        }

        public int compareTo(State state) {
            if (geodeRobots > state.geodeRobots) {
                return -1;
            }
            if (geodeRobots < state.geodeRobots) {
                return 1;
            }
            if (obsidianRobots > state.obsidianRobots) {
                return -1;
            }
            if (obsidianRobots < state.obsidianRobots) {
                return 1;
            }
            if (clayRobots > state.clayRobots) {
                return -1;
            }
            if (clayRobots < state.clayRobots) {
                return 1;
            }
            return Integer.compare(state.oreRobots, oreRobots);

        }
    }
}
