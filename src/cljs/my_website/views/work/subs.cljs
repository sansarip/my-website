(ns my-website.views.work.subs
  (:require [re-frame.core :refer [reg-sub]]
            [my-website.subs :as subs]))

(defn state->work-items-key [state]
  (condp = state
    'clojure :clojure
    'docker :docker
    'android :android
    'common :common))

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
  :<- [::subs/state]
  :<- [::all-work-items]
  (fn [[state all-work-items] _]
    (get-in all-work-items [(state->work-items-key state) :items])))
