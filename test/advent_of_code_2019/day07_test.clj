(ns advent-of-code-2019.day07-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [advent-of-code-2019.day07 :refer :all]))

(deftest test-highest-amp
  (testing "the correct highest amp"
    (is (= 43210 (highest-amp [3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0])))
    (is (= 54321 (highest-amp [3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0])))
    (is (= 65210 (highest-amp [3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0])))))

;
; the results
; 
(def input
    (into [] (map #(Long/parseLong %) (str/split (slurp "resources/day07/input.txt") #","))))

(deftest result
  (testing "the correct answer for the given input for documentation purposes"
    (is (= 199988 (highest-amp input)))))

