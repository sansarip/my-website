(ns my-website.cards.components.summary
  (:require
    [devcards.core :refer-macros [defcard]]
    [sablono.core :as sab]
    [my-website.utilities :refer [dark-background]]
    [my-website.styles :refer [color-palette]]
    [my-website.components.summary :refer [summary]]
    [my-website.components.image :refer [image]]
    [reagent.core :refer [as-element]]))

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
  slide-show-animated-image-and-summary
  "Summary with slide show animated image component"
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
                             :border (str "5px solid " (:primary color-palette))}]]]))))


(defcard
  gif-animated-image-and-summary
  "Summary component with gif-animated image component"
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
