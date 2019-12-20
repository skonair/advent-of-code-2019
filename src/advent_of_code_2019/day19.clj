(ns advent-of-code-2019.day19
  (:require [advent-of-code-2019.intcode :as intcode]))

(defn- run [in x y]
  (loop [state (intcode/create-state (into [] (concat in (repeat 500 0))) [x y])]
    (if (state :halt?)
      (first (:out state))
      (recur (intcode/step state)))))

(defn- get-grid [in maxx maxy]
  (for [x (range maxx)
            y (range maxy)]
        (run in x y)))

(defn- part1 [in]
  (count
    (filter 
      #(= 1 %)
      (get-grid in 50 50))))


(defn- print-grid [grid]
  (println (apply str (map char grid))))

(defn- get-grid [grid]
  (loop [[g & gs] grid
         x 0
         y 0
         akk {}]
    (if (nil? g)
      akk
      (let [new-x (if (= g 10) 0 (inc x))
            new-y (if (= g 10) (inc y) y)
            new-akk (if (= g 10) akk (assoc akk [new-x new-y] g))]
        (recur gs new-x new-y new-akk)))))

(defn- crossing? [x y maze]
  (let [pc (maze [x y])
        pn (maze [x (dec y)])
        ps (maze [x (inc y)])
        pe (maze [(inc x) y])
        pw (maze [(dec x) y])]
  (and (= 35 pc) (= 35 pn) (= 35 ps) (= 35 pe) (= 35 pw))))

(defn part1 [in]
  (let [grid (get-grid (run in))]
    (apply +
      (for [x (range 46)
            y (range 51)]
        (if (crossing? x y grid)
          (* (dec x) y)
          0)))))
          
; manually constructed
(def pms [65 44 66 44 65 44 66 44 67 44 67 44 66 44 67 44 66 44 65 10 82 44 49 50 44 76 44 56 44 82 44 49 50 10 82 44 56 44 82 44 54 44 82 44 54 44 82 44 56 10 82 44 56 44 76 44 56 44 82 44 56 44 82 44 52 44 82 44 52 10 110 10])

(defn part2 [in]
  (loop [state (intcode/create-state (into [] (concat (assoc in 0 2) (repeat 10000 0))) pms)]
    (if (state :halt?)
      (last (:out state))
      (recur (intcode/step state)))))

