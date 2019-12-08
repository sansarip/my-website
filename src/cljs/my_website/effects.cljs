(ns my-website.effects
  (:require [re-frame.core :refer [reg-fx dispatch]]))

(reg-fx
  :with-delays
  (fn [events-delays]
    (doall (map #(js/setTimeout (partial dispatch (first %))
                                (second %))
                events-delays))))
