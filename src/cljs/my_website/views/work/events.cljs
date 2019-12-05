(ns my-website.views.work.events
  (:require [re-frame.core :refer [reg-event-db]]
            [my-website.views.work.state :refer [start fsm]]))

(reg-event-db
  ::init-state
  (fn [db _]
    (-> db
        (assoc :fsm fsm)
        (assoc :state start))))
