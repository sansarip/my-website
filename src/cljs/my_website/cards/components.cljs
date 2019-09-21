(ns my-website.cards.components
  (:require [devcards.core :refer-macros (defcard)]
            [sablono.core :as sab]
            [reagent.core :refer [as-element]]
            [my-website.styles :refer [color-palette]]
            [my-website.utilities :refer [dark-background]]
            [my-website.components.summary :refer [summary]]
            [my-website.components.menuitem :refer [menuitem]]
            [my-website.components.icon :refer [icon]]
            [my-website.components.navbar :refer [navbar]]))

(defcard
  summary
  "Basic summary component"
  (fn [] (let [width "22.813em"]
           (sab/html
             (as-element
               (dark-background
                 [:> summary {:header   "Scratch My Patch"
                              :as       :h2
                              :content  "Satisfy your primal urge to scritch that infernal itch!"
                              :inverse  true
                              :width    width
                              :on-click #(js/alert "Summarize deez Nutellas!")}
                  [:div {:style {:width            "100%"
                                 :height           "18.750em"
                                 :background-color (:secondary color-palette)}}]]))))))

(defcard
  menuitem
  "Basic menu-item component"
  (fn [] (sab/html (as-element
                     (dark-background
                       [:> menuitem {:textAlign "center"
                                     :inverse   true
                                     :strong    true
                                     :on-click  #(js/alert "Basic menu item, bih!")
                                     :fontSize  "large"} "click me!"])))))

(defcard
  icon
  "Basic icon component"
  (fn [] (sab/html (as-element
                     [:> icon {:size     :big
                               :strength "strong"
                               :name     "hand-spock"}]))))

(defcard
  navbar
  "Basic navbar component"
  (fn []
    (sab/html (as-element
                (into [:> navbar
                       {:title           "logo"
                        :backgroundColor (:primary color-palette)
                        :inverse         true}]
                      [[:h2 "a"]
                       [:h2 "b"]
                       [:h2 "c"]
                       [:h2 "d"]
                       [:h2 "e"]])))))

(defcard
  icon-menuitem
  "Combination of menuitem and icon"
  (fn []
    (sab/html (as-element
                [:> menuitem
                 [:> icon {:name          "hand-pointer"
                           :inherit-color true
                           :strength      "strong"
                           :size          :huge}]]))))



