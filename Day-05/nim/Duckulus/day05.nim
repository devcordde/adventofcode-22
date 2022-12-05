import os, strutils, algorithm, sequtils, sugar

if os.paramCount() < 1:
  echo "No input file specified"
  quit(1)

let lines = readFile(paramStr(1)).splitLines
var split = 0;
while lines[split]!="":
  split+=1
var stackAmount = 0;
while(true):
  try:
    if(lines[split-1][stackAmount*4+1]!=' '): stackAmount+=1 else: break
  except IndexDefect:
    break

var words = newSeq[string](stackAmount)
words.fill("")
for i in countdown(split-2,0):
  for j in 0..stackAmount-1:
    if(lines[i][j*4+1]!=' '): words[j].add(lines[i][j*4+1])
  
block part1:
  var supplyStacks = words.deepCopy
  for i in split+1..lines.len-1:
    if(lines[i]!=""):
      let amount = lines[i].split(" ")[1].parseInt-1
      let stackFrom = lines[i].split(" ")[3].parseInt-1
      let stackTo = lines[i].split(" ")[5].parseInt-1
      for j in 0..amount:
        let letter = supplyStacks[stackFrom][supplyStacks[stackFrom].len-1]
        supplyStacks[stackFrom] = supplyStacks[stackFrom].substr(0, supplyStacks[stackFrom].len-2)
        supplyStacks[stackTo].add(letter)
  var message = supplyStacks.map(s => s[s.len-1]).join
  echo "Part 1: ", message

block part1:
  var supplyStacks = words.deepCopy
  for i in split+1..lines.len-1:
    if(lines[i]!=""):
      let amount = lines[i].split(" ")[1].parseInt-1
      let stackFrom = lines[i].split(" ")[3].parseInt-1
      let stackTo = lines[i].split(" ")[5].parseInt-1
      let letter = supplyStacks[stackFrom].substr(supplyStacks[stackFrom].len-(1+amount), supplyStacks[stackFrom].len-1)
      supplyStacks[stackFrom] = supplyStacks[stackFrom].substr(0, supplyStacks[stackFrom].len-(2+amount))
      supplyStacks[stackTo].add(letter)
  var message = supplyStacks.map(s => (if s.len>0: s[s.len-1] else: ' ')).join
  echo "Part 2: ", message