(ns my-website.db)

(def default-db
  {:name     "setootle"
   :state    nil
   :fsm      {}
   :games/db {:selected-game nil}
   :about/db {}
   :home/db  {}
   :work/db  {:work-items
                                [{:names       ["hand-peace" "hand-peace" "hand-peace" "hand-peace"]
                                  :name        :fp
                                  :shelves     [2 25 8 34]
                                  :items       []
                                  :description {}}
                                 {:names       ["poo" "poo" "poo" "poo"]
                                  :index       1
                                  :name        :containers
                                  :shelves     [2 25 8 34]
                                  :items       []
                                  :description {}}
                                 {:names       ["star" "star" "star" "star"]
                                  :name        :mobile
                                  :shelves     [2 25 8 34]
                                  :items       []
                                  :description {}}
                                 {:names       ["hand-spock" "hand-spock" "hand-spock" "hand-spock"]
                                  :name        :common
                                  :shelves     [2 25 8 34]
                                  :items       []
                                  :description {}}]
              :work-items-index 0}})
