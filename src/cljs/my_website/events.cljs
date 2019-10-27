(ns my-website.events
  (:require
    [re-frame.core :as re-frame]
    [my-website.db :as db]
    [my-website.state :refer [next-state]]
    [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]))


(re-frame/reg-event-db
  ::initialize-db
  (fn-traced [_ _]
             db/default-db))

(re-frame/reg-event-db
  ::set-state
  (fn-traced [db [_ state]]
             (assoc db :state state)))

(re-frame/reg-event-db
  ::transition-state
  (fn-traced [db [_ fsm transition]]
             (next-state fsm db transition)))
