(ns my-website.views.games.content
  (:require [my-website.components.text :refer [text]]
            [my-website.components.renderers :refer [gen-renderers]]
            [my-website.utilities :refer [deep-merge]]))

;; markdown for respective games
;; -----------------------------
(defonce ^{:private true}
         catching-clouds-md
         {:description (str "You play as the _woke_ cloud. Clouds are to blow in the breeze, but not you! "
                            "You control your own breeze (and destiny). Grab your fellow floofy companions, "
                            "and set sail! But do be cautious, for dangerous thunder bois lurk about. "
                            "This game is also "
                            "[hosted on Github](https://sansarip.github.io/cs325-game-prototypes/Assignment3/).")
          :more-info   (str
                         "### About\n"
                         "This was one of the first little Phaser games I made for a college elective about game "
                         "design. It ended up being a pretty amusing game to create _and_ play. Give it a whirl!\n\n"
                         "### Instructions\n"
                         "* Move around by clicking and holding down the mouse button\n"
                         "* Accrue points as you move, and collecting white clouds also grants 100 points\n"
                         "* Catching white clouds will also increases the rate at which you gain points, _but_ will "
                         "increase your size - up to a cap\n"
                         "* Watch out for the dark clouds - they can only move when you move, "
                         "and if they get you it's game-over\n\n"
                         "### Strategies\n"
                         "* The edges of the screen are very dangerous as that's where the dark clouds spawn, "
                         "so stay towards the center\n"
                         "* Collecting white clouds will increase your points and point-gain-rate, "
                         "but it will make you a bigger target - choose wisely"
                         "### Issues\n"
                         "* Horizontal movement may not be apparent when there are no other objects on the screen, "
                         "because the background doesn't have a horizontal gradient\n"
                         "* The music may not play upon game initialization, "
                         "but it should play if you touch a dark cloud\n\n"
                         "### Credits\n"
                         "* Art by [Anna Rose Chi](https://annarosechi.myportfolio.com/)\n"
                         "* Music sampled from [Masayoshi Takanaka - An Insatiable High LP 1977]"
                         "(https://www.youtube.com/watch?v=9cuxrkZeai8)\n"
                         "* [Phaser camera example](https://phaser.io/examples/v2/camera/basic-follow)\n"
                         "* [Phaser sound example](https://phaser.io/examples/v2/audio/sound-complete)")})

(defonce ^{:private true} bearly-iced-md
         {:description (str "You play as a young genius that has developed an ice gun. "
                            "To test the combat ability of such a weapon, you enter a "
                            "simulated environment with giant bears as enemies. "
                            "It was quite a joy to make a game that functioned on a simple grid system. "
                            "[Also on Github](https://sansarip.github.io/cs325-game-prototypes/Assignment4/)!")
          :more-info   (str
                         "### About\n"
                         "Crafted this little game for a game design elective in the college days. "
                         "It's nothing _too_ special, but I did enjoy developing it to run on a simple grid system."
                         "Also, the bears are _a d o r a b l e_ and the music is quite pleasant \uD83C\uDFB5 \n\n"
                         "### Instructions\n"
                         "* Use the arrow keys to move\n"
                         "* Press **A** to shoot left and **D** to shoot right - shoot the bears\n"
                         "* If a bear is close enough it can attack you and you'll lose a heart\n"
                         "* Losing all your hearts will result in game-over\n\n"
                         "### Issues\n"
                         "* The bears continue to move after the game has ended\n"
                         "* Player character can occupy the same spaces as bears\n\n"
                         "### Strategies\n"
                         "* Avoid the edged of the screen as those are where the bears will spawn\n\n"
                         "### Credits\n"
                         "* Art by [Anna Rose Chi](https://annarosechi.myportfolio.com/)\n"
                         "* Music sampled from "
                         "[Casiopea - Mint James (1982) - Midnight Rendezvous]"
                         "(https://www.youtube.com/watch?v=6GEI3PpXEAo&t=626s)\n"
                         "* [Tile sprite example](https://phaser.io/examples/v2/camera/basic-follow) "
                         "used to add the overlaying grid dynamically")})

(defonce ^{:private true} click-clack-md
         {:description "You play as a confident lad with a funky hairdo and a nack for hackin' - hacking the
         _DARK MAINFRAME_. Should be no problem for you, right?
         [Github](https://sansarip.github.io/cs325-game-prototypes/Assignment7/)!"
          :more-info   (str
                         "### About\n"
                         "Here be the final game I made for my game design elective; "
                         "it's certainly the most polished of the like. The game concept is nothing new - "
                         "typing games have been around since the dawn of Flash games. "
                         "Nevertheless, you don't see typing games _too_ often, "
                         "and this definitely was a joy to build and "
                         "really quite fun to play. Certainly brings back memories \uD83D\uDE22 \n\n"
                         "### Strategies\n"
                         "* You don't really get penalized for mistyping, "
                         " so feel free to mash your keys \uD83D\uDE09 \n\n"
                         "### Issues\n"
                         "* If you finish typing a word when no viruses have spawned, "
                         "you'll have to repeat the word you typed \n\n"
                         "### Instructions\n"
                         "* Type the word at the center of the screen to squash the viruses\n"
                         "* No need to worry about caps, everything should be lowercase/case-insensitive\n"
                         "* If a virus crosses the bottom of the screen, you will lose a cyber heart\n"
                         "* Enemies will speed up linearly every minute, up to a maximum of 5 minutes\n"
                         "* Enemy spawn rate increases after 3 minutes\n\n"
                         "### Credits\n"
                         "* Art by [Anna Rose Chi](https://annarosechi.myportfolio.com/)\n"
                         "* Music sampled from [LVX - The Naked Sun](https://www.youtube.com/watch?v=G0CWuyidm4Y)")})
;; -----------------------------

;; wrap the markdown strings in React text components
(defn to-text-components [md]
  (reduce-kv (fn [coll key val]
               (let [text-component [:> text {:src val}]]
                 (if (= key :description)
                   (assoc coll key (assoc-in text-component
                                             [2 :renderers]
                                             (gen-renderers :link-options
                                                            {:target "_self"})))
                   (assoc coll key text-component))))
             {}
             md))

(defonce content
         {:catching-clouds (deep-merge
                             {:header "Catching Clouds"
                              :src    ["assets/catching-clouds-1.png"
                                       "assets/catching-clouds.gif"]
                              :alt    "catching clouds"
                              :width  "815px"
                              :height "615px"
                              :href   "https://sansarip.github.io/cs325-game-prototypes/Assignment3/"}
                             (to-text-components catching-clouds-md))
          :bearly-iced     (deep-merge
                             {:header "Bearly Iced"
                              :src    ["assets/bearly-iced-1.png"
                                       "assets/bearly-iced.gif"]
                              :alt    "bearly iced"
                              :width  "783px"
                              :height "591px"
                              :href   "https://sansarip.github.io/cs325-game-prototypes/Assignment4/"}
                             (to-text-components bearly-iced-md))
          :click-clack     (deep-merge
                             {:header "Click Clack, I'm in!"
                              :src    ["assets/click-clack-1.png"
                                       "assets/click-clack.gif"]
                              :width  "815px"
                              :height "615px"
                              :alt    "click clack"
                              :href   "https://sansarip.github.io/cs325-game-prototypes/Assignment7/"}
                             (to-text-components click-clack-md))})