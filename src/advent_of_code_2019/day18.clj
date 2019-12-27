(ns advent-of-code-2019.day18)

(defn- finished? [world]
  (zero? (count (filter #(let [n (- (int %) 96)] (and (<= n 26) (pos? n))) (vals (:maze world))))))

(defn- stuck? [world]


(defn- run-maze [in]
  (loop [world {:maze in :visited []}]

