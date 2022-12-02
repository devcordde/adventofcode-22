import os, strutils, sequtils, sugar, tables

if os.paramCount()<1:
  echo "No input file specified"
  quit(1)

var moves = readFile(paramStr(1)).strip.splitLines

var beatTable = {
  "r": "s",
  "p": "r",
  "s": "p"
}.toTable

var points = {
  "r": 1,
  "p": 2,
  "s": 3
}.toTable

proc beats(shape: string, other: string): bool =
  beatTable[shape] == other

proc toShape(pick: string): string =
  if pick=="A" or pick=="X":
    return "r"
  elif pick=="B" or pick=="Y":
    return "p"
  elif pick=="C" or pick=="Z":
    return "s"

block part1:
  var score = 0
  for move in moves:
    var picks = move.split(" ")
    for i, pick in picks:
      picks[i] = pick.toShape
    if picks[0]==picks[1]:
      score+=3
    elif picks[1].beats(picks[0]):
      score+=6
    score+=points[picks[1]]
  echo "Part 1: ", score

block part2:
  var score = 0
  for move in moves:
    var opponentShape = move.split(" ")[0].toShape
    let result = move.split(" ")[1]
    if result == "X":
      score += points[beatTable[opponentShape]]
    elif result == "Y":
      score += points[opponentShape]
      score += 3
    elif result == "Z":
      score += points[toSeq(beatTable.pairs).map(y => (y[1], y[0])).toTable[opponentShape]]
      score += 6
    

  echo "Part 2: ", score