(ns advent-of-code-2019.day05
  (:require [advent-of-code-2019.intcode :as intcode]))

(defn run [iis in]
  (loop [state (intcode/create-state iis [in])]
    (if (state :halt?)
      (last (state :out))
      (recur (intcode/step state)))))

