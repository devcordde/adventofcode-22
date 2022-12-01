(ns day1
  (:require [clojure.string :as string]))

(defn get-elve-calories []
  (map #(reduce + %) (map (partial map read-string)
                          (map #(string/split % #"\r\n")
                               (-> (slurp "input.txt")
                                   (string/split #"\r\n\r\n"))))))

(defn part-one []
  (apply max (get-elve-calories)))

(defn part-two []
  (reduce + (take 3 (sort > (get-elve-calories)))))

(println (str "Part one: " (part-one)))
(println (str "Part two: " (part-two)))
