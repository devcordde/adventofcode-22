(ns day12
  (:require
   [clojure.string :as str]
   [clojure.set :as set]))


(def directions
  [[0 1] [0 -1] [1 0] [-1 0]])

(defn parse-input [input]
  (->> input
       str/split-lines
       (mapv vec)))

(defn possible-steps [heightmap pos]
  (let [current (get-in heightmap pos)
        current-height (int (if (= current \E) \z current))]
    (->> pos
         repeat
         (map (partial mapv +) directions)
         (filter
          (fn [pos]
            (when-let [step (get-in heightmap pos)]
              (<= current-height (inc (int (if (= step \S) \a step))))))))))

(defn pos-of [heightmap elem]
  (for [[x row] (map-indexed vector heightmap)
        [y e] (map-indexed vector row)
        :when (= elem e)]
    [x y]))

(defn shortest-paths
  ([heightmap start goals]
   (shortest-paths heightmap #{start} #{} goals {} 0))
  ([heightmap fringe visited goals distances steps]
   (let [reached-goals (set/intersection goals fringe)
         remaining-goals (set/difference goals reached-goals)
         updated-distances (into distances (zipmap reached-goals (repeat steps)))]
     (if (or (empty? remaining-goals) (empty? fringe))
       updated-distances
       (recur
        heightmap
        (set/difference (set (mapcat (partial possible-steps heightmap) fringe)) visited)
        (set/union visited fringe)
        remaining-goals
        updated-distances
        (inc steps))))))

(defn -main [file & _]
  (let [heightmap (-> file slurp parse-input)
        end-pos (first (pos-of heightmap \E))
        [s-pos :as start-pos] (concat (pos-of heightmap \S) (pos-of heightmap \a))
        paths (shortest-paths heightmap end-pos (set start-pos))]
    (println "Part 1:" (paths s-pos))
    (println "Part 2:" (reduce min (vals paths)))))
