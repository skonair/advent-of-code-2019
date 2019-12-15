(ns advent-of-code-2019.day15
  (:require [clojure.set :as set])
  (:require [advent-of-code-2019.intcode :as intcode]))

(defn- compute [initial-state color]
  (loop [state (assoc initial-state :params [color])]
    (if (state :halt?)
      state
      (let [next-state (intcode/step state)]
        (if (> (count (next-state :params)) (count (state :params)))
          next-state
          (recur next-state))))))

(def deltas [ [1 0 -1] [2 0 1] [3 -1 0] [4 1 0]]) ; north south west east

(defn- squash-results [results]
  (let [new-found (some identity (map last results))
        new-visited (apply set/union (map second results))
        new-queue (filter #(not (nil? %)) (map first results))
        new-map (apply set/union (map #(nth % 2) results))
        ]
    [new-queue new-visited new-found new-map]))

(defn- search-maze [iis st part1?]
  (let [state (intcode/create-state (into [] (concat iis (repeat 500 0))) [])]
    (loop [[q & qs] [{:x 0 :y 0 :steps 0 :state state}] ; [x y steps state]
           visited #{}
           mymap #{[0 0 "X"]}
           found false
           steps st]
      (if (or (and part1? found) (and (not part1?) (zero? steps)))
        [(:steps q) mymap]
        (if (some #{[(:x q) (:y q)]} visited)
          (recur qs visited mymap found steps)
          (let [[add-qs nv nf nm] (squash-results
            (for [[d dx dy] deltas]
              (let [next-state (compute (:state q) d)
                    res (first (:params next-state))
                    new-x (+ dx (:x q))
                    new-y (+ dy (:y q))
                    new-steps (inc (:steps q))
                    new-visited (conj visited [(:x q) (:y q)])]
                (comment println "res is " res ", new-x is " new-x ", new-y is " new-y ", new-steps is " new-steps ", new-visited is " new-visited)
                (case res
                  0 [nil (conj new-visited [new-x new-y]) (conj mymap [new-x new-y "#"] ) false]
                  1 [{:x new-x :y new-y :steps new-steps :state next-state} new-visited (conj mymap [new-x new-y " "] ) false]
                  2 [q new-visited (conj mymap [new-x new-y "O"] ) true]))))]
            (recur (concat qs add-qs) nv nm nf (dec steps))))))))
  
(defn part1 [iis] (first (search-maze iis 798 true)))

(defn- maze [iis st] (second (search-maze iis 798 false)))

(defn- print-maze [maze]
  (let [min-x (apply min (map first maze))
        max-x (apply max (map first maze))
        min-y (apply min (map second maze))
        max-y (apply max (map second maze))]
    (println "dimenstions: " min-x max-x min-y max-y)
    (for [y (range min-y (inc max-y))]
      (println (apply str 
      (for [x (range min-x (inc max-x))]
        (let [elem (filter #(and (= y (second %)) (= x (first %))) maze)]
          (or (last (first elem)) "?"))))))))

(defn- melem [maze x y]
  (last (first (filter #(and (= y (second %)) (= x (first %))) maze))))

(defn- stuck? [maze x y visited]
  (or (some #{[x y]} visited) (= "#" (melem maze x y))))

(defn- nexts [maze x y visited cnt]
  (filter #(not (nil? %))
    (for [[d dx dy] deltas]
      (let [new-x (+ dx x)
            new-y (+ dy y)]
        (if (stuck? maze new-x new-y visited)
         cnt 
         (apply max (nexts maze new-x new-y (conj visited [new-x new-y]) (inc cnt))))))))

(defn part2 [iis]
  (let [mze (maze iis 798)]
    (apply max (nexts mze 12 12 #{[12 12]} 0))))


