package unbekannt

import java.io.File
import java.util.UUID

fun main() {
    val input =
        File("input-day07.txt").readLines()
    val dirs = mutableMapOf<String, Folder>()
    var activeFolder: Folder? = null
    input.map { it.split(" ") }.forEach {
        if(it.first().startsWith("$")){
            val command = it[1]
            if(command == "cd"){
                val dir = it[2]
                if (dir == "..") {
                    activeFolder = dirs[activeFolder?.parent]
                    return@forEach
                }
                val dirName = UUID.randomUUID().toString()
                if(!dirs.containsKey(dirName)){
                    val newFolder = Folder(dirName, dir, activeFolder?.id, mutableListOf(), mutableListOf())
                    dirs[dirName] = newFolder
                    activeFolder?.childs?.add(newFolder)
                    activeFolder = newFolder
                } else
                    activeFolder = dirs[dirName]
            }
        }
        if (it.first().toLongOrNull() != null){
            activeFolder!!.files.add(FileData(it.first().toLong(), it.last()))
        }
    }

    var score = 0L
    dirs.values.forEach {
        val folderSize = it.getFolderSize()
        if(folderSize <= 100000){
            score += folderSize
        }
    }

    println(score)

    val diskspace = 70000000
    val allSizeUsed = dirs.filter { it.value.name == "/" }.firstNotNullOf { it }.value.getFolderSize()
    val diskavailable = diskspace - allSizeUsed
    val foldersize = dirs.values.filter { diskavailable + it.getFolderSize() >= 30000000 }.minOf { it.getFolderSize() }
    println(foldersize)

}

data class Folder(val id: String, val name: String, val parent: String?, val files: MutableList<FileData>, val childs: MutableList<Folder>) {
    fun getFileSize() = files.sumOf { it.size }

    fun getFolderSize() : Long {
        return childs.sumOf { it.getFolderSize() } + getFileSize()
    }
}
data class FileData(val size: Long, val name: String)