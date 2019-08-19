(ns my-website.utilities)

(defn word-concat [& words] (clojure.string/trim (reduce #(str % " " %2) "" words)))