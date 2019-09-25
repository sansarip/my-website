(ns my-website.utilities
  (:require [my-website.styles :refer [color-palette]]
            [cljs.spec.alpha :as s]))

(defn word-concat [& words] (clojure.string/trim
                              (reduce #(str % " " (if (coll? %2)
                                                    (apply word-concat %2)
                                                    %2)) "" words)))

(defn deep-merge [a b]
  (merge-with (fn [x y]
                (cond (map? y) (deep-merge x y)
                      (vector? y) (concat x y)
                      :else y))
              a b))

(defn dark-background [& children] (into
                                     [:div {:style {:background-color (:primary color-palette)}}]
                                     children))

(defn map->css-attribute-selectors [m elem attribute css-attribute]
  (map (fn [tuple] (let [key (first tuple)
                         val (second tuple)]
                     [(str (if (keyword? elem) (name elem) elem)
                           "["
                           (if (keyword? attribute) (name attribute) attribute)
                           "="
                           (if (keyword? key) (name key) key)
                           :backgroundColor (:primary color-palette)"]")
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

(s/def ::parent vector?)
(s/def ::children vector?)

(s/fdef wrap-each-child
        :args (s/and #(s/valid? ::parent (:parent %)) #(s/valid? ::children (:children %)))
        :ret vector?)
