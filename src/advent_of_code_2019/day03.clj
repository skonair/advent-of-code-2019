(ns advent-of-code-2019.day03)
(require '[clojure.string :as str]
         '[clojure.set :as set])

(defn new-coord [c d]
  (let [[x y cnt] c] 
    (cond 
      (= d \R) [(inc x) y (inc cnt)]
      (= d \L) [(dec x) y (inc cnt)]
      (= d \U) [x (inc y) (inc cnt)]
      (= d \D) [x (dec y) (inc cnt)])))

(defn path-from [sc v]
  (let [d (first v)
        n (Integer/parseInt (apply str (rest v)))]
    (loop [c sc
           i n
           akk []]
      (if 
        (zero? i) akk
        (let [nc (new-coord c d)]
          (recur nc (dec i) (conj akk nc)))))))

(defn follow-path [path]
  (loop [c [0 0 0]
         [p & ps] path
         akk []]
    (if
      (nil? p) akk
      (let [pf (path-from c p)
            nc (last pf)]
        (recur nc ps (concat akk pf))))))

(defn- abs [n] (max n (- n)))

(defn- man-dist [c]
  (let [[x y cnt] c]
    (+ (abs x) (abs y))))

(defn nearest-intersection [v1 v2]
  (let [fp1 (follow-path v1)
        fp2 (follow-path v2)
        p1 (into #{} (map #(vector (first %) (second %)) fp1))
        p2 (into #{} (map #(vector (first %) (second %)) fp2))
        is (set/intersection p1 p2)]
    (man-dist (first (sort-by man-dist is)))))

(defn- match-c [c1 c2] (and (= (first c1) (first c2)) (= (second c1) (second c2))))

(defn shortest-path [fp coord]
  (last
    (first
      (sort-by last 
        (filter #(match-c coord %) fp)))))

(defn shortest-intersection [v1 v2]
  (let [fp1 (follow-path v1)
        fp2 (follow-path v2)
        p1 (into #{} (map #(vector (first %) (second %)) fp1))
        p2 (into #{} (map #(vector (first %) (second %)) fp2))
        is (set/intersection p1 p2)]
    (apply min (map #(+ (shortest-path fp1 %) (shortest-path fp2 %)) is))))
      
