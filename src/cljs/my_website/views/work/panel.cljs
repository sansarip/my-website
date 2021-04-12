(ns my-website.views.work.panel
  (:require
    [debounce]
    [my-website.components.grid :refer [grid]]
    [my-website.views.work.components.item-grid.component :refer [item-grid]]
    [my-website.views.work.components.description.component :as dc :refer [description] :rename {description anime-description}]
    [my-website.views.work.components.work-steps.component :refer [work-steps]]
    [my-website.views.work.subs :as subs]
    [my-website.views.work.events :as events]
    [my-website.views.work.styles :refer [container-class]]
    [my-website.views.work.state :refer [fsm]]
    [my-website.subs :as root-subs]
    [my-website.events :as root-events]
    [my-website.state :refer [next-state-only]]
    [re-frame.core :refer [subscribe dispatch]]
    [peanuts.core :as pn]
    [react-scroll-wheel-handler]
    [reagent.core :as r]
    [my-website.utilities :as u]))

(defn transition [& {:keys [upcoming-index duration]}]
  (debounce
    (fn [e & _]
      (let [target-parent (try (.. e -target -parentElement) (catch js/Error _ #js {}))
            description? (= (.-id target-parent) (u/fqkw->id ::dc/description))
            no-scrollbar? (<= (.-scrollHeight target-parent) (.-clientHeight target-parent))]
        ;; Don't transition on description-scroll when it has y-overflow
        (when (or (not description?) (and description? no-scrollbar?))
          (dispatch [::events/stop-anims])
          (js/setTimeout
            #(dispatch
               [::root-events/dispatch-n
                [::events/set-work-items-index upcoming-index]
                [::events/start-anims upcoming-index]])
            duration))))
    (+ duration 60)
    true))

(pn/defc work-items->step-items
  (fn [work-items duration]
    (into []
          (map-indexed
            (fn [i v]
              {:key      i
               :title    (:name v)
               :on-click (transition
                           :upcoming-index i
                           :duration duration)}))
          work-items)))

(def animation-duration 400)

(defn panel []
  (let [step-items (work-items->step-items ::subs/work-items animation-duration)]
    (fn []
      (let [work-items-count @(subscribe [::subs/work-items-count])
            work-items-index @(subscribe [::subs/work-items-index])
            next-work-items-index (cond-> work-items-index (not= work-items-index (dec work-items-count)) inc)
            prev-work-items-index (cond-> work-items-index (> work-items-index 0) dec)]
        [:> react-scroll-wheel-handler {:downHandler (transition :current-index work-items-index
                                                                 :upcoming-index next-work-items-index
                                                                 :duration animation-duration)
                                        :upHandler   (transition :current-index work-items-index
                                                                 :upcoming-index prev-work-items-index
                                                                 :duration animation-duration)
                                        :style       {:outline "none"}
                                        :class       (container-class)}
         [:div.grid
          [work-steps step-items work-items-index]
          [item-grid :selected-work-items ::subs/selected-work-items :duration (- animation-duration 50)]
          [anime-description ::subs/description animation-duration]]]))))
