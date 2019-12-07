(ns advent-of-code-2019.day07)

(defn- exp [x n]
    (reduce * (repeat n x)))

(defn- permutations [s]
  (lazy-seq
     (if (seq (rest s))
        (apply concat (for [x s]
           (map #(cons x %) (permutations (remove #{x} s)))))
        [s])))

(defn- get-param-mods [opcode i]
  (mod (quot opcode (* 10 (exp 10 i))) 10))

(defn- get-param-position [state i]
  (nth (state :is) (nth (state :is) (+ (state :pc) i))))

(defn- get-param-immediate [state i]
  (nth (state :is)  (+ (state :pc) i)))

(defn- get-param [state i]
  (case (get-param-mods (nth (state :is) (state :pc)) i)
    0 (get-param-position state i)
    1 (get-param-immediate state i)))

(defn- handle-plus [state]
   (let [p1 (get-param state 1)
         p2 (get-param state 2)
         p3 (get-param-immediate state 3)]
     (assoc state :is (assoc (state :is) p3 (+ p1 p2)) :pc (+ (state :pc) 4))))

(defn- handle-mult [state]
   (let [p1 (get-param state 1)
         p2 (get-param state 2)
         p3 (get-param-immediate state 3)]
     (assoc state :is (assoc (state :is) p3 (* p1 p2)) :pc (+ (state :pc) 4))))

(defn- handle-input [state]
   (let [p1 (get-param-immediate state 1)
         [i & is] (state :params)]
     (assoc state :is (assoc (state :is) p1 i) :pc (+ (state :pc) 2) :params is)))

(defn- handle-output [state]
   (let [p1 (get-param state 1)]
     (assoc state :pc (+ (state :pc) 2) :params (conj (state :params) p1))))

(defn- handle-jmp-if-true [state]
   (let [p1 (get-param state 1)
         p2 (get-param state 2)
         npc (if (zero? p1) (+ (state :pc) 3) p2)]
     (assoc state :pc (if (zero? p1) (+ (state :pc) 3) p2))))

(defn- handle-jmp-if-false [state]
   (let [p1 (get-param state 1)
         p2 (get-param state 2)]
     (assoc state :pc (if (zero? p1) p2 (+ (state :pc) 3)))))

(defn- handle-less-than [state]
   (let [p1 (get-param state 1)
         p2 (get-param state 2)
         p3 (get-param-immediate state 3)]
     (assoc state :is (assoc (state :is) p3 (if (< p1 p2) 1 0)) :pc (+ (state :pc) 4))))

(defn- handle-equals [state]
   (let [p1 (get-param state 1)
         p2 (get-param state 2)
         p3 (get-param-immediate state 3)]
     (assoc state :is (assoc (state :is) p3 (if (= p1 p2) 1 0)) :pc (+ (state :pc) 4))))

(defn- handle-halt [state]
  (assoc state :halt? true))

(defn step [state]
  (case (mod (nth (state :is) (state :pc)) 100)
    1 (handle-plus state)
    2 (handle-mult state)
    3 (handle-input state)
    4 (handle-output state)
    5 (handle-jmp-if-true state)
    6 (handle-jmp-if-false state)
    7 (handle-less-than state)
    8 (handle-equals state)
    99 (handle-halt state)))

(defn- insert-list [l n]
  (concat [(first l)] [n] (rest l)))

(defn run-loop [start-state in]
  (loop [state (assoc start-state :params (conj (start-state :params) in))
         signal? false]
    (if (or signal? (state :halt?))
      state
      (let [next-state (step state)]
        (recur next-state (> (count (next-state :params)) (count (state :params))))))))

(defn- create-state [iis phase-input]
  {:is iis :pc 0 :params [phase-input] :halt? false})

(defn- amp-loop [iis phase-inputs]
  (loop [amps (into [] (map #(create-state iis %) phase-inputs))
         a 0
         in2 0]
    (let [next-amp (run-loop (nth amps a) in2)]
      (if (next-amp :halt?)
        (first (next-amp :params))
        (recur (assoc amps a next-amp) (mod (inc a) 5) (first (next-amp :params)))))))

(defn highest-amp-loop [iis]
    (apply max
      (map #(amp-loop iis %) (permutations (range 5 10)))))

(defn run [iis in]
  (loop [state {:is iis :pc 0 :params in :halt? false}]
    (if (state :halt?) 
      state
      (recur (step state)))))

(defn- amp [iis phase-inputs]
  (loop [[phase-input & is] phase-inputs
         in2 0]
    (if (nil? phase-input)
      in2
      (let [next-run (run iis [phase-input in2])]
        (recur is (last (next-run :params)))))))

(defn highest-amp [iis]
    (apply max
      (map #(amp iis %) (permutations (range 5)))))

