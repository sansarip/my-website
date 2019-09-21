(ns my-website.components.icon
  (:require [reagent.core :as r]
            [my-website.utilities :refer [word-concat omit-nil-keyword-args]]
            [my-website.styles :refer [color-palette font-sizes]]
            [spade.core :refer [defclass]]))

(let [default-size (:medium font-sizes)]
  (defclass icon-class [& {:keys [padding size inverse clickable inherit-color]
                           :or   {padding       "0"
                                  size          default-size
                                  inverse       false
                                  inherit-color false
                                  clickable     false}}]
            {:padding   padding
             :font-size (cond (string? size) (get font-sizes (keyword size))
                              (keyword? size) (get font-sizes size)
                              :else default-size)
             :cursor    (if clickable
                          "pointer"
                          "inherit")
             :color     (cond inherit-color "inherit"
                              inverse "white"
                              :else (:primary color-palette))}))

(defn render-fn [this]
  (let [children (.. this -props -children)
        classes (.. this -props -extraClasses)
        style (.. this -props -style)
        size (.. this -props -size)
        padding (.. this -props -padding)
        name (.. this -props -name)
        inherit-color (.. this -props -inheritColor)
        strength (.. this -props -strength)
        on-click (.. this -props -onClick)
        inverse (.. this -props -inverse)
        clickable (boolean on-click)]
    [:i {:class    (word-concat
                     (omit-nil-keyword-args
                       icon-class
                       :padding padding
                       :size size
                       :inverse inverse
                       :inherit-color inherit-color
                       :clickable clickable)
                     (str
                       (cond (= strength "strong") "fas "
                             (= strength "light") "fal "
                             (= strength "duotone") "fad "
                             :else "far ")
                       "fa-" name)
                     classes)
         :style    style
         :on-click on-click}
     children]))


(comment
  Params
  :size [:tiny :small :medium :large :big :huge :massive]
  :inverse true or false
  :extraClasses string
  :strength ["strong" "light" "duotone"] :else "regular"
  :onClick fn

  Example usage
  [:> icon {:size         :big
            :strength     "strong"
            :on-click     some-function
            :inverse      true
            :extraClasses "some-class"}])

(def icon (r/create-class {:display-name :icon
                           :render       render-fn}))