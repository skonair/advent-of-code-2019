(ns advent-of-code-2019.day05)
(require '[clojure.string :as str])

(defn- exp [x n]
  (reduce * (repeat n x)))

(defn- get-next-input [ & ps] 
  (println "< < < < INPUT")
  1)

(defn- print-value [p & ps] (println "> > > > OUTPUT: " p)) 

(defn- modify-is [is ps storeps value]
  (comment println "modify-is " is ps storeps value)
  (assoc is storeps value))

(defn- remain-is [is ps storeps value] 
  (comment println "remain is " is ps storeps value)
  is)

(def ops {1 {:fnc + :params 3 :apply-fnc modify-is :store 1}
          2 {:fnc * :params 3 :apply-fnc modify-is :store 1}
          3 {:fnc get-next-input :params 1 :apply-fnc modify-is :store 1}
          4 {:fnc print-value :params 1 :apply-fnc remain-is :store 0}}) 

(defn- get-param [is pc pm n]
  (comment println "get-param " is " " pc " " pm " " n)
  (case pm
    1 (nth is (+ pc n))
    0 (nth is (nth is (+ pc n)))))

(defn- parse-opcode [is pc]
  (comment println "parse-opcode <start> " is pc)
  (let [opn (mod (nth is pc) 100)
        operation (ops opn)
        pms (for [i (range (- (operation :params) (operation :store)))] (mod (quot (nth is pc) (* 100 (exp 10 i))) 10))
        ps (for [i (range (- (operation :params) (operation :store)))] (get-param is (inc pc) (nth pms i) i))
        storeps (if (zero? (operation :store)) nil (nth is (+ pc (operation :params))))]
    (println ps storeps "---parse-opcode: " )
    [operation ps storeps]))

(defn step [is pc]
  (let [[operation ps storeps] (parse-opcode is pc)
        value (apply (operation :fnc) ps)
        nis ((operation :apply-fnc) is ps storeps value)]
    (comment println "step: " value " / " is " -> " nis " with operation " operation " and ps " ps)
    [((operation :apply-fnc) is ps storeps value) (operation :params)]))

(defn run [iis]
  (loop [pc 0
         is iis]
    (if
      (= 99 (nth is pc)) (first is)
      (let [nis (step is pc)]
        (comment println "nis is " nis)
        (recur (+ pc (second nis) 1) (first nis) )))))

