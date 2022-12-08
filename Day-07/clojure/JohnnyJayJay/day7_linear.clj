(ns day7-linear
  "A version of day 7 that doesn't build a tree."
  (:require [clojure.string :as str]))

(defn parse-input [lines]
  (loop [[instr & remaining] lines
         [current & parents :as stack] ()
         result []]
    (cond
      (not instr) (into result (reductions + stack))
      (= instr "$ cd ..") (recur remaining (cons (+ current (first parents)) (rest parents)) (conj result current))
      (or (str/starts-with? instr "dir") (= instr "$ ls")) (recur remaining stack result)
      (str/starts-with? instr "$ cd") (recur remaining (cons 0 stack) result)
      :else (let [file-size (parse-long (subs instr 0 (str/index-of instr " ")))]
              (recur remaining (cons (+ current file-size) parents) result)))))

(defn part-one [sizes]
  (->> sizes (filter (partial >= 100000)) (reduce +)))

(defn part-two [sizes]
  (let [to-free (- 30000000 (- 70000000 (peek sizes)))]
    (->> sizes (filter (partial <= to-free)) (reduce min))))

(defn -main [file & _]
  (let [sizes (-> file slurp str/split-lines parse-input)]
    (println "Part 1:" (part-one sizes))
    (println "Part 2:" (part-two sizes))))
