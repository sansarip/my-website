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
                            :or   {inverse     false
                                   width       "auto"
                                   background  "none"
                                   wrap-header true
                                   animated true
                                   clickable false
                                   separator-color (:tertiary color-palette)}}]
          {:cursor (if clickable "pointer" "auto")
           :width width}
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
                                     :color (if inverse "white" (:primary color-palette))
                                     :white-space (if wrap-header "normal" "nowrap")})]
          ["h2" (merge header-style {:margin-top  "1em"
                                     :color (if inverse "white" (:primary color-palette))
                                     :white-space (if wrap-header "normal" "nowrap")})]
          ["h3" (merge header-style {:margin-top  "1em"
                                     :color (if inverse "white" (:primary color-palette))
                                     :white-space (if wrap-header "normal" "nowrap")})]
          ["h4" (merge header-style {:margin-top  "1em"
                                     :color (if inverse "white" (:primary color-palette))
                                     :white-space (if wrap-header "normal" "nowrap")})]
          [".container:hover > div[name=separator]" {:width (if animated "10em"
                                                                         "6.5em")}]
          [".description" {:font-family (:body font-families)
                           :width       (if (not= width "auto") "auto" "65%")
                           :height      "auto"}])

(defn render-fn [this] (let [inverse (.. this -props -inverse)
                             classes (.. this -props -extraClasses)
                             style (.. this -props -style)
                             children (.. this -props -children)
                             header (.. this -props -header)
                             as (.. this -props -as)
                             animated (.. this -props -animated)
                             content (.. this -props -content)
                             width (.. this -props -width)
                             href (.. this -props -href)
                             id (.. this -props -id)
                             name (.. this -props -name)
                             background (.. this -props -background)
                             on-click (.. this -props -onClick)
                             separator-color (.. this -props -separatorColor)
                             wrap-header (.. this -props -wrapHeader)
                             clickable (boolean on-click)]
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
                                :href href
                                :id id
                                :name name}
                          [:> flexbox {:extraClasses (word-concat
                                                       "container"
                                                       classes)
                                       :style        style
                                       :direction    "column"
                                       :justify      "start"
                                       :width        width
                                       :align        "start"}
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

;; spec

(s/def ::header string?)
(s/def ::style map?)
(s/def ::extra-classes vector?)
(s/def ::children vector?)
(s/def ::description string?)
(s/def ::inverse boolean?)
(s/def ::props (s/keys :req-un [::header ::description]
                       :opt-un [::extra-classes ::style ::children ::inverse]))

(s/fdef render-fn
        :args (s/and #(fn? (:this %))
                     #(s/valid? ::props (js->clj (.. (:this %) -props) :keywordize-keys true)))
        :ret vector?)


