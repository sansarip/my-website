(ns my-website.views.work.components.item-grid.component-test
  (:require [clojure.test :refer-macros [testing run-tests]]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [my-website.views.work.components.item-grid.component :refer [make-grid-areas]]
            [clojure.string :as string]))

; Number of rows equals given rows
(defspec test-grid-areas-row-count 100
         (prop/for-all [n (gen/vector gen/nat 3)]
                       (let [[rows] n
                             grid-areas (apply make-grid-areas n)]
                         (= rows (count grid-areas)))))


; Number of columns equals given (even) columns
(defspec test-grid-areas-column-count 100
         (prop/for-all [n (->> (gen/vector gen/nat 3)
                               (gen/fmap #(let [[_ column _] %
                                                even-column (* (inc column) 2)]
                                            (assoc % 1 even-column))))]
                       (let [[_ columns center-width] n
                             grid-areas (apply make-grid-areas n)]
                         (every? #(= columns (js/Math.abs (- (count %) center-width)))
                                 grid-areas))))

; Center elements contain center strings
(defspec test-grid-areas-center-element-strings 100
         (prop/for-all [n (->> (gen/vector gen/nat 3)
                               (gen/fmap #(let [[rows column column-width] %
                                                even-column (* (inc column) 2)]
                                            (-> %
                                                (assoc 2 (inc column-width))
                                                (assoc 1 even-column)
                                                (assoc 0 (inc rows))))))]
                       (let [[_ columns center-width] n
                             grid-areas (apply make-grid-areas n)
                             expected (vec (map #(str "center") (range center-width)))
                             mid-point (/ columns 2)]
                         (every? #(= expected
                                     (subvec % mid-point (+ mid-point center-width)))
                                 grid-areas))))

; Items are sorted
(defspec test-grid-areas-items-sorted 100
         (prop/for-all [n (->> (gen/vector gen/nat 3)
                               (gen/fmap #(let [[rows column column-width] %
                                                even-column (* (inc column) 2)]
                                            (-> %
                                                (assoc 2 (inc column-width))
                                                (assoc 1 even-column)
                                                (assoc 0 (inc rows))))))]
                       (let [grid-areas (apply make-grid-areas n)
                             item-numbers (flatten (map #(reduce (fn [c v]
                                                                   (if (not= v "center")
                                                                     (conj c (-> v
                                                                                 (string/split "item")
                                                                                 second
                                                                                 js/parseInt))
                                                                     c))
                                                                 [] %)
                                                        grid-areas))]
                         (apply < item-numbers))))

(enable-console-print!)

(run-tests)
