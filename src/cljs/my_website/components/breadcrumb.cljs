(ns my-website.components.breadcrumb
  (:require
    [my-website.styles :refer [color-palette font-families font-sizes]]
    [my-website.utilities :refer [word-concat deep-merge wrap-each-child omit-nil-keyword-args]]
    [reagent.core :as r]
    [spade.core :refer [defclass]]))

(defclass breadcrumb-class [])

(defn render-fn [this])

(def breadcrumb (r/create-class {:render       render-fn
                                 :display-name "breadcrumb"}))
