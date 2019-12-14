(ns advent-of-code-2019.day14)

(defn- find-formula [formulas resource-name]
  (first (filter #(= resource-name (first (last %))) formulas)))

(defn- if-completed [chemicals]
  (some (fn [[name amount]] (if (and (not= "ORE" name) (pos? amount)) [name amount] nil)) chemicals))

(defn- mult [chemicals m]
  (into {} (map (fn [[name amount]] [name (* m amount)]) chemicals)))

(defn- find-ore [formulas chemicals]
  (loop [chem chemicals]
    (if-let [[chemical amount] (if-completed chem)]
      (let [formula (find-formula formulas chemical)
            produces (second (last formula))
            multiple (long (Math/ceil (/ amount produces))) 
            new-chem (merge-with + chem (mult [(last formula)] (* -1 multiple)) (mult (drop-last formula) multiple))]
        (recur new-chem))
      chem)))

(defn part1 [formulas] ((find-ore formulas {"FUEL" 1}) "ORE"))

(defn part2 [formulas]
  (loop [min 0
         max 1000000000000
         last -1]
    (let [n (quot (+ min max) 2)
          ore ((find-ore formulas {"FUEL" n}) "ORE")]
      (if (= last n) 
        n
        (cond
          (< ore 1000000000000) (recur n max n)
          (> ore 1000000000000) (recur min n n)
          :otherwise n)))))
