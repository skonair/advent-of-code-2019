(ns advent-of-code-2019.day14-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [advent-of-code-2019.day14 :refer :all]))

;
; the results
;
(defn- parse-line [l] (map #(vector (nth % 2) (Long/parseLong (second %))) (re-seq #"(\d+) (\w+)" l)))
(def input (map parse-line (str/split (slurp "resources/day14/input.txt") #"\n")))

(deftest result
  (testing "the correct answer for the given input for documentation purposes"
    (is (= 278404 (part1 input)))
    (is (= 4436981 (part2 input)))))

