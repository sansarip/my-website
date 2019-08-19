(ns my-website.components.summary
  (:require [reagent.core :as r]
            [my-website.utilities :refer [word-concat]]
            [my-website.styles :refer [color-palette]]
            [cljs-css-modules.macro :refer-macros [defstyle]]))

(defstyle summary-style
          [".container" {:padding "1em"}]
          [".inverse" {:color "white"}]
          [".separator" {:background-color (:tertiary color-palette)
                         :width            "7em"
                         :height           ".5em"
                         :margin-top       "1em"
                         :margin-bottom    "1em"
                         :transition       "width 2s"}]
          [".header" {:font-family "\"Lucida Console\", Monaco, monospace"
                      :margin      "0"
                      :white-space "nowrap"}]
          [".container:hover > div[name=separator]" {:width "10em"}]
          [".description" {:font-family "\"Lucida Console\", Monaco, monospace"
                           :width       "60%"
                           :height      "auto"}])

(def summary
  (r/create-class {:render (fn [this] (let [inverse (-> this .-props .-inverse)
                                            classes (-> this .-props .-extraClasses)
                                            style (-> this .-props .-style)
                                            children (-> this .-props .-children)
                                            header (-> this .-props .-header)
                                            description (-> this .-props .-description)]
                                        [:div {:class (word-concat
                                                        (:container summary-style)
                                                        (if inverse (:inverse summary-style))
                                                        classes)
                                               :style style}
                                         [:h1 {:class (:header summary-style)} [:b header]]
                                         [:div {:class (:separator summary-style)
                                                :name  "separator"}]
                                         [:div {:class (:description summary-style)}
                                          description
                                          children]]))}))


