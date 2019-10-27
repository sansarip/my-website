(ns my-website.state)

(defn next-state [fsm app-state transition]
  (let [state (:state app-state)
        new-state (get-in fsm [state transition] (get-in fsm [state :else]))]
    (assoc app-state :state new-state)))

(defn next-state-only [fsm app-state transition]
  (let [state (:state app-state)]
    (get-in fsm [state transition] (get-in fsm [state :else]))))