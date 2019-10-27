(ns my-website.components.renderers
  (:require [my-website.components.menuitem :refer [menuitem]]
            [my-website.styles :refer [color-palette]]
            [reagent.core :refer [as-element]]
            [my-website.utilities :refer [deep-merge]]))

(defn gen-renderers [& {:keys [link-options]}]
  {:link (fn [props]
           (as-element
             [:> menuitem (deep-merge {:delink      true
                                       :color       (:tertiary color-palette)
                                       :hover-color (:tertiary-alt color-palette)
                                       :href        (.-href props)
                                       :strong      true}
                                      link-options)
              (.-children props)]))})
