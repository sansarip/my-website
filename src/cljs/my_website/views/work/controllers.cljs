(ns my-website.views.work.controllers
  (:require [re-frame.core :as rf]
            [my-website.views.work.events :as events]))

(def work-controller [{:start (fn [_] (rf/dispatch [::events/initialize]))}])
