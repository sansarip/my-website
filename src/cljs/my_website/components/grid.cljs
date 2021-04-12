(ns my-website.components.grid
  (:require [reagent.core :as r]
            [my-website.utilities :refer [deep-merge word-concat omit-nil-keyword-args ->css-grid-areas]]
            [my-website.styles :refer [color-palette]]
            [spade.core :refer [defclass]]
            [my-website.macros :refer-macros [assoc-component-state get-component-prop]]))

(defclass grid-class [& {:keys [padding
                                justify-content
                                align-items
                                columns
                                rows
                                row-gap
                                column-gap
                                areas]
                         :or   {padding         "1em"
                                justify-content "start"
                                align-items     "start"
                                columns         "auto"
                                rows            "auto"
                                row-gap         "0px"
                                column-gap      "0px"
                                areas           ""}}]
  {:display               "grid"
   :padding               padding
   :justify-content       justify-content
   :align-items           align-items
   :grid-template-columns columns
   :grid-template-rows    rows
   :grid-column-gap       column-gap
   :grid-row-gap          row-gap
   :grid-template-areas   areas})

(defn render-fn [this]
  (let [children (.. this -props -children)
        classes (.. this -props -extraClasses)
        style (.. this -props -style)
        columns (.. this -props -gridTemplateColumns)
        rows (.. this -props -gridTemplateRows)
        row-gap (.. this -props -gridRowGap)
        column-gap (.. this -props -gridColumnGap)
        justify-content (.. this -props -justifyContent)
        align-items (.. this -props -alignItems)
        areas (.. this -props -gridTemplateAreas)
        id (.. this -props -id)
        name (.. this -props -name)
        padding (.. this -props -padding)]
    [:div {:class (word-concat
                    (omit-nil-keyword-args
                      grid-class
                      :padding padding
                      :row-gap row-gap
                      :column-gap column-gap
                      :columns (if columns (word-concat columns))
                      :rows (if rows (word-concat rows))
                      :areas (if areas (->css-grid-areas areas))
                      :justify-content justify-content
                      :align-items align-items)
                    classes)
           :style style
           :id    id
           :name  name}
     children]))

(def grid (r/create-class {:display-name :grid
                           :render       render-fn}))
