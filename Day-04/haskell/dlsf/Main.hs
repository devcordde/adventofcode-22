module Main where

import Data.List
import Data.List.Split
import Text.Printf

main :: IO ()
main = do
    input <- readFile "input.txt"
    let x = [map stringRangeToList y | let pairs = map (splitOn ",") $ lines input, y <- pairs]
    print $ length $ filter (\y -> intersects (y !! 0) (y !! 1)) x
    print $ length $ filter (\y -> overlaps (y !! 0) (y !! 1)) x

stringRangeToList :: String -> [Int]
stringRangeToList x = [(components !! 0)..(components !! 1)]
  where components = map (read :: String -> Int) $ splitOn "-" x

intersects :: [Int] -> [Int] -> Bool
intersects x y = intersection == x || intersection == y
  where intersection = intersect x y

overlaps :: [Int] -> [Int] -> Bool
overlaps x y = [] /= intersect x y
