(ns advent-of-code-2019.day12
  (:require [advent-of-code-2019.utils :as utils])

(defn- signum [e]
  (cond
    (< e 0) -1
    (> e 0) 1
    (zero? e) 0))

(defn- vector-diff [v1 v2]
  (map signum (map #(- (first %) (second %) ) (map vector v1 v2))))

(defn- vector-add [v1 v2]
  (map #(+ (first %) (second %)) (map vector v1 v2)))

(defn- new-velocity [mp mps]
    (vector-add (:vel mp)
      (reduce vector-add  (map #(vector-diff % (:pos mp)) (map :pos mps)))))

(defn- velocity [state]
  (for [s state] (new-velocity s state)))

(defn- new-position [state velocity]
  (vector-add (:pos state) velocity))

(defn- position [state]
  (for [s state] 
    (let [v (new-velocity s state)
          p (new-position s v)]
      {:pos p :vel v})))

(defn- potential-energy [m]
  (apply + (map abs (:pos m))))

(defn- kinetic-energy [m]
  (apply + (map abs (:vel m))))

(defn- energy [m]
  (* (potential-energy m) (kinetic-energy m)))
    
(defn run [initial-state n]
  (loop [state initial-state
         all-states #{}
         cnt 0]
    (comment println state)
    (if (or (>= cnt n) (some #{state} all-states))
      (do
        (println state)
        [state (reduce + (map energy state)) (count all-states)])
      (recur (position state) (conj all-states state) (inc cnt)))))
    
(def myinput
  [{:pos [17 5 1] :vel [0 0 0]} {:pos [-2 -8 8] :vel [0 0 0]} {:pos [7 -6 14] :vel [0 0 0]} {:pos [1 -10 4] :vel [0 0 0]}]) 

(def input 
  [{:pos [-1 0 2] :vel [0 0 0]} {:pos [2 -10 -7] :vel [0 0 0]} {:pos [4 -8 8] :vel [0 0 0]} {:pos [3 5 -1] :vel [0 0 0]}])
