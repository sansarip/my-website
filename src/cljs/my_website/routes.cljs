(ns my-website.routes
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.History)
  (:require
    [secretary.core :as secretary]
    [goog.events :as gevents]
    [goog.history.EventType :as EventType]
    [re-frame.core :as re-frame]
    [my-website.utilities :refer [scroll-to-top]]
    [my-website.events :as events]
    [my-website.views.work.events :as work-events]
    [my-website.views.about.events :as about-events]
    [my-website.views.games.events :as games-events]
    [my-website.views.home.events :as home-events]))


(defn hook-browser-navigation! []
  (doto (History.)
    (gevents/listen
      EventType/NAVIGATE
      (fn [event]
        (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

(defn app-routes []
  (secretary/set-config! :prefix "#")
  ;; --------------------

  ;; define routes here
  (defroute home-path "/" []
            (re-frame/dispatch [::home-events/init-state])
            (scroll-to-top))

  (defroute about-path "/about" []
            (re-frame/dispatch [::about-events/init-state])
            (scroll-to-top))

  (defroute games-path "/games" []
            (re-frame/dispatch [::games-events/set-selected-game nil])
            (re-frame/dispatch [::games-events/init-state])
            (scroll-to-top))

  (defroute game-path "/games/:game" [game]
            (re-frame/dispatch [::games-events/set-selected-game (keyword game)])
            (re-frame/dispatch [::games-events/init-state])
            (scroll-to-top))

  (defroute work-path "/work" []
            (re-frame/dispatch [::work-events/init])
            (scroll-to-top))

  ;; --------------------
  (hook-browser-navigation!))
