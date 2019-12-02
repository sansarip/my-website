(ns my-website.views.work.components.item-grid.component
  (:require [reagent.core :as r]
            [my-website.components.animate :refer [animate]]
            [my-website.components.icon :refer [icon]]))

(defn directional-vec [columns row is-right]
  (let [column-start (* row columns)
        column-middle (+ column-start (/ columns 2))]
    (if is-right
      (vec (range column-middle (+ column-start columns)))
      (vec (range column-start (+ column-start (/ columns 2)))))))

(defn make-grid-areas [rows columns center-width]
  (if (zero? rows)
    []
    (vec (map #(let [left (vec (map (partial str "item") (directional-vec columns % false)))
                     right (vec (map (partial str "item") (directional-vec columns % true)))
                     center (vec (map (fn [_] "center") (range center-width)))]
                 (vec (concat left center right)))
              (range rows)))))

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

(defn make-ratoms [& names]
  (let [delays (shuffle (range 4 8))
        grid-areas (shuffle (range 0 32))]
    (map-indexed (fn [index name] (r/atom {:start     false
                                           :name      name
                                           :delay     (* (get delays index) 100)
                                           :grid-area (get grid-areas index)}))
                 names)))

