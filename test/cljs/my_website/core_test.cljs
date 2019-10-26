(ns my-website.core-test
  (:require [cljs.test :refer-macros [deftest is testing run-tests]]))

(deftest fake-test
  (testing "fake description"
    (is (= 1 1))))

(enable-console-print!)

(cljs.test/run-tests)
