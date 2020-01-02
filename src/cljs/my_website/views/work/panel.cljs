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

(defn transition-state [transition]
  (if transition
    (let [next-work-item-key (subs/state->work-items-key
                               (next-state-only
                                 fsm
                                 {:state @(subscribe [::root-subs/state])}
                                 transition))]
      (dispatch [::root-events/transition-state transition])
      next-work-item-key)))

(defn set-state [state]
  (when state
    (dispatch [::root-events/set-state (subs/work-items-key->state state)])
    state))

(def transition (fn [work-items-key & {:keys [state transition duration]}]
                  (debounce #(do
                               (dispatch [::events/stop-anims work-items-key])
                               (js/setTimeout (fn []
                                                (let [k (or (set-state state) (transition-state transition))]
                                                  (dispatch [::events/start-anims k])))
                                              duration))
                            (+ duration 60)
                            true)))

(defn work-items->steps-items [all-work-items work-items-key duration]
  (reduce-kv (fn [c k _v]
               (conj c {:key      k
                        :title    k
                        :on-click (transition work-items-key
                                              :state k
                                              :duration duration)}))
             []
             all-work-items))

(defn work-panel []
  (let [all-work-items @(subscribe [::subs/all-work-items])
        work-items-key @(subscribe [::subs/work-items-key])
        work-items @(subscribe [::subs/work-items])
        description @(subscribe [::subs/description])
        duration 400]
    (r/with-let [_ (dispatch [::events/start-anims work-items-key])]
                [:> react-scroll-wheel-handler {:downHandler (transition work-items-key
                                                                         :transition :next
                                                                         :duration duration)
                                                :upHandler   (transition work-items-key
                                                                         :transition :prev
                                                                         :duration duration)
                                                :style       {:outline "none"}}
                 [:> grid {:grid-template-columns ".05fr 1fr .05fr"
                           :grid-template-rows    "50vh 1fr"
                           :grid-template-areas   [[:steps :work-items :.]
                                                   [:. :description :.]]
                           :style                 {:height "80vh"}
                           :grid-row-gap          "1em"}
                  [work-steps (work-items->steps-items all-work-items work-items-key duration) work-items-key]
                  [item-grid :work-items work-items :duration (- duration 50)]
                  [anime-description description duration]]])))
