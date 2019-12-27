(ns advent-of-code-2019.day22
  (:require [clojure.string :as str])
  (:require [advent-of-code-2019.utils :as utils]))

(defn- deal-into-new-stack [cards]
  (into [] (reverse cards)))

(defn- cut [cards n]
  (let [a (if (neg? n) (+ n (count cards)) n)]
    (into [] (concat (drop a cards) (take a cards)))))

(defn- deal-with-increment [cards n]
  (let [cc (count cards)]
    (loop [deck []
           i 0
           pos 0]
      (if (= i cc)
        (into [] (map second (sort-by first deck)))
        (recur 
          (conj deck [pos (nth cards i)]) 
          (inc i)
          (mod (+ pos n) cc))))))

(defn- parse-last-number [s]
  (Integer/parseInt (last (str/split s #" "))))

(defn- run [in decksize]
  (loop [[s & rs] in
         deck (range decksize)]
    (if (nil? s)
      deck
      (recur 
        rs
        (cond 
          (str/starts-with? s "deal with increment") (deal-with-increment deck (parse-last-number s))
          (str/starts-with? s "cut") (cut deck (parse-last-number s))
          (str/starts-with? s "deal into new stack") (deal-into-new-stack deck)))))) 

(defn part1 [in]
  (.indexOf (run in 10007) 2019))

(defn part2 [in]
  (.indexOf (run in 10007) 2020))
