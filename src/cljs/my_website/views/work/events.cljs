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
              :dispatch-n (list [::init-state
                                 ::init-work-items])}))

(reg-event-db
  ::init-state
  (fn-traced [db _]
             (-> db
                 (assoc :fsm fsm)
                 (assoc :state start))))

(reg-event-db
  ::init-work-items
  (fn [db [_ names shelves rows columns]]
    (let [size (* rows columns)]
      (update-in db
                 [:work/db :all-work-items]
                 #(reduce-kv
                    (fn [c k {:keys [names shelves]}]
                      (assoc c k (make-work-items :names names
                                                  :shelves shelves
                                                  :size size)))
                    %
                    %)))))

(reg-event-db
  ::start-anim
  (fn-traced [db [_ k index]]
             (update-in db [:work/db :all-work-items k index] #(-> %
                                                                   (assoc :start true)
                                                                   (assoc :stop false)))))

(reg-event-fx
  ::start-anims
  (fn [{:keys [db]} [_ k]]
    (let [work-items (-> db :work/db :all-work-items k)
          events-delays (reduce-kv (fn [c i v]
                                     (conj c [[::start-anim k i] (:delay v)]))
                                   []
                                   work-items)]
      {:db          db
       :with-delays events-delays})))


(reg-event-db
  ::stop-anims
  (fn-traced [db [_ k]]
             (update-in db [:work/db :all-work-items k]
                        #(reduce (fn [c v]
                                   (conj c (assoc v :stop true)))
                                 []
                                 %))))
