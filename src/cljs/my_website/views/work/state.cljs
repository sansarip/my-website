(ns my-website.views.work.state)

(def start 'clojure)

(def fsm {'clojure {:next 'docker
                    :prev 'clojure}
          'docker  {:next 'android
                    :prev 'clojure}
          'android {:next 'common
                    :prev 'docker}
          'common  {:next 'common
                    :prev 'android}})