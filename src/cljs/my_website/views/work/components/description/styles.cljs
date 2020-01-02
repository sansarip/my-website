(ns my-website.views.work.components.description.styles
  (:require [spade.core :refer [defclass]]))

(defclass description-class []
          {:grid-area "description"}
          [".text" {:width "50vw"}]
          ["p" {:padding-top "1em"}]
          ["h1" {:text-align "center"}]
          ["h2" {:text-align "center"}]
          ["h3" {:text-align "center"}])
