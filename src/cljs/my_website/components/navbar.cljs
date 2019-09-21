(ns my-website.components.navbar
  (:require [reagent.core :as r]
            [my-website.utilities :refer [word-concat wrap-all-children omit-nil-keyword-args]]
            [my-website.components.flexbox :refer [flexbox]]
            [my-website.styles :refer [color-palette font-families]]
            [spade.core :refer [defclass]]
            [my-website.components.menuitem :refer [menuitem]]
            [my-website.components.icon :refer [icon]]
            [my-website.macros :refer-macros [assoc-component-state]]))



(defclass navbar-class [& {:keys [inverse]
                           :or   {inverse false}}]
          (at-media {:screen    :only
                     :max-width "768px"}
                    [".hide-desktop-navbar-children" {:display "none"}])
          (at-media {:screen    :only
                     :min-width "769px"}
                    [".hide-mobile-bars" {:display "none"}])
          {:color (if inverse "white" (:primary color-palette))}
          [".hide-mobile-navbar-children" {:opacity "0"}]
          [".mobile-navbar-children" {:max-height "0"
                                      :overflow   "hidden"
                                      :transition "max-height 1s ease,
                                                  opacity .5s ease-in"}]
          [".grow" {:max-height "20em"}])


(defn toggle-mobile-navbar-children [this]
  (let [mobileNavbarChildren (-> js/document
                                 (.getElementById (.. this
                                                      -state
                                                      -mobileNavbarChildrenId)))]
    (-> mobileNavbarChildren
        (.-classList)
        (.toggle "hide-mobile-navbar-children"))
    (-> mobileNavbarChildren
        (.-classList)
        (.toggle "grow"))
    (set! (.. this -state -isShowingMobileNavbarChildren)
          (not (-> mobileNavbarChildren
                   (.-classList)
                   (.contains "hide-mobile-navbar-children"))))))

(defn hide-mobile-navbar-children [this]
  (and (> (.-innerWidth js/window) 768)
       (.. this -state -isShowingMobileNavbarChildren)
       (toggle-mobile-navbar-children this)))

(defn get-initial-state-fn [this]
  #js {:mobileNavbarChildrenId        (str (random-uuid))
       :isShowingMobileNavbarChildren false
       :hideMobileNavbarChildrenFn    #(hide-mobile-navbar-children this)})

(defn mount-fn [this]
  (-> js/window (.addEventListener "resize" (.. this -state -hideMobileNavbarChildrenFn))))

(defn unmount-fn [this]
  (-> js/window (.removeEventListener "resize" (.. this -state -hideMobileNavbarChildrenFn))))

(defn render-fn [this]
  (let [children (.. this -props -children)
        title (.. this -props -title)
        as (.. this -props -as)
        classes (.. this -props -extraClasses)
        inverse (.. this -props -inverse)
        style (.. this -props -style)
        background-color (.. this -props -backgroundColor)]
    [:div {:class (omit-nil-keyword-args navbar-class :inverse inverse)}
     [:> flexbox {:justify         "between"
                  :extraClasses    classes
                  :align           "center"
                  :padding         "1em"
                  :backgroundColor background-color
                  :style           style}
      (wrap-all-children (if as (js->clj as) :h3) title)
      [:> flexbox {:justify      "between"
                   :extraClasses "hide-desktop-navbar-children"
                   :grow         true
                   :width        "60%"
                   :wrap         "wrap"}
       children]
      [:> icon {:extraClasses "hide-mobile-bars"
                :name         "bars"
                :strength     "strong"
                :inverse      true
                :onClick      #(toggle-mobile-navbar-children this)}]]
     [:> flexbox {:justify         "around"
                  :extraClasses    "hide-mobile-navbar-children mobile-navbar-children"
                  :id              (.. this -state -mobileNavbarChildrenId)
                  :backgroundColor background-color
                  :direction       "column"
                  :wrap            "none"
                  :align           "center"
                  :grow            true}
      children]]))

(def navbar (r/create-class {:display-name           :navbar
                             :get-initial-state      get-initial-state-fn
                             :component-did-mount    mount-fn
                             :component-will-unmount unmount-fn
                             :render                 render-fn}))