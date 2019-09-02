(ns my-website.components.summary
  (:require [reagent.core :as r]
            [my-website.utilities :refer [word-concat wrap-all-children deep-merge]]
            [my-website.styles :refer [color-palette font-families font-sizes]]
            [my-website.components.flexbox :refer [flexbox]]
            [spade.core :refer [defattrs]]
            [clojure.spec.alpha :as s]))

(def header-style
  {:font-family (:header font-families)
   :margin      "0"
   :font-weight "1000"
   :white-space "nowrap"})

(defattrs summary-class [& {:keys [inverse font-size]
                            :or   {inverse   false
                                   font-size nil}}]
          [".container" {:padding "1em"
                         :color   (if inverse "white" (:primary color-palette))}]
          [".separator" {:background-color (:tertiary color-palette)
                         :width            "7em"
                         :height           ".5em"
                         :margin-top       "1em"
                         :margin-bottom    "1em"
                         :transition       "width 2s"}]
          ["h1" (merge header-style {:font-size (:massive font-sizes)
                                     :margin-top "1em"})]
          ["h2" (merge header-style {:font-size (:huge font-sizes)
                                     :margin-top "1em"})]
          ["h3" (merge header-style {:font-size (:big font-sizes)
                                     :margin-top "1em"})]
          ["h4" (merge header-style {:font-size (:large font-sizes)
                                     :margin-top "1em"})]
          [".container:hover > div[name=separator]" {:width "10em"}]
          [".description" {:font-family (:body font-families)
                           :width       "60%"
                           :height      "auto"}])

(defn render-fn [this] (let [inverse (-> this .-props .-inverse)
                             classes (-> this .-props .-extraClasses)
                             style (-> this .-props .-style)
                             children (-> this .-props .-children)
                             header (-> this .-props .-header)
                             as (-> this .-props .-as)
                             content (-> this .-props .-content)]
                         [:div (summary-class
                                 :inverse inverse)
                          [:div {:class (word-concat
                                          "container"
                                          classes)
                                 :style style}
                           children
                           (wrap-all-children (if as (js->clj as) :h1) header)
                           [:div {:class "separator"
                                  :name  "separator"}]
                           [:div {:class "description"}
                            content]]]))

(def summary
  (r/create-class {:render render-fn}))

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
                     #(s/valid? ::props (js->clj (-> (:this %) .-props) :keywordize-keys true)))
        :ret vector?)


