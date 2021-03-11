(ns my-website.components.summary
  (:require [reagent.core :as r]
            [my-website.utilities :refer [word-concat wrap-all-children deep-merge omit-nil-keyword-args]]
            [my-website.styles :refer [header-style color-palette font-families font-sizes]]
            [my-website.components.flexbox :refer [flexbox]]
            [spade.core :refer [defclass]]
            [clojure.spec.alpha :as s]))

(defclass summary-class [& {:keys [inverse
                                   width
                                   background
                                   wrap-header
                                   animated
                                   clickable
                                   separator-color]
                            :or   {inverse         false
                                   width           "auto"
                                   background      "none"
                                   wrap-header     true
                                   animated        true
                                   clickable       false
                                   separator-color (:tertiary color-palette)}}]
  {:cursor (if clickable "pointer" "auto")
   :width  width}
  [".container" {:padding       "1em"
                 :background    background
                 :border-radius "1%"
                 :color         (if inverse "white" (:primary color-palette))}]
  [".separator" {:background-color separator-color
                 :width            "6.5em"
                 :height           ".5em"
                 :margin-top       "1em"
                 :margin-bottom    "1em"
                 :transition       "width 1s"}]
  ["h1" (merge header-style {:margin-top  "1em"
                             :color       (if inverse "white" (:primary color-palette))
                             :white-space (if wrap-header "normal" "nowrap")})]
  ["h2" (merge header-style {:margin-top  "1em"
                             :color       (if inverse "white" (:primary color-palette))
                             :white-space (if wrap-header "normal" "nowrap")})]
  ["h3" (merge header-style {:margin-top  "1em"
                             :color       (if inverse "white" (:primary color-palette))
                             :white-space (if wrap-header "normal" "nowrap")})]
  ["h4" (merge header-style {:margin-top  "1em"
                             :color       (if inverse "white" (:primary color-palette))
                             :white-space (if wrap-header "normal" "nowrap")})]
  [".container:hover > div[name=separator]" {:width (if animated "10em"
                                                                 "6.5em")}]
  [".description" {:font-family (:body font-families)
                   :width       (if (not= width "auto") "auto" "65%")
                   :height      "auto"}])

(defn render-fn [this]
  (let [animated (.. this -props -animated)
        as (.. this -props -as)
        background (.. this -props -background)
        children (.. this -props -children)
        classes (.. this -props -extraClasses)
        on-click (.. this -props -onClick)
        clickable (boolean on-click)
        content (.. this -props -content)
        header (.. this -props -header)
        href (.. this -props -href)
        id (.. this -props -id)
        inverse (.. this -props -inverse)
        name (.. this -props -name)
        separator-color (.. this -props -separatorColor)
        style (.. this -props -style)
        width (.. this -props -width)
        wrap-header (.. this -props -wrapHeader)]
    [:div {:class    (omit-nil-keyword-args
                       summary-class
                       :inverse inverse
                       :clickable clickable
                       :width width
                       :animated animated
                       :background background
                       :separator-color separator-color
                       :wrap-header wrap-header)
           :on-click on-click
           :href     href
           :id       id
           :name     name}
     [:> flexbox {:extra-classes   (word-concat
                                     "container"
                                     classes)
                  :style           style
                  :flex-direction  "column"
                  :justify-content "flex-start"
                  :width           width
                  :align-content   "start"}
      children
      (wrap-all-children (if as (js->clj as) :h2) header)
      [:div {:class "separator"
             :name  "separator"}]
      [:div {:class "description line-height"}
       (if (array? content)
         (js->clj content)
         content)]]]))

(def summary
  (r/create-class {:display-name :summary
                   :render       render-fn}))
