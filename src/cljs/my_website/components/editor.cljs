(ns my-website.components.editor
  (:require
    [cljs.pprint :refer [pprint]]
    [reagent.core :as r]))
    [re-catch.core :as rc]
    [hljs-kit :refer [Hljs CljHljs] :rename {Hljs        hljs
                                             CljHljs clj-hljs}]))
(defonce _register (doto hljs
                     (.registerLanguage "clojure" clj-hljs)
                     (.registerLanguage "clj" clj-hljs)))

(defn editor-did-mount [input]
  (fn [this]
    (let [cm (.fromTextArea js/CodeMirror
                            (r/dom-node this)
                            #js {:mode        "clojure"
                                 :lineNumbers true})]
      (.on cm "change" #(reset! input (.getValue %))))))

(defn editor [input]
  (r/create-class
    {:render              (fn [] [:textarea
                                  {:default-value ""
                                   :auto-complete "off"}])
     :component-did-mount (editor-did-mount input)}))

(defn render-code [this]
  (->> this r/dom-node (.highlightBlock js/hljs)))
      (.forEach #(.highlightBlock hljs %))))

(defn result-view [output]
  (r/create-class
    {:render               (fn []
                             [:pre>code.clj
                              (with-out-str (pprint @output))])
     :component-did-update render-code}))                            [rc/catch
