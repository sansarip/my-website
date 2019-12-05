(ns my-website.subs
  (:require
   [re-frame.core :refer [reg-sub subscribe]]
   [my-website.views.work.state :as work-state]
   [my-website.views.games.state :as games-state]
   [my-website.views.about.state :as about-state]
   [my-website.views.home.state :as home-state]))

(reg-sub
 ::name
 (fn [db]
   (:name db)))

(reg-sub
 ::state
 (fn [db _]
   (:state db)))

(reg-sub
  ::fsm
  (fn [db _]
    (:fsm db)))

(reg-sub
  ::active-panel
  :<- [::fsm]
  (fn [fsm _]
    (condp = fsm
      work-state/fsm 'work-panel
      games-state/fsm 'games-panel
      about-state/fsm 'about-panel
      home-state/fsm 'home-panel
      nil)))

