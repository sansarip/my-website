(ns my-website.views.work.components.description.component
  (:require
    [my-website.components.animate :refer [animate]]
    [my-website.components.text :refer [text]]
    [my-website.components.flexbox :refer [flexbox]]
    [my-website.views.work.components.description.styles :refer [description-class]]
    [my-website.utilities :as u]
    [peanuts.core :as pn]))

(pn/defc description
  (fn [description duration]
    (let [{:keys [stop start content]} description]
      [:> animate {:anime-props   {:translateX (if stop 20 [-20 0])
                                   :opacity    (if stop [1 0] [0 1])
                                   :duration   duration
                                   :autoplay   start
                                   :easing     "easeInOutQuad"}
                   :extra-classes (description-class)}
       [:> flexbox {:justify-content "center"
                    :extra-classes   "flex"}
        [:> text {:src           content
                  :id            (u/fqkw->id ::description)
                  :inverse       true
                  :style         {:overflow-y :auto}
                  :extra-classes (str "line-height text")}]]]))
  {:only [description]})