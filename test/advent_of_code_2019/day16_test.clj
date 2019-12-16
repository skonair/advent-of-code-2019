(ns advent-of-code-2019.day16-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [advent-of-code-2019.day16 :refer :all]))

(deftest fuel-by-mass-test-part1
  (testing "the corrext amount of fuel for a given mass"
    (is (= 2 (required-fuel 12)))
    (is (= 33583 (required-fuel 100756)))))

;
; the results
; 
(def input (map #(Long/parseLong %) (str/split (slurp "resources/day16/input.txt") #"\n")))

(deftest result
  (testing "the correct answer for the given input for documentation purposes"
    (is (= 3150224 (part1 input)))
    (is (= 4722484 (part2 input)))))

