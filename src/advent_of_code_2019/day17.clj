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
         akk {}]
    (if (state :halt?)
      akk
      (let [next-state  (compute state)
            param (first (next-state :params))
            new-pos (if (= param 10) [0 (inc (second pos))] [(inc (first pos)) (second pos)])
            new-akk (if (= param 10) akk (assoc akk [(first new-pos) (second new-pos)] param))]
        (recur next-state new-pos new-akk)))))

(defn- crossing? [x y maze]
  (let [pc (maze [x y])
        pn (maze [x (dec y)])
        ps (maze [x (inc y)])
        pe (maze [(inc x) y])
        pw (maze [(dec x) y])]
  (and (= 35 pc) (= 35 pn) (= 35 ps) (= 35 pe) (= 35 pw))))

(defn- crossings [maze]
  (apply +
    (for [x (range 46)
          y (range 51)]
      (if (crossing? x y maze) 
        (do (println "Crossing found " x y) (* (dec x) y))
        0))))
          
(defn- print-grid [maze]
  (let [min-x 1
        max-x 45
        min-y 0
        max-y 50]
    (for [y (range min-y (inc max-y))]
      (println (apply str
      (for [x (range min-x (inc max-x))]
        (let [elem (maze [x y])]
          (or (char elem) "?"))))))))
  
