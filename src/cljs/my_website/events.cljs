(ns my-website.events
  (:require
    [re-frame.core :as rf]
    [my-website.db :as db]
    [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]
    [reitit.frontend.controllers :as rfc]
    [reitit.frontend.easy :as rfe]))


(rf/reg-event-db
  ::initialize-db
  (fn-traced [_ _]
             db/default-db))

(rf/reg-event-db
  ::set-state
  (fn-traced [db [_ state]]
             (assoc db :state state)))

(rf/reg-event-fx
  ::dispatch-n
  (fn-traced [{:keys [db]} [_ & events]]
             {:db db
              :dispatch-n events}))

(rf/reg-event-fx
  ::navigate
  (fn [_ [_ & route]]
    (let [fr (first route)]
      (if (string? fr)
        {::navigate-href! fr}
        {::navigate! route}))))

(rf/reg-event-db
  ::navigated
  (fn [db [_ new-match]]
    (let [old-match (:current-route db)
          controllers (rfc/apply-controllers (:controllers old-match) new-match)]
      (assoc db :current-route (assoc new-match :controllers controllers)))))

(rf/reg-fx
  ::navigate!
  (fn [route]
    (apply rfe/push-state route)))

(rf/reg-fx
  ::navigate-href!
  (fn [href]
    (-> js/window
        (.-location)
        (.-href)
        (set! href))))

(defn next-state [app-state keys fsm transition]
  (let [state-path (conj keys :state)
        current-state (get-in app-state state-path)
        new-state (get-in fsm [current-state transition])]
    (assoc-in app-state state-path new-state)))

(rf/reg-event-db
  ::transition-state
  (fn-traced
    [db [_ path fsm transition]]
    (next-state db path fsm transition)))

(rf/reg-event-db
  ::init-state
  (fn-traced
    [db [_ path]]
    (assoc-in db (conj path :state) 'start)))

