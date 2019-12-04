(ns aoc2019.d01
  (:require [clojure.string :as s]))

(def input
  (->> (slurp "resources/d01")
       (s/split-lines)
       (map read-string)))

;; Part1
;;


(defn calc-fuel
  [mass]
  (-> mass
      (/ 3)
      (Math/floor)
      (int)
      (- 2)))

(defn solve-part1
  []
  (->> input
       (map calc-fuel)
       (reduce +)))

;; Part2
;;

(defn calc-fuel2
  [mass]
  (->> mass
       (iterate calc-fuel)
       (rest)
       (take-while #(> % 0))
       (reduce +)))

(defn solve-part2
  []
  (->> input
       (map calc-fuel2)
       (reduce +)))
