(ns my-website.views.games.panel
  (:require [spade.core :refer [defclass]]
            [my-website.styles :refer [color-palette screen-sizes font-sizes]]
            [my-website.components.flexbox :refer [flexbox]]
            [my-website.components.image :refer [image]]
            [my-website.components.menuitem :refer [menuitem]]
            [my-website.components.summary :refer [summary]]))

(defn make-game-summaries [& summaries]
  (map (fn [{:keys [header content src alt]}] [:> summary {:header  header
                                                           :width   "20em"
                                                           :inverse true
                                                           :content content}
                                               [:> image {:src          src
                                                          :extraClasses "box-shadow-inverse"
                                                          :alt          alt
                                                          :toggle       true
                                                          :width        "100%"
                                                          :height       "100%"}]])
       summaries))

(defn games-panel []
  (into [:> flexbox {:justify "around"}]
        (make-game-summaries
          {:header  "Catching Clouds"
           :content [:p
                     "One of my first Phaser games, I crafted this c u t e, lil' gem for an
                      elective during my senior year of college. You play as the
                      \"woke\" cloud. Clouds are to blow in the breeze, but not you! You control your
                      own breeze (and destiny). Grab your fellow floofy companions,
                      and set sail! But do be cautious, for dangerous thunder bois lurk about. This game is also "
                     [:> menuitem {:color      (:tertiary color-palette)
                                   :hoverColor (:tertiary-alt color-palette)
                                   :strong     true
                                   :delink     true
                                   :href       "https://sansarip.github.io/cs325-game-prototypes/Assignment3/"}
                      "hosted on Github"]
                     "."]
           :src     ["/assets/catching-clouds-1.png"
                     "/assets/catching-clouds.gif"]
           :alt     "catching clouds"}
          {:header  "Bearly Iced"
           :content [:p
                     "Another college-elective creation! You play as a young genius that has developed an ice gun.
                     To test the combat ability of such a weapon, you enter a simulated environment with giant bears as
                     enemies. It was quite a joy to make a game that functioned on a simple grid system. "
                     [:> menuitem {:color      (:tertiary color-palette)
                                   :hoverColor (:tertiary-alt color-palette)
                                   :strong     true
                                   :delink     true
                                   :href       "https://sansarip.github.io/cs325-game-prototypes/Assignment4/"}
                      "Also on Github"]
                     "!"]
           :src     ["/assets/bearly-iced-1.png"
                     "/assets/bearly-iced.gif"]
           :alt     "bearly iced"}
          {:header  "Click Clack, I'm in!"
           :content [:p
                     "I was really starting to get a hang of pumpin' out lil' bitty Phaser games towards the
                     end of the semester! You play as a confident lad with a funky hairdo and a nack for hackin' -
                     hacking the "
                     [:i "DARK MAINFRAME"]
                     " should be no problem for you, right? "
                     [:> menuitem {:color      (:tertiary color-palette)
                                   :hoverColor (:tertiary-alt color-palette)
                                   :strong     true
                                   :delink     true
                                   :href       "https://sansarip.github.io/cs325-game-prototypes/Assignment7/"}
                      "Github"]
                     "!"]
           :src     ["/assets/click-clack-1.png"
                     "/assets/click-clack.gif"]
           :alt     "click clack"})))

