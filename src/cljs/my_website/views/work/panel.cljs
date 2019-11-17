(ns my-website.views.work.panel
  (:require [my-website.components.grid :refer [grid]]
            [my-website.components.icon :refer [icon]]
            [my-website.components.animate :refer [animate]]
            [anime]
            [reagent.core :as r]))

(defn make-animated-icons [& names]
  (map-indexed #(vector :> animate {:animeProps {:translateY [-10 10]
                                                 :duration   1000
                                                 :easing     "easeInOutQuad"
                                                 :direction  "alternate"
                                                 :loop       true}
                                    :style      {:grid-area    (str "item" (inc %))
                                                 :justify-self "center"
                                                 :align-self   "center"}}
                        [:> icon {:iconName %2
                                  :size     :big
                                  :inverse  true}])
               names))

(defn work-panel []
  [:> grid {:columns "1fr"
            :rows    "2fr 1fr"
            :style   {:height "80vh"}
            :rowGap  "1em"}
   (->> (make-animated-icons "hand-spock" "hand-peace" "hand-spock" "hand-peace")
        (into [[:div {:style {:background-color "green"
                              :grid-area        "center"}}]])
        (into
          [:> grid {:columns "1fr 1fr 1fr 1fr"
                    :rowGap  ".5em"
                    :areas   [["item1" "center" "center" "item2"]
                              ["item1" "center" "center" "item2"]
                              ["item3" "center" "center" "item4"]
                              ["item3" "center" "center" "item4"]]}]))
   [:div {:style {:background-color "green"}}]])
