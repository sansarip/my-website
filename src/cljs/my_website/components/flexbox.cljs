(ns my-website.components.flexbox
  (:require [reagent.core :as r]
            [my-website.utilities :refer [deep-merge word-concat omit-nil-keyword-args]]
            [my-website.styles :refer [color-palette]]
            [spade.core :refer [defclass]]
            [my-website.macros :refer-macros [assoc-component-state get-component-prop]]))

(defclass flexbox-class [& {:keys [align
                                   justify
                                   direction
                                   padding
                                   width
                                   wrap
                                   overflowed
                                   text-align
                                   grow
                                   background-color]
                            :or   {align            "start"
                                   justify          "between"
                                   wrap             "wrap"
                                   direction        "row"
                                   padding          "0"
                                   background-color "none"
                                   width            "auto"
                                   overflowed       false
                                   text-align       "start"
                                   grow             false}}]
          {:display          "flex"
           :flex-wrap        (cond (= wrap "rigid") "nowrap"
                                   :else wrap)
           :padding-left     padding
           :padding-right    padding
           :background-color background-color
           :max-width        (if (not grow) width "auto")
           :min-width        (if grow width "auto")
           :align-items      (cond (= align "start") "flex-start"
                                   (= align "end") "flex-end"
                                   :else align)
           :align-content    "space-around"
           :text-align       text-align
           :justify-content  (cond (= justify "between") "space-between"
                                   (= justify "around") "space-around"
                                   (= justify "evenly") "space-evenly"
                                   (= justify "start") "flex-start"
                                   (= justify "end") "flex-end"
                                   :else justify)
           :flex-direction   (cond (and (= wrap "rigid") overflowed) "column"
                                   :else direction)})

(defn render-fn [this]
  (let [children (.. this -props -children)
        justify (.. this -props -justify)
        classes (.. this -props -extraClasses)
        align (.. this -props -align)
        style (.. this -props -style)
        id (or (.. this -props -id) (.. this -state -id))
        padding (.. this -props -padding)
        width (.. this -props -width)
        text-align (.. this -props -textAlign)
        grow (.. this -props -grow)
        background-color (.. this -props -backgroundColor)
        overflowed (.. this -state -overflowed)
        wrap (.. this -props -wrap)
        direction (.. this -props -direction)]
    [:div {:class (word-concat
                    (omit-nil-keyword-args flexbox-class
                                           :justify justify
                                           :direction direction
                                           :align align
                                           :background-color background-color
                                           :padding padding
                                           :width width
                                           :wrap wrap
                                           :overflowed overflowed
                                           :grow grow
                                           :text-align text-align)
                    classes)
           :style style
           :id    id}
     children]))

(defn get-initial-state-fn [this]
  #js {:id                     (or (.. this -props -id) (str (random-uuid)))
       :isMounted              false
       :overflowed             false
       :isListeningForOverflow false
       :setOverflowedFn        #(.setOverflowed this this)})

(defn mount-fn [this]
  (assoc-component-state this -isMounted true)
  (if (and (= (.. this -props -wrap) "rigid") (not (.. this -state -isListeningForOverflow)))
    (do (.setOverflowed this this)
        (-> js/window (.addEventListener "resize" (.. this -state -setOverflowedFn)))
        (set! (.. this -state -isListeningForOverflow) true))))

(defn set-overflowed [this]
  (let [is-mounted (.. this -state -isMounted)
        id (or (.. this -props -id) (.. this -state -id))

        overflowed (and id is-mounted (let [el (.getElementById js/document id)]
                                        (println (.-clientWidth el))
                                        (println (.-scrollWidth el))
                                        (< (.-clientWidth el)
                                           (.-scrollWidth el))))]
    (assoc-component-state this -overflowed (boolean overflowed))))

(defn unmmount-fn [this]
  (-> js/window (.removeEventListener "resize" (.. this -state -setOverflowedFn))))

(defn update-fn [this _ _ _]
  (cond
    (and (= (.. this -props -wrap) "rigid") (not (.. this -state -isListeningForOverflow)))
    (do
      (-> js/window (.addEventListener "resize" (.. this -state -setOverflowedFn)))
      (set! (.. this -state -isListeningForOverflow) true))
    (and (not= (.. this -props -wrap) "rigid") (.. this -state -isListeningForOverflow))
    (do
      (-> js/window (.removeEventListener "resize" (.. this -state -setOverflowedFn)))
      (set! (.. this -state -isListeningForOverflow) false))))



(def flexbox (r/create-class {:display-name           :flexbox
                              :render                 render-fn
                              :setOverflowed          set-overflowed
                              :get-initial-state      get-initial-state-fn
                              :component-will-unmount unmmount-fn
                              :component-did-mount    mount-fn
                              :component-did-update   update-fn}))