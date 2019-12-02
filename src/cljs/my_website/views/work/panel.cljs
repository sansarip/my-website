(ns my-website.views.work.panel
  (:require [my-website.components.grid :refer [grid]]
            [my-website.components.icon :refer [icon]]
            [my-website.components.animate :refer [animate]]
            [my-website.views.work.styles :refer [container-class]]
            [anime]
            [reagent.core :as r]))

(defn make-ratoms [& names]
  (let [delays (shuffle (range 4 8))
        grid-areas (shuffle (range 0 32))]
    (map-indexed (fn [index name] (r/atom {:start     false
                                           :name      name
                                           :delay     (* (get delays index) 100)
                                           :grid-area (get grid-areas index)}))
                 names)))

(defn make-animated-icons [& ratoms]
  (map-indexed (fn [_index ratom]
                 (vector :> animate {:animeProps {:opacity  [0 1]
                                                  :duration 250
                                                  :autoplay (:start @ratom)
                                                  :easing   "linear"}
                                     :style      {:grid-area    (str "item" (:grid-area @ratom))
                                                  :justify-self "center"
                                                  :align-self   "center"}}
                         [:> animate {:animeProps {:translateY [-10 10]
                                                   :duration   1000
                                                   :autoplay   (:start @ratom)
                                                   :easing     "easeInOutQuad"
                                                   :direction  "alternate"
                                                   :loop       true}}
                          [:> icon {:iconName (:name @ratom)
                                    :size     :big
                                    :inverse  true}]]))
               ratoms))

(defn work-panel []
  (r/with-let [ratoms [(make-ratoms "hand-spock" "hand-peace" "hand-spock" "hand-peace")
                       []
                       []]
               _ (doall (map #(js/setTimeout (partial swap! % assoc :start true)
                                             (:delay @%))
                             (first ratoms)))]
              [:> grid {:columns "1fr"
                        :rows    "2fr 1fr"
                        :style   {:height "80vh"}
                        :rowGap  "1em"}
               (->> (get ratoms 0)
                    (apply make-animated-icons)
                    (into [[:div {:style {:background-color "green"
                                          :grid-area        "center"}}]])
                    (into
                      [:> grid {:columns "1fr 1fr 1fr 1fr 3fr 3fr 1fr 1fr 1fr 1fr"
                                :rowGap  ".5em"
                                :areas   [["item0" "item1" "item2" "item3" "center" "center" "item4" "item5" "item6" "item7"]
                                          ["item8" "item9" "item10" "item11" "center" "center" "item12" "item13" "item14" "item15"]
                                          ["item16" "item17" "item18" "item19" "center" "center" "item20" "item21" "item22" "item23"]
                                          ["item24" "item25" "item26" "item27" "center" "center" "item28" "item29" "item30" "item31"]]}]))
               [:div {:style {:background-color "green"}}]]))
