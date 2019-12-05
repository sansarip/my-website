(ns my-website.views.work.panel
  (:require [my-website.components.grid :refer [grid]]
            [my-website.views.work.components.item-grid.component :refer [make-item-grid]]
            [my-website.views.work.subs :as subs]
            [re-frame.core :refer [subscribe]]))

(defn work-panel []
  [:> grid {:columns "1fr"
            :rows    "2fr 1fr"
            :style   {:height "80vh"}
            :rowGap  "1em"}
   (make-item-grid :work-items-index @(subscribe [::subs/work-items-index]))
   [:div {:style {:background-color "green"}}]])
