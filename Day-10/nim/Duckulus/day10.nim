import os, strutils

if os.paramCount()<1:
  echo "No input file specified"
  quit(1)

let commands = readFile(paramStr(1)).strip.splitLines

block part1:
  var signalStrengths = 0
  var x = 1
  var cycle = 1
  var index = 0
  var toAdd = 0
  while cycle<=220:
    if(((cycle)-20) mod 40==0):
      signalStrengths += cycle*x
    var command = commands[index mod commands.len].split(" ")
    if toAdd == 0:
      if(command[0]=="addx"):
        toAdd = command[1].parseInt
      else:
        index+=1
    else:
      x+=toAdd
      toAdd = 0
      index+=1
    cycle+=1 
  echo "Part 1: ", signalStrengths

block part2:
  var image = ""
  var x = 1
  var cycle = 1
  var index = 0
  var toAdd = 0
  while cycle<=240:
    if((x - ((cycle-1) mod 40)).abs<2):
      image.add("#")
    else:
      image.add(".")
    if(cycle mod 40==0):
      image.add("\n")
    var command = commands[index mod commands.len].split(" ")
    if toAdd == 0:
      if(command[0]=="addx"):
        toAdd = command[1].parseInt
      else:
        index+=1
    else:
      x+=toAdd
      toAdd = 0
      index+=1
    cycle+=1 
  echo "Part 2: "
  echo image