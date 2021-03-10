(ns my-website.views.games.controllers
  (:require [re-frame.core :as rf]
            [my-website.views.games.events :as events]))

(def games-controller [{:parameters {:path [:game]}
                        :start      (fn [{{:keys [game]} :path}]
                                      (rf/dispatch [::events/init (keyword game)]))}])

(def game-controller [{:start (fn [_] (rf/dispatch [::events/init]))}])
