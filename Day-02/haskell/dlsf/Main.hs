module Main where

import Data.Char
import Data.Map ((!), fromList)
import Text.Printf

main :: IO ()
main = do
  input <- readFile "input.txt"
  let rounds = map words (lines input)
  let part1 = calculateScore rounds
  let part2 = calculateScore $ decideMoves rounds
  printf "%s %d\n" "Part 1: " part1
  printf "%s %d\n" "Part 2: " part2

equalMove :: String -> String
equalMove move = (fromList [("A", "X"), ("B", "Y"), ("C", "Z")]) ! move

winMove :: String -> String
winMove move = (fromList [("A", "Y"), ("B", "Z"), ("C", "X")]) ! move

loseMove :: String -> String
loseMove move = (fromList [("A", "Z"), ("B", "X"), ("C", "Y")]) ! move

isDraw :: [String] -> Bool
isDraw round = equalMove (head round) == (last round)

isWin :: [String] -> Bool
isWin round = winMove (head round) == (last round)

moveValue :: [String] -> Int
moveValue x = ord (last x !! 0) - 87

calculateScore :: [[String]] -> Int
calculateScore rounds = 3 * (length $ filter isDraw rounds) + 6 * (length $ filter isWin rounds) + sum (map moveValue rounds)

decideMoves :: [[String]] -> [[String]]
decideMoves rounds = map mapMove rounds

mapMove :: [String] -> [String]
mapMove round = [head round, fromList [("X", loseMove), ("Y", equalMove), ("Z", winMove)] ! (last round) $ head round]
