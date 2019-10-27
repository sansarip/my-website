(ns my-website.views.home.styles
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
                         :width          "21.5625em"
                         :height         "25.35975em"}])
          (at-media {:screen    :only
                     :max-width (:tiny screen-sizes)}
                    [:& {:padding-bottom (:huge spacing-sizes)
                         :width          "15.8125em"
                         :height         "18.59715em"}])
          {:width  "auto"
           :height "auto"})

(defclass description-class []
          (at-media {:screen    :only
                     :max-width (:small screen-sizes)}
                    [:& {:width "100%"}]
                    ["h1" {:font-size (:huge font-sizes)}])
          {:width "50%"})
