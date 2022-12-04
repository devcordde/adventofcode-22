import os, strutils, sequtils, sugar

if os.paramCount() < 1:
  echo "No input file specified"
  quit(1)


let idRanges = readFile(paramStr(1)).strip.splitLines.map(id => id.split(","))
echo "Length: ", idRanges.len

block part1:
  var amount = 0
  for id in idRanges:
    let id1 = id[0].split("-").map(parseInt)
    let id2 = id[1].split("-").map(parseInt)
    if (id1[0]>=id2[0] and id1[1]<=id2[1]) or (id2[0]>=id1[0] and id2[1]<=id1[1]):
      amount+=1
  echo "Part 1: ", amount

block part2:
  var amount = 0
  for id in idRanges:
    let id1 = id[0].split("-").map(parseInt)
    let id2 = id[1].split("-").map(parseInt)
    if (id1[1]>=id2[0]) and (id1[0]<=id2[1]):
      amount+=1
  echo "Part 2: ", amount 
