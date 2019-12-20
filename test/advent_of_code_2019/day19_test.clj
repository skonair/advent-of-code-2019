(ns advent-of-code-2019.day19-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [advent-of-code-2019.day19 :refer :all]))

;
; the results
; 
(def input (into [] (map #(Long/parseLong %) (str/split (slurp "resources/day19/input.txt") #","))))

(deftest result
  (testing "the correct answer for the given input for documentation purposes"
    (is (= 114 (part1 input)))
    (is (= 1034009 (part2 input)))))

