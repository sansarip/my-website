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
    [my-website.components.icon :refer [icon]]
    [spade.core :refer [defclass]]
    [my-website.utilities :refer [dark-background wrap-each-child]]))

(defcard
  navbar
  "Home navbar"
  (fn []
    (sab/html (as-element (let [parent (fn [] [:> menuitem {:textAlign "center"
                                                            :inverse   true
                                                            :strong    true
                                                            :padding   "1em"
                                                            :fontSize  "medium"}])]
                            (into [:> navbar
                                   {:as              [:> icon {:name "smile-wink"
                                                               :inverse   true
                                                               :strength "strong"
                                                               :size  "big"}]
                                    :backgroundColor (:primary color-palette)
                                    :inverse         true}]
                                  (wrap-each-child parent
                                                   ["WORK"
                                                    "GAMES"
                                                    "SANDBOX"
                                                    "BLOG"
                                                    "ABOUT"])))))))
