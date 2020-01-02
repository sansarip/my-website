(ns my-website.components.steps
  (:require
    [my-website.components.icon :refer [icon]]
    [my-website.components.grid :refer [grid]]
    [my-website.styles :refer [color-palette font-families font-sizes]]
    [my-website.utilities :refer [word-concat deep-merge wrap-each-child omit-nil-keyword-args]]
    [reagent.core :as r]
    [spade.core :refer [defclass defkeyframes]]))

(defkeyframes glow-frames [inverse]
              [:to {:text-shadow (str "0 0 7px " (if inverse
                                                   (:tertiary color-palette)
                                                   (:tertiary-alt color-palette)))}])

(defclass steps-class [inverse]
          [".selected" {:animation [[(glow-frames inverse) "1.2s" "infinite" "alternate"]]}])

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
                       on-click (:on-click v)
                       selected? (= given-key selected-key)]
                   (conj c [:> icon {:icon-name     "circle"
                                     :extra-classes (if selected? "selected")
                                     :inverse       inverse
                                     :title         (:title v)
                                     :on-click      (if on-click #(on-click given-key))
                                     :strength      (if selected? "strong"
                                                                  "light")
                                     :size          size}])))
               [:> grid {:extra-classes         (word-concat (steps-class inverse) classes)
                         :style                 style
                         :grid-template-columns (get font-sizes (keyword size))
                         :grid-template-rows    "auto"
                         :grid-row-gap          step-gap}]
               (js->clj items :keywordize-keys true))))

(def steps (r/create-class {:render       render-fn
                            :display-name "steps"}))
