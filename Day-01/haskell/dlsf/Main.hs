module Main where

import Data.List
import Data.List.Split
import Text.Printf

main :: IO ()
main = do
    input <- readFile "input.txt"
    let sorted = sort [d | a <- splitOn "\n\n" input, let b = splitOn "\n" a, let c = map (read :: String -> Int) b, let d = sum c]
    printf "%s %d\n" "Part 1:" $ last sorted
    printf "%s %d\n" "Part 2:" $ sum $ take 3 $ reverse sorted
