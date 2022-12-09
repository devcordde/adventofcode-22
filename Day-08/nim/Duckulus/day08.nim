import os, strutils

if os.paramCount()<1:
  echo "No input file specified"
  quit(1)

var lines = readFile(paramStr(1)).strip.splitLines

block part1:
  var sum = 0
  var x = 0
  var y = 0
  while y<lines.len:
    x=0
    while x<lines[0].len:
      var height = parseInt($lines[y][x])
      var visibleRight = true
      var visibleLeft = true
      var visibleUp = true
      var visibleDown = true
      for i in y+1..lines.len-1:
        if parseInt($lines[i][x])>=height and i!=y: visibleDown = false
      for i in 0..y:
        if parseInt($lines[i][x])>=height and i!=y: visibleUp = false
      for i in x+1..lines[0].len-1:
        if parseInt($lines[y][i])>=height and i!=x: visibleRight = false
      for i in 0..x-1:
        if parseInt($lines[y][i])>=height and i!=x: visibleLeft = false
      if visibleRight or visibleLeft or visibleUp or visibleDown: sum+=1
      x+=1
    y+=1
  echo "Part 1: ", sum

block part2:
  var highestScore = 0
  var x = 0
  var y = 0
  while y<lines.len:
    x=0
    while x<lines[0].len:
      let height = lines[y][x]
      var score = 1
      var trees = 0
      for i in y+1..lines.len-1: #down
        trees+=1
        if lines[i][x]>=height: break   
      score*=trees
      trees = 0  

      for i in countdown(y-1,0): #up
        trees+=1
        if lines[i][x]>=height: break
      score*=trees
      trees = 0

      for i in x+1..lines[0].len-1: #right
        trees+=1
        if lines[y][i]>=height: break 
      score*=trees
      trees = 0

      for i in countdown(x-1,0): #left
        trees+=1
        if lines[y][i]>=height: break   
      score*=trees
      
      if(score>highestScore): highestScore = score
      x+=1
    y+=1
  echo "Part 2: ", highestScore