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
            "Clickable steps demonstration with hover text"
            (fn [state]
              (let [set-state #(reset! state %)
                    items [{:key      :a
                            :title    :A
                            :on-click #(set-state %)}
                           {:key      :b
                            :title    :B
                            :on-click #(set-state %)}
                           {:key      :c
                            :title    :C
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
                    items [{:on-click #(set-state %)
                            :title    0}
                           {:on-click #(set-state %)
                            :title    1}
                           {:on-click #(set-state %)
                            :title    2}]]
                (-> [:> steps {:items        items
                               :inverse      true
                               :selected-key @state}]
                    dark-background
                    as-element
                    (sab/html))))
            state))
