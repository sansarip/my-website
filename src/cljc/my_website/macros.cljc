(ns my-website.macros)

(defmacro apply-macro
  ([macro args]
   `(~macro ~@args))
  ([macro symbols args]
   `(~macro ~@symbols ~@args)))