(ns aoc2019.core
  (:require [clojure.test :refer :all]
            [aoc2019.core :refer :all]
            [aoc2019.d01  :as d01]))


(deftest day01
  (testing "Part1 test cases"
    (is (= 2 (d01/calc-fuel 12)))
    (is (= 2 (d01/calc-fuel 14)))
    (is (= 654 (d01/calc-fuel 1969)))
    (is (= 33583 (d01/calc-fuel 100756))))

  (testing "Solution for part 1"
    (is (= 3301059 (d01/solve-part1))))

  (testing "Part2 test cases"
    (is (= 2 (d01/calc-fuel2 14)))
    (is (= 966 (d01/calc-fuel2 1969)))
    (is (= 50346 (d01/calc-fuel2 100756))))

  (testing "Solution for part 2"
    (is (= 4948732 (d01/solve-part2)))))
