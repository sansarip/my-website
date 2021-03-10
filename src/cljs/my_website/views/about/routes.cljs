(ns my-website.views.about.routes
  (:require [my-website.views.about.panel :refer [panel]]))

(def routes
  ["about"
   {:name      :about
    :view      panel
    :link-text "About"}])

