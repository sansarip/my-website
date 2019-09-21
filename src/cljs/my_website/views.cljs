(ns my-website.views
  (:require
   [re-frame.core :as re-frame]
   [my-website.subs :as subs]
   [spade.core :refer [defclass]]))

(defclass some-test []
          {:height "5em"
           :width "5em"
           :background-color "blue"}
          (at-media {:screen :only
                     :min-width "200px"}
                    {:margin-left "20em"}))


;; home

(defn home-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1 (str "Hello from " @name ". This is the Home Page.")]
     [:div {:class (some-test)}]
     [:div
      [:a {:href "#/about"}
       "go to About Page"]]]))



;; about

(defn about-panel []
  [:div
   [:h1 "This is the About Page."]

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

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [show-panel @active-panel]))
