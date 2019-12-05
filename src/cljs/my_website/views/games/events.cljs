(ns my-website.views.games.events
  (:require [re-frame.core :refer [reg-event-db]]
            [my-website.views.games.state :refer [start fsm]]
            [day8.re-frame.tracing :refer-macros [fn-traced]]))

(reg-event-db
  ::set-selected-game
  (fn-traced [db [_ game]]
             (assoc-in db [:games/db :selected-game] game)))

(reg-event-db
  ::init-state
  (fn [db _]
    (-> db
        (assoc :fsm fsm)
        (assoc :state start))))
