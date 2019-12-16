(ns advent-of-code-2019.day16-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [advent-of-code-2019.day16 :refer :all]))

;
; the results
; 
(def input (map #(- (int %) 48) (slurp "resources/day16/input.txt")))

(deftest result
  (testing "the correct answer for the given input for documentation purposes"
    (is (= 96136976 (part1 input 100)))
    (is (= 85600369 (part2 input 100)))))

