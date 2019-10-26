(ns my-website.cards
  (:require [devcards.core :as dc]
            [my-website.cards.components.grid]
            [my-website.cards.components.header]
            [my-website.cards.components.icon]
            [my-website.cards.components.image]
            [my-website.cards.components.menuitem]
            [my-website.cards.components.navbar]
            [my-website.cards.components.summary]
            [my-website.cards.components.text]
            [my-website.cards.home]
            [my-website.cards.games]))

(enable-console-print!)

(defn ^:export init []
  (dc/start-devcard-ui!))
