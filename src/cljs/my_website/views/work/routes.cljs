(ns my-website.views.work.routes
  (:require
    [my-website.views.work.panel :refer [panel]]
    [my-website.views.work.controllers :as controllers]))

(def routes
  ["work"
   {:name        :work
    :view        panel
    :link-text   "Work"
    :controllers controllers/work-controller}])
