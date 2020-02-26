(ns my-website.views
  (:require
    [re-frame.core :refer [subscribe]]
    [my-website.subs :as subs]
    [my-website.components.grid :refer [grid]]
    [my-website.components.navbar :refer [navbar]]
    [my-website.components.menuitem :refer [menuitem]]
    [my-website.styles :refer [screen-sizes font-sizes spacing-sizes]]
    [my-website.utilities :refer [dark-background wrap-each-child wrap-all-children word-concat]]
    [my-website.views.home.panel :as home]
    [my-website.views.about.panel :as about]
    [my-website.views.games.panel :as games]
    [my-website.views.work.panel :as work]
    [spade.core :refer [defclass]]))

(def home-panel home/home-panel)
(def about-panel about/about-panel)
(def games-panel games/games-panel)
(def work-panel work/work-panel)

(defclass page-class []
          (at-media {:screen    :only
                     :max-width (:tiny screen-sizes)}
                    [:& {:padding-left  (:large spacing-sizes)
                         :padding-right (:large spacing-sizes)}]))

(defn main-panel []
  (let [current-view (-> @(subscribe [::subs/current-route]) :data :view)
        state @(subscribe [::subs/state])
        parent (fn [& children] (into [:> menuitem {:text-align "center"
                                                    :inverse   true
                                                    :strong    true
                                                    :padding   (:medium spacing-sizes)
                                                    :fontSize  "medium"}]
                                      children))
        link (fn [href name] [:a.delink {:href  href} name])]
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
      (when current-view
        [current-view])]]))
