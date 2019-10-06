(ns my-website.components.image
  (:require [reagent.core :as r]
            [my-website.utilities :refer [word-concat omit-nil-keyword-args seq-of? deep-merge]]
            [my-website.styles :refer [color-palette sizes]]
            [my-website.macros :refer-macros [assoc-component-state]]
            [spade.core :refer [defclass]]))

(let [default-size "auto"]
  (defclass image-class [& {:keys [padding size height width clickable border max-width max-height]
                            :or   {padding    "0"
                                   size       default-size
                                   width      default-size
                                   max-width  default-size
                                   height     default-size
                                   max-height default-size
                                   clickable  false
                                   border     "none"}}]
            (let [size (cond (string? size) (get sizes (keyword size))
                             (keyword? size) (get sizes size)
                             :else default-size)]
              {:width      (if (not= width default-size) width size)
               :max-height max-height
               :max-width  max-width
               :height     (if (not= height default-size) height size)
               :clickable  (if clickable
                             "pointer"
                             "inherit")
               :border     border})))

(defn animate-fn [this]
  (let [current-image-index (.. this -state -currentImageIndex)
        num-sources (.. this -state -numSources)]
    (set! (.. this -state -isAnimated) true)
    (assoc-component-state this -currentImageIndex (if (= current-image-index (dec num-sources))
                                                     0
                                                     (inc current-image-index)))))

(defn get-initial-state-fn [this]
  (let [sources (.. this -props -src)]
    #js {:currentImageIndex 0
         :intervalFn        nil
         :numSources        (if (array? sources)
                              (count sources)
                              1)}))

(defn render-fn [this]
  (let [children (.. this -props -children)
        classes (.. this -props -extraClasses)
        style (.. this -props -style)
        src (.. this -props -src)
        alt (.. this -props -alt)
        size (.. this -props -size)
        width (.. this -props -width)
        href (.. this -props -href)
        height (.. this -props -height)
        border (.. this -props -border)
        padding (.. this -props -padding)
        toggle (.. this -props -toggle)
        max-width (.. this -props -maxWidth)
        max-height (.. this -props -maxHeight)
        on-click (.. this -props -onClick)
        clickable (fn? on-click)
        animation-speed (or (.. this -props -animationSpeed) 750)
        intervalFn (.. this -state -intervalFn)
        current-image-index (.. this -state -currentImageIndex)
        multiple-sources (and (array? src) (seq-of? string? src))
        main [:img {:class         (word-concat
                                     (omit-nil-keyword-args
                                       image-class
                                       :padding padding
                                       :max-width max-width
                                       :max-height max-height
                                       :width width
                                       :height height
                                       :size size
                                       :border border
                                       :clickable clickable)
                                     classes)
                    :src           src
                    :alt           alt
                    :on-mouse-over (cond
                                     (and multiple-sources (not toggle))
                                     (fn [_]
                                       (and (not intervalFn)
                                            (set! (.. this -state -intervalFn)
                                                  (-> js/window
                                                      (.setInterval #(animate-fn this)
                                                                    animation-speed)))))
                                     (and multiple-sources toggle)
                                     #(animate-fn this))
                    :on-mouse-out  (cond
                                     (and multiple-sources (not toggle))
                                     (fn [_]
                                       (and intervalFn
                                            (do
                                              (set! (.. this -state -intervalFn)
                                                    (-> js/window
                                                        (.clearInterval intervalFn)))
                                              (assoc-component-state this -currentImageIndex 0))))
                                     (and multiple-sources toggle)
                                     #(assoc-component-state this -currentImageIndex 0))
                    :on-click      on-click
                    :style         style}
              children]]

    (cond
      multiple-sources
      (let [attr (get main 1)
            current-main
            (assoc main 1 (assoc attr :src (get src current-image-index)))]
        (cond href [:a {:href href
                        :alt  alt} current-main]
              :else current-main))
      href [:a {:href href
                :alt  alt} main]
      :else main)))


(def image (r/create-class {:display-name      :image
                            :get-initial-state get-initial-state-fn
                            :render            render-fn}))
