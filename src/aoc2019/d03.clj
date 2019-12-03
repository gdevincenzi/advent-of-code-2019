(ns aoc2019.d03)

(def input
  (->> (slurp "resources/d03")
       (clojure.string/split-lines)
       (map #(clojure.string/split % #","))))

;; Part 1
;;


(defn build-path [[x0 y0] [x1 y1]] (for [x (range x0 x1) y (range y0 y1)] [x y]))

(defn calc-next-path
  [[x y] instruction]
  (let [direction (re-find #"\D+" instruction)
        value (-> (re-find #"\d+" instruction) (read-string))]
    (condp = direction
      "U" (build-path [x y] [(inc x) (inc (+ y value))])
      "R" (build-path [x y] [(inc (+ x value)) (inc y)])
      "D" (reverse (build-path [x (- y value)] [(inc x) (inc y)]))
      "L" (reverse (build-path [(- x value) y] [(inc x) (inc y)])))))

(defn lay-wire-path
  ([instructions] (lay-wire-path [[0 0]] instructions))
  ([path [cur & rst]]
   (let [path' (concat path (rest (calc-next-path (last path) cur)))]
     (if (empty? rst)
       path'
       (recur path' rst)))))

(defn distance [input]
  (->> input
       (pmap lay-wire-path)
       (pmap #(into #{} %))
       (apply clojure.set/intersection)
       (remove #(= [0 0] %))
       (map (fn [[x y]] (+ (Math/abs x) (Math/abs y))))
       (sort <)
       (first)))

