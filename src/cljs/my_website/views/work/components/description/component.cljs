(ns my-website.views.work.components.description.component
  (:require [my-website.components.text :refer [text]]
            [my-website.components.flexbox :refer [flexbox]]
            [my-website.views.work.components.description.styles :refer [description-class]]))

(defn make-description [description]
  [:> flexbox {:justify "center"}
   [:> text {:src          description
             :inverse      true
             :extraClasses (str "line-height " (description-class))}]])