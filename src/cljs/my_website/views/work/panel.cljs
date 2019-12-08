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
            [react-scroll-wheel-handler]))

(defn transition-state []
  (let [next-work-item-key (subs/state->work-items-key
                             (next-state-only
                               fsm
                               {:state @(subscribe [::root-subs/state])}
                               :next))]
    (dispatch [::root-events/transition-state :next])
    next-work-item-key))

(defn transition [work-items-key]
  (do
    (dispatch [::events/stop-anims work-items-key])
    (js/setTimeout (fn []
                     (let [k (transition-state)]
                       (dispatch [::events/start-anims k])))
                   400)))

(defn work-panel []
  (let [work-items-key @(subscribe [::subs/work-items-key])
        work-items @(subscribe [::subs/work-items])]
    [:> react-scroll-wheel-handler {:downHandler #(transition work-items-key)}
     [:> grid {:columns "1fr"
               :rows    "2fr 1fr"
               :style   {:height "80vh"}
               :rowGap  "1em"}
      (make-item-grid :work-items work-items)
      [:div {:style {:background-color "green"}}]]]))
