(ns my-website.cards.components.animate
  (:require
    [anime]
    [debounce]
    [devcards.core :refer-macros [defcard-rg]]
    [my-website.components.animate :refer [animate]]
    [my-website.components.icon :refer [icon]]
    [my-website.components.menuitem :refer [menuitem]]
    [react-scroll-wheel-handler]
    [reagent.core :refer [with-let as-element atom] :rename {atom ratom}]))

(defcard-rg anime
            "Basic animation example using an icon and anime.js"
            (fn [state]
              (with-let [_interval-fn (.setInterval js/window #(swap! state inc) 1000)]
                        [:div
                         [:p @state
                          [:> icon {:size      :huge
                                    :strength  "strong"
                                    :icon-name (if (> @state 10) "hand-spock" "hand-peace")
                                    :id        "coocoo"
                                    :onClick   #(anime (clj->js {:targets    ["#coocoo"]
                                                                 :translateX 250
                                                                 :direction  "alternate"
                                                                 :rotate     "1turn"
                                                                 :duration   2000}))}]]]))
            (ratom 0))

(defcard-rg animate-1
            "Basic animation example using an icon and animate component"
            (fn [state]
              [:div [:> animate {:anime-props {:translateX 250
                                               :rotate     "1turn"
                                               :duration   2000
                                               :autoplay   @state}}
                     [:> icon {:size      :huge
                               :strength  :strong
                               :icon-name "hand-spock"}]]
               [:button {:on-click #(reset! state (not @state))} "Click to Start!"]])
            (ratom false))

(defcard-rg animate-2
            "Pausing an animate component"
            (fn [state]
              [:div
               [:> animate {:anime-props {:loop     true
                                          :rotate   "1turn"
                                          :duration 1000}
                            :pause       @state
                            :play        (not @state)}
                [:> icon {:size      :huge
                          :strength  :strong
                          :icon-name "hand-spock"}]]
               [:> menuitem {:textAlign "center"
                             :strong    true
                             :on-click  #(reset! state (not @state))
                             :fontSize  "large"} "Pause/Resume"]])
            (ratom false))

(defcard-rg animate-3
            "Starting animation after a wheel stop"
            (fn [state]
              (let [{:keys [enabled triggered timeout]} @state
                    scroll-fn (debounce (fn [event]
                                          (when (> (.-deltaY event) 0)
                                            (.clearTimeout js/window timeout)
                                            (swap! state assoc :timeout
                                                   (js/setTimeout
                                                     (swap! state assoc :triggered true)
                                                     100))))
                                        100)]
                [:div
                 [:> animate {:anime-props      {:targets    ["#coocoo-4"]
                                                 :translateY {:value    [-100 0]
                                                              :duration 1000
                                                              :easing   "easeOutBounce"}
                                                 :opacity    {:value    [0 1]
                                                              :duration 1250
                                                              :easing   "linear"}
                                                 :complete   #(swap! state assoc :triggered false)
                                                 :autoplay   false}
                              :staticAnimeProps [:complete]
                              :play             (and enabled triggered)}
                  [:> icon {:size      :huge
                            :id        "coocoo-4"
                            :strength  :strong
                            :icon-name "hand-spock"}]]
                 [:> menuitem {:textAlign "center"
                               :strong    true
                               :on-click  (fn [_]
                                            (swap! state assoc :enabled (not enabled))
                                            (if (not enabled)
                                              (.addEventListener js/window
                                                                 "wheel"
                                                                 scroll-fn)
                                              (do
                                                (swap! state assoc :triggered false)
                                                (.removeEventListener js/window "wheel" scroll-fn))))
                               :fontSize  "large"} (str (if enabled "Disable"
                                                                    "Enable")
                                                        " Scrolling-Trigger")]]))
            (ratom {:enabled false}
                   :timeout nil
                   :triggered false))

(defcard-rg animate-4
            "Starting animation after a swipe or scroll"
            (fn [state]
              (let [{:keys [enabled triggered timeout]} @state
                    scroll-fn (debounce (fn [_]
                                          (.clearTimeout js/window timeout)
                                          (swap! state assoc
                                                 :timeout
                                                 (js/setTimeout
                                                   (swap! state assoc :triggered true)
                                                   100)))
                                        60)]
                [:> react-scroll-wheel-handler {:downHandler scroll-fn}
                 [:> animate {:anime-props      {:targets    ["#coocoo-5"]
                                                 :translateY {:value    [-100 0]
                                                              :duration 1000
                                                              :easing   "easeOutBounce"}
                                                 :opacity    {:value    [0 1]
                                                              :duration 1250
                                                              :easing   "linear"}
                                                 :complete   #(swap! state assoc :triggered false)
                                                 :autoplay   false}
                              :staticAnimeProps [:complete]
                              :play             (and enabled triggered)}
                  [:> icon {:size      :huge
                            :id        "coocoo-5"
                            :strength  :strong
                            :icon-name "hand-spock"}]]
                 [:> menuitem {:textAlign "center"
                               :strong    true
                               :on-click  (fn [_]
                                            (swap! state assoc :enabled (not enabled))
                                            (if (not enabled)
                                              (swap! state assoc :triggered false)))
                               :fontSize  "large"} (str (if enabled "Disable"
                                                                    "Enable")
                                                        " Scrolling-Trigger")]]))
            (ratom {:enabled   false
                    :timeout   nil
                    :triggered false}))
