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
                                   {:as              parent
                                    :title           "PEHRANS"
                                    :backgroundColor (:primary color-palette)
                                    :inverse         true}]
                                  (wrap-each-child parent
                                                   ["WORK"
                                                    "GAMES"
                                                    "SANDBOX"
                                                    "BLOG"
                                                    "ABOUT"])))))))

(defcard
  home-page
  "Home page"
  (fn []
    (sab/html (as-element
                (let [parent (fn [] [:> menuitem {:textAlign "center"
                                                  :inverse   true
                                                  :strong    true
                                                  :padding   "1em"
                                                  :fontSize  "medium"}])
                      placeholder [:div {:style {:height           "33.438em"
                                                 :width            "28.750em"
                                                 :background-color (:secondary color-palette)}}]]
                  (dark-background
                     (into [:> navbar
                            {:as      parent
                             :title   "PEHRANS"
                             :inverse true}]
                           (wrap-each-child parent
                                            ["WORK"
                                             "GAMES"
                                             "SANDBOX"
                                             "BLOG"
                                             "ABOUT"]))
                     [:> flexbox {:justify "around"
                                  :style {:padding-top "5em"}}
                      [:> flexbox {:direction "column"
                                   :justify   "center"
                                   :align     "center"}
                       [:h1 {:style {:color "white"}} "CACA BUSSY"]]
                      placeholder]))))))

