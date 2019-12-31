(ns my-website.cards.components.menuitem
  (:require
    [devcards.core :refer-macros [defcard]]
    [sablono.core :as sab]
    [my-website.utilities :refer [dark-background]]
    [my-website.components.menuitem :refer [menuitem]]
    [my-website.components.icon :refer [icon]]
    [reagent.core :refer [as-element]]))

(defcard
  menuitem
  "Basic menu-item component"
  (fn [] (sab/html (as-element
                     (dark-background
                       [:> menuitem {:text-align "center"
                                     :inverse    true
                                     :strong     true
                                     :on-click   #(js/alert "Basic menu item, bih!")
                                     :font-size  "large"} "click me!"])))))

(defcard
  icon-menuitem
  "Combination of menuitem and icon"
  (fn []
    (sab/html (as-element
                [:> menuitem
                 [:> icon {:icon-name     "hand-pointer"
                           :inherit-color true
                           :strength      "strong"
                           :size          :huge}]]))))
