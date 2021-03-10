(ns my-website.components.editor
  (:require
    [my-website.macros :refer-macros [assoc-component-state]]
    [my-website.styles :refer [color-palette]]
    [cljs.pprint :refer [pprint]]
    [re-catch.core :as rc]
    [reagent.core :refer [dom-node create-class with-let]]
    [cljs.spec.alpha :as s]
    [my-website.utilities :refer [hiccup?]]
    [spade.core :refer [defglobal defclass defkeyframes]]
    [my-website.components.icon :refer [icon]]
    [hljs-kit :refer [Hljs CljHljs] :rename {Hljs    hljs
                                             CljHljs clj-hljs}]))

(defonce _register (doto hljs
                     (.registerLanguage "clojure" clj-hljs)
                     (.registerLanguage "clj" clj-hljs)))

(defglobal editor-class []
           [:.CodeMirror {:background-color (:secondary color-palette)
                          :color            (:primary color-palette)
                          :border-radius    "4px"
                          :border           (str "2px solid " (:primary color-palette))}]
           [:.CodeMirror-gutters {:background-color (:primary color-palette)}]
           [:.CodeMirror-linenumber {:color :white}]
           [:.CodeMirror-line>span {:background-color (:quaternary color-palette)
                                    :border-radius    :2px}])

(defn editor-did-mount [input]
  (fn [this]
    (let [cm (.fromTextArea js/CodeMirror
                            (dom-node this)
                            #js {:mode              "clojure"
                                 :lineNumbers       true
                                 :autoCloseBrackets true
                                 :matchBrackets     true})
          on-change (.. this -props -onChange)]
      (.on cm "change" #(let [val (.getValue %)]
                          (try
                            (do (reset! input val)
                                (if on-change (on-change val)))
                            (catch js/Error _e)))))))

(defn editor [input default-value]
  (create-class
    {:render              (fn [this]
                            (let [on-change (.. this -props -onChange)]
                              [:textarea
                               {:default-value default-value
                                :on-change     on-change
                                :auto-complete "off"}]))
     :component-did-mount (editor-did-mount input)}))

(defn render-code [_this]
  (-> js/document
      (.querySelectorAll "pre code")
      (.forEach #(.highlightBlock hljs %))))

(defclass result-view-class []
          {:background-color      (:secondary color-palette)
           :padding               :1em
           :display               :grid
           :grid-template-columns "auto 1fr"
           :grid-template-rows    "auto"
           :grid-column-gap       :.5em
           :border-radius         "4px"
           :border                (str "2px solid " (:primary color-palette))
           :margin-top            :1em}
          [:.prompt {:align-self :center}]
          [:.hiccup {:background-color :white
                     :border-radius    "4px"
                     :padding          :.5em}]
          [:.other {:background-color (:quaternary color-palette)
                    :border-radius    "2px"}])
(defn result-view [output]
  (create-class
    {:render              (fn [_this]
                            [rc/catch
                             [:div {:class (result-view-class)}
                              [:> icon {:icon-name     "chevron-right"
                                        :strength      "strong"
                                        :extra-classes "prompt"}]
                              (condp apply [output]
                                ;; render as hiccup if...
                                ;; hiccup vector?
                                hiccup? [:div.hiccup [(fn [] output)]] ; this fn wrapping is necessary to prevent errors
                                ;; function?
                                fn? [:div.hiccup [output]]
                                ;; everything else is treated as Clojure code
                                [:pre>code.clj
                                 [:span.other
                                  (with-out-str (pprint output))]])]])
     :component-did-mount render-code}))

(def interactive-coder (create-class {:render (fn [this]
                                                [])}))
