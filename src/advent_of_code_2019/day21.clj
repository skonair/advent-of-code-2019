(ns advent-of-code-2019.day21
  (:require [advent-of-code-2019.intcode :as intcode]))

(defn- run [in prg]
  (loop [state (intcode/create-state (into [] (concat in (repeat 10000 0))) (map int prg))]
    (if (state :halt?)
      (do 
        (if (> (count (:out state)) 80) (println (apply str (map char (:out state)))))
        (last (:out state)))
      (recur (intcode/step state)))))

(defn part1 [in]
  (run in "NOT A J\nNOT C T\nAND D T\nOR T J\nWALK\n"))
          
(defn part2 [in]
  (run in "NOT A J\nNOT C T\nAND H T\nOR T J\nNOT B T\nAND A T\nAND C T\nOR T J\nAND D J\nRUN\n"))

