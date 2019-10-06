(ns my-website.cards.components
  (:require [devcards.core :refer-macros (defcard)]
            [sablono.core :as sab]
            [reagent.core :refer [as-element]]
            [spade.core :refer [defattrs]]
            [my-website.styles :refer [color-palette]]
            [my-website.utilities :refer [dark-background]]
            [my-website.components.summary :refer [summary]]
            [my-website.components.menuitem :refer [menuitem]]
            [my-website.components.icon :refer [icon]]
            [my-website.components.navbar :refer [navbar]]
            [my-website.components.image :refer [image]]
            [my-website.components.grid :refer [grid]]))

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
                [:> navbar
                 {:title           "logo"
                  :backgroundColor (:primary color-palette)
                  :inverse         true}
                 [:h2 "a"]
                 [:h2 "b"]
                 [:h2 "c"]
                 [:h2 "d"]
                 [:h2 "e"]]))))

(defcard
  icon-menuitem
  "Combination of menuitem and icon"
  (fn []
    (sab/html (as-element
                [:> menuitem
                 [:> icon {:name         "hand-pointer"
                           :inheritColor true
                           :strength     "strong"
                           :size         :huge}]]))))

(defcard
  image
  "Basic image component"
  (fn []
    (sab/html (as-element
                [:> image {:src  "https://react.semantic-ui.com/images/wireframe/image.png"
                           :alt  "Placeholder"
                           :href "http://www.google.com"
                           :size :small}]))))

(defcard
  slide-show-animated-image
  "Animated image component"
  (fn []
    (sab/html (as-element
                [:> image {:src  ["https://image.flaticon.com/icons/svg/148/148836.svg"
                                  "https://image.flaticon.com/icons/svg/149/149217.svg"
                                  "https://image.flaticon.com/icons/svg/149/149760.svg"
                                  "https://image.flaticon.com/icons/svg/660/660467.svg"]
                           :alt  "Placeholder"
                           :href "http://www.google.com"
                           :size :tiny}]))))

(defcard
  gif-animated-image
  "Animated image component"
  (fn []
    (sab/html (as-element
                [:> image {:src    ["assets/sample-gif-1.gif"
                                    "https://cdn.dribbble.com/users/372537/screenshots/2065624/icons_km_weather.gif"]
                           :toggle true
                           :alt    "Placeholder"
                           :href   "http://www.google.com"
                           :width  "40%"
                           :border (str "5px solid " (:primary color-palette))
                           :height "40%"}]))))

(defcard
  slide-show-animated-image-and-summary
  "Slide show animated image component"
  (fn []
    (sab/html (as-element
                [:> summary {:header   "Animated Hearts"
                             :width    "20em"
                             :content  "These hearts don't beat. They animate."
                             :on-click #(js/alert "Summarize deez Nutellas!")}
                 [:div {:style {:background-color (:secondary color-palette)
                                :width            "20em"
                                :height           "20em"}}
                  [:> image {:src    ["https://image.flaticon.com/icons/svg/148/148836.svg"
                                      "https://image.flaticon.com/icons/svg/149/149217.svg"
                                      "https://image.flaticon.com/icons/svg/149/149760.svg"
                                      "https://image.flaticon.com/icons/svg/660/660467.svg"]
                             :alt    "Placeholder"
                             :width  "20em"
                             :border (str "5px solid " (:primary color-palette))
                             :height "20em"}]]]))))

(defcard
  gif-animated-image-and-summary
  "Slide show animated image component"
  (fn []
    (sab/html (as-element
                [:> summary {:header   "Weather gif"
                             :width    "20em"
                             :content  "Forecast for today is cloudy with a chance of deez Nutellas!"
                             :on-click #(js/alert "Summarize deez Nutellas!")}
                 [:> image {:src    ["assets/sample-gif-1.gif"
                                     "https://cdn.dribbble.com/users/372537/screenshots/2065624/icons_km_weather.gif"]
                            :alt    "Placeholder"
                            :toggle true
                            :border (str "5px solid " (:primary color-palette))
                            :width  "100%"
                            :height "100%"}]]))))

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





