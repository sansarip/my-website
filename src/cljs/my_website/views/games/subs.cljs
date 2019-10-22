(ns my-website.views.games.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
  ::selected-game
  (fn [db]
    (-> db :games/db :selected-game)))
