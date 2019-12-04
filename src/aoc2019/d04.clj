(ns aoc2019.d04)

(def input
  (-> (slurp "resources/d04")
      (clojure.string/split #"-")
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

(defn solve-part1 [] (count-password input))
