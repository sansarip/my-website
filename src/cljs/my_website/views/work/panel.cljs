(ns my-website.views.work.panel
  (:require [my-website.components.grid :refer [grid]]
            [my-website.views.work.components.item-grid.component :refer [make-item-grid]]
            [my-website.views.work.subs :as subs]
            [my-website.views.work.events :as events]
            [my-website.views.work.state :refer [fsm]]
            [my-website.subs :as root-subs]
            [my-website.events :as root-events]
            [my-website.state :refer [next-state-only]]
            [re-frame.core :refer [subscribe dispatch]]
            [react-scroll-wheel-handler]
            [reagent.core :as r]))

(defn transition-state [direction]
  (let [next-work-item-key (subs/state->work-items-key
                             (next-state-only
                               fsm
                               {:state @(subscribe [::root-subs/state])}
                               direction))]
    (dispatch [::root-events/transition-state direction])
    next-work-item-key))

(defn transition [work-items-key direction]
  (do
    (dispatch [::events/stop-anims work-items-key])
    (js/setTimeout (fn []
                     (let [k (transition-state direction)]
                       (dispatch [::events/start-anims k])))
                   400)))

(defn work-panel []
  (let [work-items-key @(subscribe [::subs/work-items-key])
        work-items @(subscribe [::subs/work-items])]
    (r/with-let [_ (dispatch [::events/start-anims work-items-key])]
                [:> react-scroll-wheel-handler {:downHandler #(transition work-items-key :next)
                                                :upHandler   #(transition work-items-key :prev)}
                 [:> grid {:columns "1fr"
                           :rows    "2fr 1fr"
                           :style   {:height "80vh"}
                           :rowGap  "1em"}
                  (make-item-grid :work-items work-items)
                  [:div {:style {:background-color "green"}}]]])))
