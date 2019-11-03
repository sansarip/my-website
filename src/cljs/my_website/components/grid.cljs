(ns my-website.components.grid
  (:require [reagent.core :as r]
            [my-website.utilities :refer [css-translator deep-merge word-concat omit-nil-keyword-args seq->css-grid-areas]]
            [my-website.styles :refer [color-palette]]
            [spade.core :refer [defclass]]
            [my-website.macros :refer-macros [assoc-component-state get-component-prop]]))

(defclass grid-class [& {:keys [padding
                                columns
                                rows
                                row-gap
                                column-gap
                                areas
                                justify
                                align
                                justify-items
                                align-items]
                         :or   {padding       "1em"
                                columns       "auto"
                                rows          "auto"
                                row-gap       "0"
                                column-gap    "0"
                                areas         ""
                                justify-items "initial"
                                align-items   "initial"
                                justify       "stretch"
                                align         "initial"}}]
          {:display               "grid"
           :padding               padding
           :justify-content       (get css-translator (keyword justify) justify)
           :justify-items         (get css-translator (keyword justify-items) justify-items)
           :align-content         (get css-translator (keyword align) align)
           :align-items           (get css-translator (keyword align-items) align-items)
           :grid-template-columns columns
           :grid-template-rows    rows
           :grid-column-gap       column-gap
           :grid-row-gap          row-gap
           :grid-template-areas   areas})

(defn render-fn [this]
  (let [children (.. this -props -children)
        classes (.. this -props -extraClasses)
        style (.. this -props -style)
        columns (.. this -props -columns)
        rows (.. this -props -rows)
        row-gap (.. this -props -rowGap)
        column-gap (.. this -props -columnGap)
        areas (.. this -props -areas)
        justify (.. this -props -justify)
        justify-items (.. this -props -justifyItems)
        align-items (.. this -props -alignItems)
        align (.. this -props -align)
        id (.. this -props -id)
        name (.. this -props -name)
        padding (.. this -props -padding)]
    [:div {:class (word-concat
                    (omit-nil-keyword-args grid-class
                                           :padding padding
                                           :row-gap row-gap
                                           :column-gap column-gap
                                           :columns (if columns (word-concat columns))
                                           :rows (if rows (word-concat rows))
                                           :areas (if areas (seq->css-grid-areas areas)))

                    classes)
           :style style
           :id id
           :name name}
     children]))

(def grid (r/create-class {:display-name :grid
                           :render       render-fn}))
