(ns day5
  (:require [clojure.string :as str]))

(defn parse-layer [layer]
  (->> layer
       (re-seq #"    ?|\[(\w)\] ?")
       (map second)))

(defn parse-instruction [inst]
  (let [[count from to]
        (->> inst
             (re-matches #"move (\d+) from (\d) to (\d)")
             (drop 1)
             (map parse-long))]
    {:count count
     :from (dec from)
     :to (dec to)}))

(defn add-layer [stacks layer]
  (mapv (fn [stack item] (cond-> stack item (conj item))) stacks layer))

(defn parse-input [lines]
  (let [[cargo _ instructions] (partition-by empty? lines)
        crates (rseq (mapv parse-layer (drop-last cargo)))
        stacks (reduce add-layer (repeat nil) crates)
        instructions (map parse-instruction instructions)]
    {:stacks stacks
     :instructions instructions}))

(defn run-instructions [{:keys [stacks instructions]} move-fn]
  (loop [[{:keys [from to count] :as inst} :as remaining] instructions
         stacks stacks]
    (if inst
      (recur
       (rest remaining)
       (let [affected-crates (take count (get stacks from))]
         (-> stacks
             (update from (partial drop count))
             (update to move-fn affected-crates))))
      stacks)))

(defn top-crates [stacks]
  (str/join (map first stacks)))

(defn -main [file & _]
  (let [input (parse-input (str/split-lines (slurp file)))
        result1 (run-instructions input into)
        result2 (run-instructions input #(concat %2 %1))]
    (println "Part 1:" (top-crates result1))
    (println "Part 2:" (top-crates result2))))

(def test-input
  ["    [D]    "
   "[N] [C]    "
   "[Z] [M] [P]"
   " 1   2   3 "
   ""
   "move 1 from 2 to 1"
   "move 3 from 1 to 3"
   "move 2 from 2 to 1"
   "move 1 from 1 to 2"])
