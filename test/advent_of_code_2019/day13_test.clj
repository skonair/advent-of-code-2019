(ns advent-of-code-2019.day13-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [advent-of-code-2019.day13 :refer :all]))

;
; the results
; 
(def input
    (into [] (map #(Long/parseLong %) (str/split (slurp "resources/day13/input.txt") #","))))

(deftest result
  (testing "the correct answer for the given input for documentation purposes"
    (is (= 239 (part1 input)))
    (is (= 12099 (part2 input)))))

