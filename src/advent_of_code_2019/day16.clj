(ns advent-of-code-2019.day16
  (:require [clojure.string :as str])
  (:require [advent-of-code-2019.utils :as utils]))

(defn- get-phase [i]
  (rest (cycle (flatten (map #(repeat i %) [0 1 0 -1])))))

(defn- mapa [in]
  (map-indexed (fn [idx itm] [in (take (count in) (get-phase (inc idx)))]) in))

(defn- calc-mul [in]
  (map #(apply utils/v* %) (mapa in)))

(defn- mod10 [i]
  (mod (utils/abs i) 10))

(defn- calc [in]
  (map mod10 (map #(apply + %) (calc-mul in))))

(defn- get-result [c]
  (Long/parseLong (apply str (take 8 c))))

(defn part1 [in n]
  (loop [i n
         c in] 
    (if (zero? i)
      (get-result c)
      (recur (dec i) (calc c)))))

(defn- calc2 [in]
   (loop [[x & xs] in 
          akk [] 
          r (apply + in)]
     (if (nil? x) 
       (reverse akk)
       (recur xs (cons (mod10 r) akk) (- r x)))))

(defn part2 [in n]
  (let [offset (Long/parseLong (apply str (take 7 in)))
        cs (drop offset (apply concat (repeat 10000 in)))
        c (reduce (fn [a _] (calc2 a)) cs (range n))]
    (get-result c)))


