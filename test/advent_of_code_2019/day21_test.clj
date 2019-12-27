(ns advent-of-code-2019.day21-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [advent-of-code-2019.day21 :refer :all]))

;
; the results
; 
(def input (into [] (map #(Long/parseLong %) (str/split (slurp "resources/day21/input.txt") #","))))

(deftest result
  (testing "the correct answer for the given input for documentation purposes"
    (is (= 19355364 (part1 input)))
    (is (= 1142530574 (part2 input)))))

