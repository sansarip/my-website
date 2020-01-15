(ns my-website.views.work.subs
  (:require
    [my-website.subs :as subs]
    [my-website.views.work.content :refer [descriptions]]
    [re-frame.core :refer [reg-sub]]))

(reg-sub
  ::work-items
  (fn [db _]
    (-> db
        :work/db
        :work-items)))

(reg-sub
  ::work-items-index
  (fn [db _]
    (-> db
        :work/db
        :work-items-index)))

(reg-sub
  ::selected-work-items
  :<- [::work-items-index]
  :<- [::work-items]
  (fn [[work-items-index work-items] _]
    (get-in work-items [work-items-index :items])))

(reg-sub
  ::description
  :<- [::work-items]
  :<- [::work-items-index]
  (fn [[work-items work-items-index] _]
    (let [selected-work-item-props (get work-items work-items-index)]
      (merge (:description selected-work-item-props)
             {:content (get descriptions (:name selected-work-item-props))}))))
