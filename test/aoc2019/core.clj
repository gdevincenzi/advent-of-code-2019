(ns aoc2019.core
  (:require [clojure.test :refer :all]
            [aoc2019.core :refer :all]
            [aoc2019.d01  :as d01]
            [aoc2019.d02  :as d02]
            [aoc2019.d03  :as d03]
            [aoc2019.d04  :as d04]
            [aoc2019.d05  :as d05]
            [aoc2019.d06  :as d06]))

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

(deftest day02
  (testing "Part1 test cases"
    (is (= [2 0 0 0 99] (d02/compute [1 0 0 0 99])))
    (is (= [2 3 0 6 99] (d02/compute [2 3 0 3 99])))
    (is (= [2 4 4 5 99 9801] (d02/compute [2 4 4 5 99 0])))
    (is (= [30 1 1 4 2 5 6 0 99] (d02/compute [1 1 1 4 99 5 6 0 99]))))

  (testing "Solution for part 1"
    (is (= 5305097 (d02/solve-part1))))

  (testing "Solution for part 2"
    (is (= 4925 (d02/solve-part2)))))

(deftest day03
  (testing "Part1 test cases"
    (is (= 159 (d03/distance [["R75" "D30" "R83" "U83" "L12" "D49" "R71" "U7" "L72"]
                              ["U62" "R66" "U55" "R34" "D71" "R55" "D58" "R83"]])))
    (is (= 135 (d03/distance [["R98" "U47" "R26" "D63" "R33" "U87" "L62" "D20" "R33" "U53" "R51"]
                              ["U98" "R91" "D20" "R16" "D67" "R40" "U7" "R15" "U6" "R7"]]))))

  (testing "Solution for part 1"
    (is (= 806 (d03/solve-part1))))

  (testing "Part2 test cases"
    (is (= 610 (d03/steps [["R75" "D30" "R83" "U83" "L12" "D49" "R71" "U7" "L72"]
                           ["U62" "R66" "U55" "R34" "D71" "R55" "D58" "R83"]])))
    (is (= 410 (d03/steps [["R98" "U47" "R26" "D63" "R33" "U87" "L62" "D20" "R33" "U53" "R51"]
                           ["U98" "R91" "D20" "R16" "D67" "R40" "U7" "R15" "U6" "R7"]]))))

  (testing "Solution for part 2"
    (is (= 66076 (d03/solve-part2)))))

(deftest day04
  (testing "Solution for part 1"
    (is (= 2814 (d04/solve-part1))))

  (testing "Solution for part 2"
    (is (= 1991 (d04/solve-part2)))))

(deftest day05
  (testing "Solution for part 1"
    (is (= 5074395 (d05/solve-part1))))

  (testing "Solution for part 2"
    (is (= 8346937 (d05/solve-part2)))))

(deftest day06
  (testing "Part1 test cases"
    (let [codelist (d06/input->codelist ["COM)B" "B)C" "C)D" "D)E" "E)F" "B)G" "G)H" "D)I" "E)J" "J)K" "K)L"])]
      (is (= 3 (d06/calc-orbits :D codelist)))
      (is (= 7 (d06/calc-orbits :L codelist)))
      (is (= 0 (d06/calc-orbits :COM codelist)))
      (is (= 42 (d06/calc-total-orbits codelist)))))

  (testing "Solution for part 1"
    (is (= 147223 (d06/solve-part1))))

  (testing "Part2 test cases"
    (let [codelist (d06/input->codelist ["COM)B" "B)C" "C)D" "D)E" "E)F" "B)G" "G)H" "D)I" "E)J" "J)K" "K)L" "K)YOU" "I)SAN"])]
      (is (= 4 (d06/calc-transfers codelist)))))

  (testing "Solution for part 1"
    (is (= 340 (d06/solve-part2)))))
