(ns my-website.views.work.styles
  (:require [spade.core :refer [defclass]]
            [my-website.utilities :as u]))

(defclass container-class []
          [:.grid {:display               :grid
                   :grid-template-columns ".05fr 1fr .05fr"
                   :grid-template-rows    "50vh 1fr"
                   :grid-template-areas   (u/seq->css-grid-areas [[:steps :work-items :.]
                                                                  [:. :description :.]])
                   :height                "80vh"
                   :grid-row-gap          "1em"}])
