(ns my-website.db)

(def default-db
  {:name     "setootle"
   :state    nil
   :fsm      {}
   :games/db {:selected-game nil}
   :about/db {}
   :home/db  {}
   :work/db  {}})
