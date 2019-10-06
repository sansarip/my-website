(ns my-website.views
  (:require
    [re-frame.core :as re-frame]
    [my-website.subs :as subs]
    [my-website.components.grid :refer [grid]]
    [my-website.components.navbar :refer [navbar]]
    [my-website.components.menuitem :refer [menuitem]]
    [my-website.styles :refer [screen-sizes font-sizes spacing-sizes]]
    [my-website.utilities :refer [dark-background wrap-each-child word-concat]]
    [my-website.views.home.panel :refer [home-panel]]
    [spade.core :refer [defclass]]))

;; about

(defn about-panel []
  [:div
   [:h1 {:class "inverse"} "This is the About Page."]
   [:div
    [:a {:href "#/"}
     "go to Home Page"]]])

;; main

(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    :about-panel [about-panel]
    [:div "Nothing to see here!"]))

(defn show-panel [panel-name]
  [panels panel-name])

(defclass page-class []
          (at-media {:screen    :only
                     :max-width (:tiny screen-sizes)}
                    [:& {:padding-left  (:large spacing-sizes)
                         :padding-right (:large spacing-sizes)}]))

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])
        parent (fn [] [:> menuitem {:textAlign "center"
                                    :inverse   true
                                    :strong    true
                                    :padding   (:medium spacing-sizes)
                                    :fontSize  "medium"}])]
    [:div {:class (word-concat "padding-horizontal" (page-class))}
     (into [:> navbar {:title            "SETOOTLE"
                       :as               parent
                       :widthCollapsible (:small screen-sizes)
                       :background-color "inherit"
                       :inverse          true}]
           (wrap-each-child parent
                            ["SHOP"
                             "WORK"
                             "GAMES"
                             "SANDBOX"
                             "BLOG"
                             "ABOUT"]))
     [:div {:class "padding-top"}
      [show-panel @active-panel]]]))
