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
                 :huge    "1.75em"
                 :massive "3.125em"})

(def font-families {:body   "\"Work Sans\", sans-serif"
                    :header "\"Space Mono\", monospace"})

(def color-palette {:primary      "#131F33"
                    :secondary    "#CCDEFF"
                    :tertiary     "#F7976E"
                    :tertiary-alt "#CF6F46"
                    :quaternary   "#FFF8BE"})

(def header-style
  {:font-weight "1000"
   :font-family (:header font-families)
   :margin      "0"
   :white-space "nowrap"})

(defglobal window-styles
           ["h1" (merge header-style {:font-size (:massive font-sizes)})]
           ["h2" (merge header-style {:font-size (:huge font-sizes)})]
           ["h3" (merge header-style {:font-size (:big font-sizes)})]
           ["h4" (merge header-style {:font-size (:large font-sizes)})])