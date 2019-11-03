(ns my-website.components.navbar
  (:require [reagent.core :as r]
            [my-website.utilities :refer [word-concat wrap-all-children omit-nil-keyword-args on-unit]]
            [my-website.components.flexbox :refer [flexbox]]
            [my-website.styles :refer [color-palette font-families]]
            [spade.core :refer [defclass]]
            [my-website.components.menuitem :refer [menuitem]]
            [my-website.components.icon :refer [icon]]
            [my-website.macros :refer-macros [assoc-component-state]]))



(defclass navbar-class [& {:keys [inverse width-collapsible]
                           :or   {inverse           false
                                  width-collapsible "768px"}}]
          (at-media {:screen    :only
                     :max-width width-collapsible}
                    [".hide-desktop-navbar-children" {:display "none"}])
          (at-media {:screen    :only
                     :min-width (on-unit inc width-collapsible)}
                    [".hide-mobile-bars" {:display "none"}])
          {:color (str (if inverse "white" (:primary color-palette)))}
          ["h1" {:color "white"}]
          ["h2" {:color "white"}]
          ["h3" {:color "white"}]
          ["h4" {:color "white"}]
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
        padding (.. this -props -padding)
        width-collapsible (.. this -props -widthCollapsible)
        background-color (.. this -props -backgroundColor)
        id (.. this -props -id)
        name (.. this -props -name)]
    [:div {:class (omit-nil-keyword-args navbar-class
                                         :width-collapsible width-collapsible
                                         :inverse inverse)
           :style style
           :id    id
           :name  name}
     [:> flexbox {:justify         "between"
                  :extraClasses    classes
                  :alignItems      "center"
                  :padding         padding
                  :backgroundColor background-color}
      (wrap-all-children (if as (js->clj as) :h3) title)
      [:> flexbox {:justify      "around"
                   :extraClasses "hide-desktop-navbar-children"
                   :grow         true
                   :width        "auto"
                   :wrap         "wrap"}
       children]
      [:> menuitem {:extraClasses "hide-mobile-bars"
                    :inverse      inverse}
       [:> icon {:name         "bars"
                 :inheritColor true
                 :strength     "strong"
                 :inverse      true
                 :onClick      #(toggle-mobile-navbar-children this)}]]]
     [:> flexbox {:justify         "around"
                  :extraClasses    "hide-mobile-navbar-children mobile-navbar-children"
                  :id              (.. this -state -mobileNavbarChildrenId)
                  :backgroundColor background-color
                  :direction       "column"
                  :wrap            "none"
                  :align           "around"
                  :align-items     "center"
                  :grow            true}
      children]]))

(def navbar (r/create-class {:display-name           :navbar
                             :get-initial-state      get-initial-state-fn
                             :component-did-mount    mount-fn
                             :component-will-unmount unmount-fn
                             :render                 render-fn}))