module Main where

import Data.List
import Data.List.Split
import Text.Printf

main :: IO ()
main = do
    input <- readFile "input.txt"
    let sorted = sort [calories | elve <- splitOn "\n\n" input, let calories = sum $ map (read :: String -> Int) $ splitOn "\n" elve]
    printf "%s %d\n" "Part 1:" $ last sorted
    printf "%s %d\n" "Part 2:" $ sum $ take 3 $ reverse sorted
