(ns my-website.views.work.subs
  (:require [re-frame.core :refer [reg-sub]]
            [my-website.subs :as subs]))

(reg-sub
  ::work-items-index
  :<- [::subs/state]
  (fn [state _]
    (condp = state
      'clojure 0
      'docker 1
      'android 2
      'common 3)))