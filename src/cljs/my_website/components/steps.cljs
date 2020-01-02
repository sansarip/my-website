(ns my-website.components.steps
  (:require
    [my-website.components.icon :refer [icon]]
    [my-website.components.grid :refer [grid]]
    [my-website.styles :refer [color-palette font-families font-sizes]]
    [my-website.utilities :refer [word-concat deep-merge wrap-each-child omit-nil-keyword-args]]
    [reagent.core :as r]
    [spade.core :refer [defclass]]))

(defclass steps-class [])

(defn render-fn [this]
  (let [inverse (.. this -props -inverse)
        items (.. this -props -items)
        selected-key (.. this -props -selectedKey)
        size (or (.. this -props -size) :tiny)
        step-gap (or (.. this -props -stepGap) ".4em")
        style (.. this -props -style)
        classes (.. this -props -extraClasses)]
    (reduce-kv (fn [c k v]
                 (let [given-key (or (:key v) k)
                       on-click (:on-click v)]
                   (conj c [:> icon {:icon-name "circle"
                                     :inverse   inverse
                                     :title     (:title v)
                                     :on-click  (if on-click #(on-click given-key))
                                     :strength  (if (= given-key selected-key) "strong"
                                                                               "light")
                                     :size      size}])))
               [:> grid {:extra-classes         (word-concat (steps-class) classes)
                         :style                 style
                         :grid-template-columns (get font-sizes (keyword size))
                         :grid-template-rows    "auto"
                         :grid-row-gap          step-gap}]
               (js->clj items :keywordize-keys true))))

(def steps (r/create-class {:render       render-fn
                            :display-name "steps"}))
