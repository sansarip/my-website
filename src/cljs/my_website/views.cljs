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
    [spade.core :refer [defclass]]))

(def home-panel home/home-panel)

(defclass page-class []
  (at-media {:screen    :only
             :max-width (:tiny screen-sizes)}
            [:& {:padding-left  (:large spacing-sizes)
                 :padding-right (:large spacing-sizes)}]))

(defn main-panel []
  (letfn [(link [href name] [:a.delink {:href href} name])
          (nav-link-parent [& children]
            [:> menuitem {:text-align "center"
                          :inverse    true
                          :strong     true
                          :padding    (:medium spacing-sizes)
                          :fontSize   "medium"}
             (into [:<>] children)])]

    (fn []
      (let [current-view (-> @(subscribe [::subs/current-route]) :data :view)]
        [:div {:class (word-concat "padding-horizontal" (page-class))}
         [:> navbar {:as               #(nav-link-parent (link "#/" "SETOOTLE"))
                     :widthCollapsible (:small screen-sizes)
                     :background-color "inherit"
                     :inverse          true}
          (into
            [:<>]
            (wrap-each-child
              nav-link-parent
              [#_"SHOP"
               [link "#/work" "WORK"]
               [link "#/games" "GAMES"]
               #_"SANDBOX"
               #_"BLOG"
               [link "#/about" "ABOUT"]]))]
         [:div {:class "padding-top"}
          (if current-view [current-view])]]))))
