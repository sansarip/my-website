(ns my-website.cards.views.games
  (:require [devcards.core :refer-macros [defcard]]
            [sablono.core :as sab]
            [reagent.core :refer [as-element]]
            [my-website.styles :refer [color-palette]]
            [my-website.components.summary :refer [summary]]
            [my-website.components.flexbox :refer [flexbox]]
            [my-website.utilities :refer [dark-background]]))

(defcard
  summaries
  "Group of summary components"
  (fn [] (let [width "22.813em"
               placeholder [:div {:style {:width            "100%"
                                          :height           "18.750em"
                                          :background-color (:secondary color-palette)}}]]
           (sab/html (as-element (dark-background
                                   [:> flexbox
                                    {:justify "around"
                                     :wrap    "wrap"}
                                    [:> summary {:header  "Tootleoo"
                                                 :as      :h2
                                                 :width   width
                                                 :content "Can you defeat the meanie poots with your
                                                              magical toots?"
                                                 :inverse true}
                                     placeholder]
                                    [:> summary {:header  "Scratch My Patch"
                                                 :as      :h2
                                                 :width   width
                                                 :content "Satisfy your primal urge to scritch that
                                                              infernal itch!"
                                                 :inverse true}
                                     placeholder]
                                    [:> summary {:header  "Kiss My Piss"
                                                 :as      :h2
                                                 :width   width
                                                 :content "Approved by the man, the myth, the
                                                              legend—Richard Hendricks."
                                                 :inverse true}
                                     placeholder]]))))))

(defcard
  iframe-game
  "A simple test of my Phaser game implemented as an iframe"
  (fn []
    (sab/html [:iframe
               {:src   "https://sansarip.github.io/cs325-game-prototypes/Assignment3/"
                :scrolling "no"
                :frameborder "0"
                :allowfullscreen true
                :style {:width    "815px"
                        :border (str "1em solid " (:primary color-palette))
                        :height   "615px"}}])))

