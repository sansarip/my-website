(ns my-website.views.work.components.item-grid.component
  (:require [reagent.core :as r]
            [my-website.components.animate :refer [animate]]
            [my-website.components.icon :refer [icon]]
            [my-website.components.grid :refer [grid]]
            [my-website.views.work.components.item-grid.styles :refer [grid-class item-class]]
            [clojure.string :as string]
            [peanuts.core :as pn]))

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

(defn make-animated-icons [& {:keys [selected-work-items
                                     duration]}]
  (map-indexed (fn [_index work-item]
                 (let [{:keys [start stop grid-area]} work-item]
                   (cond->> [:> icon {:icon-name (:name work-item)
                                      :size      :massive
                                      :inverse   true}]
                            '->> (vector :> animate {:anime-props {:translateY (if stop
                                                                                 20
                                                                                 [-10 10])
                                                                   :duration   (if stop
                                                                                 duration
                                                                                 (* duration 3.5))
                                                                   :autoplay   start
                                                                   :easing     "easeInOutQuad"
                                                                   :direction  "alternate"
                                                                   :loop       (not stop)}})
                            (not stop) (vector :> animate {:anime-props {:opacity  [0 1]
                                                                         :duration duration
                                                                         :autoplay start
                                                                         :easing   "linear"}})
                            stop (vector :> animate {:anime-props {:translateY 40
                                                                   :opacity    [1 0]
                                                                   :duration   (+ (/ duration 2) 50)
                                                                   :easing     "linear"}})
                            '->> (vector :div {:class (item-class (str "item" grid-area))}))))

               selected-work-items))

(defn make-work-items [& {:keys [names
                                 shelves
                                 size]}]
  (let [delays (shuffle (range 4 (+ 4 (count names))))
        grid-areas (shuffle (range size))]
    (vec (map-indexed (fn [index name]
                        {:start     false
                         :stop      false
                         :name      name
                         :delay     (* (get delays index) 100)
                         :grid-area (or (get shelves index) (get grid-areas index))})
                      names))))

(defn make-grid-columns [columns center-width center-ratio]
  (let [half-fr (vec (map #(str "1fr") (range (/ columns 2))))]
    (string/join " " (-> half-fr
                         (into (vec (map #(str center-ratio "fr") (range center-width))))
                         (into half-fr)))))

(pn/defc item-grid
  (fn [& {:keys [rows
                 columns
                 center-width
                 center-ratio
                 duration
                 selected-work-items]
          :or   {rows                4
                 columns             12
                 center-width        2
                 center-ratio        3
                 duration            400
                 selected-work-items {}}}]
    (->> (make-animated-icons :selected-work-items selected-work-items
                              :duration duration)
         (into [[:div {:style {:background-color "green"
                               :grid-area        "center"}}]])
         (into [:> grid {:grid-template-columns (make-grid-columns columns center-width center-ratio)
                         :grid-template-areas   (make-grid-areas rows columns center-width)
                         :grid-row-gap          ".5em"
                         :extra-classes         (grid-class)}])))
  {:only [selected-work-items]})

