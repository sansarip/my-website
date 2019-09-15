(ns my-website.components.flexbox
  (:require [reagent.core :as r]
            [my-website.utilities :refer [deep-merge word-concat omit-nil-keyword-args]]
            [my-website.styles :refer [color-palette]]
            [spade.core :refer [defclass]]))

(defclass flexbox-class [& {:keys [align justify direction padding width wrap overflowed text-align grow]
                            :or   {align      "start"
                                   justify    "between"
                                   wrap       "wrap"
                                   direction  "row"
                                   padding    "0"
                                   width      "auto"
                                   overflowed false
                                   text-align "start"
                                   grow false}}]
          {:display         "flex"
           :flex-wrap       (cond (= wrap "rigid") "nowrap"
                                  :else wrap)
           :padding-left    padding
           :padding-right   padding
           :flex            (str (if grow "1 0" "0 1")
                                 " "
                                 width)
           :align-items     (cond (= align "start") "flex-start"
                                  (= align "end") "flex-end"
                                  :else align)
           :align-content   "space-around"
           :text-align      text-align
           :justify-content (cond (= justify "between") "space-between"
                                  (= justify "around") "space-around"
                                  (= justify "evenly") "space-evenly"
                                  (= justify "start") "flex-start"
                                  (= justify "end") "flex-end"
                                  :else justify)
           :flex-direction  (cond (and (= wrap "rigid") overflowed) "column"
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
        overflowed (.. this -state -overflowed)
        wrap (.. this -props -wrap)
        direction (.. this -props -direction)]
    [:div {:class (word-concat
                    (omit-nil-keyword-args flexbox-class
                                           :justify justify
                                           :direction direction
                                           :align align
                                           :padding padding
                                           :width width
                                           :wrap wrap
                                           :overflowed overflowed
                                           :grow grow
                                           :text-align text-align)
                    classes)
           :style style
           :id    id
           :width width
           :align align}
     children]))

(defn get-initial-state-fn [this]
  #js {:id              (or (.. this -props -id) (str (random-uuid)))
       :isMounted       false
       :overflowed      false
       :setOverflowedFn #(.setOverflowed this this)})

(defn mount-fn [this]
  (set! (.. this -state -isMounted) true)
  (-> this (.setState (.. this -state)))
  (if (.. this -props -rigid)
    (do (.setOverflowed this this)
        (-> js/window (.addEventListener "resize" (.. this -state -setOverflowedFn))))))

(defn set-overflowed [this]
  (let [is-mounted (.. this -state -isMounted)
        id (or (.. this -props -id) (.. this -state -id))

        overflowed (and id is-mounted (let [el (.getElementById js/document id)]
                                        (< (.-clientWidth el)
                                           (.-scrollWidth el))))]
    (set! (.. this -state -overflowed) (boolean overflowed))
    (-> this (.setState (.. this -state)))))

(defn unmmount-fn [this]
  (-> js/window (.removeEventListener "resize" (.. this -state -setOverflowedFn))))

(defn update-fn [this _ _ _]
  (if (.. this -props -rigid)
    (do (-> js/window (.removeEventListener "resize" (.. this -state -setOverflowedFn)))
        (-> js/window (.addEventListener "resize" (.. this -state -setOverflowedFn))))
    (-> js/window (.removeEventListener "resize" (.. this -state -setOverflowedFn)))))



(def flexbox (r/create-class {:display-name           :flexbox
                              :render                 render-fn
                              :setOverflowed          set-overflowed
                              :get-initial-state      get-initial-state-fn
                              :component-will-unmount unmmount-fn
                              :component-did-mount    mount-fn
                              :component-did-update   update-fn}))