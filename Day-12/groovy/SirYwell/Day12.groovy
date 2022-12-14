import java.nio.file.Files
import java.nio.file.Path

class Day12 {
    static void main(String[] args) {
        def lines = Files.readAllLines(Path.of("input.txt"))
                .collect { it.toCharArray().collect { it - ('a' as char) } }
        int source = -1
        int target = -1
        List<Integer> lowest = []
        List<List<Integer>> adjacencyList = new ArrayList<>()
        int height = lines.size()
        int width = lines[0].size()
        def lin = { int x, int y -> y * width + x }
        lines.eachWithIndex { List<Integer> row, int y ->
            row.eachWithIndex { int sq, int x ->
                if (sq == -14) {
                    source = lin(x, y)
                    lines[y][x] = 0
                } else if (sq == -28) {
                    target = lin(x, y)
                    lines[y][x] = 25
                }
                if (lines[y][x] == 0) {
                    lowest << lin(x, y)
                }
            }
        }
        lines.eachWithIndex { List<Integer> row, int y ->
            row.eachWithIndex { int elev, int x ->
                def neighbors = []
                if (y > 0 && lines[y - 1][x] - elev <= 1) {
                    neighbors << lin(x, y - 1)
                }
                if (y < height - 1 && lines[y + 1][x] - elev <= 1) {
                    neighbors << lin(x, y + 1)
                }
                if (x > 0 && lines[y][x - 1] - elev <= 1) {
                    neighbors << lin(x - 1, y)
                }
                if (x < width - 1 && lines[y][x + 1] - elev <= 1) {
                    neighbors << lin(x + 1, y)
                }
                adjacencyList.add(neighbors)
            }
        }
        Map<Integer, Integer> res = lowest.collectEntries { [it, findPath(it, target, adjacencyList).size()] }
        println(res[source] - 1)
        println(res.values().findAll { it > 0 }.min() - 1)
    }

    private static ArrayList findPath(int source, int target, List<List<Integer>> adjacencyList) {
        int nodes = adjacencyList.size()
        int[] dist = new int[nodes]
        Arrays.fill(dist, Integer.MAX_VALUE)
        int[] prev = new int[nodes]
        Arrays.fill(prev, -1)
        dist[source] = 0
        def Q = new PriorityQueue<Integer>(nodes, Comparator.comparing { dist[it] })
        def seen = new HashSet<Integer>()
        Q.add(source)
        while (!Q.isEmpty()) {
            def u = Q.poll()
            if (u == target) {
                break
            }
            Q.remove(u)
            seen.add(u)
            for (v in adjacencyList[u].findAll { !seen.contains(it) }) {
                def alt = dist[u] + 1 // dist between two nodes always 1
                if (alt < dist[v]) {
                    dist[v] = alt
                    prev[v] = u
                    Q.add(v)
                }
            }
        }
        def S = []
        def u = target
        if (prev[u] >= 0 || u == source) {
            while (u >= 0) {
                S << u
                u = prev[u]
            }
        }
        S
    }
}
