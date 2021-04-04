(ns my-website.components.steps
  (:require
    [my-website.components.icon :refer [icon]]
    [my-website.components.grid :refer [grid]]
    [my-website.components.text :refer [text]]
    [my-website.styles :refer [color-palette font-families font-sizes]]
    [my-website.utilities :refer [word-concat deep-merge wrap-each-child omit-nil-keyword-args]]
    [reagent.core :as r]
    [spade.core :refer [defclass defkeyframes]]))

(defkeyframes glow-frames [inverse]
              [:to {:text-shadow (str "0 0 4px " (if inverse
                                                   (:tertiary color-palette)
                                                   (:tertiary-alt color-palette)))}])

(defclass steps-class [inverse]
  [:.selected {:animation [[(glow-frames inverse) "1.2s" "infinite" "alternate"]]}]
  [:p {:margin :0px}])

(defn render-fn [this]
  (let [inverse (.. this -props -inverse)
        items (.. this -props -items)
        selected-key (.. this -props -selectedKey)
        size (or (.. this -props -size) :tiny)
        step-gap (or (.. this -props -stepGap) ".325em")
        style (.. this -props -style)
        classes (.. this -props -extraClasses)]
    (into
      [:> grid {:extra-classes         (word-concat (steps-class inverse) classes)
                :style                 style
                :grid-template-columns "auto"
                :grid-template-rows    "auto"
                :grid-row-gap          step-gap}]
      (map-indexed
        (fn [i {k :key on-click :on-click title :title}]
          (let [given-key (or k i)
                selected? (= given-key selected-key)
                selected-class (if selected? "selected")]
            [:> grid {:grid-column-gap       step-gap
                      :align-items           "center"
                      :padding               "0px"
                      :grid-template-columns "auto auto"}
             [:> icon {:icon-name     "circle"
                       :extra-classes selected-class
                       :inverse       inverse
                       :title         title
                       :on-click      (if on-click #(on-click given-key))
                       :strength      (if selected? "strong" "light")
                       :size          size}]
             [:> text {:src           (cond-> title selected? (as-> t (str "**" t "**")))
                       :extra-classes selected-class
                       :inverse       true}]])))

      (js->clj items :keywordize-keys true))))

(def steps (r/create-class {:render       render-fn
                            :display-name "steps"}))
