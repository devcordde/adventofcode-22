import os, strutils

if os.paramCount() < 1:
  echo "No input file specified"
  quit(1)

proc priority(c: char) : int=
   int(c) - (if c.isUpperAscii: 38 else: 96)


var rucksacks = readFile(paramStr(1)).strip.splitLines

block part1:
  var score = 0
  for rucksack in rucksacks:
    block comparison:
      for letter1 in rucksack.substr(0, (rucksack.len/2).toInt):
        for letter2 in rucksack.substr((rucksack.len/2).toInt, rucksack.len):
          if letter1 == letter2:
            score += priority(letter1)
            break comparison
  echo "Part 1: ", score

block part2:
  var score = 0
  for i in 0..int(rucksacks.len/3)-1:
    block comparison:
      for letter1 in rucksacks[i*3]:
        for letter2 in rucksacks[i*3+1]:
          for letter3 in rucksacks[i*3+2]:
            if letter1==letter2 and letter2 == letter3:
              score += letter1.priority
              break comparison
  echo "Part 2: ", score
