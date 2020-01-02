(ns my-website.db)

(def default-db
  {:name     "setootle"
   :state    nil
   :fsm      {}
   :games/db {:selected-game nil}
   :about/db {}
   :home/db  {}
   :work/db  {:all-work-items {:fp         {:names       ["hand-peace" "hand-peace" "hand-peace" "hand-peace"]
                                            :shelves     [2 25 8 34]
                                            :items       []
                                            :description {}}
                               :containers {:names       ["poo" "poo" "poo" "poo"]
                                            :shelves     [2 25 8 34]
                                            :items       []
                                            :description {}}
                               :android    {:names       ["star" "star" "star" "star"]
                                            :shelves     [2 25 8 34]
                                            :items       []
                                            :description {}}
                               :common     {:names       ["hand-spock" "hand-spock" "hand-spock" "hand-spock"]
                                            :shelves     [2 25 8 34]
                                            :items       []
                                            :description {}}}}})
