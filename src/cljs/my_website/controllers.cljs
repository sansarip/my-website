(ns my-website.controllers
  (:require [my-website.views.games.controller :as games]
            [my-website.views.work.controller :as work]))

(def game-controller games/game-controller)
(def games-controller games/games-controller)
(def work-controller work/work-controller)
