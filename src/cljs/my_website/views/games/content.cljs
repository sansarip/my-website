(ns my-website.views.games.content
  (:require [my-website.components.menuitem :refer [menuitem]]
            [my-website.styles :refer [color-palette]]))

(defonce content
         {:catching-clouds {:header       "Catching Clouds"
                            :content      [:p
                                           "One of my first Phaser games, I crafted this c u t e, lil' gem for an
                                           elective during my senior year of college. You play as the \"woke\" cloud.
                                           Clouds are to blow in the breeze, but not you! You control your own breeze
                                           (and destiny). Grab your fellow floofy companions, and set sail! But do be
                                           cautious, for dangerous thunder bois lurk about. This game is also "
                                           [:> menuitem {:color      (:tertiary color-palette)
                                                         :hoverColor (:tertiary-alt color-palette)
                                                         :strong     true
                                                         :delink     true
                                                         :href       "https://sansarip.github.io/cs325-game-prototypes/Assignment3/"}
                                            "hosted on Github"]
                                           "."]
                            :src          ["/assets/catching-clouds-1.png"
                                           "/assets/catching-clouds.gif"]
                            :alt          "catching clouds"
                            :width        "815px"
                            :height       "615px"
                            :instructions "You can move around by clicking and holding down the mouse button. You
                            accrue points as you move, and collecting white clouds also gives you points! Watch out
                            for the dark clouds - they can only move when you move, and if they get you it's
                            game-over! Horizontal movement may not be apparent when there are no other objects on the
                            screen because the background doesn't have a horizontal gradient, sorry!"
                            :href         "https://sansarip.github.io/cs325-game-prototypes/Assignment3/"}
          :bearly-iced     {:header       "Bearly Iced"
                            :content      [:p
                                           "Another college-elective creation! You play as a young genius that has
                                           developed an ice gun. To test the combat ability of such a weapon, you enter
                                           a simulated environment with giant bears as enemies. It was quite a joy to
                                            make a game that functioned on a simple grid system. "
                                           [:> menuitem {:color      (:tertiary color-palette)
                                                         :hoverColor (:tertiary-alt color-palette)
                                                         :strong     true
                                                         :delink     true
                                                         :href       "https://sansarip.github.io/cs325-game-prototypes/Assignment4/"}
                                            "Also on Github"]
                                           "!"]
                            :src          ["/assets/bearly-iced-1.png"
                                           "/assets/bearly-iced.gif"]
                            :alt          "bearly iced"
                            :width        "783px"
                            :height       "591px"
                            :instructions "Use the arrow keys to move! Press A to shoot left! Press D to shoot right!
                            Shoot the bears, don't let them get near you!"
                            :href         "https://sansarip.github.io/cs325-game-prototypes/Assignment4/"}
          :click-clack     {:header       "Click Clack, I'm in!"
                            :content      [:p
                                           "I was really starting to get a hang of pumpin' out lil' bitty Phaser games
                                           towards the end of the semester! You play as a confident lad with a funky
                                           hairdo and a nack for hackin' - hacking the "
                                           [:i "DARK MAINFRAME"]
                                           " should be no problem for you, right? "
                                           [:> menuitem {:color      (:tertiary color-palette)
                                                         :hoverColor (:tertiary-alt color-palette)
                                                         :strong     true
                                                         :delink     true
                                                         :href       "https://sansarip.github.io/cs325-game-prototypes/Assignment7/"}
                                            "Github"]
                                           "!"]
                            :src          ["/assets/click-clack-1.png"
                                           "/assets/click-clack.gif"]
                            :width        "815px"
                            :height       "615px"
                            :instructions "Type the word at the center of the screen! No need to worry about caps,
                            everything should be lowercase/case-insensitive. If a virus crosses the bottom of the
                            screen, you will lose a cyber heart!"
                            :alt          "click clack"
                            :href         "https://sansarip.github.io/cs325-game-prototypes/Assignment7/"}})