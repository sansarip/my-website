(ns my-website.state)

(defonce fsm {[:ok 'home-panel]  {:games [:ok 'games-panel]
                                  :about [:ok 'about-panel]
                                  :home  [:ok 'home-panel]
                                  :else  [:ok 'home-panel]}
              [:ok 'about-panel] {:games [:ok 'games-panel]
                                  :about [:ok 'about-panel]
                                  :home  [:ok 'home-panel]
                                  :else  [:ok 'about-panel]}
              [:ok 'games-panel] {:games [:ok 'games-panel]
                                  :about [:ok 'about-panel]
                                  :home  [:ok 'home-panel]
                                  :else  [:ok 'games-panel]}})

(defn next-state [fsm app-state transition]
  (let [state (:state app-state)
        new-state (get-in fsm [state transition] (get-in fsm [state :else]))]
    (assoc app-state :state new-state)))

(defn next-state-only [fsm app-state transition]
  (let [state (:state app-state)]
    (get-in fsm [state transition] (get-in fsm [state :else]))))