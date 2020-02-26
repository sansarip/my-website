(ns my-website.routes
  (:require
    [re-frame.core :as rfr]
    [my-website.utilities :refer [scroll-to-top]]
    [my-website.controllers :as controllers]
    [reitit.frontend.easy :as rfe]
    [reitit.coercion.spec :as rss]
    [my-website.events :as events]
    [reitit.frontend :as rf]
    [my-website.views :as views]))

;(defn app-routes []
;  ;; define routes here
;  (defroute home-path "/" []
;            (rfr/dispatch [::home-events/init-state])
;            (scroll-to-top))
;
;  (defroute about-path "/about" []
;            (rfr/dispatch [::about-events/init-state])
;            (scroll-to-top))
;
;  (defroute games-path "/games" []
;            (rfr/dispatch [::games-events/set-selected-game nil])
;            (rfr/dispatch [::games-events/init-state])
;            (scroll-to-top))
;
;  (defroute game-path "/games/:game" [game]
;            (rfr/dispatch [::games-events/set-selected-game (keyword game)])
;            (rfr/dispatch [::games-events/init-state])
;            (scroll-to-top))
;
;  (defroute work-path "/work" []
;            (rfr/dispatch [::work-events/init])
;            (scroll-to-top))
;
;  ;; --------------------
;  (hook-browser-navigation!))

(def routes
  ["/"
   [""
    {:name      :home
     :view      views/home-panel
     :link-text "Home"}]
   ["about"
    {:name      :about
     :view      views/about-panel
     :link-text "About"}]
   ["games"
    [""
     {:name        :games
      :view        views/games-panel
      :link-text   "Games"
      :controllers controllers/game-controller}]
    ["/:game"
     {:name        :game
      :view        views/games-panel
      :link-text   "Games"
      :controllers controllers/games-controller}]]
   ["work"
    {:name      :work
     :view      views/work-panel
     :link-text "Work"
     :controllers controllers/work-controller}]])

(defn on-navigate [new-match]
  (when new-match
    (rfr/dispatch [::events/navigated new-match])))

(def router
  (rf/router
    routes
    {:data {:coercion rss/coercion}}))

(defn init-routes! []
  (js/console.log "initializing routes")
  (rfe/start!
    router
    on-navigate
    {:use-fragment true}))
