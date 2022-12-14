(ns day13
  (:require
   [clojure.string :as str]
   [clojure.edn :as edn]))

(defn parse-input [input]
  (->> input
       str/split-lines
       (remove empty?)
       (map edn/read-string)))

(defn compare-packets [left right]
  (cond
   (every? number? [left right]) (compare left right)
   (number? left) (recur [left] right)
   (number? right) (recur left [right])
   :else
   (loop [[lcur & lrem] left
          [rcur & rrem] right]
     (if (and lcur rcur)
       (let [res (compare-packets lcur rcur)]
         (if (zero? res)
           (recur lrem rrem)
           res))
       (compare (some? lcur) (some? rcur))))))

(defn right-order-sum [packets]
  (->> packets
       (partition 2)
       (map-indexed
        (fn [idx [left right]]
          [(inc idx) (compare-packets left right)]))
       (filter (comp (partial >= 0) second))
       (map first)
       (reduce +)))

(defn decoder-key [packets]
  (->> packets
       (list* [[2]] [[6]])
       (sort compare-packets)
       (map-indexed vector)
       (filter (comp #{[[2]] [[6]]} second))
       (map first)
       (map inc)
       (apply *)))

(defn -main [file & _]
  (let [packets (-> file slurp parse-input)]
    (println "Part 1:" (right-order-sum packets))
    (println "Part 2:" (decoder-key packets))))
