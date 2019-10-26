(ns my-website.cards.components.image
  (:require
    [devcards.core :refer-macros [defcard]]
    [sablono.core :as sab]
    [my-website.styles :refer [color-palette]]
    [my-website.components.image :refer [image]]
    [reagent.core :refer [as-element]]))

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
