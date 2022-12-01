(ns day1
  (:require [clojure.string :as string]))

(defn get-elves-calories []
  (map #(reduce + %) (map (partial map read-string)
                          (map #(string/split % #"\r\n")
                               (-> (slurp "input.txt")
                                   (string/split #"\r\n\r\n"))))))

(defn part-one []
  (apply max (get-elves-calories)))

(defn part-two []
  (let [sorted (sort > (get-elves-calories))]
    (+ (nth sorted 0) (nth sorted 1) (nth sorted 2))))

(println (str "Part one: " (part-one)))
(println (str "Part two: " (part-two)))
