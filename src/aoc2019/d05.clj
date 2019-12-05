(ns aoc2019.d05
  (:require [clojure.string :as s]))

(def input
  (-> (slurp "resources/d05")
      (s/split #",")))

(def parsed-input
  (->> (map read-string input)
       (into [])))

(def program (atom parsed-input))

;; Part1
;;

(defn update-value [position value] (swap! program assoc position value))

(defn read-value
  ([position] (nth @program position))
  ([mode value] (if (= 1 mode) value (nth @program value))))

(defn transform!
  [op mode1 mode2 param1 param2 rpos]
  (update-value rpos (op (read-value mode1 param1) (read-value mode2 param2))))

(defn parse-opcode
  [opcode]
  (let [op (rem opcode 100)
        mode1 (rem (int (/ opcode 100)) 10)
        mode2 (int (/ opcode 1000))]
    [op mode1 mode2]))

(defn compute
  ([input] (compute input 0))
  ([input pointer]
   (let [current (subvec @program pointer)
         [op mode1 mode2] (parse-opcode (first current))
         word (if (contains? #{1 2} op) (take 4 current) (take 2 current))
         params (drop 1 word)]
     (when-not (= 99 op)
       (condp = op
         1 (apply transform! + mode1 mode2 params)
         2 (apply transform! * mode1 mode2 params)
         3 (update-value (first params) input)
         4 (println "Out >>> "(read-value mode1 (first params))))
       (recur input (+ pointer (count word)))))))

(defn solve-part1
  []
  (reset! program parsed-input)
  (compute 1))
