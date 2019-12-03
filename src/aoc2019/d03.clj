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

(defn solve-part1 [] (distance input))

;; Part 2
;;


(defn wirepaths [input] (pmap lay-wire-path input))

(defn calc-intersections
  [wires]
  (->> (map #(into #{} %) wires)
       (apply clojure.set/intersection)
       (remove #(= [0 0] %))
       (into #{})))

(defn count-path
  [intersections acc length [coord & path]]
  (let [acc' (if (and (contains? intersections coord)
                      (not (contains? (into #{} (map second acc)) coord)))
               (conj acc [length coord])
               acc)]
    (if (empty? path)
      acc'
      (recur intersections acc' (inc length) path))))

(defn path->mappings
  [path]
  (->> path
       (map (fn [[steps coords]] (hash-map coords steps)))
       (reduce conj)))

(defn calc-steps
  [intersections [path1 path2]]
  (->> intersections
       (map (fn [coord] [(get path1 coord) (get path2 coord)]))
       (map #(reduce + %))))


(defn steps [input]
  (let [wirepaths (wirepaths input)
        intersections (calc-intersections wirepaths)]
    (->> wirepaths
         (map #(count-path intersections [] 0 %))
         (map path->mappings)
         (calc-steps intersections)
         (sort <)
         (first))))

(defn solve-part2 [] (steps input))

