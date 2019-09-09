(ns my-website.components.flexbox
  (:require [reagent.core :as r]
            [my-website.utilities :refer [deep-merge word-concat]]
            [my-website.styles :refer [color-palette]]
            [spade.core :refer [defclass]]))

(defclass flexbox-class [& {:keys [align justify direction padding width wrap]
                            :or   {align     "center"
                                   justify   "between"
                                   wrap      "wrap"
                                   direction "row"
                                   padding   "0"
                                   width     "auto"}}]
          {:display         "flex"
           :flex-wrap       "wrap"
           :align-content   "space-around"
           :padding-left    padding
           :padding-right   padding
           :width           width
           :align-items     (cond (= align "start") "flex-start"
                                  (= align "end") "flex-end"
                                  :else align)
           :justify-content (cond (= justify "between") "space-between"
                                  (= justify "around") "space-around"
                                  (= justify "start") "flex-start"
                                  (= justify "end") "flex-end"
                                  :else justify)
           :flex-direction  direction})

(defn render-fn [this]
  (let [children (-> this .-props .-children)
        justify (-> this .-props .-justify)
        classes (-> this .-props .-extraClasses)
        align (-> this .-props .-align)
        style (-> this .-props .-style)
        id (-> this .-props .-id)
        direction (-> this .-props .-direction)
        wrap (-> this .-props .-wrap)
        padding (-> this .-props .-padding)
        width (-> this .-props .-width)]
    [:div {:class (word-concat
                    (flexbox-class :justify justify
                                   :direction direction
                                   :align align
                                   :padding padding
                                   :width width
                                   :wrap wrap)
                    classes)
           :style style
           :id    id
           :width width
           :align align}
     children]))

(def flexbox (r/create-class {:render render-fn}))