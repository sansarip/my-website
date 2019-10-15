(ns my-website.views.games.events
  (:require [re-frame.core :as re-frame]
            [day8.re-frame.tracing :refer-macros [fn-traced]]))

(re-frame/reg-event-db
  ::set-selected-game
  (fn-traced [db [_ game]]
             (assoc-in db [:games/db :selected-game] game)))
