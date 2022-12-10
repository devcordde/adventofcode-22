import os, strutils, tables, sequtils, sugar, sets

if os.paramCount()<1:
  echo "No input file specified"
  quit(1)

let directions = {
  "R": [1,0],
  "L": [-1,0],
  "U": [0,1],
  "D": [0, -1]
}.toTable

type Instruction = object
  direction: array[2, int]
  steps: int


let instructions = readFile(paramStr(1)).strip.splitLines.map(s => Instruction(direction: directions[s.split(" ")[0]], steps: s.split(" ")[1].parseInt))

proc followNextKnot(pos: array[2, int], headPos: array[2, int]): array[2, int]=
  var tailPos = pos
  if (headPos[1]==tailPos[1]+2 and headPos[0]==tailPos[0]+1) or (headPos[0]==tailPos[0]+2 and headPos[1]==tailPos[1]+1) or (headPos[0]==tailPos[0]+2 and headPos[1]==tailPos[1]+2):
    tailPos[0]+=1
    tailPos[1]+=1
  if (headPos[1]==tailPos[1]-2 and headPos[0]==tailPos[0]+1) or (headPos[0]==tailPos[0]+2 and headPos[1]==tailPos[1]-1) or (headPos[0]==tailPos[0]+2 and headPos[1]==tailPos[1]-2):
    tailPos[0]+=1
    tailPos[1]-=1
  elif (headPos[0]==tailPos[0]-2 and headPos[1]==tailPos[1]+1) or (headPos[1]==tailPos[1]+2 and headPos[0]==tailPos[0]-1) or (headPos[0]==tailPos[0]-2 and headPos[1]==tailPos[1]+2):
    tailPos[0]-=1
    tailPos[1]+=1 
  elif (headPos[0]==tailPos[0]-2 and headPos[1]==tailPos[1]-1) or (headPos[1]==tailPos[1]-2 and headPos[0]==tailPos[0]-1) or (headPos[0]==tailPos[0]-2 and headPos[1]==tailPos[1]-2):
    tailPos[0]-=1
    tailPos[1]-=1

  elif headPos[0]==tailPos[0]+2:
    tailPos[0]+=1
  elif headPos[0]==tailPos[0]-2:
    tailPos[0]-=1
  elif headPos[1]==tailPos[1]+2:
    tailPos[1]+=1
  elif headPos[1]==tailPos[1]-2:
    tailPos[1]-=1
  return tailPos


proc printBoard(knots: seq[array[2, int]]): void=
  for i in countdown(5, -5):
    var s = ""
    for j in countup(-5, 5):
      if knots[0]==[j,i]:
        s.add("H")
      elif knots.find([j,i]) != -1:
        s.add(knots.find([j,i]).intToStr)
      elif i==0 and j==0:
        s.add("s")
      else:
        s.add(".")
    echo s
  echo ""
  echo ""

proc tailPositions(length: int, instructions: seq[Instruction]): int=
  var positionSet = initHashSet[array[2, int]]()
  var knots: seq[array[2,int]] = @[]
  for i in 0..length-1:
    knots.add([0,0])
  for instruction in instructions:
    for i in countup(0, instruction.steps-1):
      knots[0][0]+=instruction.direction[0]
      knots[0][1]+=instruction.direction[1]
      for i in 1..knots.len-1:
        knots[i] = followNextKnot(knots[i], knots[i-1])
      positionSet.incl(knots[knots.len-1])
      #printBoard(knots)
  return positionSet.len
    

block part1:
  echo "Part 1: ", tailPositions(2, instructions)
  
block part2:
  echo "Part 2: ", tailPositions(10, instructions)

