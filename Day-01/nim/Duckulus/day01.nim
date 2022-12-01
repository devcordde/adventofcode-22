import os, strutils, std/math, std/algorithm, sequtils, sugar

if os.paramCount()<1:
  echo "No input file specified"
  quit(1)

var elfs: seq[int]= readFile(paramStr(1)).strip.split("\r\n\r\n").map(s => sum(s.strip.splitLines.map(parseInt)))
elfs.sort(order = SortOrder.Descending)

block part1:
  echo "Part 1: ", elfs[0]

block part2:
  echo "Part 2: ", sum([elfs[0], elfs[1], elfs[2]])