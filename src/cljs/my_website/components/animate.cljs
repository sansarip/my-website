(ns my-website.components.animate
  (:require [reagent.core :as r]
            [anime]
            [my-website.utilities :refer [css-translator deep-merge word-concat omit-nil-keyword-args]]
            [my-website.styles :refer [color-palette]]
            [spade.core :refer [defclass]]
            [my-website.macros :refer-macros [assoc-component-state get-component-prop]]))

(defn render-fn [this]
  (let [children (.. this -props -children)
        classes (.. this -props -extraClasses)
        style (.. this -props -style)
        animation-props (.. this -props -animationProps)
        id (.. this -props -id)
        name (.. this -props -name)]
    [:> anime (deep-merge
                animation-props
                {:class classes
                 :style style
                 :id    id
                 :name  name})
     children]))

(def animate (r/create-class {:display-name animate
                              :render       render-fn}))
