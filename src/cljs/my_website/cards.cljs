(ns my-website.cards
  (:require [devcards.core :as dc]
            [my-website.cards.components]
            [my-website.cards.home]
            [my-website.cards.games]))

(enable-console-print!)

(defn ^:export init []
  (dc/start-devcard-ui!))
