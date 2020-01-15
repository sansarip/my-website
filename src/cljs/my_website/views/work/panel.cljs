(ns my-website.views.work.panel
  (:require
    [debounce]
    [my-website.components.grid :refer [grid]]
    [my-website.views.work.components.item-grid.component :refer [item-grid]]
    [my-website.views.work.components.description.component :refer [description] :rename {description anime-description}]
    [my-website.views.work.components.work-steps.component :refer [work-steps]]
    [my-website.views.work.subs :as subs]
    [my-website.views.work.events :as events]
    [my-website.views.work.state :refer [fsm]]
    [my-website.subs :as root-subs]
    [my-website.events :as root-events]
    [my-website.state :refer [next-state-only]]
    [re-frame.core :refer [subscribe dispatch]]
    [react-scroll-wheel-handler]
    [reagent.core :as r]))

(def transition (fn [& {:keys [current-index upcoming-index duration]}]
                  (debounce #(do
                               (dispatch [::events/stop-anims current-index])
                               (js/setTimeout (fn [] (dispatch
                                                       [::root-events/dispatch-n
                                                        [::events/set-work-items-index upcoming-index]
                                                        [::events/start-anims upcoming-index]]))
                                              duration))
                            (+ duration 60)
                            true)))

(defn work-items->steps-items [work-items work-items-index duration]
  (reduce-kv (fn [c k v]
               (conj c {:key      k
                        :title    (:name v)
                        :on-click (transition :current-index work-items-index
                                              :upcoming-index k
                                              :duration duration)}))
             []
             work-items))

(defn work-panel []
  (let [work-items @(subscribe [::subs/work-items])
        num-work-items (count work-items)
        work-items-index @(subscribe [::subs/work-items-index])
        next-work-items-index (if (= work-items-index (dec num-work-items)) work-items-index (inc work-items-index))
        prev-work-items-index (if (zero? work-items-index) work-items-index (dec work-items-index))
        selected-work-items @(subscribe [::subs/selected-work-items])
        description @(subscribe [::subs/description])
        duration 400]
    (r/with-let [_ (dispatch [::events/start-anims work-items-index])]
                [:> react-scroll-wheel-handler {:downHandler (transition :current-index work-items-index
                                                                         :upcoming-index next-work-items-index
                                                                         :duration duration)
                                                :upHandler   (transition :current-index work-items-index
                                                                         :upcoming-index prev-work-items-index
                                                                         :duration duration)
                                                :style       {:outline "none"}}
                 [:> grid {:grid-template-columns ".05fr 1fr .05fr"
                           :grid-template-rows    "50vh 1fr"
                           :grid-template-areas   [[:steps :work-items :.]
                                                   [:. :description :.]]
                           :style                 {:height "80vh"}
                           :grid-row-gap          "1em"}
                  [work-steps (work-items->steps-items work-items work-items-index duration) work-items-index]
                  [item-grid :selected-work-items selected-work-items :duration (- duration 50)]
                  [anime-description description duration]]])))
