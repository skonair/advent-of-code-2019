(ns advent-of-code-2019.day17
  (:require [advent-of-code-2019.intcode :as intcode]))

(defn- compute [initial-state]
  (loop [state (assoc initial-state :params [])]
    (if (state :halt?)
      state
      (let [next-state (intcode/step state)]
        (if (= 1 (count (next-state :params)))
          next-state
          (recur next-state))))))



(defn- get-grid [in]
  (loop [state (intcode/create-state (into [] (concat in (repeat 10000 0))) [])
         pos [0 0]
         akk []]
    (if (state :halt?)
      akk
      (let [next-state  (compute state)
            param (first (next-state :params))
            new-pos (if (= param 10) [0 (inc (second pos))] [(inc (first pos)) (second pos)])]
        (recur next-state new-pos (conj akk [(first new-pos) (second new-pos) param]))))))
          
(defn- print-grid [maze]
  (let [min-x 1
        max-x 45
        min-y 0
        max-y 50]
    (for [y (range min-y (inc max-y))]
      (println (apply str
      (for [x (range min-x (inc max-x))]
        (let [elem (filter #(and (= y (second %)) (= x (first %))) maze)]
          (or (char (last (first elem))) "?"))))))))
  
