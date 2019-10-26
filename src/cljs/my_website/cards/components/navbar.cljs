(ns my-website.cards.components.navbar
  (:require
    [devcards.core :refer-macros [defcard]]
    [sablono.core :as sab]
    [my-website.styles :refer [color-palette]]
    [my-website.components.navbar :refer [navbar]]
    [reagent.core :refer [as-element]]))

(defcard
  navbar
  "Basic navbar component"
  (fn []
    (sab/html (as-element
                [:> navbar
                 {:title           "logo"
                  :backgroundColor (:primary color-palette)
                  :inverse         true}
                 [:h2 "a"]
                 [:h2 "b"]
                 [:h2 "c"]
                 [:h2 "d"]
                 [:h2 "e"]]))))
