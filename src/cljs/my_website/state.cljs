(ns my-website.state
  (:require [my-website.utilities :refer [deep-merge]]))

(defonce navbar-fsm {:games [:ok 'games-panel]
                     :about [:ok 'about-panel]
                     :home  [:ok 'home-panel]})

(defonce fsm {[:ok 'home-panel]  (deep-merge
                                   navbar-fsm
                                   {:else [:ok 'home-panel]})
              [:ok 'about-panel] (deep-merge
                                   navbar-fsm
                                   {:else [:ok 'about-panel]})
              [:ok 'games-panel] (deep-merge
                                   navbar-fsm
                                   {:else [:ok 'games-panel]})})

(defn next-state [fsm app-state transition]
  (let [state (:state app-state)
        new-state (get-in fsm [state transition] (get-in fsm [state :else]))]
    (assoc app-state :state new-state)))

(defn next-state-only [fsm app-state transition]
  (let [state (:state app-state)]
    (get-in fsm [state transition] (get-in fsm [state :else]))))