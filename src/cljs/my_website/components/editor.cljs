(ns my-website.components.editor
  (:require
    [my-website.macros :refer-macros [assoc-component-state]]
    [cljs.pprint :refer [pprint]]
    [re-catch.core :as rc]
    [reagent.core :refer [create-class dom-node with-let]]
    [hljs-kit :refer [Hljs CljHljs] :rename {Hljs    hljs
                                             CljHljs clj-hljs}]))

(defonce _register (doto hljs
                     (.registerLanguage "clojure" clj-hljs)
                     (.registerLanguage "clj" clj-hljs)))

(defn editor-did-mount [input]
  (fn [this]
    (let [cm (.fromTextArea js/CodeMirror
                            (dom-node this)
                            #js {:mode        "clojure"
                                 :lineNumbers true})
          on-change (.. this -props -onChange)]
      (.on cm "change" #(let [val (.getValue %)]
                          (try
                            (do (reset! input val)
                                (if on-change (on-change val)))
                            (catch js/Error _e)))))))

(defn editor [input]
  (create-class
    {:render              (fn [this]
                            (let [on-change (.. this -props -onChange)]
                              [:textarea
                               {:default-value ""
                                :on-change     on-change
                                :auto-complete "off"}]))
     :component-did-mount (editor-did-mount input)}))

(defn render-code [_this]
  (-> js/document
      (.querySelectorAll "pre code")
      (.forEach #(.highlightBlock hljs %))))

(defn result-view [output]
  (create-class
    {:render              (fn [_this]
                            [rc/catch
                             (condp apply [output]
                               ; render as hiccup if
                               ;; hiccup vector?
                               #(and (vector? %) (keyword? (first %))) output
                               ;; function?
                               fn? [output]
                               ; everything else is treated as Clojure code
                               [:pre>code.clj
                                (with-out-str (pprint output))])])
     :component-did-mount render-code}))

(def interactive-coder (create-class {:render (fn [this]
                                                [])}))
