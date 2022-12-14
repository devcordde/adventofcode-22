import strutils, os

type File = object
  name: string
  size: int

type FileTreeNode = ref object
  name: string
  files: seq[File]
  children: seq[FileTreeNode]
  parent: FileTreeNode

proc newNode(name: string): FileTreeNode=
  var node = new(FileTreeNode)
  node.name= name
  node.files= @[]
  node.children= @[]
  return node

proc addChild(node: var FileTreeNode, name: string): FileTreeNode =
  var n = newNode(name)
  n.parent = node
  node.children.add(n)
  return n

proc addFile(node: var FileTreeNode, file: File)=
  node.files.add(file)

proc totalSize(node: FileTreeNode):int=
  var sum = 0
  for f in node.files:
    sum+=f.size
  for n in node.children:
    sum+=totalSize(n)
  return sum

proc printFileTree(root: FileTreeNode, depth: int = 0): void=
  echo " ".repeat(depth),"-", root.name
  for f in root.files:
    echo " ".repeat(depth+1), f
  for n in root.children:
    printFileTree(n, depth+4)

var root = newNode("root")
var currentNode: FileTreeNode

if os.paramCount()<1:
  echo "No input file specified"
  quit(1)

var lines = readFile(paramStr(1)).strip.splitLines

var i = 0
while i<lines.len:
  let line = lines[i]
  if(line.startsWith("$")):
    let command = line.split(" ")[1]
    case command:
      of "cd":
        let arg = line.split(" ")[2]
        case arg:
          of "/":
            currentNode = root
          of "..":
            currentNode = currentNode.parent
          else:
            currentNode = currentNode.addChild(arg)
  else:
    if(not line.startsWith("dir")):
      currentNode.addFile(File(name: line.split(" ")[1], size: line.split(" ")[0].parseInt))
  i+=1

#printFileTree(root)

block part1:
  var sum = 0
  var nodes = @[root]
  while(nodes.len>0):
    var node = nodes[0]
    nodes.delete(0)
    var size = node.totalSize
    if(size<100000):
      sum+=size
    for child in node.children:
      nodes.add(child)
  echo "Part 1: ", sum

block part2:
  var unusedSpace = 70000000 - root.totalSize
  var missingSpace = 30000000 - unusedSpace
  var smallest = root.totalSize
  var nodes = @[root]
  while(nodes.len>0):
    var node = nodes[0]
    nodes.delete(0)
    var size = node.totalSize
    if(size>missingSpace and size<smallest):
      smallest = size
    for child in node.children:
      nodes.add(child)
  echo "Part 2: ", smallest