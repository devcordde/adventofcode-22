(ns day7
  (:require
   [clojure.string :as str]
   [clojure.walk :as walk]))

(def cd-pat #"\$ cd (.+)")
(def ls-pat #"(\d+) (.+)|(?:dir .+)")

(defn file-map [[_ size name]]
  (when size
    {:type :file
     :name name
     :size (parse-long size)}))

(defn read-file-tree [[cd _ls & more]]
  (let [[_ dir-name] (re-matches cd-pat cd)
        contents (->> more
                      (map (partial re-matches ls-pat))
                      (take-while some?))
        file-children (keep file-map contents)]
    (loop [[next-cd :as remaining] (drop (count contents) more)
           dir-children []]
      (if (or (empty? remaining) (= next-cd "$ cd .."))
        [(rest remaining) {:type :dir :name dir-name :children (concat dir-children file-children)}]
        (let [[new-remaining dir-child] (read-file-tree remaining)]
          (recur new-remaining (conj dir-children dir-child)))))))

(defn compute-sizes [tree]
  (walk/postwalk
   (fn [x]
     (cond-> x
       (= (:type x) :dir) (assoc :size (->> x :children (map :size) (reduce +)))))
   tree))


(defn dir-sizes [tree]
  (->> tree
       (tree-seq (comp #{:dir} :type) :children)
       (filter (comp #{:dir} :type))
       (map :size)))

(defn part-one [tree]
  (->> tree
       dir-sizes
       (filter (partial >= 100000))
       (reduce +)))

(defn part-two [tree]
  (let [required-space (- 30000000 (- 70000000 (:size tree)))]
    (->> tree
         dir-sizes
         (filter (partial <= required-space))
         (reduce min))))

(defn -main [file & _]
  (let [input (-> file slurp str/split-lines read-file-tree second compute-sizes)]
    (println "Part 1:" (part-one input))
    (println "Part 2:" (part-two input))))
