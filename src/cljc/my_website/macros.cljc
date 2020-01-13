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

(defmacro defn+
  "Allows for better docstrings! Pass in as many strings as you want!\n\nExample: ```clojure\n(defn+ foo \n \"string1\" \n \"string2\" \n [a b] \n (+ a b))```"
  [name & rest]
  (let [docstr (apply str (-> (take-while string? rest)
                              (or "")
                              vec))
        args (or (some #(when (vector? %) %) rest) [])
        body (last rest)]
    `(defn ~name ~docstr ~args ~body)))
