(ns my-website.cards.components.steps
  (:require
    [devcards.core :refer-macros [defcard]]
    [sablono.core :as sab]
    [my-website.utilities :refer [dark-background]]
    [my-website.components.steps :refer [steps]]
    [reagent.core :refer [as-element with-let atom] :rename {atom ratom}]))

(with-let [state (ratom 0)]
          (defcard
            steps
            "Simple steps demonstration"
            (fn [state]
              (let [items [{:key :a} {:key :b} {:key :c}]
                    selected-key (-> items
                                     (get (mod @state (count items)))
                                     (get :key))]
                (-> [:<>
                     [:> steps {:items        items
                                :selected-key selected-key}]
                     [:button {:on-click #(swap! state inc)} "next"]]
                    as-element
                    (sab/html))))
            state))

(with-let [state (ratom 0)]
          (defcard
            inverted-steps
            "Simple inverted steps demonstration"
            (fn [state]
              (let [items [{:key :a} {:key :b} {:key :c}]
                    selected-key (-> items
                                     (get (mod @state (count items)))
                                     (get :key))]
                (-> [:<>
                     [:> steps {:items        items
                                :selected-key selected-key
                                :inverse      true}]
                     [:button {:on-click #(swap! state inc)} "next"]]
                    dark-background
                    as-element
                    (sab/html))))
            state))

(with-let [state (ratom :a)]
          (defcard
            clickable-steps
            "Clickable steps demonstration"
            (fn [state]
              (let [set-state #(reset! state %)
                    items [{:key      :a
                            :on-click #(set-state %)}
                           {:key      :b
                            :on-click #(set-state %)}
                           {:key      :c
                            :on-click #(set-state %)}]]
                (-> [:> steps {:items        items
                               :selected-key @state}]
                    as-element
                    (sab/html))))
            state))

(with-let [state (ratom 0)]
          (defcard
            clickable-steps-with-implicit-keys
            "Clickable steps demonstration with implicit keys"
            (fn [state]
              (let [set-state #(reset! state %)
                    items [{:on-click #(set-state %)}
                           {:on-click #(set-state %)}
                           {:on-click #(set-state %)}]]
                (-> [:> steps {:items        items
                               :selected-key @state}]
                    as-element
                    (sab/html))))
            state))
