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

(defn make-animated-icons
  [& {:keys [selected-work-items duration]}]
  (map-indexed
    (fn [_index work-item]
      (let [{:keys [start stop grid-area]} work-item]
        [:div {:class (item-class (str "item" grid-area))}
         [:> animate {:anime-props (if stop
                                     {:translateY 40
                                      :opacity    [1 0]
                                      :duration   (+ (/ duration 2) 50)
                                      :easing     "linear"}
                                     {:opacity  [0 1]
                                      :duration duration
                                      :autoplay start
                                      :easing   "linear"})}
          [:> animate {:anime-props {:translateY (if stop
                                                   20
                                                   [-10 10])
                                     :duration   (if stop
                                                   duration
                                                   (* duration 3.5))
                                     :autoplay   start
                                     :easing     "easeInOutQuad"
                                     :direction  "alternate"
                                     :loop       (not stop)}}
           [:> icon {:icon-name (:name work-item)
                     :size      :massive
                     :inverse   true}]]]]))
    selected-work-items))

(defn make-work-items [work-items]
  (let [delays (shuffle (range 4 (+ 4 (count work-items))))]
    (update work-items
            :items
            (partial into
                     []
                     (map-indexed
                       (fn [i {shelf :shelf :as work-item}]
                         (assoc work-item
                           :start false
                           :stop false
                           :delay (* (get delays i) 100)
                           :grid-area shelf)))))))

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
    [:> grid {:grid-template-columns (make-grid-columns columns center-width center-ratio)
              :grid-template-areas   (make-grid-areas rows columns center-width)
              :grid-row-gap          ".5em"
              :extra-classes         (grid-class)}
     (into [:<>
            [:div {:style {:background-color "green"
                           :height           :100%
                           :grid-area        "center"}}]]
           (make-animated-icons :selected-work-items selected-work-items
                                :duration duration))])
  {:only [selected-work-items]})

