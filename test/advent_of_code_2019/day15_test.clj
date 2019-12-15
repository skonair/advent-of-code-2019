(ns advent-of-code-2019.day15-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [advent-of-code-2019.day15 :refer :all]))

;
; the results
; 
(def input (into [] (map #(Long/parseLong %) (str/split (slurp "resources/day15/input.txt") #","))))

(deftest result
  (testing "the correct answer for the given input for documentation purposes"
    (is (= 308 (part1 input)))
    (is (= 328 (part2 input)))))

