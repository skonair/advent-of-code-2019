(ns advent-of-code-2019.day11
  (:require [advent-of-code-2019.intcode :as intcode]))

(defn- compute [initial-state color]
  (loop [state (assoc initial-state :params [color])]
    (if (state :halt?)
      state
      (let [next-state (intcode/step state)]
        (if (= 2 (count (next-state :params)))
          next-state
          (recur next-state))))))

(defn- rotate [rotation n]
  (case rotation
    :UP (if (zero? n) :LEFT :RIGHT)
    :RIGHT (if (zero? n) :UP :DOWN)
    :DOWN (if (zero? n) :RIGHT :LEFT)
    :LEFT (if (zero? n) :DOWN :UP)))
    
(defn- new-pos [[x y] rotation]
  (case rotation
    :UP [x (inc y)]
    :RIGHT [(inc x) y]
    :DOWN [x (dec y)]
    :LEFT [(dec x) y]))

(defn- new-world [world position color]
  (case color
    1 (conj world position)
    0 (remove #{position} world)))
  
(defn- update-on-output [[direction color] world position rotation]
  (let [new-rotation (rotate rotation direction)
        new-position (new-pos position new-rotation)
        new-wrld (new-world world position color)]
    [new-wrld new-position new-rotation]))

(defn- get-color [world position]
  (if (some #{position} world) 1 0))

(defn paint-robot [iis initial-world]
  (loop [state (intcode/create-state (into [] (concat iis (repeat 500 0))) [])
        world initial-world
        visited #{}
        position [0 0]
        rotation :UP]
  (let [color (get-color world position)
        new-state (compute state color)]
    (if (new-state :halt?)
      [(count visited) world] 
      (let [[nw np nr] (update-on-output (new-state :params) world position rotation)]
        (recur new-state nw (conj visited np) np nr))))))

(defn part1 [iis]
  (first (paint-robot iis #{})))
  
(defn- paint-line [world minx maxx]
   (apply str
     (for [x (range minx (inc maxx))]
       (if (some #{x} world) "#" " "))))

(defn- paint-world [world]
  (let [mfw (map first world)
        msw (map second world)]
    (for [y (range (apply max msw) (dec (apply min msw)) -1)]
      (println 
        (paint-line 
          (map first (filter #(= y (second %)) world)) 
          (apply min mfw)
          (apply max mfw))))))

(defn part2 [iip]
  (paint-world (second (paint-robot iip #{[0 0]}))))
  
