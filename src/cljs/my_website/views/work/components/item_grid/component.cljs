(ns my-website.views.work.components.item-grid.component
  (:require [reagent.core :as r]
            [my-website.components.animate :refer [animate]]
            [my-website.components.icon :refer [icon]]
            [my-website.components.grid :refer [grid]]
            [clojure.string :as string]))

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
                                    :size     :massive
                                    :inverse  true}]]))
               ratoms))

(defn make-ratoms [& {:keys [names
                             shelves
                             size]}]
  (let [delays (shuffle (range 4 (+ 4 (count names))))
        grid-areas (shuffle (range size))]
    (map-indexed (fn [index name]
                   (r/atom {:start     false
                            :name      name
                            :delay     (* (get delays index) 100)
                            :grid-area (or (get shelves index) (get grid-areas index))}))
                 names)))

(defn make-grid-columns [columns center-width center-ratio]
  (let [half-fr (vec (map #(str "1fr") (range (/ columns 2))))]
    (string/join " " (-> half-fr
                         (into (vec (map #(str center-ratio "fr") (range center-width))))
                         (into half-fr)))))

(defn make-item-grid [& {:keys [rows columns center-width center-ratio shelves work-items-index]
                         :or   {rows         4
                                columns      12
                                center-width 2
                                center-ratio 3
                                shelves      [2 25 8 34]
                                work-items-index 0}}]
  (r/with-let [ratoms [(make-ratoms :names ["smile-beam" "smile-wink" "poo" "grin-squint"]
                                    :shelves shelves
                                    :size (* rows columns))
                       (make-ratoms :names ["poo" "poo" "poo" "poo"]
                                    :shelves shelves
                                    :size (* rows columns))
                       []]
               _ (doall (map #(js/setTimeout (partial swap! % assoc :start true)
                                             (:delay @%))
                             (first ratoms)))]
              (->> (get ratoms work-items-index)
                   (apply make-animated-icons)
                   (into [[:div {:style {:background-color "green"
                                         :grid-area        "center"}}]])
                   (into [:> grid {:columns (make-grid-columns columns center-width center-ratio)
                                   :areas   (make-grid-areas rows columns center-width)
                                   :rowGap  ".5em"}]))))

