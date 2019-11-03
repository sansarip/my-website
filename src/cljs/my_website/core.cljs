(ns my-website.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [my-website.events :as events]
   [my-website.routes :as routes]
   [my-website.views :as views]))

(defn dev-setup []
  (when js/goog.DEBUG
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:after-load re-render []
  (mount-root))

(defn ^:export init []
  (routes/app-routes)
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (re-frame/clear-subscription-cache!)
  (mount-root))
