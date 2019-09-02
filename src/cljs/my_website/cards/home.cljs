(ns my-website.cards.home
  (:require
    [reagent.core :refer [as-element]]
    [devcards.core :refer-macros (defcard)]
    [sablono.core :as sab]
    [reagent.core :refer [as-element]]
    [my-website.styles :refer [color-palette]]
    [my-website.components.flexbox :refer [flexbox]]
    [my-website.components.navbar :refer [navbar]]
    [my-website.components.menuitem :refer [menuitem]]
    [my-website.utilities :refer [dark-background wrap-each-child]]))

(defcard
  navbar
  "Home navbar"
  (fn []
    (sab/html (as-element (dark-background (let [parent (fn [] [:> menuitem {:textAlign "center"
                                                                             :inverse   true
                                                                             :strong    true
                                                                             :fontSize  "medium"}])]
                                             (into [:> navbar
                                                    {:title   "PEHRANS"
                                                     :as      [:> menuitem {:textAlign "center"
                                                                            :inverse   true
                                                                            :strong    true
                                                                            :fontSize  "medium"}]
                                                     :inverse true}]
                                                   (wrap-each-child parent
                                                                    ["WORK"
                                                                     "GAMES"
                                                                     "SANDBOX"
                                                                     "BLOG"
                                                                     "ABOUT"]))))))))

