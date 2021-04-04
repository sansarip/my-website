(ns my-website.views.work.events
  (:require [re-frame.core :refer [reg-event-db reg-event-fx]]
            [my-website.views.work.state :refer [start fsm]]
            [day8.re-frame.tracing :refer-macros [fn-traced]]
            [my-website.views.work.event-handlers :as event-handlers]
            [my-website.effects]))

(reg-event-fx
  ::initialize
  (fn-traced [{:keys [db]} _]
    {:db         db
     :dispatch-n [[::init-work-items]]}))

(reg-event-db
  ::init-state
  (fn-traced [db _]
    (-> db
        (assoc :fsm fsm)
        (assoc :state start))))

(reg-event-db
  ::set-work-items-index
  (fn-traced [db [_ i]]
    (assoc-in db [:work/db :work-items-index] i)))

(reg-event-fx
  ::init-work-items
  (fn [{db :db} _]
    (-> {:db db}
        (update :db event-handlers/init-work-items)
        event-handlers/start-anims-fx)))

(reg-event-db
  :start-anim
  (fn-traced [db [_ index i]]
    (event-handlers/start-anim db index i)))

(reg-event-fx
  ::start-anims
  (fn [cofx [_ index]]
    (event-handlers/start-anims-fx cofx index)))

(reg-event-db
  ::stop-anims
  (fn-traced [db [_ index]]
    (let [index (or index (-> db :work/db :work-items-index))]
      (-> db
          (assoc-in [:work/db :work-items index :description :stop] true)
          (update-in [:work/db :work-items index :items]
                     #(reduce (fn [c v]
                                (conj c (assoc v :stop true)))
                              []
                              %))))))
