(ns my-website.components.renderers
  (:require [my-website.components.menuitem :refer [menuitem]]
            [my-website.styles :refer [color-palette]]
            [reagent.core :as r]
            [my-website.utilities :refer [deep-merge]]))

(defn renderer-options [& [link-opts]]
  {:link (fn [props]
           (r/as-element
             [:> menuitem (r/merge-props
                            {:delink      true
                             :color       (:tertiary color-palette)
                             :hover-color (:tertiary-alt color-palette)
                             :href        (.-href props)
                             :strong      true}
                            link-opts)
              (.-children props)]))})
