(ns my-website.views.games.events
  (:require [re-frame.core :refer [reg-event-db reg-event-fx]]
            [my-website.views.games.state :refer [start fsm]]
            [day8.re-frame.tracing :refer-macros [fn-traced]]))

(reg-event-fx
  ::init
  (fn-traced
    [{db :db} [_ game]]
    {:db db
     :dispatch-n [[::set-selected-game game]]}))

(reg-event-db
  ::set-selected-game
  (fn-traced [db [_ game]]
             (assoc-in db [:games/db :selected-game] game)))

