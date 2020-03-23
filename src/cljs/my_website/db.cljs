(ns my-website.db
  (:require [my-website.views.work.defaults :as work-df]))

(def default-db
  {:name     "setootle"
   :state    'start
   :games/db {:state 'start
              :selected-game nil}
   :about/db {:state 'start}
   :home/db  {:state 'start}
   :work/db  {:state 'start
              :work-items work-df/default-work-items
              :work-items-index 0}})
