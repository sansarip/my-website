(ns my-website.views.work.components.item-grid.styles
  (:require [spade.core :refer [defclass]]))

(defclass grid-class []
          {:grid-area "work-items"})

(defclass item-class [area]
          {:grid-area area
           :justify-self "center"
           :align-self "center"})
