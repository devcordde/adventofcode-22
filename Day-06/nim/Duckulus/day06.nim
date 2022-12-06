import os

if os.paramCount() < 1:
  echo "No input file specified"
  quit(1)

let input = readFile(paramStr(1))

proc anyMatch(chars: seq[char]): bool=
  for i in countup(0, chars.len-1):
    for j in countup(0, chars.len-1):
      if(chars[i] == chars[j] and i!=j): return true
  return false

proc distinctSubstring(length: int): int=
  var index = -1
  for i in 0..input.len-length:
    var chars: seq[char]= @[]
    for j in 0..length-1:
      chars.add(input[i+j])
    if not chars.anyMatch:
      index = i+length
      break
  return index

block part1:
  echo "Part 1: ", distinctSubstring(4)

block part2:
  echo "Part 1: ", distinctSubstring(14)