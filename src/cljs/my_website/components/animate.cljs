(ns my-website.components.animate
  (:require [reagent.core :as r]
            [anime]
            [my-website.utilities :refer [deep-merge word-concat omit-nil-keyword-args]]
            [my-website.styles :refer [color-palette]]
            [spade.core :refer [defclass]]
            [my-website.macros :refer-macros [assoc-component-state]]
            [clojure.string :as string]))

(defclass animate-class [& {:keys [clickable]
                            :or   {clickable false}}]
          {:cursor (if clickable
                     "hover"
                     "inherit")})

(defn initialize-anime [this anime-props]
  (let [animation (anime (clj->js anime-props))]
    (assoc-component-state this -animation animation)
    animation))

(defn refine-props [props props-to-exclude]
  (let [keywordized (map keyword (js->clj props-to-exclude))]
    (apply dissoc (into [props] keywordized))))

(defn set-current-anime-props [this anime-props]
  (assoc-component-state this -currentAnimeProps (js->clj anime-props :keywordize-keys true)))

(defn get-initial-state-fn [_this]
  #js {:animation         nil
       :currentAnimeProps {}})

(defn mount-fn [this]
  (let [anime-props (.. this -props -animeProps)]
    (set-current-anime-props this anime-props)
    (initialize-anime this (js->clj anime-props))))

(defn update-fn [this]
  (let [pause (.. this -props -pause)
        play (.. this -props -play)
        seek (.. this -props -seek)
        restart (.. this -props -restart)
        incoming-anime-props (js->clj (.. this -props -animeProps) :keywordize-keys true)
        current-anime-props (.. this -state -currentAnimeProps)
        static-anime-props (.. this -props -staticAnimeProps)
        refined-incoming-anime-props (refine-props incoming-anime-props static-anime-props)
        refined-current-anime-props (refine-props current-anime-props static-anime-props)
        animation (if (not= refined-incoming-anime-props refined-current-anime-props)
                    (do
                      (.remove anime (string/join " " (:targets current-anime-props)))
                      (set-current-anime-props this incoming-anime-props)
                      (initialize-anime this incoming-anime-props))
                    (.. this -state -animation))]
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

(def animate (r/create-class {:display-name         :animate
                              :render               render-fn
                              :get-initial-state    get-initial-state-fn
                              :component-did-mount  mount-fn
                              :component-did-update update-fn}))

