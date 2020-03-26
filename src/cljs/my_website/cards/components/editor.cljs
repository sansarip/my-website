(ns my-website.cards.components.editor
  (:require [devcards.core :refer-macros [defcard-rg]]
            [my-website.components.editor :refer [editor result-view]]
            [my-website.utilities :refer [eval-str]]
            [reagent.core :refer [as-element with-let atom] :rename {atom ratom}]))

(let [v "(+ 1 1)"]
  (defcard-rg editor
              "Simple demonstration of Clojure code editor"
              (fn [state]
                [:div
                 (with-let [s [:> (editor (:input @state) v)
                               {:on-change #(do
                                              (swap! state assoc :output (eval-str %))
                                              (swap! state assoc :key (str (random-uuid))))}]]
                           s)
                 [:div
                  ^{:key (:key @state)}
                  [result-view (:value (:output @state))]]])
              (ratom {:input  (atom nil)
                      :output (eval-str v)
                      :key    (str (random-uuid))})))
