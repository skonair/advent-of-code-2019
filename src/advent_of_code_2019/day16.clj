(ns advent-of-code-2019.day16
  (:require [clojure.string :as str]))

(defn abs [n]
  (max n (- n)))
(def v+ (partial mapv +))
(def v* (partial mapv *))

(defn- get-phase [i]
  (rest (cycle (flatten (map #(repeat i %) [0 1 0 -1])))))

(defn- mapa [in]
  (map-indexed (fn [idx itm] [in (take (count in) (get-phase (inc idx)))]) in))

(defn- calc-mul [in]
  (map #(apply v* %) (mapa in)))

(defn- calc [in]
  (map #(mod (abs %) 10) (map #(apply + %) (calc-mul in)))))

(defn- calc2 [in]
  (map #(mod (abs %) 10) (map #(apply + %) in)))

(defn part1 [in n]
  (let [ini (map #(- (int %) 48) in)]
    (loop [i n
           c ini] 
      (if (zero? i)
        (Long/parseLong (apply str (take 8 c)))
        (recur (dec i) (calc c))))))

(defn part2 [in n]
  (let [ini (map #(- (int %) 48) in)]
    (loop [i n
           c (apply concat (repeat 10000 ini))] 
      (println "Looping " i " of " n)
      (if (zero? i)
        (take 8 (drop (Long/parseLong (apply str (take 7 c))) c))
        (recur (dec i) (calc2 c))))))
    

