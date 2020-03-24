(ns my-website.utilities
  (:require
    [cljs.js :refer [empty-state eval js-eval]]
    [cljs.spec.alpha :as s]
    [cljs.tools.reader :refer [read-string]]
    [clojure.string :as str]
    [my-website.styles :refer [color-palette]]))

(defn eval-str [s]
  (eval
    (empty-state)
    (read-string (str "(do " s ")"))
    {:eval       js-eval
     :source-map true
     :context    :expr}
    (fn [result] result)))

(defn word-concat [& words] (clojure.string/trim
                              (reduce #(str % " " (if (coll? %2)
                                                    (apply word-concat %2)
                                                    %2)) "" words)))

(defn deep-merge
  ([a b]
   (merge-with (fn [x y]
                 (cond (map? y) (deep-merge x y)
                       (vector? y) (concat x y)
                       :else y))
               a b))
  ([a b & rest]
   (let [all (into [a b] rest)]
     (reduce #(deep-merge % %2) {} all))))


(defn dark-background [& children] (into
                                     [:div {:style {:background-color (:primary color-palette)
                                                    :width            "100%"
                                                    :height           "100%"}}]
                                     children))

(s/def ::hiccup? (s/and vector? #(-> % first keyword?)))

(defn hiccup? [v]
  (s/valid? ::hiccup? v))

(defn seq->css-grid-areas [areas]
  (word-concat
    (map #(-> (mapv name %)
              (update 0 (partial str "\""))
              (update (dec (count %)) (fn [l](str l "\""))))
         (js->clj areas))))

(defn map->css-attribute-selectors [m elem attribute css-attribute]
  (map (fn [tuple] (let [key (first tuple)
                         val (second tuple)]
                     [(str (if (keyword? elem) (name elem) elem)
                           "["
                           (if (keyword? attribute) (name attribute) attribute)
                           "="
                           (if (keyword? key) (name key) key)
                           :backgroundColor (:primary color-palette) "]")
                      {css-attribute val}])) m))

(defn seq-of?
  "validates whether all elements of sequence pass the given check"
  [check sq]
  (= (count (filter #(-> % check not) sq)) 0))

(defn wrap-each-child [parent children]
  "encapsulates each child with a given parent hiccup vector returning a sequence

  cases for different parent types:

  `fn`       must return a valid hiccup vector
  `string`   must correspond to a valid hiccup html element e.g. \"h1\"
  `keyword`  must correspond to a valid hiccup html element e.g. :h1
  `vector`   must be a valid hiccup vector"
  (map (fn [child] (cond (fn? parent) (conj (parent) child)
                         (string? parent) (conj [(keyword parent)] child)
                         (keyword? parent) (conj [parent] child)
                         (vector? parent) (conj parent child)))
       children))

(defn wrap-all-children
  "returns a hiccup form with the given `children` being incorporated into the given `parent` element

  cases for different parent types:

  `fn`       must return a valid hiccup vector
  `string`   must correspond to a valid hiccup html element e.g. \"h1\"
  `keyword`  must correspond to a valid hiccup html element e.g. :h1
  `vector`   must be a valid hiccup vector"
  [parent & children]
  (cond
    (fn? parent) (into (parent) children)
    (string? parent) (into [(keyword parent)] children)
    (keyword? parent) (into [parent] children)
    (vector? parent) (into parent children)))

(defn omit-nil-keyword-args [op & args]
  (let [args-map (apply hash-map args)
        only-non-nil (filter #(not (nil? (get % 1))) args-map)]
    (apply op (flatten only-non-nil))))

(def css-translator {:start   "flex-start"
                     :end     "flex-end"
                     :around  "space-around"
                     :between "space-between"
                     :evenly  "space-evenly"
                     :reverse "wrap-reverse"})

(defn on-unit [op unit & args]
  (let [to-float (js/parseFloat unit)
        unit-type (str/replace unit (str to-float) "")]
    (if args
      (str (apply op (into [to-float] args)) unit-type)
      (str (op to-float) unit-type))))

(defn scroll-to-top []
  (.scrollTo js/window 0 0))

(s/def ::parent vector?)
(s/def ::children vector?)

(s/fdef wrap-each-child
        :args (s/and #(s/valid? ::parent (:parent %)) #(s/valid? ::children (:children %)))
        :ret vector?)
