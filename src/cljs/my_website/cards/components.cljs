(ns my-website.cards.components
  (:require [devcards.core :refer-macros (defcard)]
            [sablono.core :as sab]
            [reagent.core :refer [as-element]]
            [my-website.styles :refer [color-palette]]
            [my-website.components.summary :refer [summary]]))

(defcard
  summary
  "Basic summary component"
  (fn [] (sab/html (as-element [:div {:style {:display "flex"
                                              :justify-content "space-between"
                                              :background-color (:primary color-palette)}}
                                [:> summary {:header "Scratch My Patch"
                                             :inverse true}
                                 "Satisfy your primal urge to scritch that infernal itch!"]]))))
(defcard
  summaries
  "Group of summary components"
  (fn [] (sab/html (as-element [:div {:style {:display "flex"
                                              :justify-content "space-between"
                                              :flex-wrap "wrap"
                                              :background-color (:primary color-palette)}}
                                [:> summary {:header "Tootleoo"
                                             :inverse true}
                                 "Can you defeat the meanie poots with your magical toots?"]
                                [:> summary {:header  "Scratch My Patch"
                                             :inverse true}
                                 "Satisfy your primal urge to scritch that infernal itch!"]
                                [:> summary {:header  "Kiss My Piss"
                                             :inverse true}
                                 "Approved by the man, the myth, the legend—Richard Hendricks."]]))))



