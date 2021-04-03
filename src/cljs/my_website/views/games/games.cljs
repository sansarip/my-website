(ns my-website.views.games.games
  (:require [my-website.components.text :refer [text]]
            [my-website.components.renderers :refer [renderer-options]]
            [my-website.utilities :refer [deep-merge]]
            [my-website.views.games.content :as content]))

;; wrap the markdown strings in React text components
(defn ->text-components [md]
  (reduce-kv
    (fn [coll key val]
      (assoc
        coll
        key
        [:> text
         (cond-> {:src val}
                 (= key :description) (assoc
                                        :renderers
                                        (renderer-options {:target "_self"})))]))
    {}
    md))

(def games
  {:catching-clouds (deep-merge
                      {:header "Catching Clouds"
                       :src    ["assets/catching-clouds-1.png"
                                "assets/catching-clouds.gif"]
                       :alt    "catching clouds"
                       :key    :catching-clouds
                       :width  "783px"
                       :height "591px"
                       :href   "https://sansarip.github.io/cs325-game-prototypes/Assignment3/"}
                      (->text-components content/catching-clouds-md))
   :bearly-iced     (deep-merge
                      {:header "Bearly Iced"
                       :src    ["assets/bearly-iced-1.png"
                                "assets/bearly-iced.gif"]
                       :alt    "bearly iced"
                       :key    :bearly-iced
                       :width  "783px"
                       :height "591px"
                       :href   "https://sansarip.github.io/cs325-game-prototypes/Assignment4/"}
                      (->text-components content/bearly-iced-md))
   :click-clack     (deep-merge
                      {:header "Click Clack, I'm in!"
                       :src    ["assets/click-clack-1.png"
                                "assets/click-clack.gif"]
                       :alt    "click clack"
                       :key    :click-clack
                       :width  "783px"
                       :height "591px"
                       :href   "https://sansarip.github.io/cs325-game-prototypes/Assignment7/"}
                      (->text-components content/click-clack-md))})