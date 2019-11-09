(ns my-website.components.animate
  (:require [reagent.core :as r]
            [anime]
            [my-website.utilities :refer [deep-merge word-concat omit-nil-keyword-args]]
            [my-website.styles :refer [color-palette]]
            [spade.core :refer [defclass]]
            [my-website.macros :refer-macros [assoc-component-state]]))

(defclass animate-class [& {:keys [clickable]
                            :or   {clickable false}}]
          {:cursor (if clickable
                     "hover"
                     "inherit")})

(defn get-initial-state-fn [this]
  #js {:animation nil})

(defn mount-fn [this]
  (let [anime-props (.. this -props -animeProps)]
    (assoc-component-state this -animation (anime anime-props))))

(defn update-fn [this]
  (let [pause (.. this -props -pause)
        play (.. this -props -play)
        seek (.. this -props -seek)
        restart (.. this -props -restart)
        animation (.. this -state -animation)]
    (cond pause (.pause animation)
          play (.play animation)
          seek (.seek animation seek)
          restart (.restart animation restart))))

(defn render-fn [this]
  (let [children (.. this -props -children)
        classes (.. this -props -extraClasses)
        style (.. this -props -style)
        id (.. this -props -id)
        name (.. this -props -name)
        on-click (.. this -props -onClick)
        clickable (fn? on-click)]
    [:div {:style    style
           :class    (word-concat
                       (omit-nil-keyword-args animate-class
                                              :clickable clickable)
                       classes)
           :id       id
           :on-click on-click
           :name     name}
     children]))

(def animate (r/create-class {:display-name        :animate
                              :render              render-fn
                              :get-initial-state   get-initial-state-fn
                              :component-did-mount mount-fn
                              :component-did-update update-fn}))

