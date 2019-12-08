(ns advent-of-code-2019.day08
  (:require [clojure.string :as str]))

(defn- count-chars [cs c]
  (count (filter #(= c %) cs)))

(defn- fewest-zeros [cs]
 (first (sort-by #(count-chars % \0) cs)))

(defn image-checksum [in]
  (let [layer (fewest-zeros in)]
    (* (count-chars layer \1) (count-chars layer \2))))

