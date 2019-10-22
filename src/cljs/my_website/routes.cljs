(ns my-website.routes
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.History)
  (:require
    [secretary.core :as secretary]
    [goog.events :as gevents]
    [goog.history.EventType :as EventType]
    [re-frame.core :as re-frame]
    [my-website.views.games.events :as games-events]
    [my-website.events :as events]))


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
            (re-frame/dispatch [::events/transition-state :home]))

  (defroute about-path "/about" []
            (re-frame/dispatch [::events/transition-state :about]))

  (defroute games-path "/games" []
            (re-frame/dispatch [::games-events/set-selected-game nil])
            (re-frame/dispatch [::events/transition-state :games]))

  (defroute game-path "/games/:game" [game]
            (re-frame/dispatch [::games-events/set-selected-game (keyword game)])
            (re-frame/dispatch [::events/transition-state :games]))

  ;; --------------------
  (hook-browser-navigation!))
