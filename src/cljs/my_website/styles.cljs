(ns my-website.styles
  (:require [spade.core :refer [defglobal]]))

(def sizes {:mini    "2.188em"
            :tiny    "5em"
            :small   "9.375em"
            :medium  "18.750em"
            :large   "28.125em"
            :big     "37.5em"
            :huge    "50em"
            :massive "60em"})

(def font-sizes {:mini    ".55em"
                 :tiny    ".7em"
                 :small   ".85em"
                 :medium  "1em"
                 :large   "1.25em"
                 :big     "1.5em"
                 :huge    "2.25em"
                 :massive "3.125em"})

(def font-families {:body   "\"Work Sans\", sans-serif"
                    :header "\"Space Mono\", monospace"})

(def color-palette {:primary      "#131F33"
                    :secondary    "#CCDEFF"
                    :tertiary     "#F7976E"
                    :tertiary-alt "#CF6F46"
                    :quaternary   "#FFF8BE"})

(def screen-sizes {:tiny "650px"
                   :small "890px"
                   :medium "1210px"})

(def spacing-sizes {:medium "1em"
                    :large "2.5em"
                    :big "3.25em"
                    :huge "4em"
                    :massive "7.25em"})

(def header-style
  {:font-weight "1000"
   :color       (:primary color-palette)
   :font-family (:header font-families)
   :margin      "0"})

(defglobal window-styles
           [".no-word-wrap" {:white-space "nowrap"}]
           [".inverse" {:color "white"}]
           [".delink" {:color "inherit"
                       :text-decoration "inherit"}]
           [".line-height" {:line-height "155.8%"}]
           [".box-shadow-inverse"
            {:box-shadow "0 4px 8px 0 rgba(255, 255, 255, 0.2), 0 6px 20px 0 rgba(255, 255, 255, 0.19)"}]
           [".justify-center" {:justify-self "center"}]
           [".align-center" {:align-self "center"}]
           [".align-start" {:align-self "start"}]
           [".padding-horizontal" {:padding-left  (:massive spacing-sizes)
                                   :padding-right (:massive spacing-sizes)}]
           [".padding-top" {:padding-top (:huge spacing-sizes)}]
           [".padding-bottom" {:padding-bottom (:massive spacing-sizes)}]
           ["body" {:color            (:primary color-palette)
                    :font-family      (:body font-families)
                    :background-color (:primary color-palette)}]
           ["h1" (merge header-style {:font-size (:massive font-sizes)})]
           ["h2" (merge header-style {:font-size (:huge font-sizes)})]
           ["h3" (merge header-style {:font-size (:big font-sizes)})]
           ["h4" (merge header-style {:font-size (:large font-sizes)})])