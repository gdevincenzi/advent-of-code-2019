(ns aoc2019.d02
  (:require [clojure.string :as s]))

(def input
  (-> (slurp "resources/d02")
      (s/split #",")))

(def parsed-input
  (->> (map read-string input)
       (into [])))

;; Part1
;;


(defn operation
  [opcode]
  (condp = opcode
    1 +
    2 *))

(defn perform-operation
  [[opcode pos1 pos2 rpos] program]
  (let [arg1 (nth program pos1)
        arg2 (nth program pos2)]
    (assoc program rpos ((operation opcode) arg1 arg2))))

(defn compute
  ([program] (compute 0 program))
  ([iteration program]
   (let [word (take 4 (drop (* 4 iteration) program))]
     (cond
       (= 99 (first word)) program
       (empty? word)       program
       :else               (recur (inc iteration) (perform-operation word program))))))

(defn solve-part1
  []
  (-> parsed-input
      (assoc 1 12)
      (assoc 2 2)
      (compute)
      (nth 0)))


;; Part 2
;;


(def possible-inputs (for [x (range 0 100) y (range 0 100)] [x y]))

(defn preprocess
  [[input1 input2] program]
  (-> program
      (assoc 1 input1)
      (assoc 2 input2)))

(defn solve-part2
  []
  (->> possible-inputs
       (pmap #(preprocess % parsed-input))
       (pmap compute)
       (filter #(= 19690720 (first %)))
       (first)
       (#(+ (* 100 (nth % 1)) (nth % 2)))))

