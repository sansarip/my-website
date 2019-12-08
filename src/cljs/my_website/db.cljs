(ns my-website.db)

(def default-db
  {:name     "setootle"
   :state    nil
   :fsm      {}
   :games/db {:selected-game nil}
   :about/db {}
   :home/db  {}
   :work/db  {:all-work-items {:clojure {:names   ["hand-peace" "hand-peace" "hand-peace" "hand-peace"]
                                         :shelves [2 25 8 34]}
                               :docker  {:names   ["poo" "poo" "poo" "poo"]
                                         :shelves [2 25 8 34]}
                               :android {:names   ["star" "star" "star" "star"]
                                         :shelves [2 25 8 34]}
                               :common  {:names   ["hand-spock" "hand-spock" "hand-spock" "hand-spock"]
                                         :shelves [2 25 8 34]}}}})
