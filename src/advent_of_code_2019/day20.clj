(ns advent-of-code-2019.day20
  (:require [advent-of-code-2019.utils :as utils]))

(def deltas [[0 -1] [0 1] [1 0] [-1 0]])

(defn- all-char [maze]
  (apply merge (map #(hash-map (first %) (second %)) (filter #(utils/in? "ABCDEFGHIJKLMNOPQRSTUVWXYZ" (second %)) maze))))

(defn- find-neighbour-char [maze c pos]
  (for [d deltas
        :let [np (utils/v+ pos d)]
        :when (= c (maze np))]
    np))

(defn- find-dots [maze pos1 pos2]
  (concat 
    (find-neighbour-char maze \. pos1)
    (find-neighbour-char maze \. pos2)))

(defn- neighbours [maze pos]
    (for [d deltas
          :let [np (utils/v+ pos d) e (maze pos) e2 (maze np)]
          :when ((all-char maze) np)]
          [(apply str (sort [e e2])) (first (find-dots maze pos np))]))

(defn- get-positions [nmaze wp]
  (into #{} (map second (filter #(= (first %) wp) nmaze))))

(defn- get-warp-points [nmaze pos]
  (first (map first (filter #(= (second %) pos) nmaze))))


(defn- get-warp-output [nmaze pos]
  (let [wp (get-warp-points nmaze pos)
        positions (get-positions nmaze wp)]
    (first (disj positions pos))))


(defn- filter-chars [maze a b]
  (filter #(some #{(second %)} [a b]) maze))

(defn- find-neighbours [maze pos]
  (for [d deltas
        :let [np (utils/v+ pos d)]
        :when (re-find #"[A-Z\.]" (maze np))]
    np))

(defn- warp [maze nmaze pos]
  (let [wo (get-warp-output nmaze pos)
        c (maze pos)
        result (if (nil? wo) pos wo)
        res-step (if (nil? wo) 1 2)]
    (if (not (nil? wo)) (println "  Warp from " pos " to " result "."))
    [result res-step]))

(defn- find-neighbours [maze pos]
  (for [d deltas
        :let [np (utils/v+ pos d)]
        :when (re-find #"[A-Z\.]" (maze np))]
    np))
        
(defn- find-pairs [maze a b]
  (for [[pos c] (filter-chars maze a a)]
    [pos (first (find-neighbour-char maze b pos))]))

(defn- get-dot-near-chars [maze a b]
  (first 
    (filter 
      #(not (empty? %))  
      (map 
        #(find-neighbour-char maze \. (first %)) 
        (filter-chars maze a b)))))

(defn- get-start [maze]
  (first (get-dot-near-chars maze \A \A)))

(defn- finished? [maze pos]
  (= pos (first (get-dot-near-chars maze \Z \Z))))

(defn- stuck? [maze pos visited]
  (every? #(utils/in? visited %) (find-neighbour-char maze \. pos)))

(defn- traverse [maze nmaze pos visited steps]
  (println "traverse in pos " pos " [ " steps " ] - visited: " )
    (cond 
      (finished? maze pos) steps
      (stuck? maze pos visited) 99999
      (> steps 600) 88888
      :otherwise (apply min
        (for [pp (find-neighbour-char maze \. pos)
              :let [[p st] (warp maze nmaze pp)]
              :when (utils/not-in? visited p)]
          (traverse maze nmaze p (conj visited p) (+ st steps))))))

(defn- traverse2 [maze nmaze spos svisited ssteps]
  (loop [pos spos
         visited svisited
         steps ssteps]
    (println "traverse2 in pos " pos " [ " steps " ] - visited: " )
    (cond 
      (finished? maze pos) steps
      (stuck? maze pos visited) 99999
      :otherwise (apply min
        (for [pp (find-neighbour-char maze \. pos)
              :let [[p st] (warp maze nmaze pp)]
              :when (utils/not-in? visited p)]
          (recur p (conj visited p) (+ st steps)))))))

(defn part1 [maze]
  (let [nmaze (map #(first (neighbours maze (first %))) (all-char maze))]
    (traverse maze nmaze (get-start maze) #{} 0)))

(defn part2 [maze]
  (let [nmaze (map #(first (neighbours maze (first %))) (all-char maze))]
    (traverse maze nmaze [47 2] #{} 0)))

