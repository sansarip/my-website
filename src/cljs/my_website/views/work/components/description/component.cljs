(ns my-website.views.work.components.description.component
  (:require
    [my-website.components.animate :refer [animate]]
    [my-website.components.text :refer [text]]
    [my-website.components.flexbox :refer [flexbox]]
    [my-website.views.work.components.description.styles :refer [description-class]]))

(defn description [{:keys [stop start content]} duration]
  [:> animate {:anime-props   {:translateX (if stop 20 [-20 0])
                               :opacity    (if stop [1 0] [0 1])
                               :duration   duration
                               :autoplay   start
                               :easing     "easeInOutQuad"}
               :extra-classes (description-class)}
   [:> flexbox {:justify-content "center"}
    [:> text {:src           content
              :inverse       true
              :extra-classes (str "line-height text")}]]])