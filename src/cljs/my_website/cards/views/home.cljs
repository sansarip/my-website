(ns my-website.cards.views.home
  (:require
    [reagent.core :refer [as-element]]
    [devcards.core :refer-macros [defcard]]
    [sablono.core :as sab]
    [reagent.core :refer [as-element]]
    [my-website.styles :refer [color-palette]]
    [my-website.components.flexbox :refer [flexbox]]
    [my-website.components.navbar :refer [navbar]]
    [my-website.components.menuitem :refer [menuitem]]
    [my-website.components.icon :refer [icon]]
    [my-website.components.grid :refer [grid]]
    [spade.core :refer [defclass]]
    [my-website.utilities :refer [seq->css-grid-areas dark-background wrap-each-child]]))

(defcard
  navbar
  "Home navbar"
  (fn []
    (sab/html (as-element (let [parent (fn [] [:> menuitem {:textAlign "center"
                                                            :inverse   true
                                                            :strong    true
                                                            :padding   "1em"
                                                            :fontSize  "medium"}])]
                            (into [:> navbar
                                   {:as              parent
                                    :title           "PEHRANS"
                                    :backgroundColor (:primary color-palette)
                                    :inverse         true}]
                                  (wrap-each-child parent
                                                   ["WORK"
                                                    "GAMES"
                                                    "SANDBOX"
                                                    "BLOG"
                                                    "ABOUT"])))))))

(defclass grid-class [areas]
          (at-media {:screen :only
                     :max-width "768px"}
                    [:& {:grid-template-areas (str areas " !important")}]))

(defcard
  home-page
  "Home page"
  (fn []
    (sab/html (as-element
                (let [parent (fn [] [:> menuitem {:textAlign "center"
                                                  :inverse   true
                                                  :strong    true
                                                  :padding   "1em"
                                                  :fontSize  "medium"}])
                      placeholder [:div {:style {:height           "30em"
                                                 :width            "20em"
                                                 :margin           "2em"
                                                 :grid-area        "avatar"
                                                 :background-color (:secondary color-palette)}
                                         :class "justify-center"}]]

                  (dark-background
                    [:> grid {:columns      ["1fr" "1fr"]
                              :rows         "auto"
                              :extraClasses (grid-class (seq->css-grid-areas [["navbar" "navbar"]
                                                                              ["avatar" "avatar"]
                                                                              ["main" "main"]]))
                              :padding      "0px"
                              :row-gap      "1em"
                              :areas        [["navbar" "navbar"]
                                             ["main" "avatar"]]}
                     (into [:> navbar
                            {:as      parent
                             :title   "PEHRANS"
                             :style   {:grid-area "navbar"}
                             :inverse true}]
                           (wrap-each-child parent
                                            ["WORK"
                                             "GAMES"
                                             "SANDBOX"
                                             "BLOG"
                                             "ABOUT"]))
                     [:div {:style {:grid-area "main"
                                    :padding   "2em"}}
                      [:h1 {:class "inverse"}
                       "Hi there, I'm Sepehr!"]
                      [:p {:class "inverse"}
                       "Lorem ipsum dolor amet fanny pack williamsburg tbh raw denim air plant.
                       taxidermy scenester selvage man braid hammock lyft occupy. 8-bit pickled +1
                       artisan tacos literally enamel pin kinfolk chicharrones glossier. 8-bit taxidermy
                       distillery authentic everyday carry flannel. Ethical af prism green juice plaid iceland,
                       truffaut deep v quinoa irony try-hard tbh PBR&B bushwick. DIY intelligentsia asymmetrical
                       brunch church-key slow-carb aesthetic air plant franzen vice. Freegan cold-pressed tote bag
                       migas slow-carb DIY four loko, woke fixie quinoa adaptogen master cleanse."]]
                     placeholder]))))))

