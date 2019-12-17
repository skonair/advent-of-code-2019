(ns advent-of-code-2019.day17-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [advent-of-code-2019.day17 :refer :all]))

;
; the results
; 
(def input (into [] (map #(Long/parseLong %) (str/split (slurp "resources/day17/input.txt") #","))))

(deftest result
  (testing "the correct answer for the given input for documentation purposes"
    (is (= 14332 (part1 input)))
    (is (= 1034009 (part2 input)))))

