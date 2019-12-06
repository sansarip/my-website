(ns my-website.views
  (:require
    [re-frame.core :refer [subscribe]]
    [my-website.subs :as subs]
    [my-website.components.grid :refer [grid]]
    [my-website.components.navbar :refer [navbar]]
    [my-website.components.menuitem :refer [menuitem]]
    [my-website.styles :refer [screen-sizes font-sizes spacing-sizes]]
    [my-website.utilities :refer [dark-background wrap-each-child wrap-all-children word-concat]]
    [my-website.views.home.panel :refer [home-panel]]
    [my-website.views.about.panel :refer [about-panel]]
    [my-website.views.games.panel :refer [games-panel]]
    [my-website.views.work.panel :refer [work-panel]]
    [spade.core :refer [defclass]]))

(defn- panels [panel]
  (condp = panel
    'home-panel [home-panel]
    'about-panel [about-panel]
    'games-panel [games-panel]
    'work-panel [work-panel]
    [:div "Nothing to see here!"]))

(defn show-panel [panel-name]
  [panels panel-name])

(defclass page-class []
          (at-media {:screen    :only
                     :max-width (:tiny screen-sizes)}
                    [:& {:padding-left  (:large spacing-sizes)
                         :padding-right (:large spacing-sizes)}]))

(defn main-panel []
  (let [state @(subscribe [::subs/state])
        active-panel @(subscribe [::subs/active-panel])
        parent (fn [& children] (into [:> menuitem {:textAlign "center"
                                                    :inverse   true
                                                    :strong    true
                                                    :padding   (:medium spacing-sizes)
                                                    :fontSize  "medium"}]
                                      children))
        link (fn [href name] [:a {:href  href
                                  :class "delink"} name])]
    [:div {:class (word-concat "padding-horizontal" (page-class))}
     (into ^{:key state}
           [:> navbar {:as               #(parent (link "#/" "SETOOTLE"))
                       :widthCollapsible (:small screen-sizes)
                       :background-color "inherit"
                       :inverse          true}]
           (wrap-each-child parent
                            ["SHOP"
                             (link "#/work" "WORK")
                             (link "#/games" "GAMES")
                             "SANDBOX"
                             "BLOG"
                             (link "#/about" "ABOUT")]))
     [:div {:class "padding-top"}
      [show-panel active-panel]]]))
