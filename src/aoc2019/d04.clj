(ns aoc2019.d04
  (:require [clojure.string :as s]))

(def input
  (-> (slurp "resources/d04")
      (s/split #"-")
      (#(map read-string %))))


;; Part 1
;;


(defn number->list
  [number]
  (->> (str number)
       (map (comp read-string str))))

(defn never-decrease?
  [number]
  (->> (number->list number)
       (apply <=)))

(defn has-double?
  [number]
  (->> (number->list number)
       (partition 2 1)
       (map (fn [[n m]] (= n m)))
       (some true?)))

(defn possible-password?
  [number]
  (and (never-decrease? number) (has-double? number)))

(defn count-passwords
  [[lower-bound upper-bound]]
  (->> (range lower-bound (inc upper-bound))
       (filter possible-password?)
       (count)))

(defn solve-part1 [] (count-passwords input))


;; Part 2
;;


(defn gen-regex [n] (re-pattern (str "(?<!" n ")" n n "(?!" n ")")))

(defn only-double?
  [number]
  (->> (range 0 10)
       (map gen-regex)
       (map #(re-find % (str number)))
       (some string?)))

(defn password? [number] (and (never-decrease? number) (only-double? number)))

(defn count-passwords2
  [[lower-bound upper-bound]]
  (->> (range lower-bound (inc upper-bound))
       (filter password?)
       (count)))

(defn solve-part2 [] (count-passwords2 input))

