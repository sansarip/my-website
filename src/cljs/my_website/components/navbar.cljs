(ns my-website.components.navbar
  (:require [reagent.core :as r]
            [my-website.utilities :refer [word-concat wrap-all-children]]
            [my-website.components.flexbox :refer [flexbox]]
            [my-website.styles :refer [color-palette font-families]]
            [spade.core :refer [defclass]]
            [my-website.components.menuitem :refer [menuitem]]))

(defclass navbar-class [& {:keys [inverse]
                           :or   {inverse false}}]
          {:color (if inverse "white" (:primary color-palette))}
          ["p" {:font-family (:body font-families)
                :font-weight "bold"}]
          ["h1" {:font-family (:header font-families)
                 :font-weight "bold"}])

(defn render-fn [this]
  (let [children (-> this .-props .-children)
        title (-> this .-props .-title)
        as (-> this .-props .-as)
        classes (-> this .-props .-extraClasses)
        inverse (-> this .-props .-inverse)
        style (-> this .-props .-style)]
    [:> flexbox {:justify      "between"
                 :extraClasses (word-concat
                                 (navbar-class inverse)
                                 classes)
                 :align        "center"
                 :padding      "1em"
                 :style        style}
     (wrap-all-children (if as (js->clj as) :h3) title)
     [:> flexbox {:justify "between"
                  :width   "60%"}
      children]]))

(def navbar (r/create-class {:render render-fn}))