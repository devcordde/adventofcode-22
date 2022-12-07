import java.nio.file.Files
import java.nio.file.Path

def list = Files.readString(Path.of("input.txt")).toList()
[4, 14].collect {l -> println list.collate(l, 1).findIndexOf { l == it.toSet().size() } + l }
