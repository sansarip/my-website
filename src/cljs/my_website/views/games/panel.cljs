(ns my-website.views.games.panel
  (:require
    [re-frame.core :as re-frame]
    [my-website.views.games.content :refer [content]]
    [my-website.styles :refer [color-palette screen-sizes font-sizes]]
    [my-website.components.flexbox :refer [flexbox]]
    [my-website.views.games.subs :as subs]
    [my-website.components.image :refer [image]]
    [my-website.utilities :refer [on-unit]]
    [my-website.components.summary :refer [summary]]))


(defn play-game [{:keys [header
                         more-info
                         href
                         width
                         height]}]
  [:> summary {:header         header
               :content        [:div
                                more-info]
               :width          (on-unit + width 33)
               :separatorColor (:secondary color-palette)
               :animated       false
               :height         "100%"
               :inverse        true}
   [:iframe
    {:src             href
     :class           "box-shadow-inverse"
     :scrolling       "no"
     :frameBorder     "0"
     :allowFullScreen true
     :style           {:width  "100%"
                       :height height}}]])


(defn game-summaries [& summaries]
  (map (fn [{:keys [key header description src alt]}]
         [:> summary {:header   header
                      :width    "20em"
                      :inverse  true
                      :content  description
                      :on-click #(set! (.. js/window -location)
                                       (str "#/games/" (name key)))}
          [:> image {:src           src
                     :extra-classes "box-shadow-inverse"
                     :alt           alt
                     :toggle        true
                     :width         "100%"
                     :height        "100%"}]])
       summaries))

(defn panel []
  (let [games (reduce-kv #(conj % (assoc %3 :key %2)) [] content)
        selected-game @(re-frame/subscribe [::subs/selected-game])]
    (into [:> flexbox {:justify-content "space-around"}]
          (if selected-game
            [[play-game (get content selected-game)]]
            (apply game-summaries games)))))

