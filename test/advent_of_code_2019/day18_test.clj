(ns advent-of-code-2019.day18-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [advent-of-code-2019.day18 :refer :all]))

;
; the results
; 

(defn- parse-line [l y] (map-indexed (fn [idx e] {[idx y] e}) l))
(def input (map-indexed (fn [idx e] (parse-line e idx)) (str/split (slurp "resources/day18/input.txt") #"\n")))

(deftest result
  (testing "the correct answer for the given input for documentation purposes"
    (is (= 3150224 (fuel input)))
    (is (= 4722484 (fuel-fuel input)))))

