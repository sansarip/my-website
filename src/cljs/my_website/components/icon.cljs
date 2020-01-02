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
        icon-name (.. this -props -iconName)
        inherit-color (.. this -props -inheritColor)
        strength (.. this -props -strength)
        on-click (.. this -props -onClick)
        inverse (.. this -props -inverse)
        clickable (fn? on-click)
        id (.. this -props -id)
        name (.. this -props -name)
        title (.. this -props -title)]
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
                       "fa-" icon-name)
                     classes)
         :style    style
         :id       id
         :title    title
         :name     name
         :on-click on-click}
     children]))


(comment
  Params
  :size [:tiny :small :medium :large :big :huge :massive]
  :inverse true or false
  :extra-classes string
  :icon-name string
  :strength ["strong" "light" "duotone"] :else "regular"
  :on-click fn

  Example usage
  [:> icon {:size          :big
            :strength      "strong"
            :icon-name     "hand-spock"
            :on-click      some-function
            :inverse       true
            :extra-classes "some-class"}])

(def icon (r/create-class {:display-name :icon
                           :render       render-fn}))