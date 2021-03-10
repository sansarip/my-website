(ns my-website.effects
  (:require
    [re-frame.core :as rf]
    [reitit.frontend.easy :as rfe]))

(rf/reg-fx
  :with-delays
  (fn [events-delays]
    (doall (map #(js/setTimeout (partial rf/dispatch (first %))
                                (second %))
                events-delays))))

(rf/reg-fx
  ::navigate!
  (fn [route]
    (apply rfe/push-state route)))

(rf/reg-fx
  ::navigate-href!
  (fn [href]
    (-> js/window
        (.-location)
        (.-href)
        (set! href))))

