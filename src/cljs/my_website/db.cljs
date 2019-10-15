(ns my-website.db)

(def default-db
  {:name "setootle"
   :state [:ok 'home-panel]
   :games/db {:selected-game nil}
   :about/db {}
   :home/db {}})
