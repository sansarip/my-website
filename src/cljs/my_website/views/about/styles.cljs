(ns my-website.views.about.styles
  (:require [spade.core :refer [defclass]]
            [my-website.styles :refer [screen-sizes font-sizes spacing-sizes]]))

(defclass container-class []
          (at-media {:screen    :only
                     :max-width (:small screen-sizes)}
                    [:& {:flex-wrap       "wrap-reverse !important"
                         :justify-content "space-around !important"}]))

(defclass image-class []
          (at-media {:screen    :only
                     :max-width (:small screen-sizes)}
                    [:& {:padding-bottom "4em"
                         :width          "22.313em"
                         :height         "22.375em"}])
          (at-media {:screen    :only
                     :max-width (:tiny screen-sizes)}
                    [:& {:padding-bottom (:huge spacing-sizes)
                         :width          "18.313em"
                         :height         "18.375em"}])
          {:width  "26.313em"
           :height "26.375em"})

(defclass description-class []
          (at-media {:screen    :only
                     :max-width (:small screen-sizes)}
                    [:& {:width "100%"}]
                    ["h1" {:font-size (:huge font-sizes)}])
          {:width "50%"})
