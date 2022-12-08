import java.nio.file.Files
import java.nio.file.Path
class Day07 {
    static void main(String[] args) {
        def lines = Files.readAllLines(Path.of("input.txt"))
        def root = new Dir(null)
        def current = root
        for (line in lines) {
            if (line == "\$ cd /") {
                current = root
            } else if (line == "\$ cd ..") {
                current = current.parent
            } else if (line.startsWith("\$ cd")) {
                def dirName = line.split(" ").last()
                current = current.subDirs.computeIfAbsent(dirName) { new Dir(current) }
            } else if (line.startsWith("\$") || line.startsWith("dir")) {
                // ignore
            } else {
                def (String size, String name) = line.split(" ")
                current.files.add(new File(name, size as int))
            }
        }
        println findAll(root, 100000 as int, new ArrayList<Dir>()).sum { it.totalSize() }
        int total = 70000000
        int unused = total - root.totalSize()
        int required = 30000000 - unused
        def entries = findAll(root, total).collect { it.totalSize() }
        println new TreeSet<>(entries).ceiling(required)
    }

    static List<Dir> findAll(Dir current, int maxSize, List<Dir> acc = new ArrayList<>()) {
        if (current.totalSize() <= maxSize) {
            acc.add(current)
        }
        for (final def sub in current.subDirs.values()) {
            findAll(sub, maxSize, acc)
        }
        return acc
    }

    static class Dir {
        def parent
        def subDirs = new HashMap<String, Dir>()
        def files = new ArrayList<File>()

        Dir(Dir parent) {
            this.parent = parent
        }

        int totalSize() {
            return (files.sum { it.size } ?: 0) + (subDirs.values().sum { it.totalSize()} ?: 0)
        }
    }
    static class File {
        def name
        def size

        File(String name, int size) {
            this.name = name
            this.size = size
        }
    }
}