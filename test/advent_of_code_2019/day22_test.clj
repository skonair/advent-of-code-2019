(ns advent-of-code-2019.day22-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [advent-of-code-2019.day22 :refer :all]))

;
; the results
; 
(def input (str/split (slurp "resources/day22/input.txt") #"\n"))

(deftest result
  (testing "the correct answer for the given input for documentation purposes"
    (is (= 6638 (part1 input)))
    (is (= ??? (part2 input 10007)))))

