(ns my-website.server
  (:require [my-website.handler :refer [app]]
            [config.core :refer [env]]
            [ring.adapter.jetty :refer [run-jetty]])
  (:gen-class))

(defn -main [& _args]
  (let [port (-> (env :port) (or "3000") str Integer/parseInt)]
    (run-jetty app {:port port :join? false})))
