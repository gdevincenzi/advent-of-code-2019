(ns aoc2019.d06
  (:require [clojure.string :as s]
            [clojure.zip :as z]))

(def input
  (-> (slurp "resources/d06")
      (s/split-lines)))


;; Part 1
;;


(defn code->codevec [code] (->> (s/split code #"\)") (map keyword)))

(defn input->codelist [codes] (map code->codevec codes))

(defn list-nodes [codelist] (->> codelist (mapcat flatten) set))

(def find-parent
  (memoize
   (fn [node codelist]
     (->> codelist
          (filter #(= node (second %)))
          (ffirst)))))

(def calc-orbits
  (memoize
   (fn [node codelist]
     (let [parent (find-parent node codelist)]
       (if (nil? parent)
         0
         (inc (calc-orbits parent codelist)))))))

(defn calc-total-orbits
  [codelist]
  (->> codelist
       list-nodes
       (pmap #(calc-orbits % codelist))
       (reduce +)))

(defn solve-part1 [] (->> input input->codelist calc-total-orbits))


;; Part 2
;;


(defn find-path-to-root
  ([node codelist] (find-path-to-root [] node codelist))
  ([path node codelist]
   (let [parent (find-parent node codelist)]
     (if (nil? parent)
       (conj path node)
       (recur (conj path node) parent codelist)))))

(defn find-branch
  [[node & rst] path2]
  (if (contains? (set path2) node)
    node
    (find-branch rst path2)))

(defn steps-to-branch
  [branch path]
  (->> path
       (take-while #(not (= branch %)))
       (count)))

(defn calc-transfers
  [codelist]
  (let [path1 (rest (find-path-to-root :YOU codelist))
        path2 (rest (find-path-to-root :SAN codelist))
        branch (find-branch path1 path2)]
    (->> path1
         (steps-to-branch branch)
         (+ (steps-to-branch branch path2)))))

(defn solve-part2 [] (->> input input->codelist calc-transfers))
