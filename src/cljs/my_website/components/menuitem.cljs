(ns my-website.components.menuitem
  (:require [reagent.core :as r]
            [my-website.utilities :refer [word-concat deep-merge wrap-each-child omit-nil-keyword-args]]
            [my-website.styles :refer [color-palette font-families font-sizes]]
            [spade.core :refer [defclass]]))

(defclass menuitem-class [& {:keys [inverse strong font-size padding hover-color active-color static color]
                             :or   {inverse      false
                                    strong       false
                                    font-size    :medium
                                    hover-color  (:tertiary color-palette)
                                    active-color (:tertiary-alt color-palette)
                                    static       false
                                    color        (:primary color-palette)
                                    padding      "0"}}]
          {:font-family (:body font-families)
           :border      "none"
           :background  "none"
           :transition  "color .25s"
           :padding     padding
           :color       (if inverse "white" color)
           :font-weight (if strong "bold" "normal")
           :text-align  "center"
           :font-size   (cond
                          (string? font-size) ((keyword font-size) font-sizes)
                          (keyword? font-size) (font-size font-sizes))
           :cursor      "pointer"}
          ["&:hover" {:color (if static color hover-color)}]
          ["&:active" {:color (if static color active-color)}]
          ["&:focus" {:outline "none"}])


(defn render-fn [this]
  (let [children (.. this -props -children)
        classes (.. this -props -extraClasses)
        style (.. this -props -style)
        strong (.. this -props -strong)
        href (.. this -props -href)
        delink (.. this -props -delink)
        target (or (.. this -props -target) "_blank")
        font-size (.. this -props -fontSize)
        static (.. this -props -static)
        color (.. this -props -color)
        hover-color (.. this -props -hoverColor)
        active-color (.. this -props -activeColor)
        padding (.. this -props -padding)
        on-click (.. this -props -onClick)
        inverse (.. this -props -inverse)
        class (word-concat
                (omit-nil-keyword-args
                  menuitem-class
                  :inverse inverse
                  :strong strong
                  :padding padding
                  :color color
                  :static static
                  :hover-color hover-color
                  :active-color active-color
                  :font-size font-size)
                (if delink "delink")
                classes)]
    (-> (if href
          [:a {:class  class
               :href   href
               :target target
               :style  style}]
          [:button {:class    class
                    :on-click on-click
                    :style    style}])
        (conj children))))

(def menuitem (r/create-class {:display-name :menuitem
                               :render       render-fn}))


