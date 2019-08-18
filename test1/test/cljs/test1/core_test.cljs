(ns test1.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [test1.core :as core]))

(deftest fake-test
  (testing "fake description"
    (is (= 1 2))))
