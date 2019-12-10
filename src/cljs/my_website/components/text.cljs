(ns my-website.components.text
  (:require [reagent.core :as r]
            ["react-markdown" :as markdown]
            [my-website.utilities :refer [word-concat wrap-all-children deep-merge omit-nil-keyword-args]]
            [my-website.styles :refer [header-style color-palette font-families font-sizes]]
            [my-website.components.flexbox :refer [flexbox]]
            [spade.core :refer [defclass]]
            [clojure.spec.alpha :as s]))

(defclass text-class [& {:keys [inverse]
                         :or   {inverse false}}]
          {:color (if inverse "white" "inherit")}
          ["h1" {:color (if inverse "white" "inherit")}]
          ["h2" {:color (if inverse "white" "inherit")}]
          ["h3" {:color (if inverse "white" "inherit")}]
          ["h4" {:color (if inverse "white" "inherit")}]
          ["h5" {:color (if inverse "white" "inherit")}]
          ["h6" {:color (if inverse "white" "inherit")}])

(defn render-fn [this]
  (let [children (.. this -props -children)
        src (.. this -props -src)
        renderers (.. this -props -renderers)
        classes (.. this -props -extraClasses)
        style (.. this -props -style)
        id (.. this -props -id)
        name (.. this -props -name)
        inverse (.. this -props -inverse)]
    [:div {:class (word-concat (omit-nil-keyword-args text-class :inverse inverse)
                               classes)
           :style style
           :id    id
           :name  name}
     [:> markdown {:source    src
                   :renderers renderers}]
     children]))


(def text (r/create-class {:display-name :text
                           :render       render-fn}))
