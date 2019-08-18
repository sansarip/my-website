(ns test1.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [test1.core-test]))

(doo-tests 'test1.core-test)
