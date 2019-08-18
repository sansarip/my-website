(ns test1.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [test1.events :as events]
   [test1.routes :as routes]
   [test1.views :as views]
   [test1.config :as config]
   ))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (routes/app-routes)
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
