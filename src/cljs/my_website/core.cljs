(ns ^:figwheel-hooks my-website.core
  (:require
    [reagent.core :refer [render]]
    [re-frame.core :as rf]
    [my-website.events :as events]
    [my-website.routes :as routes]
    [my-website.views :as views]))

(defn dev-setup []
  (when js/goog.DEBUG
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (rf/clear-subscription-cache!)
  (routes/init-routes!)
  (render [views/main-panel]
          (.getElementById js/document "app")))

(defn ^:after-load re-render []
  (mount-root))

(defn ^:export init []
  (rf/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
