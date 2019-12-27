(ns advent-of-code-2019.day20-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [advent-of-code-2019.day20 :refer :all]))

;
; the results
; 

(defn- parse-line [y line]
  (map-indexed (fn [idx itm] { [idx y] itm }) line)) 

(defn parse-file [f]
    (into {} (map-indexed (fn [idx itm] (into {} (parse-line idx itm))) (str/split (slurp f) #"\n"))))

(def input (parse-file "resources/day20/input.txt"))

(def example01 (parse-file "resources/day20/example01.txt"))

(deftest result
  (testing "the correct answer for the given input for documentation purposes"
    (is (= 3150224 (fuel input)))
    (is (= 4722484 (fuel-fuel input)))))

