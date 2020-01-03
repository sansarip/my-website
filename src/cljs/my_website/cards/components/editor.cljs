(ns my-website.cards.components.editor
  (:require [devcards.core :refer-macros [defcard]]
            [sablono.core :as sab]
            [my-website.components.editor :refer [editor result-view]]
            [my-website.utilities :refer [eval-str]]
            [reagent.core :refer [as-element with-let atom] :rename {atom ratom}]))

(let [input (ratom nil)
      output (ratom nil)]
  (defcard editor
           "Simple demonstration of Clojure code editor"
           (fn []
             (-> [:div
                  [editor input]
                  [:div
                   [:button
                    {:on-click #(reset! output (eval-str @input))}
                    "run"]]
                  [:div
                   [result-view output]]]
                 as-element
                 (sab/html)))))
