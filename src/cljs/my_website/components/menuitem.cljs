(ns my-website.components.menuitem
  (:require [reagent.core :as r]
            [my-website.utilities :refer [word-concat deep-merge wrap-each-child map->css-attribute-selectors]]
            [my-website.styles :refer [color-palette font-families font-sizes]]
            [spade.core :refer [defclass]]))

(defclass menuitem-class [& {:keys [inverse strong font-size]
                             :or   {inverse   false
                                    strong    false
                                    font-size :medium}}]
          {:font-family (:body font-families)
           :border      "none"
           :background  "none"
           :transition  "color .25s"
           :padding     "0"
           :color       (if inverse "white" (:primary color-palette))
           :font-weight (if strong "bold" "0")
           :text-align  "center"
           :font-size   (cond
                          (string? font-size) ((keyword font-size) font-sizes)
                          (keyword? font-size) (font-size font-sizes))
           :cursor      "pointer"}
          ["&:hover" {:color (:tertiary color-palette)}]
          ["&:active" {:color (:tertiary-alt color-palette)}]
          ["&:focus" {:outline "none"}])


(defn render-fn [this]
  (let [children (-> this .-props .-children)
        classes (-> this .-props .-extraClasses)
        style (-> this .-props .-style)
        strong (-> this .-props .-strong)
        font-size (-> this .-props .-fontSize)
        on-click (-> this .-props .-onClick)
        inverse (-> this .-props .-inverse)]
    [:button {:class     (word-concat
                           (menuitem-class :inverse inverse :strong strong :font-size font-size)
                           classes)
              :on-click  on-click
              :font-size font-size
              :style     style}
     (if (array? children)
       (wrap-each-child [:p] children)
       children)]))

(def menuitem (r/create-class {:render render-fn}))


