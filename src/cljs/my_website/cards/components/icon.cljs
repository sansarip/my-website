(ns my-website.cards.components.icon
  (:require
    [devcards.core :refer-macros [defcard]]
    [sablono.core :as sab]
    [my-website.components.icon :refer [icon]]
    [reagent.core :refer [as-element]]))

(defcard
  icon
  "Basic icon component"
  (fn [] (sab/html (as-element
                     [:> icon {:size     :big
                               :strength "strong"
                               :name     "hand-spock"}]))))
