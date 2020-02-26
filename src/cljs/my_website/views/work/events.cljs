(ns my-website.views.work.events
  (:require [re-frame.core :refer [reg-event-db reg-event-fx]]
            [my-website.views.work.components.item-grid.component :refer [make-work-items]]
            [my-website.views.work.state :refer [start fsm]]
            [day8.re-frame.tracing :refer-macros [fn-traced]]
            [my-website.effects]))

(reg-event-fx
  ::init
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


(reg-event-db
  ::init-work-items
  (fn [db [_ rows columns]]
    (let [size (* rows columns)]
      (update-in db
                 [:work/db :work-items]
                 #(reduce-kv
                    (fn [c k {:keys [names shelves]}]
                      (assoc-in c
                                [k :items]
                                (make-work-items :names names
                                                 :shelves shelves
                                                 :size size)))
                    %
                    %)))))

(reg-event-db
  ::start-anim
  (fn-traced [db [_ index i]]
             (-> db
                 (update-in [:work/db :work-items index :description] #(-> %
                                                                           (assoc :start true)
                                                                           (assoc :stop false)))
                 (update-in [:work/db :work-items index :items i] #(-> %
                                                                       (assoc :start true)
                                                                       (assoc :stop false))))))

(reg-event-fx
  ::start-anims
  (fn [{:keys [db]} [_ index]]
    (let [work-items (get-in db [:work/db :work-items index :items])
          events-delays (reduce-kv (fn [c k v]
                                     (conj c [[::start-anim index k] (:delay v)]))
                                   []
                                   work-items)]
      {:db          db
       :with-delays events-delays})))

(reg-event-db
  ::stop-anims
  (fn-traced [db [_ index]]
             (-> db
                 (assoc-in [:work/db :work-items index :description :stop] true)
                 (update-in [:work/db :work-items index :items]
                            #(reduce (fn [c v]
                                       (conj c (assoc v :stop true)))
                                     []
                                     %)))))
