(ns advent-of-code-2019.day06)

(defn all-orbits-for [os s]
  (loop [cs s
      cnt 0]
    (if (= "COM" cs)
      cnt
      (recur (os cs) (inc cnt)))))

(defn all-orbits [os]
  (let [ks (into #{} (keys os))]
    (apply + (map #(all-orbits-for os %) ks))))

(defn orbit-map [l]
  (loop [[a & as] l
         akk {}]
    (if (nil? a)
      akk
      (recur as (assoc akk (second a) (first a))))))
