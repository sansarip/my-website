(ns my-website.cards.components.text
  (:require
    [devcards.core :refer-macros [defcard]]
    [sablono.core :as sab]
    [my-website.components.text :refer [text]]
    [reagent.core :refer [as-element]]))

(defcard
  markdown
  "basic text component with markdown"
  (fn []
    (sab/html (as-element [:> text {:src "# Testing?\n\n```python\nprint(\"Hello World!\")\n```\n* Bullet time!"}]))))

(defcard
  markdown
  "basic text component with markdown and children"
  (fn []
    (sab/html (as-element [:> text {:src "# MD Header1\n## Header 2"} [:p "Test paragraph"]]))))
