(ns my-website.cards.components.grid
  (:require [devcards.core :refer-macros [defcard]]
            [sablono.core :as sab]
            [my-website.styles :refer [color-palette]]
            [my-website.components.grid :refer [grid]]
            [reagent.core :refer [as-element]]))

(defcard
  grid
  "Basic grid component"
  (fn []
    (let [header1 [:div {:style {:width            "100%"
                                 :height           "1em"
                                 :background-color (:primary color-palette)
                                 :grid-area        "header1"}}]
          header2 [:div {:style {:width            "100%"
                                 :height           "1em"
                                 :background-color (:secondary color-palette)
                                 :grid-area        "header2"}}]
          main [:p {:style {:grid-area "main"}}
                "Lorem ipsum dolor amet etsy VHS health goth iPhone.
                Banjo williamsburg vinyl edison bulb typewriter,
                lo-fi bushwick biodiesel air plant direct trade.
                Pickled flannel pitchfork blue bottle letterpress tbh
                glossier ethical salvia literally yr vinyl offal.
                Slow-carb blog fashion axe, drinking vinegar organic
                austin edison bulb VHS bushwick artisan helvetica.
                Letterpress readymade pork belly distillery, vape pabst tilde.
                Austin disrupt enamel pin pour-over."]]
      (sab/html (as-element [:> grid {:columns   ["1fr 1fr 1fr"]
                                      :rows      "auto"
                                      :rowGap    "1em"
                                      :columnGap "1em"
                                      :areas     [["header1" "header2" "header2"]
                                                  ["main" "main" "main"]]}
                             header1 header2 main])))))

