(ns day11
  (:require [clojure.string :as str]))

(defn parse-monkey [[_header items operation & other]]
  (let [items  (map parse-long (re-seq #"\d+" (apply str items other)))
        [items [test then else]] (split-at (- (count items) 3) items)
        [a op b] (->> (re-matches #".+new = (old|\d+) ([*+]) (old|\d+)" operation)
                        (drop 1)
                        (mapv read-string))]
    {:item-queue (vec items)
     :test test
     :then then
     :else else
     :operator (eval op)
     :operands [a b]
     :inspections 0}))

(defn inspect-1 [old {:keys [operator operands]} _test]
  (quot (apply operator (map #(if (symbol? %) old %) operands)) 3))

(defn inspect-2 [old {:keys [operator operands]} test]
  (rem (apply operator (map #(if (symbol? %) old (rem % test)) operands)) test))

(defn parse-input [input]
  (let [monkeys (->> (str/split input #"\n\n")
                     (map str/split-lines)
                     (map parse-monkey))
        items (mapcat :item-queue monkeys)]
    (loop [item-idxs (range (count items))
           [{:keys [item-queue] :as monkey} & remaining] monkeys
           result []]
      (if monkey
        (let [[start-items other-items] (split-at (count item-queue) item-idxs)]
          (recur
           other-items
           remaining
           (conj result (assoc monkey :item-queue (vec start-items) :items (vec items)))))
        result))))

(defn prepare-part-2 [monkeys]
  (mapv
   (fn [{:keys [test] :as monkey}]
     (update monkey :items (partial mapv #(rem % test))))
   monkeys))

(defn monkey-item-turn [inspect-fn idx monkeys item]
  (let [{:keys [test then else] :as monkey} (get monkeys idx)
        updated-monkeys (mapv #(update-in % [:items item] inspect-fn monkey (:test %)) monkeys)
        item-remainder (rem (get-in updated-monkeys [idx :items item]) test)
        new-owner (if (zero? item-remainder) then else)]
    (update-in updated-monkeys [new-owner :item-queue] conj item)))

(defn monkey-turn [inspect-fn monkeys idx]
  (let [{:keys [item-queue]} (get monkeys idx)]
    (-> (reduce
         (partial monkey-item-turn inspect-fn idx)
         monkeys
         item-queue)
        (update-in [idx :inspections] + (count item-queue))
        (assoc-in [idx :item-queue] []))))

(defn simulate-monkeys [inspect-fn monkeys]
  (iterate (fn [monkeys] (reduce (partial monkey-turn inspect-fn) monkeys (range (count monkeys)))) monkeys))

(defn monkey-business [inspect-fn rounds monkeys]
  (->> monkeys
       (simulate-monkeys inspect-fn)
       (drop rounds)
       first
       (map :inspections)
       (sort >)
       (take 2)
       (apply *)))

(defn -main [file & _]
  (let [monkeys (-> file slurp parse-input)]
    (println "Part 1:" (monkey-business inspect-1 20 monkeys))
    (println "Part 2:" (monkey-business inspect-2 10000 (prepare-part-2 monkeys)))))
