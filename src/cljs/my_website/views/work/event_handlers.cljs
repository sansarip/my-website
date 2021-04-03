(ns my-website.views.work.event-handlers
  (:require
    [my-website.views.work.components.item-grid.component :as igc]))

(defn start-anim [db index i]
  (-> db
      (update-in [:work/db :work-items index :description] #(assoc % :start true :stop false))
      (update-in [:work/db :work-items index :items i] #(assoc % :start true :stop false))))

(defn start-anims-fx
  ([{db :db} index]
   (let [work-items (get-in db [:work/db :work-items index :items])
         events-delays (reduce-kv
                         (fn [c k v]
                           (conj c [[:start-anim index k] (:delay v)]))
                         []
                         work-items)]
     {:db          db
      :with-delays events-delays}))
  ([{db :db :as cofx}] (start-anims-fx cofx (-> db :work/db :work-items-index))))

(defn init-work-items [db size]
  (update-in
    db
    [:work/db :work-items]
    (partial
      mapv
      #(assoc
         %
         :items
         (igc/make-work-items
           :names (:names %)
           :shelves (:shelves %)
           :size size)))))
