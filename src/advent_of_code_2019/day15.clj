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
        new-queue (filter #(not (nil? %)) (map first results))]
    [new-queue new-visited new-found]))

(defn- part1 [iis]
  (let [state (intcode/create-state (into [] (concat iis (repeat 500 0))) [])]
    (loop [[q & qs] [{:x 0 :y 0 :steps 0 :state state}] ; [x y steps state]
           visited #{}
           found false]
      (if found
        (:steps q)
        (if (some #{[(:x q) (:y q)]} visited)
          (recur qs visited found)
          (let [[add-qs nv nf] (squash-results
            (for [[d dx dy] deltas]
              (let [next-state (compute (:state q) d)
                    res (first (:params next-state))
                    new-x (+ dx (:x q))
                    new-y (+ dy (:y q))
                    new-steps (inc (:steps q))
                    new-visited (conj visited [(:x q) (:y q)])]
                (comment println "res is " res ", new-x is " new-x ", new-y is " new-y ", new-steps is " new-steps ", new-visited is " new-visited)
                (case res
                  0 [nil (conj new-visited [new-x new-y]) false]
                  1 [{:x new-x :y new-y :steps new-steps :state next-state} new-visited false]
                  2 [q new-visited true]))))]
            (comment
            (println "squashed results are QS ---> " (concat add-qs qs))
            (println "squashed results are NV ---> " nv)
            (println "squashed results are NF ---> " nf)
              )
            (recur (concat qs add-qs) nv nf)))))))
  
