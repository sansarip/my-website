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

(defn add-id-to-anime-targets [anime-props id]
  (let [clj-anime-props (js->clj anime-props :keywordize-keys true)]
    (clj->js (update clj-anime-props :targets #(conj (set %) (str "#" id))))))

(defn get-initial-state-fn [this]
  #js {:animation         nil
       :id                (or (.. this -props -id) (str "id-" (random-uuid)))
       :currentAnimeProps {}})

(defn mount-fn [this]
  (let [id (.. this -state -id)
        delay-start (or (.. this -props -delayStart) 0)
        anime-props (add-id-to-anime-targets (.. this -props -animeProps) id)
        anime-props-deep-copy (js->clj anime-props)]
    (set-current-anime-props this anime-props)
    ;; conversion serves as deep copy of anime props
    (js/setTimeout #(initialize-anime this anime-props-deep-copy)
                   delay-start)))

(defn update-fn [this]
  (let [pause (.. this -props -pause)
        play (.. this -props -play)
        seek (.. this -props -seek)
        restart (.. this -props -restart)
        id (.. this -state -id)
        incoming-id (.. this -props -id)
        incoming-anime-props (js->clj (add-id-to-anime-targets (.. this -props -animeProps) id)
                                      :keywordize-keys true)
        current-anime-props (.. this -state -currentAnimeProps)
        static-anime-props (.. this -props -staticAnimeProps)
        refined-incoming-anime-props (refine-props incoming-anime-props static-anime-props)
        refined-current-anime-props (refine-props current-anime-props static-anime-props)
        ;; handle anime prop updates
        animation (if (not= refined-incoming-anime-props refined-current-anime-props)
                    (do
                      (.remove anime (string/join " " (:targets current-anime-props)))
                      (set-current-anime-props this incoming-anime-props)
                      (initialize-anime this incoming-anime-props))
                    (.. this -state -animation))]
    (when (and incoming-id (not= incoming-id id))
      (assoc-component-state this -id incoming-id))
    (cond pause (.pause animation)
          play (.play animation)
          seek (.seek animation seek)
          restart (.restart animation restart))))

(defn render-fn [this]
  (let [children (.. this -props -children)
        classes (.. this -props -extraClasses)
        style (js->clj (.. this -props -style))
        id (.. this -state -id)
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

