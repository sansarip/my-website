(ns my-website.components.summary
  (:require [reagent.core :as r]
            [my-website.utilities :refer [word-concat wrap-all-children deep-merge omit-nil-keyword-args]]
            [my-website.styles :refer [color-palette font-families font-sizes]]
            [my-website.components.flexbox :refer [flexbox]]
            [spade.core :refer [defclass]]
            [clojure.spec.alpha :as s]))

(def header-style
  {:font-family (:header font-families)
   :margin      "0"
   :font-weight "1000"})

(defclass summary-class [& {:keys [inverse width background wrap-header]
                            :or   {inverse     false
                                   width       "auto"
                                   background  "none"
                                   wrap-header true}}]
          {:cursor "pointer"
           :width width}
          [".container" {:padding       "1em"
                         :background    background
                         :border-radius "1%"
                         :color         (if inverse "white" (:primary color-palette))}]
          [".separator" {:background-color (:tertiary color-palette)
                         :width            "6.5em"
                         :height           ".5em"
                         :margin-top       "1em"
                         :margin-bottom    "1em"
                         :transition       "width 1s"}]
          ["h1" (merge header-style {:font-size   (:massive font-sizes)
                                     :margin-top  "1em"
                                     :white-space (if wrap-header "normal" "nowrap")})]
          ["h2" (merge header-style {:font-size   (:huge font-sizes)
                                     :margin-top  "1em"
                                     :white-space (if wrap-header "normal" "nowrap")})]
          ["h3" (merge header-style {:font-size   (:big font-sizes)
                                     :margin-top  "1em"
                                     :white-space (if wrap-header "normal" "nowrap")})]
          ["h4" (merge header-style {:font-size   (:large font-sizes)
                                     :margin-top  "1em"
                                     :white-space (if wrap-header "normal" "nowrap")})]
          [".container:hover > div[name=separator]" {:width "10em"}]
          [".description" {:font-family (:body font-families)
                           :width       (if (not= width "auto") "auto" "65%")
                           :height      "auto"}])

(defn render-fn [this] (let [inverse (.. this -props -inverse)
                             classes (.. this -props -extraClasses)
                             style (.. this -props -style)
                             children (.. this -props -children)
                             header (.. this -props -header)
                             as (.. this -props -as)
                             content (.. this -props -content)
                             width (.. this -props -width)
                             background (.. this -props -background)
                             on-click (.. this -props -onClick)
                             wrap-header (.. this -props -wrapHeader)]
                         [:div {:class    (omit-nil-keyword-args
                                            summary-class
                                            :inverse inverse
                                            :width width
                                            :background background
                                            :wrap-header wrap-header)
                                :on-click on-click}
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
                           [:div {:class "description"}
                            content]]]))

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


