(ns my-website.macros)

(defmacro apply-macro
  ([macro args]
   `(~macro ~@args))
  ([macro symbols args]
   `(~macro ~@symbols ~@args)))

(defmacro assoc-component-state [component key value]
  `(do (set! (.. ~component -state ~key) ~value)
       (-> ~component (.setState (.. ~component -state)))))

(defmacro get-component-prop [component key]
  `(.. ~component -prop ~key))