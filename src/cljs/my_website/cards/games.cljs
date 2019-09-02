(ns my-website.cards.games
  (:require [devcards.core :refer-macros (defcard)]
            [sablono.core :as sab]
            [reagent.core :refer [as-element]]
            [my-website.styles :refer [color-palette]]
            [my-website.components.summary :refer [summary]]
            [my-website.components.flexbox :refer [flexbox]]
            [my-website.utilities :refer [dark-background]]))

(defcard
  summaries
  "Group of summary components"
  (fn [] (sab/html (as-element (dark-background [:> flexbox
                                                 {:justify "between"}
                                                 [:> summary {:header  "Tootleoo"
                                                              :as      :h2
                                                              :content "Can you defeat the meanie poots with your
                                                              magical toots?"
                                                              :inverse true}]
                                                 [:> summary {:header  "Scratch My Patch"
                                                              :as      :h2
                                                              :content "Satisfy your primal urge to scritch that
                                                              infernal itch!"
                                                              :inverse true}]
                                                 [:> summary {:header  "Kiss My Piss"
                                                              :as      :h2
                                                              :content "Approved by the man, the myth, the
                                                              legend—Richard Hendricks."
                                                              :inverse true}]])))))