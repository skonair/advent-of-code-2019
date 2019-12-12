(ns advent-of-code-2019.utils)

(defn abs [n] 
  (max n (- n)))

(defn exp [x n]
    (reduce * (repeat n x)))

(defn manhatten-distance [c]
  (let [[x y & zs] c]
    (+ (abs x) (abs y))))

(defn positions
  [pred coll]
  (keep-indexed (fn [idx x]
                  (when (pred x)
                    idx))
                coll))

(defn permutations [s]
  (lazy-seq
     (if (seq (rest s))
        (apply concat (for [x s]
           (map #(cons x %) (permutations (remove #{x} s)))))
        [s])))
