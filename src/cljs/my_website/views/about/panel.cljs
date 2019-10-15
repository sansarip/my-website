(ns my-website.views.about.panel
  (:require [re-frame.core :as re-frame]
            [my-website.styles :refer [color-palette screen-sizes font-sizes spacing-sizes]]
            [my-website.utilities :refer [seq->css-grid-areas word-concat]]
            [my-website.components.flexbox :refer [flexbox]]
            [my-website.components.image :refer [image]]
            [spade.core :refer [defclass]]))

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

(defn about-panel []
  [:> flexbox {:extraClasses  (container-class)
               :justify       "between"
               :align         "center"
               :justify-items "center"
               :align-items   "start"
               :wrap          "nowrap"}
   [:div {:class (description-class)}
    [:h1 {:class "inverse"}
     "About me"]
    [:p {:class "inverse"}
     "Hey—my name’s Sepehr, and I’m nineteen. I like to lift weights and wear sunscreen. SPF 100?
     I hope it’s in stock...’cause I rub it on my nips and my bell-shaped cook. I’ve been known to nut
     for Clojure, pecan pie, and reading manga on my iPhone X."]]
   [:> image {:extraClasses (word-concat (image-class))
              :src          "assets/profpic.png"}]])

