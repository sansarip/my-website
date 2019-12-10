(ns my-website.views.work.components.item-grid.styles
  (:require [spade.core :refer [defclass]]))

(defclass item-class [area]
          {:grid-area area
           :justify-self "center"
           :align-self "center"})
