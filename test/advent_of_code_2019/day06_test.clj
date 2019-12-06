(ns advent-of-code-2019.day06-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [advent-of-code-2019.day06 :refer :all]))

(def test-orbits (orbit-map '(["COM" "B"] ["B" "C"] ["C" "D"] ["D" "E"] ["E" "F"] ["B" "G"] ["G" "H"] ["D" "I"] ["E" "J"] ["J" "K"] ["K" "L"])))

(deftest number-of-orbits
  (testing "the correct number of direct and indirect orbits"
    (is (= 3 (all-orbits-for test-orbits "D")))
    (is (= 7 (all-orbits-for test-orbits "L")))
    (is (= 0 (all-orbits-for test-orbits "COM"))))
  (testing "the correct number of all orbits"
    (is (= 42 (all-orbits test-orbits)))))
  

;
; the results
; 
(def input
  (orbit-map
    (map #(str/split % #"\)") (str/split (slurp "resources/day06/input.txt") #"\n"))))

(deftest result
  (testing "the correct answer for the given input for documentation purposes"
    (is (= 160040 (all-orbits input)))))

