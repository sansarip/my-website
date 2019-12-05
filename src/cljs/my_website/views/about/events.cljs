(ns my-website.views.about.events
  (:require [re-frame.core :refer [reg-event-db]]
            [my-website.views.about.state :refer [start fsm]]))

(reg-event-db
  ::init-state
  (fn [db _]
    (-> db
        (assoc :fsm fsm)
        (assoc :state start))))
