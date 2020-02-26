(ns my-website.db)

(def default-db
  {:name     "setootle"
   :state    'start
   :games/db {:state 'start
              :selected-game nil}
   :about/db {:state 'start}
   :home/db  {:state 'start}
   :work/db  {:state 'start
              :work-items [{:names       ["hand-peace" "hand-peace" "hand-peace" "hand-peace"]
                            :name        :fp
                            :shelves     [2 25 8 34]
                            :items       []
                            :description {}}
                           {:names       ["poo" "poo" "poo" "poo"]
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
