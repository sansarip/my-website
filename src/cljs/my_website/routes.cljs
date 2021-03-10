(ns my-website.routes
  (:require
    [re-frame.core :as rfr]
    [reitit.frontend.easy :as rfe]
    [reitit.coercion.spec :as rss]
    [my-website.events :as events]
    [my-website.views.games.routes :as game-routes]
    [my-website.views.work.routes :as work-routes]
    [my-website.views.about.routes :as about-routes]
    [reitit.frontend :as rf]
    [my-website.views :as views]))

(def routes
  ["/"
   [""
    {:name      :home
     :view      views/home-panel
     :link-text "Home"}]
   about-routes/routes
   game-routes/routes
   work-routes/routes])

(defn on-navigate [new-match]
  (when new-match
    (rfr/dispatch [::events/navigated new-match])))

(def router
  (rf/router
    routes
    {:data {:coercion rss/coercion}}))

(defn init-routes! []
  (js/console.log "initializing routes")
  (rfe/start!
    router
    on-navigate
    {:use-fragment true}))
