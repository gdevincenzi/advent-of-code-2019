(ns aoc2019.d05
  (:require [clojure.string :as s]))

(def input
  (-> (slurp "resources/d05")
      (s/split #",")))

(def parsed-input
  (->> (map read-string input)
       (into [])))

(def program (atom parsed-input))
(def output (atom nil))

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
         4 (reset! output (read-value mode1 (first params))))
       (recur input (+ pointer (count word)))))))

(defn solve-part1
  []
  (reset! output 0)
  (reset! program parsed-input)
  (compute 1)
  @output)


;; Part 2
;;

(def pointer (atom 0))

(defn JIT
  [mode1 mode2 param1 param2]
  (if-not (zero? (read-value mode1 param1))
    (reset! pointer (read-value mode2 param2))
    (swap! pointer + 3)))

(defn JIF
  [mode1 mode2 param1 param2]
  (if (zero? (read-value mode1 param1))
    (reset! pointer (read-value mode2 param2))
    (swap! pointer + 3)))

(defn less-than
  [mode1 mode2 param1 param2 rpos]
  (if (< (read-value mode1 param1) (read-value mode2 param2))
    (update-value rpos 1)
    (update-value rpos 0)))

(defn equals
  [mode1 mode2 param1 param2 rpos]
  (if (= (read-value mode1 param1) (read-value mode2 param2))
    (update-value rpos 1)
    (update-value rpos 0)))

(defn get-word
  [opcode program]
  (condp contains? opcode
   #{1 2 7 8} (take 4 program)
   #{3 4} (take 2 program)
   #{5 6} (take 3 program)
   #{99} nil))

(defn update-pointer
  [opcode]
  (condp contains? opcode
   #{1 2 7 8} (swap! pointer + 4)
   #{3 4} (swap! pointer + 2)
   #{5 6} nil))

(defn compute2
 [input]
 (let [current (subvec @program @pointer)
       [op mode1 mode2] (parse-opcode (first current))
       word (get-word op current)
       params (drop 1 word)]
   (when-not (= 99 op)
     (condp = op
       1 (apply transform! + mode1 mode2 params)
       2 (apply transform! * mode1 mode2 params)
       3 (update-value (first params) input)
       4 (reset! output (read-value mode1 (first params)))
       5 (apply JIT mode1 mode2 params)
       6 (apply JIF mode1 mode2 params)
       7 (apply less-than mode1 mode2 params)
       8 (apply equals mode1 mode2 params))
     (update-pointer op)
     (recur input))))

(defn solve-part2
  []
  (reset! pointer 0)
  (reset! output 0)
  (reset! program parsed-input)
  (compute2 5)
  @output)
