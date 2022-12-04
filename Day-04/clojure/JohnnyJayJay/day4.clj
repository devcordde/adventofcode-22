(ns day4
  (:require [clojure.string :as str]))

(defn parse-line [line]
  (->> line
       (re-find #"(\d+)-(\d+),(\d+)-(\d+)")
       (drop 1)
       (map parse-long)
       (split-at 2)))

;; neg? * fold x - y
(defn full-overlap? [[[a1 b1] [a2 b2]]]
  (or (and (<= a1 a2) (>= b1 b2))
      (and (>= a1 a2) (<= b1 b2))))

(defn partial-overlap? [[[a1 b1] [a2 b2]]]
  (or (and (<= a1 a2) (>= b1 a2))
      (and (>= a1 a2) (>= b2 a1))))

(defn -main [file & _]
  (let [input (->> file
                   slurp
                   str/split-lines
                   (map parse-line))]
    (println "Part 1:" (count (filter full-overlap? input)))
    (println "Part 2:" (count (filter partial-overlap? input)))))
