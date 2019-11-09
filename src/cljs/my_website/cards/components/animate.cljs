(ns my-website.cards.components.animate
  (:require [reagent.core :as r]
            [devcards.core :refer-macros [defcard]]
            [sablono.core :as sab]
            [anime]
            [my-website.components.animate :refer [animate]]
            [my-website.components.icon :refer [icon]]
            [my-website.components.menuitem :refer [menuitem]]))

(defonce state-1 (r/atom 0))
(defonce interval-fn (.setInterval js/window #(swap! state-1 inc) 1000))
(defonce state-2 (r/atom false))

(defcard anime-js
         "Basic animation example using an icon and anime.js"
         (fn [state]
           (sab/html
             (r/as-element
               [:div
                [:p @state]
                [:> icon {:size     :huge
                          :strength "strong"
                          :iconName (if (> @state 10) "hand-spock" "hand-peace")
                          :id       "coocoo"
                          :onClick  #(anime (clj->js {:targets    ["#coocoo"]
                                                      :translateX 250
                                                      :rotate     "1turn"
                                                      :duration   2000}))}]])))
         state-1)

(defcard animate
         "Basic animation example using an icon and animate component"
         (sab/html
           (r/as-element
             [:> animate {:animeProps {:targets    ["#coocoo-2"]
                                       :translateX 250
                                       :rotate     "1turn"
                                       :duration   2000}}
              [:> icon {:size     :huge
                        :id       "coocoo-2"
                        :strength :strong
                        :iconName "hand-spock"}]])))

(defcard animate-pause
         "Pausing an animate component"
         (fn [state]
           (sab/html
             (r/as-element
               [:div
                [:> animate {:animeProps {:targets  ["#coocoo-3"]
                                          :loop     true
                                          :rotate   "1turn"
                                          :duration 1000}
                             :pause    @state
                             :play     (not @state)}
                 [:> icon {:size     :huge
                           :id       "coocoo-3"
                           :strength :strong
                           :iconName "hand-spock"}]]
                [:> menuitem {:textAlign "center"
                              :strong    true
                              :on-click  #(reset! state (not @state))
                              :fontSize  "large"} "Pause/Resume"]])))
         state-2)
