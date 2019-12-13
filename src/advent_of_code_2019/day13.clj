(ns advent-of-code-2019.day13
  (:require [clojure.string :as str])
  (:require [advent-of-code-2019.utils :as utils])
  (:require [advent-of-code-2019.intcode :as intcode]))

(defn- compute [initial-state]
  (loop [state (assoc initial-state :params [])]
      (let [next-state (intcode/step state)]
        (if (= 3 (count (next-state :params)))
          next-state
          (recur next-state)))))

; 239
(defn part1 [iis]
  (loop [state (intcode/create-state (into [] (concat iis (repeat 500 0))) [])
         block-tiles #{}]
    (if (state :halt?)
      (count (filter #(= 2 (nth % 2)) (partition 3 (:params state))))
      (let [new-state (intcode/step state)
            [x y t] (:params new-state)
            new-block-tiles (if (= 2 t) (conj block-tiles [x y]) block-tiles)]
        (recur new-state new-block-tiles)))))

(defn- tile-to-char [t]
  (case t
    0 " "
    1 "#"
    2 "~"
    3 "-"
    4 "o"
    -1 "?"))

(defn- print-game [params]
  (println params))
;  (println (map identity (sort-by (juxt first second) (map vec (partition 3 params))))))

(defn- count-tiles [params i]
  (count (filter #(= i (nth % 2)) (partition 3 params))))
  
(defn- run-game [start-state in]
  (loop [state (assoc start-state :params in)]
    (if (state :halt?)
      state
      (let [next-state (intcode/step state)]
        (recur next-state)))))

(defn- compute2 [initial-state in]
  (comment println "compute2.in: " in)
  (loop [state (assoc initial-state :params in)]
      (let [next-state (intcode/step state)]
        (comment if (not (= (count (:params next-state)) (count (:params state)))) (println "compute2. params: " (:params next-state)))
        (if (= 3 (count (next-state :params)))
          next-state
          (recur next-state)))))

(defn- tile [world x y]
  (world [x y]))

(defn- print-line [world y]
  (apply str 
    (for [x (range 44)] (tile world x y))))

(defn- print-world [world]
  (for [y (range 22)]
    (println (print-line world y))))


(defn- update-world [world x y t]
  (assoc world [x y] t))

(defn- get-tile-count [world t]
  (count (filter #(= t (second %)) world)))

(defn- get-ball [world]
  (first (first (filter #(= 4 (second %)) world))))

(defn- get-paddle [world]
  (first (first (filter #(= 3 (second %)) world))))

(defn- compute-input [world]
  (let [ball-x (first (get-ball world))
        paddle-x (first (get-paddle world))]
    (comment println "comp-inp (ball/paddle) - x: " ball-x paddle-x)
    (cond
      (< paddle-x ball-x) [1]
      (> paddle-x ball-x) [-1]
      :otherwise [0])))

;12099
(defn part2 [iis]
  (let [initial-state (intcode/create-state (into [] (concat (assoc iis 0 2) (repeat 500 0))) [])]
    (loop [state initial-state
           world { }
           in []
           initial? true]
      (if (and (not initial?) (zero? (get-tile-count world 2)))
        (last (:params (compute2 state [])))
        (let [
              new-state (compute2 state (vec in))
              [x y t & rs] (:params new-state)
              new-world (update-world world x y t)
              new-in (if (or (and initial? (= x -1)) (and (not initial?) (= t 4))) (vec (compute-input new-world)) [])
            ]
          (comment println "Tile: " x y t " - " rs)
          (comment if (= x -1) (println "SCORE: " x y t))
          (recur new-state new-world new-in (and initial? (> x -1))))))))
  

