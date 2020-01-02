(ns my-website.views.work.subs
  (:require
    [my-website.subs :as subs]
    [my-website.views.work.content :refer [descriptions]]
    [re-frame.core :refer [reg-sub]]))

(defn state->work-items-key [state]
  (condp = state
    'fp :fp
    'containers :containers
    'android :android
    'common :common))

(defn work-items-key->state [work-items-key]
  (condp = work-items-key
    :fp 'fp
    :containers 'containers
    :android 'android
    :common 'common))

(reg-sub
  ::all-work-items
  (fn [db _]
    (-> db :work/db :all-work-items)))

(reg-sub
  ::work-items-key
  :<- [::subs/state]
  (fn [state _]
    (state->work-items-key state)))

(reg-sub
  ::work-items
  :<- [::work-items-key]
  :<- [::all-work-items]
  (fn [[work-items-key all-work-items] _]
    (get-in all-work-items [work-items-key :items])))

(reg-sub
  ::description
  :<- [::work-items-key]
  :<- [::all-work-items]
  (fn [[work-items-key all-work-items] _]
    (-> all-work-items
        (get-in [work-items-key :description])
        (merge {:content (get descriptions work-items-key)}))))
