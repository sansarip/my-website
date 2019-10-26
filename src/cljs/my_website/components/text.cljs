(ns my-website.components.text
  (:require [reagent.core :as r]
            ["react-markdown" :as markdown]
            [my-website.utilities :refer [word-concat wrap-all-children deep-merge omit-nil-keyword-args]]
            [my-website.styles :refer [header-style color-palette font-families font-sizes]]
            [my-website.components.flexbox :refer [flexbox]]
            [spade.core :refer [defclass]]
            [clojure.spec.alpha :as s]))

(defclass text-class [])

(defn render-fn [this]
  (let [children (.. this -props -children)
        src (.. this -props -src)]
    [:> markdown {:source src}]))

(def text (r/create-class {:display-name :text
                           :render render-fn}))
