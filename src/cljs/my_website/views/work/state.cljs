(ns my-website.views.work.state)

(def start 'fp)

(def fsm {'fp         {:next 'containers
                       :prev 'fp}
          'containers {:next 'android
                       :prev 'fp}
          'android    {:next 'common
                       :prev 'containers}
          'common     {:next 'common
                       :prev 'android}})