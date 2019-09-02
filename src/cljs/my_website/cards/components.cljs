(ns my-website.cards.components
  (:require [devcards.core :refer-macros (defcard)]
            [sablono.core :as sab]
            [reagent.core :refer [as-element]]
            [my-website.styles :refer [color-palette]]
            [my-website.utilities :refer [dark-background]]
            [my-website.components.summary :refer [summary]]
            [my-website.components.menuitem :refer [menuitem]]))

(defcard
  summary
  "Basic summary component"
  (fn [] (sab/html (as-element
                     (dark-background
                       [:> summary {:header  "Scratch My Patch"
                                    :as      :h2
                                    :content "Satisfy your primal urge to scritch that infernal itch!"
                                    :inverse true}
                        [:div {:style {:width            "22.813em"
                                       :height           "18.750em"
                                       :background-color (:secondary color-palette)}}]])))))

(defcard
  menuitem
  "Basic menu-item component"
  (fn [] (sab/html (as-element
                     (dark-background
                       [:> menuitem {:textAlign "center"
                                     :inverse   true
                                     :strong    true
                                     :fontSize  "large"} "click me!"])))))


