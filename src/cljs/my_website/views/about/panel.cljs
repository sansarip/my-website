(ns my-website.views.about.panel
  (:require [my-website.styles :refer [color-palette screen-sizes font-sizes spacing-sizes]]
            [my-website.utilities :refer [seq->css-grid-areas word-concat]]
            [my-website.components.flexbox :refer [flexbox]]
            [my-website.components.image :refer [image]]
            [my-website.views.about.styles :as styles]
            [spade.core :refer [defclass]]))

(defn about-panel []
  [:> flexbox {:extra-classes   (styles/container-class)
               :justify-content "between"
               :align-content   "center"
               :justify-items   "center"
               :align-items     "start"
               :wrap            "nowrap"}
   [:div {:class (styles/description-class)}
    [:h1 {:class "inverse"}
     "About me"]
    [:p {:class "inverse line-height"}
     "HI, my name is Sepehr, and I'm coo coo for creative programming! From faulting segments in C, collecting
     garbage in Java, cooking spaghetti with JavaScript, doing everything in Python, to composing code as "
     [:i "d a t a"]
     " in Clojure, and everything in between - I'm an engineer who's done and seen a lot, folks \uD83D\uDC40
     These days I've found myself wondering the realms of functional programming + category theory, and learning
     from the wise sages I encounter along with sharing the fruits of knowledge with hungry students also on
     their own journeys."]]
   [:> image {:extraClasses (word-concat (styles/image-class))
              :src          "assets/profpic.png"}]])

