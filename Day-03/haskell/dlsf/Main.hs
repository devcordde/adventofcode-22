module Main where

import Control.Lens.Combinators
import Control.Lens.Fold
import Data.Char
import Data.List
import Data.List.Split
import Data.Set (fromList, intersection, toList)
import Text.Printf

main :: IO ()
main = do
  input <- readFile "input.txt"
  let internalHalves = map halveString $ lines input
  let internalIntersections = map biIntersection $ internalHalves
  let groups = chunksOf 3 $ lines input

  printf "%s %d\n" "Part 1: " $ sum $ map charValue internalIntersections
  printf "%s %d\n" "Part 2:" $ sum $ map charValue $ map triIntersection $ groups ^.. each

halveString :: String -> (String, String)
halveString x = (splitAt (div (length x) 2)) x

biIntersection :: (String, String) -> String
biIntersection (x, y) = toList $ (fromList x) `intersection` (fromList y)

triIntersection :: [String] -> String
triIntersection x = biIntersection (biIntersection (x !! 0, x !! 1), biIntersection (x !! 1, x !! 2))

charValue :: String -> Int
charValue x
  | o > 96 = o - 96
  | otherwise = o - 65 + 27
  where o = (ord $ x !! 0)
