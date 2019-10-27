(ns my-website.views
  (:require
    [re-frame.core :as re-frame]
    [my-website.subs :as subs]
    [my-website.components.grid :refer [grid]]
    [my-website.components.navbar :refer [navbar]]
    [my-website.components.menuitem :refer [menuitem]]
    [my-website.styles :refer [screen-sizes font-sizes spacing-sizes]]
    [my-website.utilities :refer [dark-background wrap-each-child wrap-all-children word-concat]]
    [my-website.views.home.panel :refer [home-panel]]
    [my-website.views.about.panel :refer [about-panel]]
    [my-website.views.games.panel :refer [games-panel]]
    [spade.core :refer [defclass]]))

(defn- panels [panel]
  (cond
    (= panel 'home-panel) [home-panel]
    (= panel 'about-panel) [about-panel]
    (= panel 'games-panel) [games-panel]
    :else [:div "Nothing to see here!"]))

(defn show-panel [panel-name]
  [panels panel-name])

(defclass page-class []
          (at-media {:screen    :only
                     :max-width (:tiny screen-sizes)}
                    [:& {:padding-left  (:large spacing-sizes)
                         :padding-right (:large spacing-sizes)}]))

(defn main-panel []
  (let [state (re-frame/subscribe [::subs/state])
        parent (fn [& children] (into [:> menuitem {:textAlign "center"
                                                    :inverse   true
                                                    :strong    true
                                                    :padding   (:medium spacing-sizes)
                                                    :fontSize  "medium"}]
                                      children))
        link (fn [href name] [:a {:href  href
                                  :class "delink"} name])]

    [:div {:class (word-concat "padding-horizontal" (page-class))}
     (into [:> navbar {:as               #(parent (link "#/" "SETOOTLE"))
                       :widthCollapsible (:small screen-sizes)
                       :background-color "inherit"
                       :inverse          true}]
           (wrap-each-child parent
                            ["SHOP"
                             "WORK"
                             (link "#/games" "GAMES")
                             "SANDBOX"
                             "BLOG"
                             (link "#/about" "ABOUT")]))
     [:div {:class "padding-top"}
      [show-panel @state]]]))
