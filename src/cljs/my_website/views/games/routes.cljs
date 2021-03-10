(ns my-website.views.games.routes
  (:require [my-website.views.games.controllers :as controllers]
            [my-website.views.games.panel :refer [panel]]))

(def routes
  ["games"
   [""
    {:name        :games
     :view        panel
     :link-text   "Games"
     :controllers controllers/game-controller}]
   ["/:game"
    {:name        :game
     :view        panel
     :link-text   "Games"
     :controllers controllers/games-controller}]])
